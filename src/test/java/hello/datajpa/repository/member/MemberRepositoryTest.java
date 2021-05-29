package hello.datajpa.repository.member;

import hello.datajpa.dto.MemberDto;
import hello.datajpa.entity.Member;
import hello.datajpa.entity.Team;
import hello.datajpa.repository.team.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
public class MemberRepositoryTest {

    @PersistenceContext EntityManager em;
    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    public void saveWithFind() throws Exception {
        // given
        Member member = new Member("member1");

        // when
        Member savedMember = memberRepository.save(member);
        Member foundMember = memberRepository.findById(savedMember.getId()).get();

        // then
        assertThat(member.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(member.getId()).isEqualTo(savedMember.getId());
        assertThat(member).isEqualTo(savedMember);
        assertThat(savedMember).isInstanceOf(Member.class);
        assertThat(foundMember).isSameAs(savedMember);
        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getUsername()).isEqualTo(savedMember.getUsername());
    }

    @Test
    public void findById() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // when
        memberRepository.save(member1);
        memberRepository.save(member2);

        Member foundMember2 = memberRepository.findById(member2.getId()).get();
        Member foundMember1 = memberRepository.findById(member1.getId()).get();

        // then
        assertThat(foundMember1).isEqualTo(member1);
        assertThat(foundMember2).isEqualTo(member2);
    }

    @Test
    public void findAll() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    public void count() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        long membersCount = memberRepository.count();

        // then
        assertThat(membersCount).isEqualTo(2);
    }

    @Test
    public void delete() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        memberRepository.delete(member1);
        memberRepository.delete(member2);
        long membersCount = memberRepository.count();

        // then
        assertThat(membersCount).isEqualTo(0);
    }

    @Test
//    @Rollback(false)
    public void update() throws Exception {
        // given
        Member member1 = new Member("member1");
        memberRepository.save(member1);

        // when
        member1.setUsername("member11");

        em.flush();
        em.clear();

        Member foundMember = memberRepository.findById(member1.getId()).get();

        // then
        assertThat(foundMember.getUsername()).isEqualTo(member1.getUsername());
    }

    @Test
    public void findTop3HelloBy() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> foundMembers = memberRepository.findTop3HelloBy();

        // then
        for (Member foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember);
        }
    }


    @Test
    public void findByUsernameAndAgeGreaterThan() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        // when
        List<Member> foundMembers = memberRepository.findByUsernameAndAgeGreaterThan("member1", 5);

        // then
        assertThat(foundMembers.get(0).getUsername()).isEqualTo(member1.getUsername());
        assertThat(foundMembers.get(0).getAge()).isEqualTo(member1.getAge());
        assertThat(foundMembers.size()).isEqualTo(1);
    }

    /**
     * <h3>Named query.</h3>
     */
    @Test
    public void findByUsername() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);
        // when
        List<Member> foundMembers = memberRepository.findByUsername(member1.getUsername());
        Member foundMember = foundMembers.get(0);

        // then
        assertThat(foundMember).isEqualTo(member1);
    }

    /**
     * <h3>Find user.</h3>
     * <p>Find user as member entity with @Query.</p>
     */
    @Test
    public void findMember() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> foundMembers = memberRepository.findUser(member1.getUsername(), member1.getAge());
        Member foundMember = foundMembers.get(0);

        // then
        assertThat(foundMember).isEqualTo(member1);
    }

    @Test
    public void findUsernameAsList() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<String> usernames = memberRepository.findUsernameAsList();
        for (String username : usernames) {
            System.out.println("username = " + username);
        }
        String firstUsername = usernames.get(0);

        // then
        assertThat(firstUsername).isEqualTo(member1.getUsername());
    }

    /**
     * <h3>Find member by dto.</h3>
     * <p>Find member by dto object.</p>
     */
    @Test
    public void findMemberByDto() throws Exception {
        // given
        Team team1 = new Team("team1");
        teamRepository.save(team1);

        Member member1 = new Member("member1", 10);
        member1.setTeam(team1);
        memberRepository.save(member1);

        // when
        List<MemberDto> foundMembers = memberRepository.findMemberByDto();
        for (MemberDto foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember);
        }
        MemberDto foundMember = foundMembers.get(0);

        // then
        assertThat(foundMember.getUsername()).isEqualTo(member1.getUsername());
        assertThat(foundMember.getTeamName()).isEqualTo(member1.getTeam().getName());
    }

    @Test
    public void findByNames() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> foundMembers = memberRepository.findByNames(Arrays.asList("member1", "member2"));
        for (Member foundMember : foundMembers) {
            System.out.println("foundMember = " + foundMember);
        }
        Member foundMember = foundMembers.get(0);
        // then
        assertThat(foundMembers.size()).isEqualTo(2);
    }

    @Test
    public void types() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);
        memberRepository.save(member1);
        memberRepository.save(member2);

        // when
        List<Member> asList = memberRepository.findAsListByUsername("member1");
        Member asSingle = memberRepository.findAsSingleByUsername("member2");
        Optional<Member> asOptional = memberRepository.findAsOptionalByUsername("member1");

        // then
        System.out.println("asList = " + asList);
        System.out.println("asSingle = " + asSingle);
        System.out.println("asOptional = " + asOptional);
    }

    @Test
    public void paging() throws Exception {
        // given
        memberRepository.save(new Member("member1", 10));
        memberRepository.save(new Member("member2", 10));
        memberRepository.save(new Member("member3", 10));
        memberRepository.save(new Member("member4", 10));
        memberRepository.save(new Member("member5", 10));
        memberRepository.save(new Member("member6", 10));
        memberRepository.save(new Member("member7", 10));
        memberRepository.save(new Member("member8", 10));
        memberRepository.save(new Member("member9", 10));
        memberRepository.save(new Member("member10", 10));

        int age = 10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> page = memberRepository.findAsPageByAge(age, pageRequest);
        Slice<Member> slice = memberRepository.findAsSliceByAge(age, pageRequest);

        // note. Entity to DTO.
        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), m.getTeam().getName()));

        // then
        // note. Check page.
        System.out.println("totalElements = " + page.getTotalElements());
        for (Member member : page.getContent()) {
            System.out.println("member = " + member);
        }
        assertThat(page.getContent().size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(10);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(4);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();
        assertThat(page.hasPrevious()).isFalse();

        // note. Check slice.
        for (Member member : slice.getContent()) {
            System.out.println("member = " + member);
        }
        assertThat(slice.getContent().size()).isEqualTo(3);
        assertThat(slice.getNumber()).isEqualTo(0);
        assertThat(slice.isFirst()).isTrue();
        assertThat(slice.hasNext()).isTrue();
        assertThat(slice.hasPrevious()).isFalse();
    }

    @Test
    public void bulkPlusAge() throws Exception {
        // given
        memberRepository.save(new Member("member1", 32));
        memberRepository.save(new Member("member2", 17));
        memberRepository.save(new Member("member3", 28));
        memberRepository.save(new Member("member4", 30));
        memberRepository.save(new Member("member5", 28));
        memberRepository.save(new Member("member6", 24));
        memberRepository.save(new Member("member7", 17));
        memberRepository.save(new Member("member8", 19));
        memberRepository.save(new Member("member9", 20));
        memberRepository.save(new Member("member10", 10));

        // when
        int resultCount = memberRepository.bulkAgePlus(20);

        List<Member> result = memberRepository.findByUsername("member9");
        Member member = result.get(0);
        System.out.println("member = " + member);

        // then
        assertThat(resultCount).isEqualTo(6);
    }

}
