package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.Services.LoanService;
import sg.edu.sportsschool.DTO.LoanDTO;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    private LoanService lService;

    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllLoans() {
        return lService.getAllLoans();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addLoan(@RequestBody LoanDTO loanDTO) {
        return lService.addLoan(loanDTO);
    }

    @GetMapping(path = "/getNumAvailablePassesForDate")
    public ResponseEntity<JSONBody> getNumAvailablePassesForDate(@RequestParam Integer aId,
            @RequestParam int yyyy, @RequestParam int mm, @RequestParam int dd) {
        return lService.getNumAvailablePassesForDate(aId, yyyy, mm, dd);
    }
    
    // // ------------------------------------------------------------------------------------------------
    // // -- Following codes are used for testing only
    // @GetMapping(path = "/findAllCurrentlyLoanedPassesByAttrId")
    // public ResponseEntity<JSONBody> findAllCurrentlyLoanedPassesByAttrId(@RequestParam Integer aId,
    //         @RequestParam int yyyy, @RequestParam int mm, @RequestParam int dd) {
    //     return lService.findAllCurrentlyLoanedPassesByAttrId(aId, yyyy, mm, dd);
    // }
    // // ------------------------------------------------------------------------------------------------

}
