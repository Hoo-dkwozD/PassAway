package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.DTO.Request.SignInDto;
import sg.edu.sportsschool.DTO.Request.SignupDto;
import sg.edu.sportsschool.DTO.Request.UpdatePasswordDto;
import sg.edu.sportsschool.DTO.Request.UpdateProfileDto;
import sg.edu.sportsschool.Services.StaffService;
import sg.edu.sportsschool.helper.JSONBody;

@CrossOrigin
@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllStaff() {
        return staffService.getAllStaff();
    }

    @GetMapping(path = "/get")
    public ResponseEntity<JSONBody> getStaff(@RequestBody String token) {
        return staffService.getStaff(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<JSONBody> signup(@RequestBody SignupDto signupDto) {
        return staffService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<JSONBody> signIn(@RequestBody SignInDto signInDto) {
        return staffService.signIn(signInDto);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<JSONBody> updateStaffProfile(@RequestBody UpdateProfileDto dto) {
        return staffService.updateStaffProfile(dto);
    }

    @PutMapping("/update-password")
    public ResponseEntity<JSONBody> updateStaffPassword(@RequestBody UpdatePasswordDto dto) {
        return staffService.updateStaffPassword(dto);
    }


}
