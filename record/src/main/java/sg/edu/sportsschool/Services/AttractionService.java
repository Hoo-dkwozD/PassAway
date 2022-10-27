package sg.edu.sportsschool.Services;

import java.util.List;
import java.io.File;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.DTO.CreateAttractionDto;
import sg.edu.sportsschool.DTO.UpdateAttractionDto;
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

    public ResponseEntity<JSONBody> addAttraction(CreateAttractionDto dto) {
        try {
            Attraction a = new Attraction(dto.getName(), dto.getDescription(), dto.getPassType(),
                    dto.getReplacementFee(), dto.getNumAccompanyingGuests(), dto.getMaxPassesPerLoan(),
                    dto.getMaxLoansPerMonth(), dto.getAddress(), false);
            Attraction results = aRepository.save(a);
            JSONWithData<Attraction> body = new JSONWithData<Attraction>(200, results);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (Exception e) {
            throw new InternalServerException("Server unable to add attraction");
        }
    }
    
    public ResponseEntity<JSONBody> updateAttraction(UpdateAttractionDto dto) {
        try {
            Integer aId = dto.getAttractionId();
            Attraction a = aRepository.findById(aId).get();
            a.setAddress(dto.getAddress());
            a.setCannotBook(dto.isCannotBook());
            a.setDescription(dto.getDescription());
            a.setMaxLoansPerMonth(dto.getMaxLoansPerMonth());
            a.setMaxPassesPerLoan(dto.getMaxPassesPerLoan());
            a.setName(dto.getName());
            a.setNumAccompanyingGuests(dto.getNumAccompanyingGuests());
            a.setPassType(dto.getPassType());
            a.setReplacementFee(dto.getReplacementFee());

            Attraction results = aRepository.save(a);
            JSONWithData<Attraction> body = new JSONWithData<Attraction>(200, results);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    
        } catch (Exception e) {
            throw new InternalServerException("Server unable to update attraction");
        }
    }

    // // Add corporate barcode for an attraction
    // public ResponseEntity<JSONBody> addBarcodeToAttr(Integer aId, MultipartFile barcodeImage) {

    //     // check barcodeImage to be jpg ("image/jpeg") or png ("image/png")
    //     String contentType = barcodeImage.getContentType();

    //     if (contentType == null) {
    //         throw new BadRequestException("No file was uploaded.");
    //     }

    //     if (!(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
    //         throw new BadRequestException("Incorrect image format. Please only upload barcode image of jpg/jpeg/png.");
    //     }

    //     String storageFolder = "src/main/static/Barcodes/";
    //     // delete all existing barcodes for this attraction if exists
    //     deleteExistingBarcodes(storageFolder, aId);

    //     // save barcode image according to the attractionId
    //     String fileName = barcodeImage.getOriginalFilename();
    //     String fileExtension = fileName.substring(fileName.lastIndexOf("."), fileName.length());

    //     try (FileOutputStream fileOutputStream = new FileOutputStream(
    //             new File(storageFolder + aId + fileExtension), false)) {
    //         byte[] barcodeImageData = barcodeImage.getBytes();
    //         fileOutputStream.write(barcodeImageData);

    //     } catch (IOException e) {
    //         throw new InternalServerException(e.getMessage());
    //     }

    //     return new ResponseEntity<JSONBody>(
    //             new JSONWithMessage(200, "Barcode image saved for attraction id " + aId), HttpStatus.OK);
    // }

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

    public void deleteExistingBarcodes(String storageFolder, Integer aId) {
        // Deletes all existing barcode images (jpg, jpeg, png) of the id from a folder
        String[] fileExtensions = { ".jpg", ".jpeg", ".png" };
        for (int i = 0; i < fileExtensions.length; i++) {
            File f = new File(storageFolder + aId + fileExtensions[i]);
            f.delete();
        }
    }

}
