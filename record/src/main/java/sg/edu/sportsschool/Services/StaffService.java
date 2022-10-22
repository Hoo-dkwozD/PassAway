package sg.edu.sportsschool.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Repositories.StaffRepository;
import sg.edu.sportsschool.helper.CreateJSONResponse;
import sg.edu.sportsschool.helper.JSONBody;

@Service
public class StaffService {

    @Autowired
    private StaffRepository sRepository;

    public ResponseEntity<JSONBody> getAllStaff() {
        CreateJSONResponse<List<Staff>> jsonResponse = new CreateJSONResponse<>();
        try {
            List<Staff> allStaff = new ArrayList<>();
            sRepository.findAll().forEach(allStaff::add);
            return jsonResponse.create(allStaff);

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to retrieve all staff records");
        }
    }

    public ResponseEntity<JSONBody> addStaff(Staff staff) {
        CreateJSONResponse<Staff> jsonResponse = new CreateJSONResponse<>();
        try {
            return jsonResponse.create(sRepository.save(staff));// staff object added to db is added to JSON data field

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to add staff");
        }
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Staff returnStaffById(Integer staffId) {
        try {
            return sRepository.findById(staffId).get();
            
        } catch (Exception e) {
            return null;
        }
    }

}
