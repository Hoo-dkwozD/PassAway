package sg.edu.sportsschool.DTO.Response;

public class SignInReponseDto {

    private Integer staffId;
    private String role;

    public SignInReponseDto(Integer staffId, String role) {
        this.staffId = staffId;
        this.role = role;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
