package sg.edu.sportsschool.DTO.Request;

public class UpdateProfileDto {
    private Integer staffId;
    private String email;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String role;
    private boolean cannotBook;

    public UpdateProfileDto() {
    }

    public UpdateProfileDto(Integer staffId, String email, String firstName, String lastName, String contactNumber,
            String role, boolean cannotBook) {
        this.staffId = staffId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.role = role;
        this.cannotBook = cannotBook;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isCannotBook() {
        return cannotBook;
    }

    public void setCannotBook(boolean cannotBook) {
        this.cannotBook = cannotBook;
    }

}
