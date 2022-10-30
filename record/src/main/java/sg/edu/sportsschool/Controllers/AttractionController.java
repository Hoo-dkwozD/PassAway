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

import sg.edu.sportsschool.DTO.CreateAttractionDto;
import sg.edu.sportsschool.DTO.UpdateAttractionDto;
import sg.edu.sportsschool.Services.AttractionService;
import sg.edu.sportsschool.helper.JSONBody;

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
    public ResponseEntity<JSONBody> addBarcodeToAttraction(@RequestParam Integer aId,
            @RequestParam MultipartFile barcodeImage) {
        return aService.addBarcodeToAttr(aId, barcodeImage);
    }

    // @GetMapping(path = "/test")
    // public String test(@RequestParam MultipartFile file) throws MessagingException{
    //     Attraction a = new Attraction("Zoo", "Corporate pass zoo firends", 'd', 53.15F, 2, 2, 2, false,
    //             "champions court", "234563125", new Date(System.currentTimeMillis()), "Lots of benefits",
    //             "Terms and conditions...........");
    //     try {
    //         byte[] data = file.getBytes();
    //         emailService.sendEmailWithCorpLetter("zwthean.2021@scis.smu.edu.sg", "Zhi wei", "2022-10-10", "2022-10-20",a, file.getBytes());
            
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }
                
    //     return null;
    // }
}
