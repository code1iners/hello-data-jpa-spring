package hello.datajpa.controller;

import hello.datajpa.entity.Member;
import hello.datajpa.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
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
        System.out.println("id = " + id);
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

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("member1"));
    }
}
