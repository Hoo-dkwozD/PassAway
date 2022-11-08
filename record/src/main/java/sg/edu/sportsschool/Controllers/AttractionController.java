package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.CreateAttractionDto;
import sg.edu.sportsschool.DTO.Request.UpdateAttractionDto;
import sg.edu.sportsschool.Helper.JSONBody;
import sg.edu.sportsschool.Services.AttractionService;

@CrossOrigin
@RestController
@RequestMapping("/api/attraction")
public class AttractionController {

    private AttractionService aService;

    @Autowired
    public AttractionController(AttractionService aService) {
        this.aService = aService;
    }

    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllAttractions() {
        return aService.getAllAttractions();
    }

    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addAttraction(@RequestBody CreateAttractionDto dto) {
        return aService.addAttraction(dto);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<JSONBody> updateAttraction(@RequestBody UpdateAttractionDto dto) {
        return aService.updateAttraction(dto);
    }

    @PostMapping(path = "/add-barcode")
    public ResponseEntity<JSONBody> addBarcodeToAttraction(@RequestParam String aId,
            @RequestParam MultipartFile barcodeImage) {
        return aService.addBarcodeToAttr(Integer.parseInt(aId), barcodeImage);
    }
    
    
}
