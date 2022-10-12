package sg.edu.sportsschool.record.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.record.Entities.Staff;
import sg.edu.sportsschool.record.Services.StaffService;
import sg.edu.sportsschool.helper.JSONBody;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllStaff() {
        return staffService.getAllStaff();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addStaff(@RequestBody Staff staff) {
        return staffService.addStaff(staff);
    }
}
