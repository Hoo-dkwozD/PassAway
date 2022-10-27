package sg.edu.sportsschool.Entities;

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

    @ManyToOne
    @JoinColumn(name = "barcode_id")
    private Barcode barcode;

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

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pass) {
            Pass pass = (Pass) obj;
            return (passId.equals(pass.getPassId()))
                    && (attraction.getAttractionId() == pass.attraction.getAttractionId());
        }
        return false;

    }

}
