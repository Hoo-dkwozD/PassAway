package sg.edu.sportsschool.Repositories;

import sg.edu.sportsschool.Entities.AuthenticationToken;
import sg.edu.sportsschool.Entities.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// TODO Deprecation

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Integer> {
    AuthenticationToken findByStaff(Staff staff);

    AuthenticationToken findByToken(String token);
}
