package sg.edu.sportsschool.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Entities.Pass;
import sg.edu.sportsschool.Repositories.PassRepository;
import sg.edu.sportsschool.helper.CreateJSONResponse;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;
import sg.edu.sportsschool.helper.ReadCsv;

@Service
public class PassService {

    @Autowired
    private PassRepository pRepository;

    @Autowired
    private AttractionService aService;

    public ResponseEntity<JSONBody> getAllPasses() {
        try {
            List<Pass> results = new ArrayList<>();
            pRepository.findAll().forEach(results::add);

            JSONWithData<List<Pass>> body = new JSONWithData<List<Pass>>(200, results);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve all passes");
            return new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<JSONBody> addPasses(Integer aId, MultipartFile cardNumbersCSVFile) {
        CreateJSONResponse<List<Pass>> jsonResponse = new CreateJSONResponse<>();
        List<Pass> passesAdded = new ArrayList<>();

        if (cardNumbersCSVFile == null || cardNumbersCSVFile.isEmpty()) {
            return jsonResponse.create(400, "Bad request. CSV file is null or CSV file is empty");
        }

        Attraction a = aService.returnAttraction(aId);
        if (a == null) {
            return jsonResponse.create(500, "Server unable to find attraction of id: " + aId + " from the database");
        }

        // Get all passIds from csv file, add a new pass for each passId
        List<String[]> passesList = ReadCsv.read(cardNumbersCSVFile);

        if (passesList == null) {
            return jsonResponse.create(500, "Exception occured when reading csv file");
        }

        for (String[] line : passesList) {
            for (String passId : line) {
                if (!passId.equals("")) {
                    Pass pass = new Pass(passId, false, a);
                    pRepository.save(pass); // Add each passId for that attraction into passes table
                    passesAdded.add(pass);
                }
            }
        }

        return jsonResponse.create(passesAdded);
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods

    public Set<Pass> returnAllPassesByAttrId(Integer aId) {
        try {
            Set<Pass> passes = pRepository.findAllPassesByAttrId(aId);
            return passes;

        } catch (Exception e) {
            return null;
        }
    }

    // -- Following codes are used for testing only
    // public ResponseEntity<JSONBody> getPassesByAttrId(Integer aId) {
    // try {
    // List<Pass> results = pRepository.findAllPassesByAttrId(aId);

    // JSONWithData<List<Pass>> body = new JSONWithData<List<Pass>>(200, results);
    // return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

    // } catch (Exception e) {
    // JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve
    // passes for attraction ID " + aId);
    // return new ResponseEntity<JSONBody>(results,
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
    // ------------------------------------------------------------------------------------------------
}
