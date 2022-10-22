package sg.edu.sportsschool.Repositories;

import org.springframework.data.repository.CrudRepository;

import sg.edu.sportsschool.Entities.Rule;

public interface RuleRepository extends CrudRepository<Rule, String> {
}
