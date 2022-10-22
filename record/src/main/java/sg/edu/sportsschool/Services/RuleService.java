package sg.edu.sportsschool.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.Entities.Rule;
import sg.edu.sportsschool.Repositories.RuleRepository;
import sg.edu.sportsschool.helper.CreateJSONResponse;
import sg.edu.sportsschool.helper.JSONBody;

@Service
public class RuleService {
    @Autowired
    private RuleRepository ruleRepository;

    public ResponseEntity<JSONBody> getAllRules() {
        CreateJSONResponse<List<Rule>> jsonResponse = new CreateJSONResponse<>();
        try {
            List<Rule> rules = new ArrayList<>();
            ruleRepository.findAll().forEach(rules::add);

            return jsonResponse.create(rules);

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to get all business rules");

        }
    }

    public ResponseEntity<JSONBody> getRule(String name) {
        CreateJSONResponse<Rule> jsonResponse = new CreateJSONResponse<>();
        try {
            Rule r = ruleRepository.findById(name).get();
            if (r == null)
                return jsonResponse.create(500, "Rule" + name + " not found in database.");
            
            return jsonResponse.create(r);

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to get all business rules");

        }
    }


    public ResponseEntity<JSONBody> updateRule(String name, String value) {
        CreateJSONResponse<Rule> jsonResponse = new CreateJSONResponse<>();
        try {
            Rule r = ruleRepository.findById(name).get();
            if (r == null)
                return jsonResponse.create(500, "Rule" + name + " not found in database.");

            r.setValue(value);

            return jsonResponse.create(r);

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to get business rule " + name);

        }
    }

    public ResponseEntity<JSONBody> addRule(String name, String value) {
        CreateJSONResponse<Rule> jsonResponse = new CreateJSONResponse<>();
        try {
            Rule r = new Rule(name, value);
            ruleRepository.save(r);
            return jsonResponse.create(r);

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to add rule " + name + " into database");

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
