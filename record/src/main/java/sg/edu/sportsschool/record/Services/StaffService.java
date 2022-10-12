package sg.edu.sportsschool.record.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.record.Entities.Staff;
import sg.edu.sportsschool.record.Repositories.StaffRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;

@Service
public class StaffService {
    
    @Autowired
    private StaffRepository sRepository;

    public ResponseEntity<JSONBody> getAllStaff() {
        try {
            List<Staff> allStaff = new ArrayList<>();
            sRepository.findAll().forEach(allStaff::add);

            JSONWithData<List<Staff>> body = new JSONWithData<List<Staff>>(200, allStaff);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            JSONWithMessage errMessage = new JSONWithMessage(500, "Server unable to retrieve all staff records");
            return new ResponseEntity<JSONBody>(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<JSONBody> addStaff(Staff staff) {
        try {
            JSONWithData<Staff> body = new JSONWithData<Staff>(200, sRepository.save(staff)); // staff object added into the database is put as argument in JSONWithData body
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            JSONWithMessage errMessage = new JSONWithMessage(500, "Server unable to add staff");
            return new ResponseEntity<JSONBody>(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    

}
