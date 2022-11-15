package sg.edu.sportsschool.DTO.Request;

import java.util.List;

public class CollectPassDto {
    String emailTo;
    List<Integer> loanIds;

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public List<Integer> getLoanIds() {
        return loanIds;
    }

    public void setLoanIds(List<Integer> loanIds) {
        this.loanIds = loanIds;
    }

}
