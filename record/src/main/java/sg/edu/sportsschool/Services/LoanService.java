package sg.edu.sportsschool.Services;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.DTO.LoanDTO;
import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Entities.Pass;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.BadRequestException;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Repositories.LoanRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;
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
    private EmailService emailService;

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
            throw new InternalServerException(
                    "Server unable to find staff of staff id: " + staffId + " from the database");
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
        int maxLoansPerMonth = a.getMaxLoansPerMonth();

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

        TreeSet<Pass> sortedAvailablePasses = sortSetPasses(availablePassesForLoan);
        List<Loan> loansMade = assignPasses(sortedAvailablePasses, staff, yyyy, mm, dd, numPassesRequested);

        JSONWithData<List<Loan>> body = new JSONWithData<>(200, loansMade);

        // Send confirmation email
        Date startDate = Date.valueOf(String.format("%d-%d-%d", yyyy, mm, dd));
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM YYYY"); // e.g. Wednesday, 2 Oct 2022
        String visitDateStr = dateFormat.format(startDate);

        for (Loan loan : loansMade) {
            try {
                emailService.sendConfirmationMail(staff.getEmail(), staff.getFirstName(), aName, visitDateStr,
                        loan.getPass().getPassId(), a.getAddress(), a.getDescription(), a.getPassType());
                System.out.println("Confirmation email sent successfully");
            }

            catch (MessagingException e) {
                throw new InternalServerException("Server unable to send confirmation message to " + staff.getEmail()
                        + " for booking of " + aName + " on visit date: " + visitDateStr);
            }
        }

        System.out.println("Returning body in confirmation email");
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

    }

    public ResponseEntity<JSONBody> getNumAvailablePassesForDate(Integer aId, int yyyy, int mm, int dd) {
        String yyyyString = yyyy + "";
        String mmString = (mm < 10) ? "0" + mm : mm + "";
        String ddString = (dd < 10) ? "0" + dd : dd + "";

        try {
            Set<Pass> availablePasses = getAvailablePassesForDate(aId, yyyyString, mmString, ddString);
            Integer numAvailablePassesForDate = availablePasses.size();
            JSONWithData<Integer> body = new JSONWithData<>(200, numAvailablePassesForDate);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException(String.format(
                    "Server unable to get all currently booked passes of attraction ID %d for (yyyy-mm-dd) %s-%s-%s ",
                    aId, yyyyString, mmString, ddString));
        }
    }

    public ResponseEntity<JSONBody> getLoansByEmail(String email) {
        try {
            List<Loan> loans = lRepository.getLoanedPassByEmail(email);
            JSONWithData<List<Loan>> body = new JSONWithData<>(200, loans);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get all loans of email: " + email);
        }
    }

    public ResponseEntity<JSONBody> collectPasses(String emailTo, List<Integer> loanIds) {
        try {
            Iterator<Loan> loans = lRepository.findAllById(loanIds).iterator();
            if (!loans.hasNext()) {
                throw new BadRequestException(
                        "There are no loan passes for collection");
            }

            List<Loan> loanPassesToCollect = new ArrayList<>();
            while (loans.hasNext()) {
                Loan loan = loans.next();
                loan.setHasCollected(true);
                loanPassesToCollect.add(loan);
            }

            // save(update) back to db after updating the has_collected
            lRepository.saveAll(loanPassesToCollect);

            // get staff name, attraction names
            String staffName = null;
            boolean nameSet = false;
            Set<String> uniqueAttractions = new HashSet<>();
            for (Loan loan : loanPassesToCollect) {
                if (!nameSet) {
                    staffName = loan.getStaff().getFirstName();
                    nameSet = true;
                }

                String attrName = loan.getPass().getAttraction().getName();
                if (!(uniqueAttractions.contains(attrName))) {
                    uniqueAttractions.add(attrName);
                }
            }

            try {
                // Send collection email (asynchronous)
                emailService.sendCollectionEmail(emailTo, staffName, uniqueAttractions.toString());

            } catch (MessagingException e) {
                throw new InternalServerException("Server unable to send collection email to " + staffName
                        + " for collection of " + uniqueAttractions.toString());
            }

            JSONWithMessage body = new JSONWithMessage(200, "Loan passes marked as collected.");
            System.out.println("Returning JSON body in collectPasses service");
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to update collection status of loans");
        }
    }

    public List<Loan> getReminderDateLoans(Date reminderDate) {
        try {
            // Date reminderDate = Date.valueOf("2022-10-23");
            // reminderDate = Date.valueOf(reminderDate.toLocalDate().minusDays(1));
            List<Loan> loans = lRepository.getReminderDateLoans(reminderDate);
            // JSONWithData<List<Loan>> body = new JSONWithData<>(200, loans);
            return loans;

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get all loans of reminder date ");
        }
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Integer getNumPassesLoanedOnDate(Integer staffId, String yyyy, String mm, String dd, Integer aId) {
        return lRepository.getNumPassesLoanedOnDate(staffId, yyyy, mm, dd, aId);
    }

    public Set<Pass> getAvailablePassesForDate(Integer aId, String yyyyString, String mmString, String ddString) {
        Set<Pass> availableAttrPasses = pService.returnAllPassesByAttrId(aId);

        Set<Pass> currentLoansPasses = lRepository.findAllCurrentlyLoanedPassesByAttrId(aId, yyyyString, mmString,
                ddString); // Get all the currently booked passes for that attraction on a particular date

        if (availableAttrPasses != null && currentLoansPasses != null) {
            availableAttrPasses.removeAll(currentLoansPasses);
        }
        return availableAttrPasses;
    }

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

    public List<Loan> assignPasses(TreeSet<Pass> sortedAvailablePasses, Staff staff, int yyyy, int mm, int dd,
            int numPassesRequested) {
        List<Loan> loansMade = new ArrayList<>();
        int i = 0; // counter to add number of passes according to numPassesRequested
        Date startDate = Date.valueOf(String.format("%d-%d-%d", yyyy, mm, dd));

        Iterator<Pass> availablePasses = sortedAvailablePasses.iterator();

        Calendar calendar = new GregorianCalendar(yyyy, mm, dd);
        if (calendar.get(Calendar.DAY_OF_YEAR) % 2 == 0) { // assign passes from behind/front if day is even/odd
            availablePasses = sortedAvailablePasses.descendingIterator();
        }
        
        // Add loan according to the number of passes that borrower wants
        while (availablePasses.hasNext() && (i < numPassesRequested)) {
            Pass pass = availablePasses.next();
            boolean hasCollectedReturned = pass.getAttraction().getPassType() == 'd' ? true : false; // true/false for digital/physical pass
            Loan loan = new Loan(staff, pass, startDate,  hasCollectedReturned, hasCollectedReturned);
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
