package sg.edu.sportsschool.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.sportsschool.Entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer>{
    List<Staff> findAll();

    Staff findByStaffId(Integer staffId);

    Staff findByEmail(String email);

    @Query(value="""
            SELECT * FROM STAFF WHERE role=:role
        """, nativeQuery = true)
    List<Staff> findByRole(Integer role); // 0 - Borrower, 1 - Admin, 2 - GOP
}
