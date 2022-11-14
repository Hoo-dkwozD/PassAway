package sg.edu.sportsschool.DTO.Request;

import sg.edu.sportsschool.Helper.StaffRole;

public class CreateStaffDto {
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private StaffRole role;
    private boolean cannotBook;
    private boolean isDisabled;

    public CreateStaffDto() {}

    public CreateStaffDto(String firstName, String lastName, String email, 
                            String contactNumber, String role, boolean cannotBook, 
                            boolean isDisabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.cannotBook = cannotBook;
        this.isDisabled = isDisabled;

        switch (role) {
            case "administrator":
                this.role = StaffRole.ADMINISTRATOR;
                break;
            case "borrower":
                this.role = StaffRole.BORROWER;
                break;
            case "gop":
                this.role = StaffRole.GOP;
                break;
            default:
                this.role = StaffRole.BORROWER;
        }
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public StaffRole getRole() {
        return role;
    }

    public void setRole(StaffRole role) {
        this.role = role;
    }

    public boolean isCannotBook() {
        return cannotBook;
    }

    public void setCannotBook(boolean cannotBook) {
        this.cannotBook = cannotBook;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    public void setDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }
}
