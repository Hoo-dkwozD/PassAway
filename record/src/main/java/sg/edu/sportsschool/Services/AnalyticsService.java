package sg.edu.sportsschool.Services;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;

import sg.edu.sportsschool.DTO.Request.AnalyticsDto;
import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Repositories.LoanRepository;

@Service
public class AnalyticsService {
    private LoanRepository loanRepository;

    @Autowired
    public AnalyticsService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    // public ResponseEntity<JSONBody> loanTrend(AnalyticsDto dto) {
    //     String startDateString = toProperDateString(
    //         dto.getFromYear(), 
    //         dto.getFromMonth(), 
    //         dto.getFromDay()
    //     );

    //     Date startDate = Date.valueOf(startDateString);

    //     String endDateString = toProperDateString(
    //         dto.getToYear(), 
    //         dto.getToMonth(), 
    //         dto.getToDay()
    //     );

    //     Date endDate = Date.valueOf(endDateString);

    //     List<Loan> loans = loanRepository.getLoansBetweenDates(startDate, endDate);

    //     List<String[]> csv = loansToCSV(loans);
    // }

    public void getLoanCSV(AnalyticsDto dto, HttpServletResponse response) {
        response.setContentType("text/csv; charset=utf-8");

        String startDateString = toProperDateString(
            dto.getFromYear(), 
            dto.getFromMonth(), 
            dto.getFromDay()
        );

        Date startDate = Date.valueOf(startDateString);

        String endDateString = toProperDateString(
            dto.getToYear(), 
            dto.getToMonth(), 
            dto.getToDay()
        );

        Date endDate = Date.valueOf(endDateString);

        List<Loan> loans = loanRepository.getLoansBetweenDates(startDate, endDate);

        List<String[]> csv = loansToCSV(loans);

        try {
            CSVWriter writer = new CSVWriter(response.getWriter());

            writer.writeAll(csv);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Non-API methods
    public String toProperDateString(int year, int month, int day) {
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    public List<String[]> loansToCSV(List<Loan> loans) {
        List<String[]> csv = new ArrayList<>();

        csv.add(new String[]{
            "Loan ID", "Pass ID", "Attraction ID", "Attraction Name", "Staff ID", "Staff Name", "Start Date", "Has Collected", "Has Returned"
        });

        for (Loan loan : loans) {
            String[] row = new String[9];

            row[0] = loan.getLoanId() + "";
            row[1] = loan.getPass().getPassId();
            row[2] = loan.getPass().getAttraction().getAttractionId() + "";
            row[3] = loan.getPass().getAttraction().getName();
            row[4] = loan.getStaff().getStaffId() + "";
            row[5] = loan.getStaff().getFirstName() + " " + loan.getStaff().getLastName();
            row[6] = loan.getStartDate().toString();
            row[7] = loan.isHasCollected() ? "1" : "0";
            row[8] = loan.isHasReturned() ? "1" : "0";

            csv.add(row);
        }

        return csv;
    }
}
