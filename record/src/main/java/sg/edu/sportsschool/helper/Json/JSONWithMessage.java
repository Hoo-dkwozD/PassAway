package sg.edu.sportsschool.Helper.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class JSONWithMessage extends JSONBody {
    private String message;

    public JSONWithMessage() {
    }

    public JSONWithMessage(int code, String message) {
        super(code);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
