package sg.edu.sportsschool.DTO.Request;

import sg.edu.sportsschool.Helper.PassType;

public class CreateAttractionDto {
    private String name;
    private String description;
    private PassType passType;
    private float replacementFee;
    private int numAccompanyingGuests;
    private int maxPassesPerLoan;
    private int maxLoansPerMonth;
    private String address;
    private String membershipId;
    private int expiryDateYYYY;
    private int expiryDateMM;
    private int expiryDateDD;
    private String benefits;
    private String termsConditions;

    public CreateAttractionDto() {}

    public CreateAttractionDto(String name, String description, PassType passType, float replacementFee,
            int numAccompanyingGuests, int maxPassesPerLoan, int maxLoansPerMonth, String address, String membershipId,
            int expiryDateYYYY, int expiryDateMM, int expiryDateDD, String benefits, String termsConditions) {
        this.name = name;
        this.description = description;
        this.passType = passType;
        this.replacementFee = replacementFee;
        this.numAccompanyingGuests = numAccompanyingGuests;
        this.maxPassesPerLoan = maxPassesPerLoan;
        this.maxLoansPerMonth = maxLoansPerMonth;
        this.address = address;
        this.membershipId = membershipId;
        this.expiryDateYYYY = expiryDateYYYY;
        this.expiryDateMM = expiryDateMM;
        this.expiryDateDD = expiryDateDD;
        this.benefits = benefits;
        this.termsConditions = termsConditions;
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

    public PassType getPassType() {
        return passType;
    }

    public void setPassType(PassType passType) {
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

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public int getExpiryDateYYYY() {
        return expiryDateYYYY;
    }

    public void setExpiryDateYYYY(int expiryDateYYYY) {
        this.expiryDateYYYY = expiryDateYYYY;
    }

    public int getExpiryDateMM() {
        return expiryDateMM;
    }

    public void setExpiryDateMM(int expiryDateMM) {
        this.expiryDateMM = expiryDateMM;
    }

    public int getExpiryDateDD() {
        return expiryDateDD;
    }

    public void setExpiryDateDD(int expiryDateDD) {
        this.expiryDateDD = expiryDateDD;
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
}
