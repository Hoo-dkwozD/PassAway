package sg.edu.sportsschool.Entities;

import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "pass")
// @JsonIgnoreProperties({"attraction"})
public class Pass {
    @Id
    private String passId;

    private boolean isLost;

    @ManyToOne
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    @OneToMany(mappedBy = "pass")
    private Set<Loan> loans;

    public Pass() {}

    public Pass(String passId, boolean isLost, Attraction attraction) {
        this.passId = passId;
        this.isLost = isLost;
        this.attraction = attraction;
    }

    public String getPassId() {
        return passId;
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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pass) {
            Pass pass = (Pass)obj;

            return (
                passId.equals(pass.getPassId())
            ) && (
                attraction.getAttractionId() == pass.attraction.getAttractionId()
            );
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(passId, attraction.getAttractionId());
    }

    @Override
    public String toString() {
      return passId + "";
    }

    
}
