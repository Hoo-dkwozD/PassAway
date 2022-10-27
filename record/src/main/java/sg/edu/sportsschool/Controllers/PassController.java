package sg.edu.sportsschool.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addPass(@RequestParam Integer attractionId, @RequestParam String passId) {
        return pService.addPass(attractionId, passId);
    }

    @PostMapping(path = "/add-csv")
    public ResponseEntity<JSONBody> addPasses(@RequestParam Integer attractionId,
            @RequestParam("file") MultipartFile cardNumbersCSVFile) {
        return pService.addPasses(attractionId, cardNumbersCSVFile);
    }

    @GetMapping(path = "/list-by-attraction")
    public ResponseEntity<JSONBody> getPassesByAttraction(@RequestParam Integer attractionId) {
        return pService.getPassesByAttraction(attractionId);
    }

    @PostMapping(path="/add-barcode", consumes={MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<JSONBody> addBarcodeToPasses(@RequestParam List<String> passIds,
            @RequestParam(name = "file") MultipartFile barcodeImageFile) {
        return pService.addBarcodeToPasses(passIds, barcodeImageFile);
    }

    // TODO Generic Put request to Update pass given a Pass object in request body
    // e.g. for lost passes

    // TODO Return of passes

}