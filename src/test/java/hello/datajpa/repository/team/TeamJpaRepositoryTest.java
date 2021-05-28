package hello.datajpa.repository.team;

import hello.datajpa.entity.Member;
import hello.datajpa.repository.member.MemberJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(false)
class TeamJpaRepositoryTest {

    @Autowired TeamJpaRepository teamJpaRepository;

    @Test
    public void teamTest() throws Exception {
        // given

        // when

        // then
    }

}