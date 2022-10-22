package sg.edu.sportsschool.Services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Repositories.AttractionRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;

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
            throw new InternalServerException("Server unable to get all attractions");
        }
    }

    public ResponseEntity<JSONBody> getAttraction(Integer aId) {
        try {
            Attraction a = returnAttraction(aId);
            JSONWithData<Attraction> body = new JSONWithData<>(200, a);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to retrieve attraction of id: " + aId);
        }
    }

    public ResponseEntity<JSONBody> addAttraction(Attraction a) {
        try {
            Attraction results = aRepository.save(a);
            JSONWithData<Attraction> body = new JSONWithData<Attraction>(200, results);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to add attraction");
        }
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Attraction returnAttraction(Integer aId) {
        try {
            Attraction a = aRepository.findById(aId).get();
            return a;
        } catch (Exception e) {
            return null;
        }
    }

}
