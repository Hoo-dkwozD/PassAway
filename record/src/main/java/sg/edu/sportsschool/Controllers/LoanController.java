package sg.edu.sportsschool.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import sg.edu.sportsschool.DTO.Request.CollectPassDto;
import sg.edu.sportsschool.DTO.Request.LoanDTO;
import sg.edu.sportsschool.DTO.Request.LoanIdsDto;
import sg.edu.sportsschool.DTO.Request.UpdateLoanDto;
import sg.edu.sportsschool.Helper.Json.JSONBody;
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

    /**
     * @api {get} / Get all loans
     * @apiName SignIn
     * @apiGroup Auth
     * 
     * @apiBody {String} email Email of the user.
     * @apiBody {String} password Password of the user.
     * 
     * @apiSuccess (Success 200) {Number} code HTTP status code.
     * @apiSuccess (Success 200) {Object} data JSON object representing the staff's information.
     * @apiSuccess (Success 200) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 200) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": {
     *             "staffId": 3, 
     *             "role": "0"
     *         }
     *     }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 404,
     *         "message": "Authentication failed. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to authenticate. "
     *     }
     * 
     * @apiDescription Sets HTTP-Only Cookie as side-effect. 
     * Role value mapping is as follows: 
     * ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     */
    @GetMapping(path = "")
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

    @GetMapping(path = "/available-passes")
    public ResponseEntity<JSONBody> getNumAvailablePassesForMonth(@RequestParam Integer aId,
            @RequestParam int yyyy, @RequestParam int mm) {
        return lService.getNumAvailablePassesForMonth(aId, yyyy, mm);
    }

    @PutMapping(path = "/return")
    public ResponseEntity<JSONBody> updateReturned(@RequestBody UpdateLoanDto dto) {
        return lService.updateReturned(dto);
    }

    @PutMapping(path = "/collect")
    public ResponseEntity<JSONBody> updateCollected(@RequestBody UpdateLoanDto dto) {
        return lService.updateCollected(dto);
    }

    @DeleteMapping(path = "/cancel")
    public ResponseEntity<JSONBody> cancelLoans(@RequestBody LoanIdsDto dto) {
        return lService.cancelLoans(dto);
    }

    @PostMapping(path = "/report-lost")
    public ResponseEntity<JSONBody> reportLostPass(@RequestBody LoanIdsDto dto) {
        return lService.reportLostPass(dto);
    }

    // ------------------------------------------------------------------------------------------------
    // // -- Following codes are used for testing only
   
   
    // //
    // ------------------------------------------------------------------------------------------------

}
