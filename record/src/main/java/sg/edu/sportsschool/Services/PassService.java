package sg.edu.sportsschool.Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Entities.Pass;
import sg.edu.sportsschool.Repositories.AttractionRepository;
import sg.edu.sportsschool.Repositories.PassRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;
import sg.edu.sportsschool.helper.ReadCsv;

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
    // try {
    // List<Pass> results = pRepository.findAllPassesByAttractionId(aId);

    // JSONWithData<List<Pass>> body = new JSONWithData<List<Pass>>(200, results);
    // return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

    // } catch (Exception e) {
    // JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve
    // passes for attraction ID " + aId);
    // return new ResponseEntity<JSONBody>(results,
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }

    public ResponseEntity<JSONBody> addPasses(Integer attractionId, MultipartFile cardNumbersCSVFile) {

        List<Pass> passesAdded = new ArrayList<>();

        if (cardNumbersCSVFile == null || cardNumbersCSVFile.isEmpty()) {
            JSONWithMessage errMessage = new JSONWithMessage(400, "Bad request. CSV file is null or CSV file is empty");
            return new ResponseEntity<JSONBody>(errMessage, HttpStatus.BAD_REQUEST);
        }

        Attraction a = aRepository.findById(attractionId).get();

        // Get all passIds from csv file, add a new pass for each passId
        List<String[]> passesList = ReadCsv.read(cardNumbersCSVFile);

        if (passesList == null) {
            JSONWithMessage errMessage = new JSONWithMessage(500,
                    "Exception occured when reading csv file");
            return new ResponseEntity<JSONBody>(errMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        for (String[] line : passesList) {
            for (String passId : line) {
                if (!passId.equals("")) {
                    Pass pass = new Pass(passId, false, a);
                    pRepository.save(pass); // Add each pass into passes table
                    passesAdded.add(pass);
                }
            }
        }

        
        JSONWithData<List<Pass>> body = new JSONWithData<>(200, passesAdded);
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

    }

    // public ResponseEntity<JSONBody> findAllPassesByAttractionId(Integer aId) {
    // try {
    // Set<Pass> passes = pRepository.findAllPassesByAttractionId(aId);
    // JSONWithData<Set<Pass>> body = new JSONWithData<Set<Pass>>(200, passes);
    // return new ResponseEntity<>(body, HttpStatus.OK);

    // } catch (Exception e) {
    // JSONWithMessage errMessage = new JSONWithMessage(500, "Server unable to find
    // all passes by attraction id");
    // return new ResponseEntity<JSONBody>(errMessage,
    // HttpStatus.INTERNAL_SERVER_ERROR);
    // }
    // }
}
