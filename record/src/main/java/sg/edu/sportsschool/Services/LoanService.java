package sg.edu.sportsschool.Services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.DTO.Request.CollectPassDto;
import sg.edu.sportsschool.DTO.Request.LoanDTO;
import sg.edu.sportsschool.DTO.Request.LoanIdsDto;
import sg.edu.sportsschool.DTO.Request.UpdateLoanDto;
import sg.edu.sportsschool.DTO.Response.LoanResponseDto;
import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Entities.Pass;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.BadRequestException;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Helper.PassComparator;
import sg.edu.sportsschool.Helper.PassType;
import sg.edu.sportsschool.Helper.StaffRole;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithData;
import sg.edu.sportsschool.Helper.Json.JSONWithMessage;
import sg.edu.sportsschool.Repositories.LoanRepository;
import sg.edu.sportsschool.Repositories.PassRepository;
import sg.edu.sportsschool.Repositories.StaffRepository;

@Service
public class LoanService {

    private LoanRepository lRepository;
    private AttractionService aService;
    private PassRepository pRepository;
    private StaffRepository sRepository;
    private EmailService emailService;

    private final String STATIC_FOLDER = System.getProperty("user.dir") + "/src/main/resources/static/";

    @Autowired
    public LoanService(LoanRepository loRepository, AttractionService aService, PassRepository pRepository,
            StaffRepository sRepository, EmailService emailService) {
        this.lRepository = loRepository;
        this.aService = aService;
        this.pRepository = pRepository;
        this.sRepository = sRepository;
        this.emailService = emailService;
    }

    public ResponseEntity<JSONBody> getAllLoans() {
        try {
            List<Loan> loans = lRepository.findAll();

            List<LoanResponseDto> response = createLoanResponseDto(loans);

            JSONWithData<List<LoanResponseDto>> body = new JSONWithData<>(200, response);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to retrieve all loans");
        }
    }

    public ResponseEntity<JSONBody> getLoan(int id) {
        try {
            Optional<Loan> optLoan = lRepository.findById(id);
            if (!optLoan.isPresent()) {
                throw new BadRequestException("Loan with id " + id + " does not exist");
            }

            Loan l = optLoan.get();
            List<Loan> temp = new ArrayList<>();
            temp.add(l);

            List<LoanResponseDto> response = createLoanResponseDto(temp);

            return new ResponseEntity<JSONBody>(new JSONWithData<>(200, response), HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get loan of id: " + id);
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
        Staff staff = sRepository.findByStaffId(staffId);
        if (staff == null) {
            throw new InternalServerException(
                    "Staff of staff id: " + staffId + " does not exist in the database");
        }

        // Check if staff can book
        if (staff.isCannotBook()) {
            throw new BadRequestException("Staff of staff id: " + staffId + " cannot book");
        }

        String aName = a.getName();
        int aMaxPassesPerLoan = a.getMaxPassesPerLoan();
        int yyyy = loanDTO.getyyyy();
        String yyyyString = yyyy + "";
        int mm = loanDTO.getmm();
        String mmString = (mm < 10) ? "0" + mm : mm + "";
        int dd = loanDTO.getdd();
        String ddString = (dd < 10) ? "0" + dd : dd + "";

        // Cannot book if today < visit date - 8 months
        Date vDate = Date.valueOf(String.format("%d-%d-%d", yyyy, mm, dd));
        if (isBookingTooEarly(vDate)) {
            throw new BadRequestException(
                    "Booking date is too early. You can only book up to 8 months before visit date.");
        }

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
                    "Unable to make anymore loans. Max. loans per month for %s is %d. You have %d loans for (yyyy-mm): %d-%d currently. Cancel other loans if you wish to make new loans.",
                    aName, maxLoansPerMonth, loanCountInMonth, yyyy, mm));

        // Cannot book if # available pass for that attraction for the date < # passes
        // that user requested
        Set<Pass> availablePassesForLoan = getAvailablePassesForDate(aId, yyyyString, mmString, ddString);
        if (availablePassesForLoan.size() < numPassesRequested)
            throw new InternalServerException(String.format(
                    "Unable to book %d pass(es). There are insufficient available pass(es) for %s for loan on (yyyy-mm-dd): %s-%s-%s",
                    numPassesRequested, aName, yyyyString, mmString, ddString));

        // Send confirmation email
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM YYYY"); // e.g. Wednesday, 2 Oct 2022
        String visitDate = dateFormat.format(vDate);
        dateFormat = new SimpleDateFormat("d MMM YYYY"); // e.g. 2 Oct 2022
        String ballotDate = dateFormat.format(new Date(System.currentTimeMillis()));

        // Send confirmation email with corporate letter if digital pass
        if (a.getPassType() == PassType.DIGITAL) { // check barcode present if digital pass
            String barcodeImagePath = a.getBarcodeImage();
            if (barcodeImagePath == null || barcodeImagePath.equals("")) {
                throw new BadRequestException("Attraction of id: " + aId + " does not have a barcode image");
            }
            // System.out.println("barcodeImagePath " + barcodeImagePath);
            byte[] barcodeImage = null;
            try {
                // System.out.println(Paths.get(STATIC_FOLDER, barcodeImagePath));
                barcodeImage = Files.readAllBytes(Paths.get(STATIC_FOLDER, barcodeImagePath));
            } catch (Exception e) {
                throw new InternalServerException(
                        "Server unable to read barcode image at path: " + Paths.get(STATIC_FOLDER, barcodeImagePath));
            }

            try {
                emailService.sendEmailWithCorpLetter(staff.getEmail(), staff.getFirstName(), ballotDate, visitDate, a,
                        barcodeImage);
                System.out.println("Confirmation email sent successfully");

            } catch (MessagingException e) {
                // Log error
                System.out.println("Server unable to send loan confirmation email to: " + staff.getEmail());
                
            }
        } else {
            try {
                emailService.sendEmailWithAuthLetter(staff.getEmail(), staff.getFirstName(), ballotDate, visitDate, a);

            } catch (MessagingException e) {
                // TODO: Log the error here
                System.out.println("Server unable to send loan confirmation email to: " + staff.getEmail());
            }
        }

        // assign passes if can book and email can be sent successfully
        TreeSet<Pass> sortedAvailablePasses = sortSetPasses(availablePassesForLoan);
        List<Loan> loans = assignPasses(sortedAvailablePasses, staff, yyyy, mm, dd, numPassesRequested);
        List<LoanResponseDto> response = createLoanResponseDto(loans);

        System.out.println("Returning body in loan service");
        return new ResponseEntity<JSONBody>(new JSONWithData<List<LoanResponseDto>>(200, response), HttpStatus.OK);

    }

    public ResponseEntity<JSONBody> getNumAvailablePassesForMonth(Integer aId, int yyyy, int mm) {
        String yyyyString = yyyy + "";
        String mmString = (mm < 10) ? "0" + mm : mm + "";

        Map<String, Integer> res = new HashMap<>();
        Set<Pass> allAttrPasses = pRepository.findAllPassesByAttrId(aId);

        int totalNumPasses = allAttrPasses.size();
        if (totalNumPasses == 0) {
            throw new BadRequestException("No passes have been allocated yet for attraction id: " + aId);
        }

        try {
            YearMonth yearMonthObj = YearMonth.of(yyyy, mm);
            int daysInMonth = yearMonthObj.lengthOfMonth();
            for (int i = 1; i <= daysInMonth; i++) {
                String ddString = (i < 10) ? "0" + i : i + "";
                Integer numAvailPasses = totalNumPasses
                        - lRepository.getLoanedPassesByDate(aId, yyyyString, mmString, ddString).size();
                res.put(yearMonthObj.atDay(i).toString(), numAvailPasses); // e.g. {"2020-12-01": 30}
            }

            JSONWithData<Map<String, Integer>> body = new JSONWithData<>(200, res);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException(String.format(
                    "Server unable to get all currently booked passes of attraction ID %d for (yyyy-mm) %s-%s ",
                    aId, yyyyString, mmString));
        }
    }

    public ResponseEntity<JSONBody> getLoansByEmail(String email) {
        try {
            List<LoanResponseDto> response = new ArrayList<>();
            List<Loan> loans = lRepository.getLoanedPassByEmail(email);

            for (Loan l : loans) {
                Staff s = l.getStaff();
                Pass p = l.getPass();
                List<Loan> prevBorrowers = getPrevBorrowers(p.getPassId(), s.getStaffId(),
                        l.getStartDate());
                String prevBorrowerName = (prevBorrowers.size() > 0) ? prevBorrowers.get(0).getStaff().getFirstName()
                        : "None";
                String prevBorrowerContact = (prevBorrowers.size() > 0)
                        ? prevBorrowers.get(0).getStaff().getContactNumber()
                        : "None";
                response.add(new LoanResponseDto(l.getLoanId(), s.getFirstName(), s.getEmail(), l.getStartDate(),
                        p.getAttraction().getName(), l.isHasCollected(), l.isHasReturned(), p.getPassId(), p.getAttraction().getPassType(), p.isLost(),
                        prevBorrowerName, prevBorrowerContact));
            }
            JSONWithData<List<LoanResponseDto>> body = new JSONWithData<>(200, response);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get all loans of email: " + email);
        }
    }

    public List<Loan> getReminderDateLoans(Date reminderDate) {
        try {
            List<Loan> loans = lRepository.getReminderDateLoans(reminderDate);
            return loans;

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get all loans of reminder date ");
        }
    }

    public ResponseEntity<JSONBody> updateCollected(UpdateLoanDto dto) {
        Optional<Loan> optLoan = lRepository.findById(dto.getLoanId());

        if (!optLoan.isPresent()) {
            JSONWithMessage results = new JSONWithMessage(404, "Loan not found");
            return new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);
        }

        Loan loan = optLoan.get();
        loan.setHasCollected(dto.isHasCollected());
        lRepository.save(loan);

        LoanResponseDto res = createLoanResponseDto(loan);
        JSONWithData<LoanResponseDto> result = new JSONWithData<>(200, res);
        ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(result, HttpStatus.OK);
        if (!dto.isHasCollected()) {
            return response;
        }

        Staff s = loan.getStaff();
        String emailTo = s.getEmail();
        String staffName = s.getFirstName();
        try {
            // Send collection email (asynchronous) only if pass is marked as collected
            emailService.sendCollectionEmail(emailTo, staffName, loan.getPass().getAttraction().getName());

        } catch (MessagingException e) {
            System.out.println("Server unable to send collection email to " + staffName
                    + " for collection of " + loan.getPass().getAttraction().getName());
        }

        return response;
    }

    public ResponseEntity<JSONBody> updateReturned(UpdateLoanDto dto) {
        Optional<Loan> optLoan = lRepository.findById(dto.getLoanId());
        if (!optLoan.isPresent()) {
            JSONWithMessage results = new JSONWithMessage(404, "Loan not found");
            return new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);
        }
        Loan loan = optLoan.get();

        loan.setHasCollected(dto.isHasCollected());
        loan.setHasReturned(dto.isHasReturned());
        lRepository.save(loan);

        LoanResponseDto res = createLoanResponseDto(loan);
        JSONWithData<LoanResponseDto> result = new JSONWithData<>(200, res);
        ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(result, HttpStatus.OK);
        if (!dto.isHasReturned()) {
            return response;
        }

        // update previous borrowers if current borrower has returned pass
        List<Loan> prevBorrowers = lRepository.getPrevBorrowers(loan.getPass().getPassId(),
                loan.getStaff().getStaffId(), loan.getStartDate());
        System.out.println(prevBorrowers);
        for (Loan l : prevBorrowers) {
            l.setHasCollected(true);
            l.setHasReturned(true);
        }
        lRepository.saveAll(prevBorrowers);

        return response;

    }

    public ResponseEntity<JSONBody> cancelLoans(LoanIdsDto dto) {
        List<Integer> loanIds = dto.getLoanIds();
        if (loanIds == null || loanIds.size() == 0) {
            throw new BadRequestException("Please select loan(s) to cancel.");
        }
        List<Integer> canCancelLoanIds = new ArrayList<>();
        List<Integer> cannotCancelLoanIds = new ArrayList<>();
        List<Loan> allLoans = lRepository.findAllById(loanIds);
        for (Loan loan : allLoans) {
            if (loan.getStartDate().before(Date.valueOf(LocalDate.now().plusDays(1)))) {
                cannotCancelLoanIds.add(loan.getLoanId());
            } else {
                canCancelLoanIds.add(loan.getLoanId());
            }
        }
        try {
            lRepository.deleteAllById(canCancelLoanIds);

            if (cannotCancelLoanIds.size() > 0) {
                return new ResponseEntity<JSONBody>(new JSONWithMessage(200,
                        "successfully deleted loans: " + canCancelLoanIds + " Unable to cancel loans of: "
                                + cannotCancelLoanIds + " 1 day before the visit date."),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<JSONBody>(
                        new JSONWithMessage(200, "successfully deleted loans: " + canCancelLoanIds), HttpStatus.OK);
            }

        } catch (Exception e) {
            throw new InternalServerException("Server unable to delete loans");
        }
    }

    public ResponseEntity<JSONBody> reportLostPass(LoanIdsDto dto) {
        List<Integer> loanIds = dto.getLoanIds();
        if (loanIds == null || loanIds.size() == 0) {
            throw new BadRequestException("Please select loan(s) to report lost passes.");
        }

        List<Loan> lostLoans = lRepository.findAllById(loanIds);

        List<Staff> admin = sRepository.findByRole(StaffRole.ADMINISTRATOR); // 0 - borrower, 1 - admin, 2 - GOP
            String[] adminEmails = new String[admin.size()];
            admin.stream()
                    .map(s -> s.getEmail())
                    .collect(Collectors.toList())
                    .toArray(adminEmails);

        // write all passes belonging to lost loans as being lost to prevent further
        // borrowing
        for (Loan lostLoan : lostLoans) {
            Pass p = lostLoan.getPass();
            p.setLost(true);
            pRepository.saveAndFlush(p);
            Staff s = lostLoan.getStaff();
            s.setCannotBook(true);
            lRepository.saveAndFlush(lostLoan);

            // Send email to notify administrator
            try {
                emailService.notifyAdminLostPass(adminEmails, s.getFirstName(), p.getAttraction().getName(),
                        p.getPassId());

            } catch (MessagingException e) {
                System.out.println("Server unable to send email to notify administrator of lost pass for "
                        + s.getFirstName() + " for " + p.getAttraction().getName());
            }
        }
        
        for (Loan lostLoan : lostLoans) {

            List<Loan> affectedLoans = lRepository.getAffectedLoansOfLostPass(lostLoan.getPass().getPassId(),
                    lostLoan.getStaff().getStaffId(),
                    lostLoan.getStartDate());
            System.out.println("affectedLoans" + affectedLoans);

            for (Loan affectedLoan : affectedLoans) {
                lRepository.deleteById(affectedLoan.getLoanId());
                Staff affectedLoanStaff = affectedLoan.getStaff();

                String yyyyString = String.valueOf(affectedLoan.getStartDate().toLocalDate().getYear());
                int mm = affectedLoan.getStartDate().toLocalDate().getMonthValue();
                String mmString = (mm < 10) ? "0" + mm : mm + "";
                int dd = affectedLoan.getStartDate().toLocalDate().getDayOfMonth();
                String ddString = (dd < 10) ? "0" + dd : dd + "";
                Set<Pass> availablePasses = getAvailablePassesForDate(
                        affectedLoan.getPass().getAttraction().getAttractionId(), yyyyString, mmString, ddString);
                System.out.println("availablePasses for " + yyyyString + mmString + ddString + " : " + availablePasses);

                if (availablePasses.size() == 0) {
                    // send email to them to make new booking
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d MMM YYYY");
                    String visitDate = dateFormat.format(affectedLoan.getStartDate());
                    try {
                        emailService.sendRebookLoanEmail(affectedLoanStaff.getEmail(), affectedLoanStaff.getFirstName(),
                                affectedLoan.getPass().getAttraction().getName(), visitDate);

                    } catch (MessagingException e) {
                        // TODO: Log the error
                        System.out.println("Error sending email to " + affectedLoanStaff.getEmail());
                    }

                } else {
                    // reassign their passes (add new loans but dont send email)
                    Pass availablePass = availablePasses.iterator().next();
                    Loan newLoan = new Loan(affectedLoan.getStaff(), availablePass,
                            affectedLoan.getStartDate(), false, false);
                    // newLoan.setLoanId(affectedLoan.getLoanId());
                    lRepository.saveAndFlush(newLoan);
                }
            }

        }

        return new ResponseEntity<JSONBody>(new JSONWithMessage(200, "successfully reported lost passes: " + loanIds),
                HttpStatus.OK);
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Integer getNumPassesLoanedOnDate(Integer staffId, String yyyy, String mm, String dd, Integer aId) {
        return lRepository.getNumPassesLoanedOnDate(staffId, yyyy, mm, dd, aId);
    }

    public Set<Pass> getAvailablePassesForDate(Integer aId, String yyyyString, String mmString, String ddString) {
        Set<Pass> availableAttrPasses = pRepository.findAllPassesByAttrId(aId);
        System.out.println("availableAttrPasses" + availableAttrPasses);
        Set<Pass> currentLoansPasses = lRepository.getLoanedPassesByDate(aId, yyyyString, mmString, ddString);
        System.out.println("currentLoansPasses" + currentLoansPasses);
        availableAttrPasses.removeAll(currentLoansPasses);
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
        int i = 0; // counter to add number of passes according to numPassesRequested
        Date startDate = Date.valueOf(String.format("%d-%d-%d", yyyy, mm, dd));

        Iterator<Pass> availablePasses = sortedAvailablePasses.iterator();

        Calendar calendar = new GregorianCalendar(yyyy, mm, dd);
        if (calendar.get(Calendar.DAY_OF_YEAR) % 2 == 0) { // assign passes from behind/front if day is even/odd
            availablePasses = sortedAvailablePasses.descendingIterator();
        }
        List<Loan> res = new ArrayList<>();
        // Add loan according to the number of passes that borrower wants
        while (availablePasses.hasNext() && (i < numPassesRequested)) {
            Pass pass = availablePasses.next();
            boolean hasCollectedReturned = pass.getAttraction().getPassType() == PassType.DIGITAL ? true : false; // true/false
                                                                                                                  // digital/physical
                                                                                                                  // pass
            Loan loan = new Loan(
                    staff,
                    pass,
                    startDate,
                    hasCollectedReturned,
                    hasCollectedReturned);
            res.add(loan);
            lRepository.save(loan);
            i++;
        }
        return res;

    }

    public boolean isBookingTooEarly(Date visitDate) {
        // returns true if today < visitDate - 8 months
        Date earliestDateToBook = Date.valueOf(visitDate.toLocalDate().minusMonths(8));
        Date today = new Date(System.currentTimeMillis());
        return today.before(earliestDateToBook);
    }

    public List<Loan> getPrevBorrowers(String passId, Integer staffId, Date visitDate) {
        return lRepository.getPrevBorrowers(passId, staffId, visitDate);
    }

    public List<LoanResponseDto> createLoanResponseDto(List<Loan> loans) {
        List<LoanResponseDto> res = new ArrayList<>();

        for (Loan l : loans) {
            Staff s = l.getStaff();
            Pass p = l.getPass();
            List<Loan> prevBorrowers = getPrevBorrowers(p.getPassId(), s.getStaffId(),
                    l.getStartDate());
            String prevBorrowerName = (prevBorrowers.size() > 0) ? prevBorrowers.get(0).getStaff().getFirstName()
                    : "None";
            String prevBorrowerContact = (prevBorrowers.size() > 0)
                    ? prevBorrowers.get(0).getStaff().getContactNumber()
                    : "None";
            res.add(new LoanResponseDto(l.getLoanId(), s.getFirstName(), s.getEmail(), l.getStartDate(),
                    p.getAttraction().getName(), l.isHasCollected(), l.isHasReturned(), p.getPassId(), p.getAttraction().getPassType(), p.isLost(),
                    prevBorrowerName, prevBorrowerContact));
        }

        return res;
    }
    public LoanResponseDto createLoanResponseDto(Loan l) {
        Staff s = l.getStaff();
        Pass p = l.getPass();
        List<Loan> prevBorrowers = getPrevBorrowers(p.getPassId(), s.getStaffId(),
                l.getStartDate());
        String prevBorrowerName = (prevBorrowers.size() > 0) ? prevBorrowers.get(0).getStaff().getFirstName()
                : "None";
        String prevBorrowerContact = (prevBorrowers.size() > 0)
                ? prevBorrowers.get(0).getStaff().getContactNumber()
                : "None";
        return new LoanResponseDto(l.getLoanId(), s.getFirstName(), s.getEmail(), l.getStartDate(),
                p.getAttraction().getName(), l.isHasCollected(), l.isHasReturned(), p.getPassId(), p.getAttraction().getPassType(), p.isLost(),
                prevBorrowerName, prevBorrowerContact);
    

    }

    // -- Following codes are used for testing only

    //
    // ------------------------------------------------------------------------------------------------

}
