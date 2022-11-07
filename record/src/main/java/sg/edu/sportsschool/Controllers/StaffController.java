package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.CompleteRegisterStaffDto;
import sg.edu.sportsschool.DTO.Request.CreateStaffDto;
import sg.edu.sportsschool.DTO.Request.RegisterStaffDto;
import sg.edu.sportsschool.DTO.Request.UpdatePasswordDto;
import sg.edu.sportsschool.DTO.Request.UpdateProfileDto;
import sg.edu.sportsschool.Helper.JSONBody;
import sg.edu.sportsschool.Services.StaffService;

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

    @PostMapping("s")
    public ResponseEntity<JSONBody> createStaffs(@RequestParam("file") MultipartFile file) {
        return staffService.createStaffs(file);
    }

    @PostMapping("")
    public ResponseEntity<JSONBody> createStaff(@RequestBody CreateStaffDto dto) {
        return staffService.createStaff(dto);
    }

    @PostMapping("/register")
    public ResponseEntity<JSONBody> registerStaff(@RequestBody RegisterStaffDto dto) {
        return staffService.registerStaff(dto);
    }

    @PostMapping("/register/complete")
    public ResponseEntity<JSONBody> completeStaffRegistration(@RequestBody CompleteRegisterStaffDto dto) {
        return staffService.completeStaffRegistration(dto);
    }

    // @PostMapping("/signup")
    // public ResponseEntity<JSONBody> signup(@RequestBody RegisterStaffDto signupDto) {
    //     return staffService.signUp(signupDto);
    // }

    // @PostMapping("/signin")
    // public ResponseEntity<JSONBody> signIn(@RequestBody SignInDto signInDto) {
    //     return staffService.signIn(signInDto);
    // }

    @PutMapping("/update-profile")
    public ResponseEntity<JSONBody> updateStaffProfile(@RequestBody UpdateProfileDto dto) {
        return staffService.updateStaffProfile(dto);
    }

    @PutMapping("/update-password")
    public ResponseEntity<JSONBody> updateStaffPassword(@RequestBody UpdatePasswordDto dto) {
        return staffService.updateStaffPassword(dto);
    }


}
