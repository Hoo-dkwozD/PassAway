package sg.edu.sportsschool.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.sportsschool.Entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer>{
    List<Staff> findAll();

    Staff findByStaffId(Integer staffId);

    Staff findByEmail(String email);
}
