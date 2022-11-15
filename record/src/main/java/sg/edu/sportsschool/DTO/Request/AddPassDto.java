package sg.edu.sportsschool.DTO.Request;

public class AddPassDto {
    private Integer attractionId;
    private String passId;

    public Integer getAttractionId() {
        return attractionId;
    }

    public void setAttractionId(Integer attractionId) {
        this.attractionId = attractionId;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }
}
