package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Services.AttractionService;
import sg.edu.sportsschool.helper.JSONBody;

@RestController
@RequestMapping("/attraction")
public class AttractionController {

    @Autowired
    private AttractionService aService;

    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllAttractions() {
        return aService.getAllAttractions();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addAttraction(@RequestBody Attraction a) {
        return aService.addAttraction(a);
    }



}
