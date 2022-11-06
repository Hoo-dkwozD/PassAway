package sg.edu.sportsschool.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.sportsschool.Entities.Auth;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    Auth findFirstByOrderByCreatedDateDesc();
}
