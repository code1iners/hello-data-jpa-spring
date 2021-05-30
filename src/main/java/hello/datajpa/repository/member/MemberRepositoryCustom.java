package hello.datajpa.repository.member;

import hello.datajpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMembersCustom();
}
