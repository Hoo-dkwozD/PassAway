package sg.edu.sportsschool.Services;

import sg.edu.sportsschool.DTO.Request.SignInDto;
import sg.edu.sportsschool.DTO.Response.SignInReponseDto;
import sg.edu.sportsschool.Entities.Auth;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithData;
import sg.edu.sportsschool.Helper.Json.JSONWithMessage;
import sg.edu.sportsschool.Repositories.AuthRepository;
import sg.edu.sportsschool.Repositories.StaffRepository;

import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
public class AuthService {
    private AuthRepository authRepository;
    private StaffRepository staffRepository;
    private StaffService staffService;

    @Autowired
    public AuthService(AuthRepository authRepository, StaffRepository staffRepository, StaffService staffService) {
        this.authRepository = authRepository;
        this.staffRepository = staffRepository;
        this.staffService = staffService;
    }

    public ResponseEntity<JSONBody> signin(SignInDto dto, HttpServletResponse servletResponse) {
        try {
            Staff targetStaff = staffRepository.findByEmail(dto.getEmail());

            if (targetStaff == null || !checkPassword(targetStaff.getHashedPassword(), dto.getPassword()) || targetStaff.isDisabled()) {
                JSONWithMessage results = new JSONWithMessage(400, "Authentication failed. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

                return response;
            } else {
                Map<String, Object> keys = getKeys();
                RSAPrivateKey privateKey = (RSAPrivateKey)keys.get("private");
                RSAPublicKey publicKey = (RSAPublicKey)keys.get("public");
                long loginHours = 2; // 2 hours login time

                Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
                String token = JWT.create()
                    .withIssuer("sss")
                    .withExpiresAt(Instant.now().plus(loginHours, ChronoUnit.HOURS))
                    .withClaim("staff-id", targetStaff.getStaffId())
                    .sign(algorithm);

                SignInReponseDto resDto = new SignInReponseDto(
                    targetStaff.getStaffId(), 
                    targetStaff.getRole(),
                    token
                );

                JSONWithData<SignInReponseDto> results = new JSONWithData<>(200, resDto);
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.OK);

                // Cookie tokenCookie = new Cookie("token", token);
                // tokenCookie.setMaxAge(((int)loginHours) * 60 * 60);
                // tokenCookie.setHttpOnly(true);
                // tokenCookie.setPath("/");
                // servletResponse.addCookie(tokenCookie);

                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to authenticate. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    // Non-REST main functionalities

    public Staff authenticate(String jwtToken) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        Map<String, Object> keys = getKeys();
        RSAPrivateKey privateKey = (RSAPrivateKey)keys.get("private");
        RSAPublicKey publicKey = (RSAPublicKey)keys.get("public");

        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        JWTVerifier verifier = JWT
                                .require(algorithm)
                                .withIssuer("sss")
                                .withClaimPresence("staff-id")
                                .acceptLeeway(10L)
                                .build();
        DecodedJWT jwt = verifier.verify(jwtToken);

        Integer staffId = jwt.getClaim("staff-id").asInt();
        return staffRepository.findByStaffId(staffId);
    }

    // Helper functions

    public boolean checkPassword(String hashedPassword, String password) {
        String candidateHash = staffService.hashPassword(password);

        return hashedPassword.equals(candidateHash);
    }

    public Map<String, Object> getKeys() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeySpecException {
        Auth targetAuth = authRepository.findFirstByOrderByCreatedDateDesc();

        if (targetAuth == null) {
            return generateKeys();
        } else {
            String privateKeyString = targetAuth.getPrivateKey();
            String publicKeyString = targetAuth.getPublicKey();

            byte[] keyBytes = (new Base64()).decode(privateKeyString.getBytes("utf-8"));
            PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPrivateKey privateKey = (RSAPrivateKey)keyFactory.generatePrivate(privateSpec);

            keyBytes = (new Base64()).decode(publicKeyString.getBytes("utf-8"));
            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(keyBytes);
            keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey publicKey = (RSAPublicKey)keyFactory.generatePublic(publicSpec);

            Map<String, Object> results = new HashMap<>();
            results.put("private", privateKey);
            results.put("public", publicKey);

            return results;
        }
    }

    public Map<String, Object> generateKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();

        String privateKeyString = (new Base64()).encodeToString(privateKey.getEncoded());
        String publicKeyString = (new Base64()).encodeToString(publicKey.getEncoded());

        authRepository.save(new Auth(privateKeyString, publicKeyString));

        Map<String, Object> results = new HashMap<>();
        results.put("private", privateKey);
        results.put("public", publicKey);

        return results;
    }
}
