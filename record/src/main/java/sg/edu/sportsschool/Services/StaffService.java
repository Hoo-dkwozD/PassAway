package sg.edu.sportsschool.Services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.SignInDto;
import sg.edu.sportsschool.DTO.Request.SignupDto;
import sg.edu.sportsschool.DTO.Request.UpdatePasswordDto;
import sg.edu.sportsschool.DTO.Request.UpdateProfileDto;
import sg.edu.sportsschool.DTO.Response.SignInReponseDto;
import sg.edu.sportsschool.Entities.AuthenticationToken;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.BadRequestException;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Helper.JSONBody;
import sg.edu.sportsschool.Helper.JSONWithData;
import sg.edu.sportsschool.Helper.JSONWithMessage;
import sg.edu.sportsschool.Helper.ReadCsv;
import sg.edu.sportsschool.Helper.StaffRole;
import sg.edu.sportsschool.Repositories.StaffRepository;

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

    public ResponseEntity<JSONBody> createStaffs(MultipartFile csvFile) {
        try {
            List<String[]> csvData = ReadCsv.read(csvFile);

            if (csvData.size() == 0 || (csvData.size() == 1 && csvData.get(0).length != 2)) {
                JSONWithData<Map<String, List<Staff>>> results = new JSONWithData<>(201, new HashMap<>());
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.CREATED);

                return response;
            } else if (csvData.get(0).length != 2) {
                JSONWithMessage results = new JSONWithMessage(400, "The uploaded CSV file is not valid. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

                return response;
            } else {
                List<Staff> addedStaff = csvData
                                            .stream()
                                            .map(
                                                arr -> new Staff(
                                                    arr[1], 
                                                    arr[0].split(" ")[0], 
                                                    arr[0].split(" ")[1], 
                                                    StaffRole.BORROWER
                                                )
                                            )
                                            .collect(Collectors.toList());

                for (Staff targetStaff : addedStaff) {
                    String targetStaffEmail = targetStaff.getEmail();
                    Staff staff = sRepository.findByEmail(targetStaffEmail);
                    if (
                        staff == null 
                        && (
                            targetStaffEmail.endsWith("@sportsschool.edu.sg") 
                            || targetStaffEmail.endsWith("@nysi.org.sg")
                        )
                    ) {
                        sRepository.save(targetStaff);
                    }
                }

                Map<String, List<Staff>> data = new HashMap<>();
                data.put("staffs", addedStaff);
                JSONWithData<Map<String, List<Staff>>> results = new JSONWithData<>(201, data);
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.CREATED);

                return response;
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to parse file as CSV file. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> signUp(SignupDto signupDto) {
        // Check if user is already present
        Staff staff = sRepository.findByEmail(signupDto.getEmail());
        if (staff.getHashedPassword() != null) {
            throw new BadRequestException("Staff already exists in database. ");
        }

        // Hash the password
        String encryptedPassword = hashPassword(signupDto.getPassword());
        staff.setFirstName(signupDto.getFirstName());
        staff.setLastName(signupDto.getLastName());
        staff.setContactNumber(signupDto.getContactNumber());
        staff.setHashedPassword(encryptedPassword);
        staff.setCannotBook(false);

        sRepository.save(staff);

        Map<String, Staff> data = new HashMap<>();
        data.put("staff", staff);
        JSONWithData<Map<String, Staff>> body = new JSONWithData<>(201, data);
        return new ResponseEntity<JSONBody>(body, HttpStatus.CREATED);
    }

    // public ResponseEntity<JSONBody> signIn(SignInDto signInDto) {
    //     // Check if user exists by email
    //     String staffEmail = signInDto.getEmail();
    //     Staff staff = sRepository.findByEmail(staffEmail);

    //     if (staff == null) {
    //         throw new BadRequestException("Staff of email: " + staffEmail + " not found");
    //     }

    //     if (!staff.getHashedPassword().equals(hashPassword(signInDto.getPassword()))) {
    //         throw new BadRequestException("Wrong password");
    //     }

    //     // if password match, retrieve the token
    //     AuthenticationToken token = authenticationService.getToken(staff);

    //     if (token == null) {
    //         throw new InternalServerException("Token is not present");
    //     }

    //     JSONWithData<SignInReponseDto> body = new JSONWithData<>(200,
    //             new SignInReponseDto(staff.getStaffId(), token.getToken()));
    //     return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    // }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Staff returnStaffById(Integer staffId) {
        Optional<Staff> optS = sRepository.findById(staffId);
        if (optS.isEmpty()) {
            return null;
        }
        return optS.get();
    }

    public String hashPassword(String password) {
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
                "Exception occurred when hashing password. No such algorithm exists: " + hashingAlgorithm
            );
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
