package sg.edu.sportsschool.Entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "staff")
@JsonIgnoreProperties({ "hashedPassword" })
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffId;

    private String email;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private String role;
    private String hashedPassword;
    private boolean cannotBook;

    @OneToMany(mappedBy = "staff")
    private Set<Loan> loans;

    public Staff() {
    }

    public Staff(String email, String firstName, String lastName, String contactNumber, String role,
            String hashedPassword, boolean cannotBook) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contactNumber = contactNumber;
        this.role = role;
        this.hashedPassword = hashedPassword;
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
}
