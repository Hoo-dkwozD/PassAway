package sg.edu.sportsschool.Entities;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer attractionId;

    private String name;
    private String description;
    private char passType;
    private float replacementFee;
    private int numAccompanyingGuests;
    private int maxPassesPerLoan;
    private int maxLoansPerMonth;
    private String address;
    private boolean cannotBook;

    @OneToMany(mappedBy = "attraction")
    private Set<Pass> passes;

    public Attraction() {
    }

    public Attraction(String name, String description, char passType, float replacementFee, int numAccompanyingGuests,
            int maxPassesPerLoan, int maxLoansPerMonth, String address, boolean cannotBook) {
        this.name = name;
        this.description = description;
        this.passType = passType;
        this.replacementFee = replacementFee;
        this.numAccompanyingGuests = numAccompanyingGuests;
        this.maxPassesPerLoan = maxPassesPerLoan;
        this.maxLoansPerMonth = maxLoansPerMonth;
        this.address = address;
        this.cannotBook = cannotBook;
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

    public int getMaxLoansPerMonth() {
        return maxLoansPerMonth;
    }

    public void setMaxLoansPerMonth(int maxLoansPerMonth) {
        this.maxLoansPerMonth = maxLoansPerMonth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCannotBook() {
        return cannotBook;
    }

    public void setCannotBook(boolean cannotBook) {
        this.cannotBook = cannotBook;
    }

    @Override
    public String toString() {
      return "Attraction [attractionId=" + attractionId + ", name=" + name + ", description=" + description
          + ", passType=" + passType + ", replacementFee=" + replacementFee + ", numAccompanyingGuests="
          + numAccompanyingGuests + ", maxPassesPerLoan=" + maxPassesPerLoan + ", maxLoansPerMonth=" + maxLoansPerMonth
          + ", address=" + address + ", cannotBook=" + cannotBook + "]";
    }



}
