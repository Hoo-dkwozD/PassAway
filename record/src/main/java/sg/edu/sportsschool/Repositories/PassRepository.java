package sg.edu.sportsschool.Repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.sportsschool.Entities.Pass;

public interface PassRepository extends JpaRepository<Pass, String> {
    @Query("SELECT p FROM Pass p WHERE p.attraction.attractionId = :aId AND p.isLost = false")
    Set<Pass> findAllPassesByAttrId(Integer aId);

    @Query("SELECT p FROM Pass p WHERE p.attraction.attractionId = :aId AND p.passId = :passId")
    Set<Pass> findAllPassesByAttrIdAndPassId(Integer aId, String passId);
}
