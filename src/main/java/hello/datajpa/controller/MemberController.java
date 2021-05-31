package hello.datajpa.controller;

import hello.datajpa.dto.MemberDto;
import hello.datajpa.entity.Member;
import hello.datajpa.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member foundMember = memberRepository.findById(id).get();
        return foundMember.getUsername();
    }

    /**
     * <h3>Domain class converter.</h3>
     * <p>Extend web.</p>
     * <p>Not recommended way.</p>
     * <p>Use only lookup.</p>
     */
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    /**
     * <h3>List as page.</h3>
     * <p>find members as list with Page interface.</p>
     * <p>Able to use query string.</p>
     * <p>...?page=4&&size=3&&sort=id,desc</p>
     * <p>Setting by application.xml or @PageableDefault annotation</p>
     */
    @GetMapping("/members")
    public Page<MemberDto> listAsPage(
//            @Qualifier("member")
            @PageableDefault(size = 5) Pageable pageable
//            , @Qualifier("order") Pageable orderPageable
    ) {
        // note. other way.
//        PageRequest request = PageRequest.of(1, 2);
//        Page<Member> members = memberRepository.findAll(request);

        // note. basic way.
        Page<Member> members = memberRepository.findAll(pageable);
        return members.map(MemberDto::new);
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("member" + i, i));
        }
    }
}
