package sg.edu.sportsschool.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.Entities.Rule;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Repositories.RuleRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    public ResponseEntity<JSONBody> getAllRules() {
        try {
            List<Rule> rules = new ArrayList<>();
            ruleRepository.findAll().forEach(rules::add);

            JSONWithData<List<Rule>> body = new JSONWithData<>(200, rules);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get all business rules");

        }
    }

    public ResponseEntity<JSONBody> getRule(String name) {
        try {
            Rule r = ruleRepository.findById(name).get();
            if (r == null)
                throw new InternalServerException("Rule" + name + " not found in database.");
    
            JSONWithData<Rule> body = new JSONWithData<>(200, r);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get all business rules");

        }
    }


    public ResponseEntity<JSONBody> updateRule(String name, String value) {
        try {
            Rule r = ruleRepository.findById(name).get();
            if (r == null)
                throw new InternalServerException("Rule" + name + " not found in database.");

            r.setValue(value);
            JSONWithData<Rule> body = new JSONWithData<>(200, r);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get business rule " + name);

        }
    }

    public ResponseEntity<JSONBody> addRule(String name, String value) {
        try {
            Rule r = new Rule(name, value);
            ruleRepository.save(r);
            JSONWithData<Rule> body = new JSONWithData<>(200, r);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to add rule " + name + " into database");

        }
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public int getMaxLoansPerMonth() {
        try {
            Rule r = ruleRepository.findById("maxLoansPerMonth").get();
            return Integer.parseInt(r.getValue());
            
        } catch (Exception e) {
            return -1;
        }
    }
}
