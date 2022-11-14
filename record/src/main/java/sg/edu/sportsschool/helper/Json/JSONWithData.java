package sg.edu.sportsschool.Helper.Json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class JSONWithData<T> extends JSONBody {
    private T data;

    public JSONWithData() {
    }

    public JSONWithData(int code, T data) {
        super(code);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
