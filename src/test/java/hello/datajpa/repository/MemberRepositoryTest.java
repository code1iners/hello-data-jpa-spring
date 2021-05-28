package hello.datajpa.repository;

import hello.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void 저장_및_조회() throws Exception {
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

}
