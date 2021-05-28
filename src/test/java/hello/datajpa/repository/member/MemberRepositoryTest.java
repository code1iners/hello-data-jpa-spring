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
//@Rollback(false)
public class MemberRepositoryTest {

    @PersistenceContext EntityManager em;
    @Autowired MemberRepository memberRepository;

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
}
