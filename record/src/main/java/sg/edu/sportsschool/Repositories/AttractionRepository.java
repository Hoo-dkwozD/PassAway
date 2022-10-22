package sg.edu.sportsschool.Repositories;

import org.springframework.data.repository.CrudRepository;

import sg.edu.sportsschool.Entities.Attraction;

public interface AttractionRepository extends CrudRepository<Attraction, Integer> {
    
}
