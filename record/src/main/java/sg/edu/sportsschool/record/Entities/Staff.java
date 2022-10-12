package sg.edu.sportsschool.record.Entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer staffId;

    private String email;
    private String name;
    private String contactNumber;
    private String role;
    private boolean hasQuit;
    private String hashedPassword;

    @OneToMany(mappedBy = "staff")
    private Set<Loan> loans;
    
    public Staff() {
    }
    

    /**
     * staffId will be auto generated
     * @param email 
     * @param name
     * @param contactNumber
     * @param role
     * @param hasQuit
     * @param hashedPassword to be left as empty string "" for employees that are just added by the admin i.e. user will set their password from the email sent when they login
     */
    public Staff(String email, String name, String contactNumber, String role, boolean hasQuit, String hashedPassword) {
      this.email = email;
      this.name = name;
      this.contactNumber = contactNumber;
      this.role = role;
      this.hasQuit = hasQuit;
      this.hashedPassword = hashedPassword;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isHasQuit() {
        return hasQuit;
    }

    public void setHasQuit(boolean hasQuit) {
        this.hasQuit = hasQuit;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

}
