package hello.datajpa.repository.member;

import hello.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<Member> findMembersCustom() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
