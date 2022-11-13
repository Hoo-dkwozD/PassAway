package sg.edu.sportsschool.DTO.Request;

public class CompleteRegisterStaffDto {
    private String email;
    private String registerKey;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String password;

    public CompleteRegisterStaffDto(String email, String registerKey, String firstName, 
                                    String lastName, String contactNumber, String password) {
        this.email = email;
        this.registerKey = registerKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getRegisterKey() {
        return registerKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
