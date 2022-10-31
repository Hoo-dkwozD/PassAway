package sg.edu.sportsschool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.sportsschool.Entities.Attraction;

public interface AttractionRepository extends JpaRepository<Attraction, Integer> {

}
