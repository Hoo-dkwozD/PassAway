package sg.edu.sportsschool.DTO.Response;

import java.sql.Date;

import sg.edu.sportsschool.Helper.PassType;

public class LoanResponseDto {
    private Integer loanId;
    private String staffName;
    private String staffEmail;
    private Date visitDate;
    private String attractionName;
    private boolean hasCollected;
    private boolean hasReturned;
    private String passId;
    private PassType passType;
    private boolean isLost;
    private String prevBorrowerName;
    private String prevBorrowerContact;

    public LoanResponseDto() {}

    public LoanResponseDto(Integer loanId, String staffName, String staffEmail, Date visitDate, String attractionName,
            boolean hasCollected, boolean hasReturned, String passId, PassType passType, boolean isLost, String prevBorrowerName,
            String prevBorrowerContact) {
        this.loanId = loanId;
        this.staffName = staffName;
        this.staffEmail = staffEmail;
        this.visitDate = visitDate;
        this.attractionName = attractionName;
        this.hasCollected = hasCollected;
        this.hasReturned = hasReturned;
        this.passId = passId;
        this.isLost = isLost;
        this.prevBorrowerName = prevBorrowerName;
        this.prevBorrowerContact = prevBorrowerContact;
        this.passType = passType;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public boolean isHasCollected() {
        return hasCollected;
    }

    public void setHasCollected(boolean hasCollected) {
        this.hasCollected = hasCollected;
    }

    public boolean isHasReturned() {
        return hasReturned;
    }

    public void setHasReturned(boolean hasReturned) {
        this.hasReturned = hasReturned;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean isLost) {
        this.isLost = isLost;
    }

    public String getPrevBorrowerName() {
        return prevBorrowerName;
    }

    public void setPrevBorrowerName(String prevBorrowerName) {
        this.prevBorrowerName = prevBorrowerName;
    }

    public String getPrevBorrowerContact() {
        return prevBorrowerContact;
    }

    public void setPrevBorrowerContact(String prevBorrowerContact) {
        this.prevBorrowerContact = prevBorrowerContact;
    }
}
