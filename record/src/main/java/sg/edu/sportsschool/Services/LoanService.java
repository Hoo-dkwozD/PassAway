package sg.edu.sportsschool.Services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.DTO.LoanDTO;
import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Entities.Pass;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Repositories.LoanRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.PassComparator;

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
        try {
            List<Loan> results = new ArrayList<>();
            lRepository.findAll().forEach(results::add);
            JSONWithData<List<Loan>> body = new JSONWithData<>(200, results);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to retrieve all loans");
        }
    }

    public ResponseEntity<JSONBody> addLoan(LoanDTO loanDTO) {
        int numPassesRequested = loanDTO.getNumPasses();
        Integer aId = loanDTO.getAttractionId();

        Attraction a = aService.returnAttraction(aId);
        if (a == null) {
            throw new InternalServerException("Server unable to find attraction of id: " + aId + " from the database");
        }

        String aName = a.getName();
        int aMaxPassesPerLoan = a.getMaxPassesPerLoan();

        Integer staffId = loanDTO.getStaffId();
        Staff staff = staffService.returnStaffById(staffId);
        if (staff == null) {
            throw new InternalServerException("Server unable to find staff of staff id: " + staffId + " from the database");
        }

        int yyyy = loanDTO.getyyyy();
        String yyyyString = yyyy + "";
        int mm = loanDTO.getmm();
        String mmString = (mm < 10) ? "0" + mm : mm + "";
        int dd = loanDTO.getdd();
        String ddString = (dd < 10) ? "0" + dd : dd + "";

        // Check 1: Cannot book if # passes borrower has + passes requested >
        // aMaxPassesPerLoan
        Integer numPassesLoanedOnDate = lRepository.getNumPassesLoanedOnDate(staffId, yyyyString, mmString, ddString,
                aId);
        boolean newLoan = (numPassesLoanedOnDate > 0) ? false : true;

        if (numPassesLoanedOnDate + numPassesRequested > aMaxPassesPerLoan)
            throw new InternalServerException(String.format(
                    "Max. passes allowed per loan for %s is %d. You currently have %d passes booked for %s. You cannot book %d passes for %s on (yyyy-mm-ddd): %d-%d-%d.",
                    aName, aMaxPassesPerLoan, numPassesLoanedOnDate, aName, numPassesRequested, aName, yyyy, mm,
                    dd));

        // Check 2: Cannot book if new loan will exceed max loans per month
        int loanCountInMonth = lRepository.getLoanCountInMonth(staffId, yyyyString, mmString).size();
        int maxLoansPerMonth = rService.getMaxLoansPerMonth();

        if (newLoan && (loanCountInMonth + 1 > maxLoansPerMonth))
            throw new InternalServerException(String.format(
                    "Unable to make anymore loans. Max. loans per month is %d. You have %d loans for (yyyy-mm): %d-%d currently. Cancel other loans if you wish to make new loans.",
                    maxLoansPerMonth, loanCountInMonth, yyyy, mm));

        // Check 3: Cannot book if # available pass for that
        // attraction for the date < # passes that user requested
        Set<Pass> availablePassesForLoan = getAvailablePassesForDate(aId, yyyyString, mmString, ddString);
        if (availablePassesForLoan.size() < numPassesRequested)
            throw new InternalServerException(String.format(
                    "Unable to book %d pass(es). There are insufficient available pass(es) for %s for loan on (yyyy-mm-dd): %s-%s-%s",
                    numPassesRequested, aName, yyyyString, mmString, ddString));

        // TODO: Ask if staff want to be in waiting list, Put the staff into waiting
        // list if selected yes

        TreeSet<Pass> sortedAvailablePasses = sortSetPasses(availablePassesForLoan);
        List<Loan> loansMade = assignPasses(sortedAvailablePasses, staff, yyyy, mm, dd, numPassesRequested);

        JSONWithData<List<Loan>> body = new JSONWithData<>(200, loansMade);
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

    }

    public ResponseEntity<JSONBody> getNumAvailablePassesForDate(Integer aId, int yyyy, int mm, int dd) {
        String yyyyString = yyyy + "";
        String mmString = (mm < 10) ? "0" + mm : mm + "";
        String ddString = (dd < 10) ? "0" + dd : dd + "";

        try {
            Set<Pass> allCurrentlyLoanedPassesByAttrId = getAvailablePassesForDate(aId, yyyyString, mmString, ddString);
            Integer numAvailablePassesForDate = allCurrentlyLoanedPassesByAttrId.size();
            JSONWithData<Integer> body = new JSONWithData<>(200, numAvailablePassesForDate);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException(String.format(
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

    public TreeSet<Pass> sortSetPasses(Set<Pass> setPasses) {
        // Returns TreeSet of passes sorted by their pass IDs
        Iterator<Pass> setPassesIterator = setPasses.iterator();
        TreeSet<Pass> sortedPasses = new TreeSet<>(new PassComparator());
        while (setPassesIterator.hasNext()) {
            sortedPasses.add(setPassesIterator.next());
        }
        return sortedPasses;
    }

    /*
     * Returns a list of loans made to the staff on the date
     * Assigns passes to the staff based on odd/even of the day of the year
     * Allocate pass from the first of the TreeSet to the last if day is odd, else allocate from last to the first if day is even
     * To allow for 1 day of buffer time for borrower to return before the pass is being borrowed again
     * E.g. pass IDs: [1,2,3,4] Borrower borrow on 1 Jan (odd day of the year). Pass allocated is from id 1 to 4.
     * If another borrower borrow on 2 Jan (Even day of the year), pass allocated is from id 4 to 1 (from last to first)
     * to minimise id 1 being allocated to next borrower (to give time buffer for previous borrower to return)
     * If another borrower borrow on 3 Jan (odd day of the year), pass allocated is back from id 1 to 4 (giving 1 day buffer for first borrower to return)
     */
    public List<Loan> assignPasses(TreeSet<Pass> sortedAvailablePasses, Staff staff, int yyyy, int mm, int dd, int numPassesRequested) {
        List<Loan> loansMade = new ArrayList<>();
        int i = 0; // counter to add number of passes according to numPassesRequested
        Date startDate = Date.valueOf(String.format("%d-%d-%d", yyyy, mm, dd));
        
        Iterator<Pass> availablePasses = sortedAvailablePasses.iterator();

        Calendar calendar = new GregorianCalendar(yyyy, mm, dd);
        if (calendar.get(Calendar.DAY_OF_YEAR) % 2 == 0) {
            availablePasses = sortedAvailablePasses.descendingIterator();
        }

        // Add loan according to the number of passes that borrower wants
        while (availablePasses.hasNext() && (i < numPassesRequested)) {
            Loan loan = new Loan(staff, availablePasses.next(), startDate, false, false);
            lRepository.save(loan);
            loansMade.add(loan);
            i++;
        }

        return loansMade;
    }

    // -- Following codes are used for testing only
    
    //
    // ------------------------------------------------------------------------------------------------

}
