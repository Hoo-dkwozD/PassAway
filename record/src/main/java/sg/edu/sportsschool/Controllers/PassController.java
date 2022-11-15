package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.AddPassDto;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Services.PassService;

@CrossOrigin
@RestController
@RequestMapping("/api/pass")
public class PassController {

    private PassService pService;

    @Autowired
    public PassController(PassService pService) {
        this.pService = pService;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllPasses() {
        return pService.getAllPasses();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addPass(@RequestBody AddPassDto dto) {
        return pService.addPass(dto);
    }

    @PostMapping(path = "/add-csv")
    public ResponseEntity<JSONBody> addPassesByCsv(@RequestParam Integer attractionId,
            @RequestParam("file") MultipartFile cardNumbersCSVFile) {
        return pService.addPassesByCsv(attractionId, cardNumbersCSVFile);
    }

    @GetMapping(path = "/list-by-attraction")
    public ResponseEntity<JSONBody> getPassesByAttraction(@RequestParam Integer attractionId) {
        return pService.getPassesByAttraction(attractionId);
    }

}
