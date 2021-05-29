package hello.datajpa.repository.member;

import hello.datajpa.dto.MemberDto;
import hello.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * <p>Included Query methods.</p>
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    /**
     * <h3>Find by username.</h3>
     * <p>Find member by member's username with NamedQuery.</p>
     * <p>Able to comment process @Query...</p>
     * <p>in case of Same name which method name with NamedQuery's name.</p>
     * <p>Not recommended way because write in entity object directly.</p>
     */
//    @Query(name = "Member.findByUsername") // note. QueryNamed's name == method name
    List<Member> findByUsername(@Param("username") String username);


    /**
     * <h3>Find user.</h3>
     * <p>Find user by member entity with @Query.</p>
     * <p>Recommended way because write in repository object directly.</p>
     */
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(
              @Param("username") String username
            , @Param("age") int age
    );

    @Query("select m.username from Member m")
    List<String> findUsernameAsList();

    @Query("select new hello.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberByDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    List<Member> findAsListByUsername(String username);
    Member findAsSingleByUsername(String username);
    Optional<Member> findAsOptionalByUsername(String username);



}
