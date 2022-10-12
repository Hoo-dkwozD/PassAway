package sg.edu.sportsschool.record.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.record.Entities.Attraction;
import sg.edu.sportsschool.record.Entities.Pass;
import sg.edu.sportsschool.record.Repositories.AttractionRepository;
import sg.edu.sportsschool.record.Repositories.PassRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;

@Service
public class PassService {

    @Autowired
    private PassRepository pRepository;

    @Autowired
    private AttractionRepository aRepository;

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

    // public ResponseEntity<JSONBody> getPassesByAttractionId(Integer aId) {
    //     try {
    //         List<Pass> results = pRepository.findAllPassesByAttractionId(aId);

    //         JSONWithData<List<Pass>> body = new JSONWithData<List<Pass>>(200, results);
    //         return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

    //     } catch (Exception e) {
    //         JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve passes for attraction ID " + aId);
    //         return new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    public ResponseEntity<JSONBody> addPasses(Integer attractionId, MultipartFile cardNumbersCSVFile) {

        List<Pass> passesAdded = new ArrayList<>();

        if (cardNumbersCSVFile == null || cardNumbersCSVFile.isEmpty()) {
            JSONWithMessage errMessage = new JSONWithMessage(400, "Bad request. CSV file is null or CSV file is empty");
            return new ResponseEntity<JSONBody>(errMessage, HttpStatus.BAD_REQUEST);
        }

        Attraction a = aRepository.findById(attractionId).get();

        // Get all passIds from csv file, add a new pass for each passId
        try (Scanner fIn = new Scanner(cardNumbersCSVFile.getInputStream())) {
            while (fIn.hasNextLine()) {
                String line = fIn.nextLine();
                String[] passIds = line.split(",");

                for (String passId : passIds) {
                    Pass pass = new Pass(passId, false, a);
                    passesAdded.add(pass);
                }
            }

            // Add each pass into passes table
            for (Pass pass : passesAdded) {
                pRepository.save(pass);
            }

            JSONWithData<List<Pass>> body = new JSONWithData<>(200, passesAdded);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (IOException e) {
            JSONWithMessage errMessage = new JSONWithMessage(500,
                    "IOException occurred when creating scanner object from card numbers CSV file input stream");
            return new ResponseEntity<JSONBody>(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    // public ResponseEntity<JSONBody> findAllPassesByAttractionId(Integer aId) {
    //     try {
    //         Set<Pass> passes = pRepository.findAllPassesByAttractionId(aId);
    //         JSONWithData<Set<Pass>> body = new JSONWithData<Set<Pass>>(200, passes);
    //         return new ResponseEntity<>(body, HttpStatus.OK);

    //     } catch (Exception e) {
    //         JSONWithMessage errMessage = new JSONWithMessage(500, "Server unable to find all passes by attraction id");
    //         return new ResponseEntity<JSONBody>(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

}
