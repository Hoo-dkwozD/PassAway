package sg.edu.sportsschool.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.DTO.Request.LoanDTO;
import sg.edu.sportsschool.Services.LoanService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/loan")
public class LoanController {

    private LoanService lService;

    @Autowired
    public LoanController(LoanService lService) {
        this.lService = lService;
    }

    @GetMapping(path = "/list-all")
    public ResponseEntity<JSONBody> getAllLoans() {
        return lService.getAllLoans();
    }

    @GetMapping(path = "/list-by-email")
    public ResponseEntity<JSONBody> getLoansByEmail(@RequestParam String email) {
        return lService.getLoansByEmail(email);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addLoan(@RequestBody LoanDTO loanDTO) {
        return lService.addLoan(loanDTO);
    }

    @GetMapping(path = "/available-passes")
    public ResponseEntity<JSONBody> getNumAvailablePassesForDate(@RequestParam Integer aId,
            @RequestParam int yyyy, @RequestParam int mm, @RequestParam int dd) {
        return lService.getNumAvailablePassesForDate(aId, yyyy, mm, dd);
    }

    @PutMapping(path = "/collect")
    public ResponseEntity<JSONBody> collectPasses(@RequestParam String emailTo, @RequestParam List<Integer> loanIds) {
        return lService.collectPasses(emailTo, loanIds);
    }

    // TODO Cancellation of loans

    // ------------------------------------------------------------------------------------------------
    // // -- Following codes are used for testing only


    // //
    // ------------------------------------------------------------------------------------------------

}
