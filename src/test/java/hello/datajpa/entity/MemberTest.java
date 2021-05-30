package hello.datajpa.entity;

import hello.datajpa.repository.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;


@SpringBootTest
@Transactional
//@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void entity() throws Exception {
        // given
        Team team1 = new Team("team1");
        Team team2 = new Team("team2");
        em.persist(team1);
        em.persist(team2);

        Member member1 = new Member("member1", 10, team1);
        Member member2 = new Member("member2", 12, team2);
        Member member3 = new Member("member3", 15, team1);
        Member member4 = new Member("member4", 16, team2);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        em.flush();
        em.clear();

        // when
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();

        // then
        for (Member member : members) {
            System.out.println("member = " + member + ", team = " + member.getTeam());
        }
    }

    @Test
    public void JpaEventBaseEntity() throws Exception {
        // given
        Member member = new Member("member1");
        memberRepository.save(member); // note. @PrePersist

        Thread.sleep(100);
        member.setUsername("member2");

        em.flush(); // note. @PreUpdate
        em.clear();

        // when
        Member foundMember = memberRepository.findById(member.getId()).get();

        // then
        System.out.println("foundMember = " + foundMember.getCreatedDate());
        System.out.println("foundMember = " + foundMember.getLastModifiedDate());
        System.out.println("foundMember = " + foundMember.getCreatedBy());
        System.out.println("foundMember = " + foundMember.getLastModifiedBy());
    }

}