package sg.edu.sportsschool.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import sg.edu.sportsschool.DTO.Request.LoanDTO;
import sg.edu.sportsschool.DTO.Response.LoanResponseDto;
import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Entities.Pass;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.BadRequestException;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Helper.PassComparator;
import sg.edu.sportsschool.Helper.PassType;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithData;
import sg.edu.sportsschool.Helper.Json.JSONWithMessage;
import sg.edu.sportsschool.Repositories.LoanRepository;

@Service
public class LoanService {

    private LoanRepository lRepository;
    private AttractionService aService;
    private PassService pService;
    private StaffService staffService;
    private EmailService emailService;

    private final String STATIC_FOLDER = System.getProperty("user.dir") + "src/main/resources/static";

    @Autowired
    public LoanService(LoanRepository loRepository, AttractionService aService, PassService pService,
            StaffService staffService, EmailService emailService) {
        this.lRepository = loRepository;
        this.aService = aService;
        this.pService = pService;
        this.staffService = staffService;
        this.emailService = emailService;
    }

    public ResponseEntity<JSONBody> getAllLoans() {
        try {
            List<LoanResponseDto> response = new ArrayList<>();
            List<Loan> loans = lRepository.findAll();
            
            // TODO insert code to get previous borrower from kaiwei

            for (Loan l : loans) {
                Staff s = l.getStaff();
                Pass p = l.getPass();
                response.add(new LoanResponseDto(l.getLoanId(), s.getFirstName(), s.getEmail(), l.getStartDate(),
                        p.getAttraction().getName(), l.isHasCollected(), l.isHasReturned(), p.getPassId(), p.isLost(),
                        "prevBorrowerName", "prevBorrowerContact"));
            }
            JSONWithData<List<LoanResponseDto>> body = new JSONWithData<>(200, response);
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
            throw new InternalServerException("Attraction of id: " + aId + " does not exist in the database");
        }

        Integer staffId = loanDTO.getStaffId();
        Staff staff = staffService.returnStaffById(staffId);
        if (staff == null) {
            throw new InternalServerException(
                    "Staff of staff id: " + staffId + " does not exist in the database");
        }

        String aName = a.getName();
        int aMaxPassesPerLoan = a.getMaxPassesPerLoan();
        int yyyy = loanDTO.getyyyy();
        String yyyyString = yyyy + "";
        int mm = loanDTO.getmm();
        String mmString = (mm < 10) ? "0" + mm : mm + "";
        int dd = loanDTO.getdd();
        String ddString = (dd < 10) ? "0" + dd : dd + "";

        // Cannot book if # passes borrower has + passes requested > aMaxPassesPerLoan
        Integer numPassesLoanedOnDate = lRepository.getNumPassesLoanedOnDate(staffId, yyyyString, mmString, ddString,
                aId);
        boolean newLoan = (numPassesLoanedOnDate > 0) ? false : true;

        if (numPassesLoanedOnDate + numPassesRequested > aMaxPassesPerLoan)
            throw new InternalServerException(String.format(
                    "Max. passes allowed per loan for %s is %d. You currently have %d passes booked for %s. You cannot book %d passes for %s on (yyyy-mm-ddd): %d-%d-%d.",
                    aName, aMaxPassesPerLoan, numPassesLoanedOnDate, aName, numPassesRequested, aName, yyyy, mm,
                    dd));

        // Cannot book if new loan will exceed max loans per month
        int loanCountInMonth = lRepository.getLoanCountInMonth(staffId, yyyyString, mmString).size();
        int maxLoansPerMonth = a.getMaxLoansPerMonth();

        if (newLoan && (loanCountInMonth + 1 > maxLoansPerMonth))
            throw new InternalServerException(String.format(
                    "Unable to make anymore loans. Max. loans per month is %d. You have %d loans for (yyyy-mm): %d-%d currently. Cancel other loans if you wish to make new loans.",
                    maxLoansPerMonth, loanCountInMonth, yyyy, mm));

        // Cannot book if # available pass for that attraction for the date < # passes that user requested
        Set<Pass> availablePassesForLoan = getAvailablePassesForDate(aId, yyyyString, mmString, ddString);
        if (availablePassesForLoan.size() < numPassesRequested)
            throw new InternalServerException(String.format(
                    "Unable to book %d pass(es). There are insufficient available pass(es) for %s for loan on (yyyy-mm-dd): %s-%s-%s",
                    numPassesRequested, aName, yyyyString, mmString, ddString));

        // assign passes if can book
        TreeSet<Pass> sortedAvailablePasses = sortSetPasses(availablePassesForLoan);
        assignPasses(sortedAvailablePasses, staff, yyyy, mm, dd, numPassesRequested);

        // Send confirmation email
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM YYYY"); // e.g. Wednesday, 2 Oct 2022
        String visitDate = dateFormat.format(Date.valueOf(String.format("%d-%d-%d", yyyy, mm, dd)));
        dateFormat = new SimpleDateFormat("d MMM YYYY"); // e.g. 2 Oct 2022
        String ballotDate = dateFormat.format(new Date(System.currentTimeMillis()));
        
        // Send confirmation email with corporate letter if digital pass
        if (a.getPassType() == PassType.DIGITAL) { // check barcode present if digital pass
            String barcodeImagePath = a.getBarcodeImage();
            byte[] barcodeImage = null;
            try {
                barcodeImage = Files.readAllBytes(Paths.get(STATIC_FOLDER, barcodeImagePath));
            } catch (IOException e) {
                throw new BadRequestException("Barcode for attraction " + a.getName() + " is not set yet.");
            }

            try {
                emailService.sendEmailWithCorpLetter(staff.getEmail(), staff.getFirstName(), ballotDate, visitDate, a, barcodeImage);
                System.out.println("Confirmation email sent successfully");
                
            } catch (MessagingException e) {
                // TODO Log the error here
            }
        } else {
            try {
                emailService.sendEmailWithAuthLetter(staff.getEmail(), staff.getFirstName(), ballotDate, visitDate, a);
                
            } catch (MessagingException e) {
                // TODO: Log the error here
            }
        }

        System.out.println("Returning body in loan service");
        return new ResponseEntity<JSONBody>(new JSONWithMessage(200, "Loans added successfully"), HttpStatus.OK);

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
            List<LoanResponseDto> response = new ArrayList<>();
            List<Loan> loans = lRepository.getLoanedPassByEmail(email);
            
            // TODO insert code to get previous borrower from kaiwei

            for (Loan l : loans) {
                Staff s = l.getStaff();
                Pass p = l.getPass();
                response.add(new LoanResponseDto(l.getLoanId(), s.getFirstName(), s.getEmail(), l.getStartDate(),
                        p.getAttraction().getName(), l.isHasCollected(), l.isHasReturned(), p.getPassId(), p.isLost(),
                        "prevBorrowerName", "prevBorrowerContact"));
            }
            JSONWithData<List<LoanResponseDto>> body = new JSONWithData<>(200, response);
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

    public void assignPasses(TreeSet<Pass> sortedAvailablePasses, Staff staff, int yyyy, int mm, int dd,
            int numPassesRequested) {
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
            boolean hasCollectedReturned = pass.getAttraction().getPassType() == PassType.DIGITAL ? true : false; // true/false for
                                                                                                     // digital/physical
                                                                                                     // pass
            Loan loan = new Loan(staff, pass, startDate, hasCollectedReturned, hasCollectedReturned);
            lRepository.save(loan);
            i++;
        }

    }

    // -- Following codes are used for testing only

    //
    // ------------------------------------------------------------------------------------------------

}
