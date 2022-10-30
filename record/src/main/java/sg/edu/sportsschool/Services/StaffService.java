package sg.edu.sportsschool.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.sportsschool.DTO.SignInDto;
import sg.edu.sportsschool.DTO.SignInReponseDto;
import sg.edu.sportsschool.DTO.SignupDto;
import sg.edu.sportsschool.DTO.UpdatePasswordDto;
import sg.edu.sportsschool.DTO.UpdateProfileDto;
import sg.edu.sportsschool.Entities.AuthenticationToken;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.BadRequestException;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Repositories.StaffRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;

@Service
public class StaffService {

    private StaffRepository sRepository;
    private AuthenticationService authenticationService;

    @Autowired
    public StaffService(StaffRepository sRepository, AuthenticationService authenticationService) {
        this.sRepository = sRepository;
        this.authenticationService = authenticationService;
    }

    public ResponseEntity<JSONBody> getAllStaff() {
        try {
            List<Staff> allStaff = new ArrayList<>();
            sRepository.findAll().forEach(allStaff::add);
            JSONWithData<List<Staff>> body = new JSONWithData<List<Staff>>(200, allStaff);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get all staff from database");
        }
    }

    public ResponseEntity<JSONBody> getStaff(String token) {
        try {
            Staff s = authenticationService.getStaff(token);
            JSONWithData<Staff> body = new JSONWithData<>(200, s);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get staff from database");
        }
    }

    public ResponseEntity<JSONBody> getStaff(Integer staffId) {
        try {
            Staff s = sRepository.findById(staffId).get();
            JSONWithData<Staff> body = new JSONWithData<>(200, s);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to get staff from database");
        }
    }

    @Transactional
    public ResponseEntity<JSONBody> signUp(SignupDto signupDto) {
        // check if user is already present
        Staff staff = sRepository.findByEmail(signupDto.getEmail());
        if (staff != null) {
            throw new BadRequestException("Staff already exists in database");
        }

        // hash the password
        String encryptedpassword = signupDto.getPassword();

        encryptedpassword = hashPassword(signupDto.getPassword());

        staff = new Staff(signupDto.getEmail(), signupDto.getFirstName(), signupDto.getLastName(),
                signupDto.getContactNumber(), signupDto.getRole(), encryptedpassword, false);

        sRepository.save(staff);

        // create the token
        final AuthenticationToken authenticationToken = new AuthenticationToken(staff);

        authenticationService.saveConfirmationToken(authenticationToken);

        JSONWithMessage body = new JSONWithMessage(200, "Staff created successfully");
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    }

    public ResponseEntity<JSONBody> signIn(SignInDto signInDto) {
        // Check if user exists by email
        String staffEmail = signInDto.getEmail();
        Staff staff = sRepository.findByEmail(staffEmail);

        if (staff == null) {
            throw new BadRequestException("Staff of email: " + staffEmail + " not found");
        }

        if (!staff.getHashedPassword().equals(hashPassword(signInDto.getPassword()))) {
            throw new BadRequestException("Wrong password");
        }

        // if password match, retrieve the token
        AuthenticationToken token = authenticationService.getToken(staff);

        if (token == null) {
            throw new InternalServerException("Token is not present");
        }

        JSONWithData<SignInReponseDto> body = new JSONWithData<>(200,
                new SignInReponseDto(staff.getStaffId(), token.getToken()));
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Staff returnStaffById(Integer staffId) {
        Optional<Staff> optS = sRepository.findById(staffId);
        if (optS.isEmpty()) {
            return null;
        }
        return optS.get();
    }

    private String hashPassword(String password) {
        String hashingAlgorithm = "SHA-256";
        try {
            MessageDigest md = MessageDigest.getInstance(hashingAlgorithm);
            md.update(password.getBytes());
            byte[] digest = md.digest();
            String hash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            return hash;
        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerException(
                    "Exception occurred when hashing password. No such algorithm exists: " + hashingAlgorithm);
        }

    }

    public ResponseEntity<JSONBody> updateStaffProfile(UpdateProfileDto dto) {
        // TO DO
        return null;
    }

    public ResponseEntity<JSONBody> updateStaffPassword(UpdatePasswordDto dto) {
        // TO DO
        return null;
    }

}
