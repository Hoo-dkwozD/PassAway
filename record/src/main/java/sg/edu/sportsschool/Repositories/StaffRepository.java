package sg.edu.sportsschool.Repositories;

import org.springframework.data.repository.CrudRepository;

import sg.edu.sportsschool.Entities.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer>{
    
}
