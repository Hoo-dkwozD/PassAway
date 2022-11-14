package sg.edu.sportsschool.DTO.Request;

public class RegisterStaffDto {
    private String email;

    public RegisterStaffDto() {}

    public RegisterStaffDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
