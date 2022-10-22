package sg.edu.sportsschool.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/*
 * Helper class with static overloaded methods to return JSON Response and HttpStatus depending on error or no error
 */
public class CreateJSONResponse<T> {

    public CreateJSONResponse() {
    }

    public ResponseEntity<JSONBody> create(T data) {
        JSONWithData<T> body = new JSONWithData<>(200, data);
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    }

    public ResponseEntity<JSONBody> create(int code, String message) {
        JSONWithMessage errMessage = new JSONWithMessage(code, message);

        HttpStatus status;
        switch (code) {
            case 400:
                status = HttpStatus.BAD_REQUEST;
                break;
            default:
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                break;
        }

        return new ResponseEntity<JSONBody>(errMessage, status);

    }
}
