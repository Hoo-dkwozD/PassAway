package sg.edu.sportsschool.Controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.DTO.Request.LoanDTO;
import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithData;
import sg.edu.sportsschool.Repositories.LoanRepository;
import sg.edu.sportsschool.Services.LoanService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/loan")
public class LoanController {

    private LoanService lService;

    @Autowired
    private LoanRepository lRepository;

    @Autowired
    public LoanController(LoanService lService) {
        this.lService = lService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<JSONBody> getAllLoans() {
        return lService.getAllLoans();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<JSONBody> getLoan(@PathVariable int id) {
        return lService.getLoan(id);
    }

    @GetMapping(path = "/list-by-email")
    public ResponseEntity<JSONBody> getLoansByEmail(@RequestParam String email) {
        return lService.getLoansByEmail(email);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addLoan(@RequestBody LoanDTO loanDTO) {
        return lService.addLoan(loanDTO);
    }

    @GetMapping(path = "/available-passes-month")
    public ResponseEntity<JSONBody> getNumAvailablePassesForMonth(@RequestParam Integer aId,
            @RequestParam int yyyy, @RequestParam int mm) {
        return lService.getNumAvailablePassesForMonth(aId, yyyy, mm);
    }

    @PutMapping(path = "/collect")
    public ResponseEntity<JSONBody> collectPasses(@RequestParam String emailTo, @RequestParam List<Integer> loanIds) {
        return lService.collectPasses(emailTo, loanIds);
    }

    @DeleteMapping(path = "/cancel")
    public ResponseEntity<JSONBody> cancelLoans(@RequestParam List<Integer> loanIds) {
        return lService.cancelLoans(loanIds);
    }
    
    // ------------------------------------------------------------------------------------------------
    // // -- Following codes are used for testing only
   
    @GetMapping(path = "/test")
    public ResponseEntity<JSONBody> getPrevBorrowers(@RequestParam String passId, @RequestParam Integer staffId, @RequestParam int yyyy,
            @RequestParam int mm, @RequestParam int dd) {
        Date startDate = Date.valueOf(String.format("%d-%d-%d", yyyy, mm, dd));
        List<Loan> prevBorrowers = lRepository.getPrevBorrowers(passId, staffId, startDate);
        JSONWithData<List<Loan>> body = new JSONWithData<>(200, prevBorrowers);
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    
    }
    // //
    // ------------------------------------------------------------------------------------------------

}
