package hello.datajpa.repository.member;

import hello.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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

    /**
     * <h3>Delete.</h3>
     * <p>Delete member by member's id.</p>
     */
    public void delete(Member member) {
        em.remove(member);
    }

    /**
     * <h3>Find all.</h3>
     * <p>Find all members as list with jpql.</p>
     * <p>Return member as entity.</p>
     */
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    /**
     * <h3>Find by id</h3>
     * <p>Find member by member's id.</p>
     * <p>Return member as Optional object.</p>
     */
    public Optional<Member> findById(Long memberId) {
        Member foundMember = em.find(Member.class, memberId);
        return Optional.ofNullable(foundMember);
    }

    /**
     * <h3>Count.</h3>
     * <p>Find all members count.</p>
     * <p>Return value as long type.</p>
     */
    public long count() {
        return em.createQuery("select count(m) from Member m", Long.class)
                .getSingleResult();
    }

    /**
     * <h3>Find user by member's username.</h3>
     * <p>Find user by member's username and age with query parameters.</p>
     */
    public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
        return em.createQuery("select m from Member m where m.username = :username and m.age > :age", Member.class)
                .setParameter("username", username)
                .setParameter("age", age)
                .getResultList();
    }
}
