package sg.edu.sportsschool.DTO.Request;

import java.util.List;

public class LostPassDto {
    private List<Integer> loanIds;

    public LostPassDto(List<Integer> loanIds) {
        this.loanIds = loanIds;
    }

    public List<Integer> getLoanIds() {
        return loanIds;
    }

    public void setLoanIds(List<Integer> loanIds) {
        this.loanIds = loanIds;
    }

}
