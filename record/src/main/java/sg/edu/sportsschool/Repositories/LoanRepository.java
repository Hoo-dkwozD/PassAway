package sg.edu.sportsschool.Repositories;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Entities.Pass;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    @Query("""
            SELECT l.pass FROM Loan l
            WHERE l.pass.attraction.attractionId =:aId
            AND SUBSTRING(l.startDate, 1, 4) = :yyyy AND SUBSTRING(l.startDate, 6, 2) = :mm
            AND SUBSTRING(l.startDate, 9, 2) = :dd
            """)
    Set<Pass> getLoanedPassesByDate(Integer aId, String yyyy, String mm, String dd); // returns empty set of length 0 if no passes are loaned for the date

    @Query(value = """
            SELECT loans.pass.attraction.attractionId, loans.startDate, COUNT(loans.loanId) FROM
            Loan loans WHERE loans.loanId IN 
            (SELECT l.loanId FROM Loan l WHERE l.staff.staffId = :staffId AND SUBSTRING(l.startDate, 1, 4) = :yyyy
            AND SUBSTRING(l.startDate, 6, 2) = :mm)
            GROUP BY loans.pass.attraction.attractionId, loans.startDate
            """) // Query is equivalent to Filter loans by the month and year as t1 SELECT aId, startDate, count(loan_id) FROM t1 GROUP BY aId, startDate in MYSQL
    ArrayList<Object[]> getLoanCountInMonth(Integer staffId, String yyyy, String mm); // length of arrayList is the number of loans made by staffId for year yyyy and month mm

    @Query(value = """
            SELECT COUNT(l.pass) FROM Loan l 
            WHERE l.staff.staffId = :staffId AND SUBSTRING(l.startDate, 1, 4) = :yyyy 
            AND SUBSTRING(l.startDate, 6, 2) = :mm AND SUBSTRING(l.startDate, 9, 2) = :dd
            AND l.pass.attraction.attractionId = :aId
            """)
    Integer getNumPassesLoanedOnDate(Integer staffId, String yyyy, String mm, String dd, Integer aId);

    @Query(value = """
            SELECT l FROM Loan l WHERE l.staff.email = :staffEmail AND l.pass.passId = :passId AND l.hasCollected = FALSE
            """)
    List<Loan> getUncollectedLoanedPasses(String staffEmail, String passId);

    @Query(value = """
            SELECT l FROM Loan l WHERE l.staff.email = :staffEmail
            """)
    List<Loan> getLoanedPassByEmail(String staffEmail);

    @Query(value = """
            SELECT l FROM Loan l WHERE l.staff.email = :staffEmail AND l.startDate >= :currentDate 
            """)
    List<Loan> getFutureLoanedPassByEmail(String staffEmail, Date currentDate);

    @Query(value = """
            SELECT * FROM Loan WHERE start_date = :reminderDate AND has_collected = FALSE
            """, nativeQuery = true)
    List<Loan> getReminderDateLoans(Date reminderDate);

    @Query(value = """
            SELECT * FROM Loan WHERE start_date >= :startDate AND start_date <= :endDate
            """, nativeQuery = true)
    List<Loan> getLoansBetweenDates(Date startDate, Date endDate);

    @Query(value = """
            SELECT * FROM Loan WHERE start_date < :overdueDate AND has_returned = FALSE
            """, nativeQuery = true)
    List<Loan> getOverdueLoans(Date overdueDate);

    @Query(value = """
            SELECT * FROM LOAN WHERE pass_id = :passId AND staff_id != :staffId
            AND has_returned = FALSE AND start_date < :startDate
            ORDER BY start_date DESC
            """, nativeQuery = true)
    List<Loan> getPrevBorrowers(String passId, Integer staffId, Date startDate);

    @Query(value = """
            SELECT * FROM Loan WHERE pass_id = :passId AND staff_id != :staffId
            AND has_collected = FALSE AND has_returned = FALSE AND start_date > :startDate
            """, nativeQuery = true)
    List<Loan> getAffectedLoansOfLostPass(String passId, Integer staffId, Date startDate);
}
