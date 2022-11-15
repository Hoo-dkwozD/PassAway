package sg.edu.sportsschool.DTO.Request;

public class UpdateLoanDto {
    private Integer loanId;
    private boolean hasCollected;
    private boolean hasReturned;

    public UpdateLoanDto() {}

    public UpdateLoanDto(Integer loanId, boolean hasCollected, boolean hasReturned) {
        this.loanId = loanId;
        this.hasCollected = hasCollected;
        this.hasReturned = hasReturned;
    }

    public Integer getLoanId() {
        return loanId;
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
}
