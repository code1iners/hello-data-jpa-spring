package hello.datajpa.repository;

import hello.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberJpaRepository {

    private final EntityManager em;

    /**
     * <h3>Save.</h3>
     * <p>Save member by member entity.</p>
     */
    @Transactional
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    /**
     * <h3>Find.</h3>
     * <p>Find member by member's id.</p>
     */
    public Member find(Long memberId) {
        return em.find(Member.class, memberId);
    }
}
