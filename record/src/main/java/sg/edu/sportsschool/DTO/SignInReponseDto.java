package sg.edu.sportsschool.DTO;

public class SignInReponseDto {

    private Integer staffId;
    private String token;

    public SignInReponseDto(Integer staffId, String token) {
        this.staffId = staffId;
        this.token = token;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
