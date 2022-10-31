package sg.edu.sportsschool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.sportsschool.Entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer>{
    Staff findByEmail(String email);
}
