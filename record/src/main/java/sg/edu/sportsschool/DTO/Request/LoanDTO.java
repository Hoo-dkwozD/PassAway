package sg.edu.sportsschool.DTO.Request;

public class LoanDTO { 
    // Loan Data Transmission Object sent by front end as an object to ease the
    // transfer of loan details (since a loan has staff and pass entities as
    // FKs) to be recorded in loan table in database
    private Integer staffId;
    private Integer attractionId;

    private int numPasses;
    private int yyyy;
    private int mm;
    private int dd;

    // hasCollected and hasReturned not required to be passed in in the JSON from
    // front end as their value will be false (fixed) for each new loan

    public LoanDTO() {}

    public LoanDTO(Integer staffId, Integer attractionId, int numPasses, int yyyy, int mm, int dd) {
        this.staffId = staffId;
        this.attractionId = attractionId;
        this.numPasses = numPasses;
        this.yyyy = yyyy;
        this.mm = mm;
        this.dd = dd;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Integer attractionId) {
        this.attractionId = attractionId;
    }

    public int getNumPasses() {
        return numPasses;
    }

    public void setNumPasses(int numPasses) {
        this.numPasses = numPasses;
    }

    public int getyyyy() {
        return yyyy;
    }

    public void setyyyy(int yyyy) {
        this.yyyy = yyyy;
    }

    public int getmm() {
        return mm;
    }

    public void setmm(int mm) {
        this.mm = mm;
    }

    public int getdd() {
        return dd;
    }

    public void setdd(int dd) {
        this.dd = dd;
    }
}
