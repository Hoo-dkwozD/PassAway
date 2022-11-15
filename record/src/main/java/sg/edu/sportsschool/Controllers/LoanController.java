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
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Repositories.StaffRepository;
import sg.edu.sportsschool.Services.LoanService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/loan")
public class LoanController {

    private LoanService lService;

    @Autowired
    private StaffRepository sRepository;

    @Autowired
    public LoanController(LoanService lService) {
        this.lService = lService;
    }

    /**
     * @api {get} Get all loans
     * @apiName GetLoans
     * @apiGroup Loan
     *           "loanId": 1,
     *           "staffName": "Zhi Wei",
     *           "staffEmail": "zwthean.2021@scis.smu.edu.sg",
     *           "visitDate": "2021-01-22",
     *           "attractionName": "Universal Studios",
     *           "hasCollected": false,
     *           "hasReturned": false,
     *           "passId": "ABC12345F",
     *           "prevBorrowerName": "None",
     *           "prevBorrowerContact": "None",
     *           "lost": true
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object[]} data List of JSON objects representing loans.
     * @apiSuccess {Number} data[loanId] Loan ID.
     * @apiSuccess {String} data[staffName] Staff name.
     * @apiSuccess {String} data[staffEmail] Staff email.
     * @apiSuccess {String} data[visitDate] Visit date.
     * @apiSuccess {String} data[attractionName] Attraction name
     * @apiSuccess {Boolean} data[hasCollected] Whether the loan pass has been
     *             collected.
     * @apiSuccess {Boolean} data[hasReturned] Whether the loan pass has been
     *             returned.
     * @apiSuccess {String} data[passId] Pass Id
     * @apiSuccess {String} data[prevBorrowerName] Previous borrower name
     * @apiSuccess {String} data[prevBorrowerContact] Previous borrower contact.
     * @apiSuccess {Boolean} data[lost] Whether the pass is lost.
     * 
     * @apiSuccessExample {json} Success-Response:
     *                    HTTP/1.1 200 OK
     *                    {
     *                    "code": 200,
     *                    "data": [
     *                    {
     *                    "loanId": 1,
     *                    "staffName": "Zhi Wei",
     *                    "staffEmail": "zwthean.2021@scis.smu.edu.sg",
     *                    "visitDate": "2021-01-22",
     *                    "attractionName": "Universal Studios",
     *                    "hasCollected": false,
     *                    "hasReturned": false,
     *                    "passId": "ABC12345F",
     *                    "prevBorrowerName": "None",
     *                    "prevBorrowerContact": "None",
     *                    "lost": true
     *                    },
     *                    {
     *                    "loanId": 2,
     *                    "staffName": "Zhi Wei",
     *                    "staffEmail": "zwthean.2021@scis.smu.edu.sg",
     *                    "visitDate": "2021-01-22",
     *                    "attractionName": "Universal Studios",
     *                    "hasCollected": false,
     *                    "hasReturned": false,
     *                    "passId": "ABC12345G",
     *                    "prevBorrowerName": "None",
     *                    "prevBorrowerContact": "None",
     *                    "lost": true
     *                    }
     *                    ]
     *                    }
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Server unable to retrieve all loans"
     *                  }
     * 
     * @apiDescription Gets the details of all loans.
     */
    @GetMapping(path = "")
    public ResponseEntity<JSONBody> getAllLoans() {
        return lService.getAllLoans();
    }

    /**
     * @api {get} /:id Get loan by ID
     * @apiName GetLoan
     * @apiGroup Loan
     * 
     * @apiParam {Number} id Loan ID.
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object[]} data List of JSON objects representing loans.
     * @apiSuccess {Number} data[loanId] Loan ID.
     * @apiSuccess {String} data[staffName] Staff name.
     * @apiSuccess {String} data[staffEmail] Staff email.
     * @apiSuccess {String} data[visitDate] Visit date.
     * @apiSuccess {String} data[attractionName] Attraction name
     * @apiSuccess {Boolean} data[hasCollected] Whether the loan pass has been
     *             collected.
     * @apiSuccess {Boolean} data[hasReturned] Whether the loan pass has been
     *             returned.
     * @apiSuccess {String} data[passId] Pass Id
     * @apiSuccess {String} data[prevBorrowerName] Previous borrower name
     * @apiSuccess {String} data[prevBorrowerContact] Previous borrower contact.
     * @apiSuccess {Boolean} data[lost] Whether the pass is lost.
     * 
     * 
     * @apiSuccessExample {json} Success-Response:
     *                    HTTP/1.1 200 OK
     *                    {
     *                    "code": 200,
     *                    "data": [
     *                    {
     *                    "loanId": 2,
     *                    "staffName": "Zhi Wei",
     *                    "staffEmail": "zwthean.2021@scis.smu.edu.sg",
     *                    "visitDate": "2021-01-22",
     *                    "attractionName": "Universal Studios",
     *                    "hasCollected": false,
     *                    "hasReturned": false,
     *                    "passId": "ABC12345G",
     *                    "prevBorrowerName": "None",
     *                    "prevBorrowerContact": "None",
     *                    "lost": true
     *                    }
     *                    ]
     *                    }
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad request
     *                  {
     *                  "code": 400,
     *                  "message": "Loan with id 20 does not exist"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Server unable to get loan of id: 20"
     *                  }
     * 
     * @apiDescription Gets the details of a loan
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<JSONBody> getLoan(@PathVariable int id) {
        return lService.getLoan(id);
    }

    /**
     * @api {get} /list-by-email Get loans by staff email
     * @apiName Get loans by email
     * @apiGroup Loan
     * 
     * @apiParam {String} email Staff email.
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object[]} data List of JSON objects representing loans.
     * @apiSuccess {Number} data[loanId] Loan ID.
     * @apiSuccess {String} data[staffName] Staff name.
     * @apiSuccess {String} data[staffEmail] Staff email.
     * @apiSuccess {String} data[visitDate] Visit date.
     * @apiSuccess {String} data[attractionName] Attraction name
     * @apiSuccess {Boolean} data[hasCollected] Whether the loan pass has been
     *             collected.
     * @apiSuccess {Boolean} data[hasReturned] Whether the loan pass has been
     *             returned.
     * @apiSuccess {String} data[passId] Pass Id
     * @apiSuccess {String} data[prevBorrowerName] Previous borrower name
     * @apiSuccess {String} data[prevBorrowerContact] Previous borrower contact.
     * @apiSuccess {Boolean} data[lost] Whether the pass is lost.
     * 
     * 
     * @apiSuccessExample {json} Success-Response:
     *                    HTTP/1.1 200 OK
     *                    {
     *                    "code": 200,
     *                    "data": [
     *                    {
     *                    "loanId": 1,
     *                    "staffName": "Zhi Wei",
     *                    "staffEmail": "zwthean.2021@scis.smu.edu.sg",
     *                    "visitDate": "2021-01-22",
     *                    "attractionName": "Universal Studios",
     *                    "hasCollected": false,
     *                    "hasReturned": false,
     *                    "passId": "ABC12345F",
     *                    "prevBorrowerName": "None",
     *                    "prevBorrowerContact": "None",
     *                    "lost": true
     *                    },
     *                    {
     *                    "loanId": 2,
     *                    "staffName": "Zhi Wei",
     *                    "staffEmail": "zwthean.2021@scis.smu.edu.sg",
     *                    "visitDate": "2021-01-22",
     *                    "attractionName": "Universal Studios",
     *                    "hasCollected": false,
     *                    "hasReturned": false,
     *                    "passId": "ABC12345G",
     *                    "prevBorrowerName": "None",
     *                    "prevBorrowerContact": "None",
     *                    "lost": true
     *                    },
     *                    {
     *                    "loanId": 6,
     *                    "staffName": "Zhi Wei",
     *                    "staffEmail": "zwthean.2021@scis.smu.edu.sg",
     *                    "visitDate": "2021-01-24",
     *                    "attractionName": "Gardens by the bay",
     *                    "hasCollected": true,
     *                    "hasReturned": true,
     *                    "passId": "1",
     *                    "prevBorrowerName": "None",
     *                    "prevBorrowerContact": "None",
     *                    "lost": false
     *                    }
     *                    ]
     *                    }
     *
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Server unable to get all loans of email:
     *                  henry@scis.smu.edu.sg"
     *                  }
     * 
     * @apiDescription Gets the details of a loan
     */
    @GetMapping(path = "/list-by-email")
    public ResponseEntity<JSONBody> getLoansByEmail(@RequestParam String email) {
        return lService.getLoansByEmail(email);
    }

    /**
     * @api {post} /add Add a loan
     * @apiName Add Loan
     * @apiGroup Loan
     * 
     * @apiBody {Number} staffId Staff ID.
     * @apiBody {Number} attractionId Attraction ID.
     * @apiBody {Number} numPasses Number of passes requested.
     * @apiBody {Number} yyyy Year of visit date
     * @apiBody {Number} mm Month of visit date from 1-12
     * @apiBody {Number} dd Day of visit date from 1-31
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object[]} data List of JSON objects representing loans.
     * @apiSuccess {Number} data[loanId] Loan ID.
     * @apiSuccess {String} data[staffName] Staff name.
     * @apiSuccess {String} data[staffEmail] Staff email.
     * @apiSuccess {String} data[visitDate] Visit date.
     * @apiSuccess {String} data[attractionName] Attraction name
     * @apiSuccess {Boolean} data[hasCollected] Whether the loan pass has been
     *             collected.
     * @apiSuccess {Boolean} data[hasReturned] Whether the loan pass has been
     *             returned.
     * @apiSuccess {String} data[passId] Pass Id
     * @apiSuccess {String} data[prevBorrowerName] Previous borrower name
     * @apiSuccess {String} data[prevBorrowerContact] Previous borrower contact.
     * @apiSuccess {Boolean} data[lost] Whether the pass is lost.
     * 
     * @apiSuccessExample {json} Success-Response:
     *                    HTTP/1.1 200 OK
     *                    {
     *                    "code": 200,
     *                    "data": [
     *                    {
     *                    "loanId": 10,
     *                    "staffName": "ZWgmail",
     *                    "staffEmail": "theanzhiwei@gmail.com",
     *                    "visitDate": "2021-01-26",
     *                    "attractionName": "Universal Studios",
     *                    "hasCollected": false,
     *                    "hasReturned": false,
     *                    "passId": "ABC12345F",
     *                    "prevBorrowerName": "Zhi Wei",
     *                    "prevBorrowerContact": "91234567",
     *                    "lost": false
     *                    },
     *                    {
     *                    "loanId": 11,
     *                    "staffName": "ZWgmail",
     *                    "staffEmail": "theanzhiwei@gmail.com",
     *                    "visitDate": "2021-01-26",
     *                    "attractionName": "Universal Studios",
     *                    "hasCollected": false,
     *                    "hasReturned": false,
     *                    "passId": "ABC12345G",
     *                    "prevBorrowerName": "Zhi Wei",
     *                    "prevBorrowerContact": "91234567",
     *                    "lost": false
     *                    }
     *                    ]
     *                    }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad Request
     *                  {
     *                  "code": 400,
     *                  "message": "Staff of staff id: 3 cannot book"
     *                  }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad Request
     *                  {
     *                  "code": 400,
     *                  "message": "Booking date is too early. You can only book up
     *                  to 8 months before visit date."
     *                  }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad Request
     *                  {
     *                  "code": 400,
     *                  "message": "Attraction of id: 3 does not have a barcode
     *                  image"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Attraction of id: 20 does not exist in the
     *                  database"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Staff of staff id: 21 does not exist in the
     *                  database"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Max. passes allowed per loan for Singapore zoo
     *                  is 2. You currently have 2 passes booked for Singapore zoo.
     *                  You cannot book 1 passes for Singapore zoo on (yyyy-mm-ddd):
     *                  2022-11-15.",
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Unable to make anymore loans. Max. loans per
     *                  month for Singapore Zoo is 2. You have 2 loans for
     *                  (yyyy-mm): 2022-11 currently. Cancel other loans if you wish
     *                  to make new loans."
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Unable to book 2 pass(es). There are
     *                  insufficient available pass(es) for Singapore Zoo for loan
     *                  on (yyyy-mm-dd): 2022-11-15"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Server unable to read barcode image at path:
     *                  /src/main/resources/static/Singapore
     *                  Zoo14-11-2022_18-15-13.jpeg"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Server unable to send email to:
     *                  theanzhiwei@gmail.com"
     *                  }
     * 
     * @apiDescription Adds a loan into the loans table. One pass = one loan entry
     *                 in loans table. Two passes = two loan entries in loan table
     */
    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addLoan(@RequestBody LoanDTO loanDTO) {
        return lService.addLoan(loanDTO);
    }

    /**
     * @api {get} /available-passes Get available passes by attraction id in a month
     * @apiName Get available passes for each day in a month
     * @apiGroup Loan
     * 
     * @apiParam {String} aId Attraction Id.
     * @apiParam {Number} yyyy Year.
     * @apiParam {Number} mm Month from 1-12.
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON Object representing number of passes available
     *             for loans each date.
     * @apiSuccess {Number} data[date] Number of passes available for loans that
     *             date.
     * 
     * @apiSuccessExample {json} Success-Response:
     *                    HTTP/1.1 200 OK
     *                    {
     *                    "code": 200,
     *                    "data": {
     *                    "2021-01-02": 1,
     *                    "2021-01-24": 0,
     *                    "2021-01-03": 1,
     *                    "2021-01-25": 1,
     *                    "2021-01-22": -1,
     *                    "2021-01-01": 1,
     *                    "2021-01-23": 1,
     *                    "2021-01-20": 1,
     *                    "2021-01-21": 1,
     *                    "2021-01-19": 1,
     *                    "2021-01-17": 1,
     *                    "2021-01-18": 1,
     *                    "2021-01-15": 1,
     *                    "2021-01-16": 1,
     *                    "2021-01-13": 1,
     *                    "2021-01-14": 1,
     *                    "2021-01-11": 1,
     *                    "2021-01-12": 1,
     *                    "2021-01-31": 1,
     *                    "2021-01-10": 1,
     *                    "2021-01-30": 1,
     *                    "2021-01-08": 1,
     *                    "2021-01-09": 1,
     *                    "2021-01-06": 1,
     *                    "2021-01-28": 1,
     *                    "2021-01-07": 1,
     *                    "2021-01-29": 1,
     *                    "2021-01-04": 1,
     *                    "2021-01-26": 1,
     *                    "2021-01-05": 1,
     *                    "2021-01-27": 1
     *                    }
     *                    }
     *
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad Request
     *                  {
     *                  "code": 400,
     *                  "message": "No passes have been allocated yet for attraction
     *                  id: 5"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Server unable to get all currently booked passes
     *                  of attraction ID Singapore Zoo for (yyyy-mm) 2021-11"
     *                  }
     * 
     * @apiDescription Gets the number of available passes for each date in a month
     *                 for an attraction.
     */
    @GetMapping(path = "/available-passes")
    public ResponseEntity<JSONBody> getNumAvailablePassesForMonth(@RequestParam Integer aId,
            @RequestParam int yyyy, @RequestParam int mm) {
        return lService.getNumAvailablePassesForMonth(aId, yyyy, mm);
    }

    /**
     * @api {put} /return Update loan pass return status
     * @apiName Update loan pass return status
     * @apiGroup Loan
     * 
     * @apiBody {Number} attractionId Attraction ID.
     * @apiBody {String} name Attraction name.
     * @apiBody {String} description Attraction description.
     * @apiBody {String} passType Attraction pass type. ("0": PHYSICAL, "1":
     *          DIGITAL)
     * @apiBody {Number} replacementFee Attraction replacement fee in SGD.
     * @apiBody {Number} numAccompanyingGuests Attraction number of accompanying
     *          guests.
     * @apiBody {Number} maxPassesPerLoan Attraction maximum passes per loan.
     * @apiBody {Number} maxLoansPerMonth Attraction maximum loans per month.
     * @apiBody {String} address Attraction address.
     * @apiBody {String} membershipId Attraction membership ID.
     * @apiBody {Number} expiryDateYYYY Attraction expiry date's year.
     * @apiBody {Number} expiryDateMM Attraction expiry date's month.
     * @apiBody {Number} expiryDateDD Attraction expiry date's day.
     * @apiBody {String} benefits Attraction benefits.
     * @apiBody {String} termsConditions Attraction terms and conditions
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON Object representing number of passes available
     *             for loans each date.
     * @apiSuccess {Number} data[date] Number of passes available for loans that
     *             date.
     * 
     * @apiSuccessExample {json} Success-Response:
     *                    HTTP/1.1 200 OK
     *                    {
     *                    "code": 200,
     *                    "data": {
     *                    "2021-01-02": 1,
     *                    "2021-01-24": 0,
     *                    "2021-01-03": 1,
     *                    "2021-01-25": 1,
     *                    "2021-01-22": -1,
     *                    "2021-01-01": 1,
     *                    "2021-01-23": 1,
     *                    "2021-01-20": 1,
     *                    "2021-01-21": 1,
     *                    "2021-01-19": 1,
     *                    "2021-01-17": 1,
     *                    "2021-01-18": 1,
     *                    "2021-01-15": 1,
     *                    "2021-01-16": 1,
     *                    "2021-01-13": 1,
     *                    "2021-01-14": 1,
     *                    "2021-01-11": 1,
     *                    "2021-01-12": 1,
     *                    "2021-01-31": 1,
     *                    "2021-01-10": 1,
     *                    "2021-01-30": 1,
     *                    "2021-01-08": 1,
     *                    "2021-01-09": 1,
     *                    "2021-01-06": 1,
     *                    "2021-01-28": 1,
     *                    "2021-01-07": 1,
     *                    "2021-01-29": 1,
     *                    "2021-01-04": 1,
     *                    "2021-01-26": 1,
     *                    "2021-01-05": 1,
     *                    "2021-01-27": 1
     *                    }
     *                    }
     *
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad Request
     *                  {
     *                  "code": 400,
     *                  "message": "No passes have been allocated yet for attraction
     *                  id: 5"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Server unable to get all currently booked passes
     *                  of attraction ID Singapore Zoo for (yyyy-mm) 2021-11"
     *                  }
     * 
     * @apiDescription Gets the number of available passes for each date in a month
     *                 for an attraction.
     */
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
