package sg.edu.sportsschool.DTO.Response;

public class PassResponseDto {
    private String passId;
    private Integer attractionId;
    private String name;
    private String passType;
    private boolean lost;

    public PassResponseDto() {
    }

    public PassResponseDto(String passId, Integer attractionId, String name, String passType, boolean lost) {
        this.passId = passId;
        this.attractionId = attractionId;
        this.name = name;
        this.passType = passType;
        this.lost = lost;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
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

    public String getPassType() {
        return passType;
    }

    public void setPassType(String passType) {
        this.passType = passType;
    }

    public boolean isLost() {
        return lost;
    }

    public void setLost(boolean lost) {
        this.lost = lost;
    }

}
