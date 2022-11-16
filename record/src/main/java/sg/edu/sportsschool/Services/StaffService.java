package sg.edu.sportsschool.Services;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.CompleteRegisterStaffDto;
import sg.edu.sportsschool.DTO.Request.CreateStaffDto;
import sg.edu.sportsschool.DTO.Request.RegisterStaffDto;
import sg.edu.sportsschool.DTO.Request.UpdatePasswordDto;
import sg.edu.sportsschool.DTO.Request.UpdateProfileDto;
import sg.edu.sportsschool.Entities.Loan;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Helper.ReadCsv;
import sg.edu.sportsschool.Helper.StaffRole;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithData;
import sg.edu.sportsschool.Helper.Json.JSONWithMessage;
import sg.edu.sportsschool.Repositories.LoanRepository;
import sg.edu.sportsschool.Repositories.StaffRepository;

@Service
public class StaffService {

    private StaffRepository sRepository;
    // private AuthenticationService authenticationService;
    private EmailService emailService;
    private LoanRepository lRepository;

    @Autowired
    public StaffService(StaffRepository sRepository, EmailService emailService, LoanRepository lRepository) {
        this.sRepository = sRepository;
        // this.authenticationService = authenticationService;
        this.emailService = emailService;
        this.lRepository = lRepository;
    }

    public ResponseEntity<JSONBody> getAllStaff() {
        try {
            List<Staff> allStaff = new ArrayList<>();
            sRepository.findAll().forEach(allStaff::add);
            JSONWithData<List<Staff>> body = new JSONWithData<List<Staff>>(200, allStaff);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve staff details. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    // public ResponseEntity<JSONBody> getStaff(String token) {
    // try {
    // Staff s = authenticationService.getStaff(token);
    // JSONWithData<Staff> body = new JSONWithData<>(200, s);
    // return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    // } catch (Exception e) {
    // throw new InternalServerException("Server unable to get staff from
    // database");
    // }
    // }

    public ResponseEntity<JSONBody> getStaff(Integer staffId) {
        try {
            Staff s = sRepository.findById(staffId).get();
            JSONWithData<Staff> body = new JSONWithData<>(200, s);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve staff detail. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> createStaffs(MultipartFile csvFile) {
        try {
            List<String[]> csvData = ReadCsv.read(csvFile);

            if (csvData.size() == 0) {
                JSONWithData<Map<String, List<Staff>>> results = new JSONWithData<>(201, new HashMap<>());
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.CREATED);

                return response;
            } else if (csvData.get(0).length < 2) {
                JSONWithMessage results = new JSONWithMessage(400, "The uploaded CSV file is not valid. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

                return response;
            } else {
                List<Staff> addedStaff = csvData
                                            .stream()
                                            .map(
                                                arr -> new Staff(
                                                    arr[arr.length - 2], 
                                                    arr[arr.length - 3].split(" ")[0], 
                                                    arr[arr.length - 3].split(" ")[1], 
                                                    StaffRole.BORROWER
                                                )
                                            )
                                            .collect(Collectors.toList());

                for (Staff targetStaff : addedStaff) {
                    String targetStaffEmail = targetStaff.getEmail();
                    Staff staff = sRepository.findByEmail(targetStaffEmail);
                    if (staff == null
                            && (targetStaffEmail.endsWith("@sportsschool.edu.sg")
                                    || targetStaffEmail.endsWith("@nysi.org.sg"))) {
                        sRepository.save(targetStaff);
                    }
                }

                Map<String, List<Staff>> data = new HashMap<>();
                addedStaff = sRepository.findAll();
                data.put("staffs", addedStaff);
                JSONWithData<Map<String, List<Staff>>> results = new JSONWithData<>(201, data);
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.CREATED);

                return response;
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to parse file as CSV file. " + e.getMessage());
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> createStaff(CreateStaffDto staffDto) {
        try {
            String targetStaffEmail = staffDto.getEmail();
            Staff staff = sRepository.findByEmail(targetStaffEmail);
            if (staff == null
                    && (targetStaffEmail.endsWith("@sportsschool.edu.sg")
                            || targetStaffEmail.endsWith("@nysi.org.sg")
                            || targetStaffEmail.endsWith(".edu.sg")
                            || targetStaffEmail.endsWith("@gmail.com"))) {
                Staff targetStaff = new Staff();
                targetStaff.setEmail(targetStaffEmail);
                targetStaff.setFirstName(staffDto.getFirstName());
                targetStaff.setLastName(staffDto.getLastName());
                targetStaff.setContactNumber(staffDto.getContactNumber());
                targetStaff.setRole(staffDto.getRole());
                targetStaff.setHashedPassword(null);
                targetStaff.setCannotBook(staffDto.isCannotBook());
                targetStaff.setDisabled(staffDto.isDisabled());

                sRepository.save(targetStaff);

                Map<String, Staff> data = new HashMap<>();
                data.put("staff", targetStaff);
                JSONWithData<Map<String, Staff>> results = new JSONWithData<>(201, data);
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.CREATED);

                return response;
            } else {
                JSONWithMessage results = new JSONWithMessage(400,
                        "The staff to add already exists or has invalid fields. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

                return response;
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable create new staff. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> registerStaff(RegisterStaffDto staffDto) {
        try {
            // Check if user is already present
            Staff staff = sRepository.findByEmail(staffDto.getEmail());

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(401,
                        "The specified user is not authorised to use this service. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (staff.isRegistered()) {
                JSONWithMessage results = new JSONWithMessage(403, "The specified user is already registered. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.FORBIDDEN);

                return response;
            } else {
                String registerKey = randomStringToken(20);
                String encodedString = Base64.getEncoder().encodeToString(registerKey.getBytes());
                staff.setHashedPassword(hashPassword(encodedString));

                sRepository.save(staff);

                emailService.sendRegistrationEmail(staff.getEmail(), staff.getFirstName() + " " + staff.getLastName(),
                        URLEncoder.encode(encodedString, StandardCharsets.UTF_8));

                JSONBody results = new JSONBody(204);
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NO_CONTENT);

                return response;
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not register the user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> completeStaffRegistration(CompleteRegisterStaffDto staffDto) {
        try {
            // Check if user is already present
            Staff staff = sRepository.findByEmail(staffDto.getEmail());

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(401,
                        "The specified user is not authorised to use this service. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (staff.isRegistered()) {
                JSONWithMessage results = new JSONWithMessage(403, "The specified user is already registered. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.FORBIDDEN);

                return response;
            } else if (staff.getHashedPassword().equals(hashPassword(staffDto.getRegisterKey()))) {
                String encryptedPassword = hashPassword(staffDto.getPassword());
                staff.setFirstName(staffDto.getFirstName());
                staff.setLastName(staffDto.getLastName());
                staff.setContactNumber(staffDto.getContactNumber());
                staff.setHashedPassword(encryptedPassword);
                staff.setCannotBook(false);
                staff.setDisabled(false);
                staff.setRegistered(true);

                sRepository.save(staff);

                Map<String, Staff> data = new HashMap<>();
                data.put("staff", staff);
                JSONWithData<Map<String, Staff>> body = new JSONWithData<>(201, data);
                return new ResponseEntity<JSONBody>(body, HttpStatus.CREATED);
            } else {
                JSONWithMessage results = new JSONWithMessage(401, "Unauthorised account creation. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not register the user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> updateStaffProfileAdmin(Integer staffId, UpdateProfileDto dto) {
        try {
            Staff staff = sRepository.findByStaffId(staffId);

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(404, "User not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            }

            staff.setFirstName(dto.getFirstName());
            staff.setLastName(dto.getLastName());
            staff.setContactNumber(dto.getContactNumber());
            staff.setRole(dto.getRole());
            staff.setCannotBook(dto.isCannotBook());
            staff.setDisabled(dto.isDisabled());

            if (!staff.getEmail().equals(dto.getEmail())) {
                String registerKey = randomStringToken(20);
                String encodedString = Base64.getEncoder().encodeToString(registerKey.getBytes());
                staff.setHashedPassword(hashPassword(encodedString));

                sRepository.save(staff);

                emailService.sendEmailChangeEmail(staff.getEmail(), staff.getFirstName() + " " + staff.getLastName(),
                        encodedString);

                JSONBody results = new JSONBody(204);
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NO_CONTENT);

                return response;
            } else {
                sRepository.save(staff);

                JSONWithData<Staff> body = new JSONWithData<>(200, staff);
                return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not change user details. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> updateStaffProfile(Integer staffId, UpdateProfileDto dto) {
        try {
            Staff staff = sRepository.findByStaffId(staffId);

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(404, "User not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            }

            staff.setFirstName(dto.getFirstName());
            staff.setLastName(dto.getLastName());
            staff.setContactNumber(dto.getContactNumber());

            if (!staff.getEmail().equals(dto.getEmail())) {
                String registerKey = randomStringToken(20);
                String encodedString = Base64.getEncoder().encodeToString(registerKey.getBytes());
                staff.setHashedPassword(hashPassword(encodedString));

                sRepository.save(staff);

                emailService.sendEmailChangeEmail(staff.getEmail(), staff.getFirstName() + " " + staff.getLastName(),
                        encodedString);

                JSONBody results = new JSONBody(204);
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NO_CONTENT);

                return response;
            } else {
                sRepository.save(staff);

                JSONWithData<Staff> body = new JSONWithData<>(200, staff);
                return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not change user details. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> updateStaffPassword(UpdatePasswordDto dto) {
        try {
            Staff staff = sRepository.findByStaffId(dto.getStaffId());

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(404, "User not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            } else if (!staff.getHashedPassword().equals(hashPassword(dto.getOldPassword()))) {
                JSONWithMessage results = new JSONWithMessage(401,
                        "The specified user is not authorised to use this service. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
                JSONWithMessage results = new JSONWithMessage(400,
                        "The specified new password does not match the confirmation password. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

                return response;
            } else {
                staff.setHashedPassword(hashPassword(dto.getNewPassword()));

                sRepository.save(staff);

                JSONWithData<Staff> body = new JSONWithData<>(200, staff);
                return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not change user password. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> resetPassword(int staffId) {
        try {
            Staff staff = sRepository.findByStaffId(staffId);

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(404, "User not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            } else {
                String registerKey = randomStringToken(20);
                String encodedString = Base64.getEncoder().encodeToString(registerKey.getBytes());
                staff.setHashedPassword(hashPassword(encodedString));

                emailService.sendPasswordChangeEmail(staff.getEmail(), staff.getFirstName() + " " + staff.getLastName(),
                        encodedString);

                sRepository.save(staff);

                JSONWithData<Staff> body = new JSONWithData<>(200, staff);
                return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not change user password. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> disableStaff(int staffId) {
        try {
            Staff staff = sRepository.findByStaffId(staffId);

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(404, "User not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            } else {
                staff.setDisabled(true);

                sRepository.save(staff);

                List<Loan> futureLoans = lRepository.getFutureLoanedPassByEmail(staff.getEmail(), new Date( System.currentTimeMillis()));

                for (Loan loan : futureLoans) {
                    lRepository.delete(loan);
                }

                JSONWithData<Staff> body = new JSONWithData<>(200, staff);
                return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not soft-delete user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> lockStaff(int staffId) {
        try {
            Staff staff = sRepository.findByStaffId(staffId);

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(404, "User not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            } else {
                staff.setCannotBook(true);

                sRepository.save(staff);

                JSONWithData<Staff> body = new JSONWithData<>(200, staff);
                return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not lock the user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> unlockStaff(int staffId) {
        try {
            Staff staff = sRepository.findByStaffId(staffId);

            if (staff == null) {
                JSONWithMessage results = new JSONWithMessage(404, "User not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            } else {
                staff.setCannotBook(false);

                sRepository.save(staff);

                JSONWithData<Staff> body = new JSONWithData<>(200, staff);
                return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server could not unlock the user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    // public ResponseEntity<JSONBody> signIn(SignInDto signInDto) {
    // // Check if user exists by email
    // String staffEmail = signInDto.getEmail();
    // Staff staff = sRepository.findByEmail(staffEmail);

    // if (staff == null) {
    // throw new BadRequestException("Staff of email: " + staffEmail + " not
    // found");
    // }

    // if (!staff.getHashedPassword().equals(hashPassword(signInDto.getPassword())))
    // {
    // throw new BadRequestException("Wrong password");
    // }

    // // if password match, retrieve the token
    // AuthenticationToken token = authenticationService.getToken(staff);

    // if (token == null) {
    // throw new InternalServerException("Token is not present");
    // }

    // JSONWithData<SignInReponseDto> body = new JSONWithData<>(200,
    // new SignInReponseDto(staff.getStaffId(), token.getToken()));
    // return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
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
                    "Exception occurred when hashing password. No such algorithm exists: " + hashingAlgorithm);
        }
    }

    public String randomStringToken(int length) {
        int upperLimit = 122;
        int lowerLimit = 65;
        Random random = new Random();

        String result = random.ints(lowerLimit, upperLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return result;
    }

    public ResponseEntity<JSONBody> getAdmin() {
        try {
            List<Staff> adminStaff = new ArrayList<>();
            sRepository.findByRole(StaffRole.ADMINISTRATOR).forEach(adminStaff::add);
            JSONWithData<List<Staff>> body = new JSONWithData<List<Staff>>(200, adminStaff);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve staff detail. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> addAdmin(Integer staffId) {
        try {
            Staff s = sRepository.findById(staffId).get();

            s.setRole(StaffRole.ADMINISTRATOR);

            sRepository.save(s);

            JSONWithData<Staff> body = new JSONWithData<>(200, s);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve staff detail. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> removeAdmin(Integer staffId) {
        try {
            Staff s = sRepository.findById(staffId).get();

            s.setRole(StaffRole.BORROWER);

            sRepository.save(s);

            JSONWithData<Staff> body = new JSONWithData<>(200, s);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve staff detail. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

}
