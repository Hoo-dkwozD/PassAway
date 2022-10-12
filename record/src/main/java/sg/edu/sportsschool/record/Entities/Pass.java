package sg.edu.sportsschool.record.Entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "pass")
public class Pass {
    @Id
    private String passId; // primary key, may contain alphabets, to be set based on csv file provided by
                           // admin, not auto generated

    private boolean isLost;

    @ManyToOne
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    @OneToMany(mappedBy = "pass")
    private Set<Loan> loans;

    public Pass() {
    }

    public Pass(String passId, boolean isLost, Attraction attraction) {
        this.passId = passId;
        this.isLost = isLost;
        this.attraction = attraction;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public boolean isLost() {
        return isLost;
    }

    public void setLost(boolean isLost) {
        this.isLost = isLost;
    }

    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }
}
