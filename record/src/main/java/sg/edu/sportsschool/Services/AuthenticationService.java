package sg.edu.sportsschool.Services;

import sg.edu.sportsschool.Entities.AuthenticationToken;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Helper.JSONBody;
import sg.edu.sportsschool.Helper.JSONWithData;
import sg.edu.sportsschool.Repositories.TokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private TokenRepository tokenRepository;
    
    @Autowired
    public AuthenticationService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<JSONBody> saveConfirmationToken(AuthenticationToken authenticationToken) {
        try {
            JSONWithData<AuthenticationToken> body = new JSONWithData<AuthenticationToken>(200,
                    tokenRepository.save(authenticationToken));
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to save confirmation token into database");
        }
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public AuthenticationToken getToken(Staff staff) {
        return tokenRepository.findByStaff(staff);
    }

    public Staff getStaff(String token) {
        AuthenticationToken aToken = tokenRepository.findByToken(token);
        return aToken.getStaff();

    }
}
