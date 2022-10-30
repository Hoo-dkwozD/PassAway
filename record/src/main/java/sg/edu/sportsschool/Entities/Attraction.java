package sg.edu.sportsschool.Entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "attraction")
@JsonIgnoreProperties({ "barcodeImage" })
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
    private boolean cannotBook;
    private String address;
    private String membershipId;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] barcodeImage;

    private Date expiryDate;

    @Column(columnDefinition = "TEXT")
    private String benefits;

    @Column(columnDefinition = "TEXT")
    private String termsConditions;

    @OneToMany(mappedBy = "attraction")
    private Set<Pass> passes;

    public Attraction() {
    }

    public Attraction(String name, String description, char passType, float replacementFee, int numAccompanyingGuests,
            int maxPassesPerLoan, int maxLoansPerMonth, boolean cannotBook, String address, String membershipId,
            Date expiryDate, String benefits, String termsConditions) {
        this.name = name;
        this.description = description;
        this.passType = passType;
        this.replacementFee = replacementFee;
        this.numAccompanyingGuests = numAccompanyingGuests;
        this.maxPassesPerLoan = maxPassesPerLoan;
        this.maxLoansPerMonth = maxLoansPerMonth;
        this.cannotBook = cannotBook;
        this.address = address;
        this.membershipId = membershipId;
        this.expiryDate = expiryDate;
        this.benefits = benefits;
        this.termsConditions = termsConditions;
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

    public boolean isCannotBook() {
        return cannotBook;
    }

    public void setCannotBook(boolean cannotBook) {
        this.cannotBook = cannotBook;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public byte[] getBarcodeImage() {
        return barcodeImage;
    }

    public void setBarcodeImage(byte[] barcodeImage) {
        this.barcodeImage = barcodeImage;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getTermsConditions() {
        return termsConditions;
    }

    public void setTermsConditions(String termsConditions) {
        this.termsConditions = termsConditions;
    }

    @Override
    public String toString() {
        return name;
    }

}
