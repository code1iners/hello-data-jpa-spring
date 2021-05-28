package hello.datajpa.repository;

import hello.datajpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false) // note. Rollback disable.
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    /**
     * <h3>Save.</h3>
     * <p>Save member test case.</p>
     */
    @Test
    void 저장_및_가져오기() {
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

}