package sg.edu.sportsschool.DTO;

public class UpdateAttractionDto {
    private Integer attractionId;
    private String name;
    private String description;
    private char passType;
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
    private boolean cannotBook;

    public UpdateAttractionDto() {
    }

    public Integer getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Integer attractionId) {
        this.attractionId = attractionId;
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

    public boolean isCannotBook() {
        return cannotBook;
    }

    public void setCannotBook(boolean cannotBook) {
        this.cannotBook = cannotBook;
    }

}
