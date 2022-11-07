package sg.edu.sportsschool.Entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import sg.edu.sportsschool.Helper.StaffRole;

@Entity
@Table(name = "staff")
@JsonIgnoreProperties({"hashedPassword"})
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffId;

    private String email;
    private String firstName;
    private String lastName;
    private String contactNumber;
    @Enumerated(EnumType.ORDINAL)
    private StaffRole role;
    private String hashedPassword;
    private boolean cannotBook;
    private boolean isDisabled;

    @OneToMany(mappedBy = "staff")
    private Set<Loan> loans;

    public Staff() {}

    public Staff(String email, String firstName, String lastName, String contactNumber, StaffRole role,
                String hashedPassword, boolean cannotBook, boolean isDisabled) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.role = role;
        this.hashedPassword = hashedPassword;
        this.cannotBook = cannotBook;
        this.isDisabled = isDisabled;
    }

    public Staff(String email, String firstName, String lastName, StaffRole role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = null;
        this.role = StaffRole.ADMINISTRATOR;
        this.hashedPassword = null;
        this.cannotBook = true;
        this.isDisabled = false;
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

    public StaffRole getRole() {
        return role;
    }

    public void setRole(StaffRole role) {
        this.role = role;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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
