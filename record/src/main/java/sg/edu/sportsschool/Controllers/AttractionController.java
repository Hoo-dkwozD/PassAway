package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.DTO.CreateAttractionDto;
import sg.edu.sportsschool.DTO.UpdateAttractionDto;
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
    public ResponseEntity<JSONBody> addAttraction(@RequestBody CreateAttractionDto dto) {
        return aService.addAttraction(dto);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<JSONBody> updateAttraction(@RequestBody UpdateAttractionDto dto) {
        return aService.updateAttraction(dto);
    }
    
    // @PostMapping(path = "/add-barcode")
    // public ResponseEntity<JSONBody> addBarcodeToAttr(@RequestParam Integer aId,
    //         @RequestParam MultipartFile barcodeImage) {
    //     return aService.addBarcodeToAttr(aId, barcodeImage);
    // }

}
