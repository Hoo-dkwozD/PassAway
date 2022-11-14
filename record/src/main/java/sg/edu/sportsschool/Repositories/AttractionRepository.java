package sg.edu.sportsschool.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.sportsschool.Entities.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {
    List<Attraction> findAll();

    Optional<Attraction> findById(Integer attractionId);
}
