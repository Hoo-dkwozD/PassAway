package sg.edu.sportsschool.Repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.scheduling.annotation.Async;

import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Entities.Pass;

public interface LoanRepository extends CrudRepository<Loan, Integer> {

    
    /**
     * Purpose of this query is to get all the passes that are loaned out
     * for a particular date and attraction. So that we can remove these loaned out passes
     * from all the passes for an attraction to get the passes available for loans
     * Return set of all pass IDs from loan table for the attraction id on a selected date
     */
    @Async
    @Query("""
            SELECT l.pass FROM Loan l
            WHERE l.pass.attraction.attractionId =:aId
            AND SUBSTRING(l.startDate, 1, 4) = :yyyy AND SUBSTRING(l.startDate, 6, 2) = :mm
            AND SUBSTRING(l.startDate, 9, 2) = :dd
            """)
    Set<Pass> findAllCurrentlyLoanedPassesByAttrId(Integer aId, String yyyy, String mm, String dd);

    /**
     * Purpose of this query is to know how many loans a staff has made in a particular month
     * as there is a rule that a staff can only borrow x e.g. 2 number of loans per month
     * Returns a list of array of objects where each object is a row returned from the SELECT statement
     * Example of response [{attractionId1, startDate1, loanId1}, {attractionId2, startDate2, loanId2}]
     * e.g. [{1, "2022-09-21", 1}, {1, "2022-09-23", 2}] => There are two loans in month of september as the size of array is 2
     */
    @Async
    @Query(value = """
            SELECT loans.pass.attraction.attractionId, loans.startDate, COUNT(loans.loanId) FROM
            Loan loans WHERE loans.loanId IN 
            (SELECT l.loanId FROM Loan l WHERE l.staff.staffId = :staffId AND SUBSTRING(l.startDate, 1, 4) = :yyyy
            AND SUBSTRING(l.startDate, 6, 2) = :mm)
            GROUP BY loans.pass.attraction.attractionId, loans.startDate
            """) // Query is equivalent to Filter loans by the month and year as t1 SELECT aId, startDate, count(loan_id) FROM t1 GROUP BY aId, startDate in MYSQL
    ArrayList<Object[]> getLoanCountInMonth(Integer staffId, String yyyy, String mm); // length of arrayList is the number of loans made by staffId for year yyyy and month mm

    /** 
     * Returns the number of passes loaned by a staff on a date
     */
    @Async
    @Query(value = """
            SELECT COUNT(l.pass) FROM Loan l 
            WHERE l.staff.staffId = :staffId AND SUBSTRING(l.startDate, 1, 4) = :yyyy 
            AND SUBSTRING(l.startDate, 6, 2) = :mm AND SUBSTRING(l.startDate, 9, 2) = :dd
            AND l.pass.attraction.attractionId = :aId
            """)
    Integer getNumPassesLoanedOnDate(Integer staffId, String yyyy, String mm, String dd, Integer aId);

    
}
