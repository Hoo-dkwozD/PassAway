package sg.edu.sportsschool.Services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.DTO.LoanDTO;
import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Entities.Pass;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Repositories.LoanRepository;
import sg.edu.sportsschool.helper.CreateJSONResponse;
import sg.edu.sportsschool.helper.JSONBody;

@Service
public class LoanService {

    @Autowired
    private LoanRepository lRepository;

    @Autowired
    private AttractionService aService;

    @Autowired
    private PassService pService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private RuleService rService;

    public ResponseEntity<JSONBody> getAllLoans() {
        CreateJSONResponse<List<Loan>> jsonResponse = new CreateJSONResponse<>();
        try {
            List<Loan> results = new ArrayList<>();
            lRepository.findAll().forEach(results::add);

            return jsonResponse.create(results);

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to retrieve all loans");
        }
    }

    public ResponseEntity<JSONBody> addLoan(LoanDTO loanDTO) {
        CreateJSONResponse<List<Loan>> jsonResponse = new CreateJSONResponse<>(); 
        int numPassesRequested = loanDTO.getNumPasses();
        Integer aId = loanDTO.getAttractionId();

        Attraction a = aService.returnAttraction(aId);
        if (a == null) {
            return jsonResponse.create(500, "Server unable to find attraction of id: " + aId + " from the database");
        }

        String aName = a.getName();
        int aMaxPassesPerLoan = a.getMaxPassesPerLoan();

        Integer staffId = loanDTO.getStaffId();
        Staff staff = staffService.returnStaffById(staffId);
        if (staff == null) {
            return jsonResponse.create(500,
                    "Server unable to find staff of staff id: " + staffId + " from the database");
        }

        int yyyy = loanDTO.getyyyy();
        String yyyyString = yyyy + "";
        int mm = loanDTO.getmm();
        String mmString = (mm < 10) ? "0" + mm : mm + "";
        int dd = loanDTO.getdd();
        String ddString = (dd < 10) ? "0" + dd : dd + "";
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
    // -- Non-JSON response Methods
    public Integer getNumPassesLoanedOnDate(Integer staffId, String yyyy, String mm, String dd, Integer aId) {
        return lRepository.getNumPassesLoanedOnDate(staffId, yyyy, mm, dd, aId);
    }

    /*
     * All passes are available for loan every day provided they are not lost
     * Available passes = All passes for that attraction - Passes current booked for
     * that attraction for that date
     */
    public Set<Pass> getAvailablePassesForDate(Integer aId, String yyyyString, String mmString, String ddString) {
        Set<Pass> availableAttrPasses = pService.returnAllPassesByAttrId(aId);

        Set<Pass> currentLoansPasses = lRepository.findAllCurrentlyLoanedPassesByAttrId(aId, yyyyString, mmString,
                ddString); // Get all the currently booked passes for that attraction on a particular date

        availableAttrPasses.removeAll(currentLoansPasses);
        return availableAttrPasses;
    }

    /**
     * Returns the number of loans i.e. loans to different place on different
     * date of the month).
     * 
     * @param staffId
     * @param yyyyString
     * @param mmString
     * @return
     */
    public int getLoanCountInMonth(Integer staffId, String yyyyString, String mmString) {
        return lRepository.getLoanCountInMonth(staffId, yyyyString, mmString).size();
    }

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
