package hello.datajpa.repository.team;

import hello.datajpa.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamJpaRepository {

    private final EntityManager em;

    /**
     * <h3>Save.</h3>
     * <p>Save team by entity.</p>
     */
    public Team save(Team team) {
        em.persist(team);
        return team;
    }

    /**
     * <h3>Find all.</h3>
     * <p>Find all teams by entity.</p>
     */
    public List<Team> findAll() {
        return em.createQuery("select t from Team t", Team.class)
                .getResultList();
    }

    /**
     * <h3>Delete.</h3>
     * <p>Delete team by team entity.</p>
     */
    public void delete(Team team) {
        em.remove(team);
    }

    /**
     * <h3>Find by id.</h3>
     * <p>Find team by team's id.</p>
     */
    public Optional<Team> findById(Long teamId) {
        Team foundTeam = em.find(Team.class, teamId);
        return Optional.ofNullable(foundTeam);
    }

    /**
     * <h3>Count.</h3>
     * <p>Get all teams count.</p>
     */
    public long count() {
        return em.createQuery("select count(t) from Team t", Long.class)
                .getSingleResult();
    }
}
