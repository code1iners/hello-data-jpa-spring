package hello.datajpa.repository.member;

import hello.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false) // note. Rollback disable.
class MemberJpaRepositoryTest {

    @PersistenceContext EntityManager em;
    @Autowired MemberJpaRepository memberJpaRepository;

    /**
     * <h3>Save.</h3>
     * <p>Save member test case.</p>
     */
    @Test
    void saveWithFind() {
        // note. given
        Member member = new Member("member1");

        // note. when
        Member savedMember = memberJpaRepository.save(member);
        Member foundMember = memberJpaRepository.find(savedMember.getId());

        // note. then
        assertThat(savedMember.getId()).isEqualTo(foundMember.getId());
        assertThat(savedMember).isSameAs(foundMember);
        assertThat(savedMember).isInstanceOf(Member.class);
        assertThat(savedMember).isEqualTo(member);

        assertThat(foundMember).isInstanceOf(Member.class);
        assertThat(foundMember).isEqualTo(member);
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getUsername()).isEqualTo(member.getUsername());
    }

    @Test
    public void findById() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // when
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        Member foundMember2 = memberJpaRepository.findById(member2.getId()).get();
        Member foundMember1 = memberJpaRepository.findById(member1.getId()).get();

        // then
        assertThat(foundMember1).isEqualTo(member1);
        assertThat(foundMember2).isEqualTo(member2);
    }

    @Test
    public void findAll() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // when
        List<Member> members = memberJpaRepository.findAll();

        // then
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    public void count() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // when
        long membersCount = memberJpaRepository.count();

        // then
        assertThat(membersCount).isEqualTo(2);
    }

    @Test
    public void delete() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);

        // when
        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        long membersCount = memberJpaRepository.count();

        // then
        assertThat(membersCount).isEqualTo(0);
    }

    @Test
//    @Rollback(false)
    public void update() throws Exception {
        // given
        Member member1 = new Member("member1");
        memberJpaRepository.save(member1);

        // when
        member1.setUsername("member11");

        em.flush();
        em.clear();

        Member foundMember = memberJpaRepository.findById(member1.getId()).get();

        // then
        assertThat(foundMember.getUsername()).isEqualTo(member1.getUsername());
    }

    @Test
    public void findByUsernameAndAgeGreaterThen() throws Exception {
        // given
        Member member1 = new Member("member1", 10);
        Member member2 = new Member("member2", 20);

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        // when
        List<Member> foundMembers = memberJpaRepository.findByUsernameAndAgeGreaterThan("member1", 5);

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

        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        // when
        List<Member> foundMembers = memberJpaRepository.findByUsername(member1.getUsername());
        Member foundMember = foundMembers.get(0);

        // then
        assertThat(foundMember).isEqualTo(member1);
    }

    @Test
    public void paging() throws Exception {
        // given
        memberJpaRepository.save(new Member("member1", 10));
        memberJpaRepository.save(new Member("member2", 10));
        memberJpaRepository.save(new Member("member3", 10));
        memberJpaRepository.save(new Member("member4", 10));
        memberJpaRepository.save(new Member("member5", 10));
        memberJpaRepository.save(new Member("member6", 10));
        memberJpaRepository.save(new Member("member7", 10));
        memberJpaRepository.save(new Member("member8", 10));
        memberJpaRepository.save(new Member("member9", 10));
        memberJpaRepository.save(new Member("member10", 10));

        int age = 10;
        int offset = 1;
        int limit = 3;

        // when
        List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
        long totalCount = memberJpaRepository.totalCount(age);

        // then
        assertThat(members.size()).isEqualTo(3);
        assertThat(totalCount).isEqualTo(10);
    }

    @Test
    public void bulkAgePlus() throws Exception {
        // given
        memberJpaRepository.save(new Member("member1", 32));
        memberJpaRepository.save(new Member("member2", 17));
        memberJpaRepository.save(new Member("member3", 28));
        memberJpaRepository.save(new Member("member4", 30));
        memberJpaRepository.save(new Member("member5", 28));
        memberJpaRepository.save(new Member("member6", 24));
        memberJpaRepository.save(new Member("member7", 17));
        memberJpaRepository.save(new Member("member8", 19));
        memberJpaRepository.save(new Member("member9", 20));
        memberJpaRepository.save(new Member("member10", 10));

        // when
        int resultCount = memberJpaRepository.bulkAgePlus(20);

        // then
        assertThat(resultCount).isEqualTo(6);
    }

}