package sg.edu.sportsschool.DTO.Response;

import sg.edu.sportsschool.Helper.StaffRole;

public class SignInReponseDto {

    private Integer staffId;
    private StaffRole role;

    public SignInReponseDto(Integer staffId, StaffRole role) {
        this.staffId = staffId;
        this.role = role;
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
}
