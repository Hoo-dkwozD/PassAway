package sg.edu.sportsschool.record.Repositories;

import org.springframework.data.repository.CrudRepository;

import sg.edu.sportsschool.record.Entities.Rule;

public interface RuleRepository extends CrudRepository<Rule, String> {
}
