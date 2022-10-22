package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.Services.RuleService;
import sg.edu.sportsschool.helper.JSONBody;

@CrossOrigin
@RestController
@RequestMapping("/rule")
public class RuleController {
    
    @Autowired
    private RuleService rService;

    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllRules() {
        return rService.getAllRules();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addRule(@RequestParam String name, @RequestParam String value) {
        return rService.addRule(name, value);
    }

    @GetMapping(path = "/maxLoansPerMonth")
    public ResponseEntity<JSONBody> getMaxLoansPerMonth() {
        return rService.getRule("maxLoansPerMonth");
    }

    @PutMapping(path = "/update")
    public ResponseEntity<JSONBody> updateRule(@RequestParam String name, @RequestParam String value) {
        return rService.updateRule(name, value);
    }
}
