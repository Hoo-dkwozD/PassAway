package sg.edu.sportsschool.record.Services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.record.DTO.LoanDTO;
import sg.edu.sportsschool.record.Entities.Attraction;
import sg.edu.sportsschool.record.Entities.Loan;
import sg.edu.sportsschool.record.Entities.Pass;
import sg.edu.sportsschool.record.Entities.Staff;
import sg.edu.sportsschool.record.Repositories.AttractionRepository;
import sg.edu.sportsschool.record.Repositories.LoanRepository;
import sg.edu.sportsschool.record.Repositories.PassRepository;
import sg.edu.sportsschool.record.Repositories.StaffRepository;
import sg.edu.sportsschool.helper.CreateJSONResponse;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;

@Service
public class LoanService {

    @Autowired
    private LoanRepository lRepository;

    @Autowired
    private AttractionRepository aRepository;

    @Autowired
    private PassRepository pRepository;

    @Autowired
    private StaffRepository sRepository;

    @Autowired
    private RuleService rService;

    public ResponseEntity<JSONBody> getAllLoans() {
        try {
            List<Loan> results = new ArrayList<>();
            lRepository.findAll().forEach(results::add);

            JSONWithData<List<Loan>> body = new JSONWithData<>(200, results);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            JSONWithMessage errMessage = new JSONWithMessage(500, "Server unable to retrieve all loans");
            return new ResponseEntity<JSONBody>(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<JSONBody> addLoan(LoanDTO loanDTO) {
        int numPassesRequested = loanDTO.getNumPasses();
        Integer aId = loanDTO.getAttractionId();

        Attraction a = aRepository.findById(aId).get();

        CreateJSONResponse<List<Loan>> jsonResponse = new CreateJSONResponse<>(); // json data response type
                                                                                  // List<Loan> if status is 200

        if (a == null)
            return jsonResponse.create(400, "Server unable to get attraction using attractionId " + aId);

        String aName = a.getName();
        int aMaxPassesPerLoan = a.getMaxPassesPerLoan();

        Integer staffId = loanDTO.getStaffId();
        Staff staff = sRepository.findById(staffId).get();

        int yyyy = loanDTO.getyyyy();
        String yyyyString = yyyy + "";
        int mm = loanDTO.getmm();
        String mmString = (mm < 10) ? "0" + mm : mm + "";
        int dd = loanDTO.getdd();
        String ddString = (dd < 10) ? "0" + dd : dd + "";
        System.out.println(String.format("%d-%d-%d", yyyy, mm, dd));
        Date startDate = Date.valueOf(String.format("%d-%d-%d", yyyy, mm, dd));

        // Check 1: Disallow to book more passes for that loan if num passes that
        // borrower has currently borrowed (will be 0 for new loans and >0 for current
        // loans) + passes requested > aMaxPassesPerLoan
        Integer numPassesLoanedOnDate = lRepository.getNumPassesLoanedOnDate(staffId, yyyyString, mmString, ddString,
                aId);
        boolean newLoan = (numPassesLoanedOnDate > 0) ? false : true;

        if (numPassesLoanedOnDate + numPassesRequested > aMaxPassesPerLoan)
            return jsonResponse.create(400, String.format(
                    "Max. passes allowed per loan for %s is %d. You currently have %d passes booked for %s. You cannot book %d passes for %s on (yyyy-mm-ddd): %d-%d-%d.",
                    aName, aMaxPassesPerLoan, numPassesLoanedOnDate, aName, numPassesRequested, aName, yyyy, mm,
                    dd));

        // Check 2: Disallow to book a loan if borrower is making a new loan and his
        // current loans for the attraction for selected month has reached the
        // max_loans_per_month
        int loanCountInMonth = lRepository.getLoanCountInMonth(staffId, yyyyString, mmString).size();
        int maxLoansPerMonth = rService.getMaxLoansPerMonth();

        if (newLoan && (loanCountInMonth + 1 > maxLoansPerMonth))
            return jsonResponse.create(400, String.format(
                    "Unable to make anymore loans. Max. loans per month for %s is %d. You have %d loans for (yyyy-mm): %d-%d currently. Cancel other loans if you wish to make new loans.",
                    aName, maxLoansPerMonth, loanCountInMonth, yyyy, mm));

        // Check 3: Unable to loan if number of available pass for that
        // attraction for the date is < number of passes that user requested
        Set<Pass> availablePassesForLoan = getAvailablePassesForDate(aId, yyyyString, mmString, ddString);
        if (availablePassesForLoan.size() < numPassesRequested)
            return jsonResponse.create(400, String.format(
                    "Unable to book %d pass(es). There are insufficient available pass(es) for %s for loan on (yyyy-mm-dd): %s-%s-%s",
                    numPassesRequested, aName, yyyyString, mmString, ddString));

        Iterator<Pass> availablePasses = availablePassesForLoan.iterator();
        List<Loan> loansMade = new ArrayList<>();
        int i = 0; // counter to add number of passes according to numPassesRequested

        // Add loan according to the number of passes that borrower wants
        while (availablePasses.hasNext() && (i < numPassesRequested)) {
            Loan loan = new Loan(staff, availablePasses.next(), startDate, false, false);
            lRepository.save(loan);
            loansMade.add(loan);
            i++;
        }

        return jsonResponse.create(loansMade);

    }

    /*
     * All passes are available for loan every day provided they are not lost
     * Available passes = All passes for that attraction - Passes current booked for
     * that attraction for that date
     */
    public Set<Pass> getAvailablePassesForDate(Integer aId, String yyyyString, String mmString, String ddString) {
        Set<Pass> allAttractionPasses = pRepository.findAllPassesByAttractionId(aId); //

        Set<Pass> currentLoansPasses = lRepository.findAllCurrentlyLoanedPassesByAttrId(aId, yyyyString, mmString,
                ddString); // Get all the currently booked passes for that attraction on a particular date

        allAttractionPasses.removeAll(currentLoansPasses); // Remove the passes that have been booked for this
                                                           // attraction on date from all available
                                                           // passes the attraction has, to get all the passes
                                                           // available for user to book for this attraction on the
                                                           // date
        return allAttractionPasses;
    }

    /**
     * Returns the number of loans i.e. loans to different place on different
     * date of the month).
     * 1 loan = 1 group of loan where the place is the same or 1 group of
     * loans where the date is the same.
     * I.e. groupby the loans by date, then further groupby place and count the
     * number of groups,
     * or groupby place, then further groupby date, and count the number of groups.
     * Same result both ways.
     * Ex 1. Staff ID 1 has pass IDs 4 (place A), 5 (place A), 6 (place B) on 1st,
     * 1st and 2nd Jan respectively.
     * Number of loans = 2 (initially 2 groups between 1st Jan and 2nd Jan. Within
     * group 1 (1st Jan), only going place A (+1). Within group 2 (2nd Jan), only
     * going place B (+1))
     * Ex 2. Staff ID 1 has pass IDs 4 (place A), 5 (place A), 6 (place B) on 1st,
     * 2nd and 2nd of the month respectively.
     * Number of loans = 3 (initially 2 groups between 1st Jan and 2nd Jan. Within
     * group 1 (1st Jan), only going place A (+1). Within group 2 (2nd Jan), going
     * place A (+1) and place B (+1))
     * 
     * @param staffId
     * @param yyyyString
     * @param mmString
     * @return
     */
    public int getLoanCountInMonth(Integer staffId, String yyyyString, String mmString) {
        return lRepository.getLoanCountInMonth(staffId, yyyyString, mmString).size();
    }

    public ResponseEntity<JSONBody> getNumAvailablePassesForDate(Integer aId, int yyyy, int mm, int dd) {
        CreateJSONResponse<Integer> response = new CreateJSONResponse<>();
        String yyyyString = yyyy + "";
        String mmString = (mm < 10) ? "0" + mm : mm + "";
        String ddString = (dd < 10) ? "0" + dd : dd + "";

        try {
            Set<Pass> allCurrentlyLoanedPassesByAttrId = getAvailablePassesForDate(aId, yyyyString, mmString, ddString);
            Integer numAvailablePassesForDate = allCurrentlyLoanedPassesByAttrId.size();
            return response.create(numAvailablePassesForDate);

        } catch (Exception e) {
            return response.create(500, String.format(
                    "Server unable to get all currently booked passes of attraction ID %d for (yyyy-mm-dd) %s-%s-%s ",
                    aId, yyyyString, mmString, ddString));
        }
    }

    // ------------------------------------------------------------------------------------------------
    // -- Following codes are used for testing only
    // public ResponseEntity<JSONBody> findAllCurrentlyLoanedPassesByAttrId(Integer
    // aId, int yyyy, int mm,
    // int dd) {
    // CreateJSONResponse<Set<Pass>> response = new CreateJSONResponse<>();
    // try {
    // Set<Pass> allCurrentlyLoanedPassesByAttrId =
    // lRepository.findAllCurrentlyLoanedPassesByAttrId(aId, yyyy + "", (mm < 10) ?
    // "0" + mm : mm + "", (dd < 10) ? "0" + dd : dd + "");
    // return response.create(allCurrentlyLoanedPassesByAttrId);

    // } catch (Exception e) {
    // return response.create(500, String.format(
    // "Server unable to get all currently booked passes of attraction ID %d for
    // (yyyy-mm-dd) %s-%s-%s ",
    // aId, yyyy,
    // mm, dd));
    // }
    // }
    // //
    // ------------------------------------------------------------------------------------------------

}
