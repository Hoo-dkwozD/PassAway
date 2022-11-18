package sg.edu.sportsschool.DTO.Response;

import sg.edu.sportsschool.Helper.StaffRole;

public class SignInReponseDto {

    private Integer staffId;
    private StaffRole role;
    private String token;

    public SignInReponseDto(Integer staffId, StaffRole role, String token) {
        this.staffId = staffId;
        this.role = role;
        this.token = token;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public StaffRole getRole() {
        return role;
    }

    public void setRole(StaffRole role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
