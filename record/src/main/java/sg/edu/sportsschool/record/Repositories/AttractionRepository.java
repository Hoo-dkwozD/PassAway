package sg.edu.sportsschool.record.Repositories;

import org.springframework.data.repository.CrudRepository;

import sg.edu.sportsschool.record.Entities.Attraction;

public interface AttractionRepository extends CrudRepository<Attraction, Integer> {
    
}
