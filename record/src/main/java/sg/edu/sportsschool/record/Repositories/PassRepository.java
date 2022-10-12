package sg.edu.sportsschool.record.Repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import sg.edu.sportsschool.record.Entities.Pass;

public interface PassRepository extends CrudRepository<Pass, String> {
    @Async
    @Query("select p from Pass p where p.attraction.attractionId = :aId")
    Set<Pass> findAllPassesByAttractionId(Integer aId);
}
