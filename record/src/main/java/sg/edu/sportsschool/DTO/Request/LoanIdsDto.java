package sg.edu.sportsschool.DTO.Request;

import java.util.List;

public class LoanIdsDto {
    List<Integer> loanIds;

    public List<Integer> getLoanIds() {
        return loanIds;
    }

    public void setLoanIds(List<Integer> loanIds) {
        this.loanIds = loanIds;
    }
}
