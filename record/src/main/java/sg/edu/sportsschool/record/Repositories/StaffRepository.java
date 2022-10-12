package sg.edu.sportsschool.record.Repositories;

import org.springframework.data.repository.CrudRepository;

import sg.edu.sportsschool.record.Entities.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer>{
    
}
