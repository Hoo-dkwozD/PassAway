package sg.edu.sportsschool.Services;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Repositories.AttractionRepository;
import sg.edu.sportsschool.helper.CreateJSONResponse;
import sg.edu.sportsschool.helper.JSONBody;

@Service
public class AttractionService {
    @Autowired
    private AttractionRepository aRepository;

    public ResponseEntity<JSONBody> getAllAttractions() {
        CreateJSONResponse<List<Attraction>> jsonResponse = new CreateJSONResponse<>();
        try {
            List<Attraction> attractions = new ArrayList<>();
            aRepository.findAll().forEach(attractions::add);

            return jsonResponse.create(attractions);

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to retrieve attractions");
        }
    }

    public ResponseEntity<JSONBody> getAttraction(Integer aId) {
        CreateJSONResponse<Attraction> jsonResponse = new CreateJSONResponse<>();
        try {
            Attraction a = returnAttraction(aId);
            return jsonResponse.create(a);

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to retrieve attraction of id: " + aId);
        }
    }

    public ResponseEntity<JSONBody> addAttraction(Attraction a) {
        CreateJSONResponse<Attraction> jsonResponse = new CreateJSONResponse<>();
        try {
            Attraction results = aRepository.save(a);
            return jsonResponse.create(results); // returns an object with code:200 and Attraction
                                                 // object that was added in the JSON body

        } catch (Exception e) {
            return jsonResponse.create(500, "Server unable to add attraction");
        }
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Attraction returnAttraction(Integer aId) {
        // try {
            Attraction a = aRepository.findById(aId).get();
            return a;
        // } catch (Exception e) {
            // return null;
        // }
    }

}
