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
import sg.edu.sportsschool.Exceptions.BadRequestException;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Repositories.PassRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
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
            throw new InternalServerException("Server unable to retrieve all passes");
        }
    }

    public ResponseEntity<JSONBody> addPasses(Integer aId, MultipartFile cardNumbersCSVFile) {
        List<Pass> passesAdded = new ArrayList<>();

        if (cardNumbersCSVFile == null || cardNumbersCSVFile.isEmpty()) {
            throw new BadRequestException("Bad request. CSV file is null or CSV file is empty");
        }

        Attraction a = aService.returnAttraction(aId);
        if (a == null) {
            throw new InternalServerException("Server unable to find attraction of id: " + aId + " from the database");
        }

        // Get all passIds from csv file, add a new pass for each passId
        List<String[]> passesList = ReadCsv.read(cardNumbersCSVFile);

        if (passesList == null) {
            throw new InternalServerException("Exception occured when reading csv file");
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

        JSONWithData<List<Pass>> body = new JSONWithData<>(200, passesAdded);
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
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
    
    // ------------------------------------------------------------------------------------------------
}
