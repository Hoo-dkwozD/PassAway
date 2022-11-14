package sg.edu.sportsschool.DTO.Request;

public class AddAttractionImageURLDto {
    private String url;

    public AddAttractionImageURLDto() {}

    public AddAttractionImageURLDto(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
