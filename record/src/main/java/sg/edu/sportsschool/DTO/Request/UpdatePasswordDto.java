package sg.edu.sportsschool.DTO.Request;

public class UpdatePasswordDto {
    private Integer staffId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public UpdatePasswordDto() {
    }

    public Integer getStaffId() {
        return staffId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
