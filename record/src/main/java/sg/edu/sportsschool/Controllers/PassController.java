package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.Services.PassService;
import sg.edu.sportsschool.helper.JSONBody;

@CrossOrigin
@RestController
@RequestMapping("/pass")
public class PassController {

    @Autowired
    private PassService pService;

    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllPasses() {
        return pService.getAllPasses();
    }

    @PostMapping(path = "/upload-csv-file")
    public ResponseEntity<JSONBody> addPasses(@RequestParam Integer attractionId,
            @RequestParam("file") MultipartFile cardNumbersCSVFile) {
        return pService.addPasses(attractionId, cardNumbersCSVFile);
    }

}
