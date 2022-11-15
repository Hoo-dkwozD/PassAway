package sg.edu.sportsschool.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Helper.StaffRole;

public interface StaffRepository extends JpaRepository<Staff, Integer>{
    List<Staff> findAll();

    Staff findByStaffId(Integer staffId);

    Staff findByEmail(String email);

    List<Staff> findByRole(StaffRole roleId);
}
