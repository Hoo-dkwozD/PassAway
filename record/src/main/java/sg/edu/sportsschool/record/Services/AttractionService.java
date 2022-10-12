package sg.edu.sportsschool.record.Services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.record.Entities.Attraction;
import sg.edu.sportsschool.record.Repositories.AttractionRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;

@Service
public class AttractionService {
    @Autowired
    private AttractionRepository aRepository;

    public ResponseEntity<JSONBody> getAllAttractions() {
        try {
            List<Attraction> attractions = new ArrayList<>();
            aRepository.findAll().forEach(attractions::add);

            JSONWithData<List<Attraction>> body = new JSONWithData<>(200, attractions);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            JSONWithMessage body = new JSONWithMessage(500, "Server unable to retrieve attractions");
            return new ResponseEntity<JSONBody>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<JSONBody> getAttraction(Integer aId) {
        try {
            Attraction a = aRepository.findById(aId).get();

            JSONWithData<Attraction> body = new JSONWithData<>(200, a);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            JSONWithMessage body = new JSONWithMessage(500, "Server unable to retrieve attraction of id: " + aId);
            return new ResponseEntity<JSONBody>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<JSONBody> addAttraction(Attraction a) {
        try {
            Attraction results = aRepository.save(a);
            JSONWithData<Attraction> body = new JSONWithData<>(200, results);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK); // returns an object with code:200 and Attraction
                                                                      // object that was added in the JSON body

        } catch (Exception e) {
            JSONWithMessage body = new JSONWithMessage(500, "Server unable to add attraction");
            return new ResponseEntity<JSONBody>(body, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
