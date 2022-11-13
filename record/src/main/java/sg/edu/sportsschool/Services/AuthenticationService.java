package sg.edu.sportsschool.Services;

import sg.edu.sportsschool.Entities.AuthenticationToken;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithData;
import sg.edu.sportsschool.Repositories.TokenRepository;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.StreamUtils;

// TODO Deprecation

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

    public String getPrivateKey() throws RuntimeException {
        return getResourceFile("private.key");
    }

    public String getPublicKey() throws RuntimeException {
        return getResourceFile("public.key");
    }

    public String getResourceFile(String fileName) throws RuntimeException {
        String keyPath = getClass().getClassLoader().getResource(fileName).getPath();
        Resource key = new FileSystemResource(keyPath);

        try {
            return StreamUtils.copyToString(key.getInputStream(), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
