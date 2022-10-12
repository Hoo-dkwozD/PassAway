package sg.edu.sportsschool.record.Entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "attraction")
public class Attraction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer attractionId;

    private String name;
    private String description;
    private char passType;
    private float replacementFee;
    private int numAccompanyingGuests;
    private int maxPassesPerLoan;

    @OneToMany(mappedBy = "attraction")
    private Set<Pass> passes;

    public Attraction() {
    }

    public Attraction(String name, String description, char passType, float replacementFee, int numAccompanyingGuests,
            int maxPassesPerLoan) {
        this.name = name;
        this.description = description;
        this.passType = passType;
        this.replacementFee = replacementFee;
        this.numAccompanyingGuests = numAccompanyingGuests;
        this.maxPassesPerLoan = maxPassesPerLoan;
    }

    public Integer getAttractionId() {
        return attractionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getPassType() {
        return passType;
    }

    public void setPassType(char passType) {
        this.passType = passType;
    }

    public float getReplacementFee() {
        return replacementFee;
    }

    public void setReplacementFee(float replacementFee) {
        this.replacementFee = replacementFee;
    }

    public int getNumAccompanyingGuests() {
        return numAccompanyingGuests;
    }

    public void setNumAccompanyingGuests(int numAccompanyingGuests) {
        this.numAccompanyingGuests = numAccompanyingGuests;
    }

    public int getMaxPassesPerLoan() {
        return maxPassesPerLoan;
    }

    public void setMaxPassesPerLoan(int maxPassesPerLoan) {
        this.maxPassesPerLoan = maxPassesPerLoan;
    }

}
