package sg.edu.sportsschool.Services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.CreateAttractionDto;
import sg.edu.sportsschool.DTO.Request.UpdateAttractionDto;
import sg.edu.sportsschool.Entities.Attraction;
// import sg.edu.sportsschool.Exceptions.BadRequestException;
// import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithData;
import sg.edu.sportsschool.Helper.Json.JSONWithMessage;
import sg.edu.sportsschool.Repositories.AttractionRepository;

@Service
public class AttractionService {
    private AttractionRepository aRepository;
    private final String STATIC_FOLDER = System.getProperty("user.dir") + "src/main/resources/static";

    @Autowired
    public AttractionService(AttractionRepository aRepository) {
        this.aRepository = aRepository;
    }

    public ResponseEntity<JSONBody> getAllAttractions() {
        try {
            List<Attraction> attractions = new ArrayList<>();
            aRepository.findAll().forEach(attractions::add);
            JSONWithData<List<Attraction>> body = new JSONWithData<>(200, attractions);

            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve attraction details. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> getAttraction(Integer aId) {
        try {
            Attraction a = returnAttraction(aId);

            if (a == null) {
                JSONWithMessage results = new JSONWithMessage(404, "Attraction not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            }

            JSONWithData<Attraction> body = new JSONWithData<>(200, a);

            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve attraction details. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> addAttraction(CreateAttractionDto dto) {
        try {
            Date expiryDate = Date
                    .valueOf(String.format("%s-%s-%s", dto.getExpiryDateYYYY(), dto.getExpiryDateMM(), dto.getExpiryDateDD()));
            Attraction a = new Attraction(dto.getName(), dto.getDescription(), dto.getPassType(),
                    dto.getReplacementFee(), dto.getNumAccompanyingGuests(), dto.getMaxPassesPerLoan(),
                    dto.getMaxLoansPerMonth(), false, dto.getAddress(), dto.getMembershipId(), expiryDate, 
                    null, null, dto.getBenefits(), dto.getTermsConditions());
            Attraction results = aRepository.save(a);

            JSONWithData<Attraction> body = new JSONWithData<Attraction>(200, results);

            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to add attraction. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> updateAttraction(UpdateAttractionDto dto) {
        try {
            Integer aId = dto.getAttractionId();
            Attraction a = returnAttraction(aId);

            if (a == null) {
                JSONWithMessage results = new JSONWithMessage(404, "Attraction not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            }

            a.setAddress(dto.getAddress());
            a.setCannotBook(dto.isCannotBook());
            a.setDescription(dto.getDescription());
            a.setMaxLoansPerMonth(dto.getMaxLoansPerMonth());
            a.setMaxPassesPerLoan(dto.getMaxPassesPerLoan());
            a.setName(dto.getName());
            a.setNumAccompanyingGuests(dto.getNumAccompanyingGuests());
            a.setPassType(dto.getPassType());
            a.setReplacementFee(dto.getReplacementFee());
            a.setMembershipId(dto.getMembershipId());
            Date expiryDate = Date.valueOf(
                String.format(
                    "%d-%d-%d", 
                    dto.getExpiryDateYYYY(), 
                    dto.getExpiryDateMM(), 
                    dto.getExpiryDateDD()
                )
            );
            a.setExpiryDate(expiryDate);
            a.setBenefits(dto.getBenefits());
            a.setTermsConditions(dto.getTermsConditions());

            Attraction results = aRepository.save(a);
            JSONWithData<Attraction> body = new JSONWithData<Attraction>(200, results);

            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            JSONWithMessage results = new JSONWithMessage(404, "Attraction not found. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to update attraction. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    // Add corporate barcode for an attraction
    public ResponseEntity<JSONBody> addBarcodeToAttr(Integer aId, MultipartFile barcodeImage) {
        // Check barcodeImage to be jpg ("image/jpeg") or png ("image/png")
        String contentType = barcodeImage.getContentType();

        if (contentType == null) {
            JSONWithMessage results = new JSONWithMessage(400, "No file was uploaded. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

            return response;
        }

        if (!(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
            JSONWithMessage results = new JSONWithMessage(400, "Incorrect image format. Please only upload barcode image of jpg/jpeg/png. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

            return response;
        }

        Optional<Attraction> optA = aRepository.findById(aId);
        if (optA.isEmpty()) {
            JSONWithMessage results = new JSONWithMessage(404, "Attraction not found. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

            return response;
        }

        Attraction a = optA.get();

        // Save barcode image as a file
        try {
            String newFileName = a.getName();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            newFileName += formatter.format(new Date(System.currentTimeMillis()));
            Path newFilePath = Paths.get(STATIC_FOLDER, "attractionBarcodes", newFileName);

            Files.write(newFilePath, barcodeImage.getBytes());

            a.setBarcodeImage("attractionBarcodes/" + newFileName);

            aRepository.save(a);
        } catch (IOException e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to store barcode image file.");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }

        return new ResponseEntity<JSONBody>(
            new JSONWithMessage(200, "Barcode image saved for attraction id: " + aId), 
            HttpStatus.OK
        );
    }

    // Add corporate barcode for an attraction
    public ResponseEntity<JSONBody> addImageToAttr(Integer aId, MultipartFile image) {
        // Check barcodeImage to be jpg ("image/jpeg") or png ("image/png")
        String contentType = image.getContentType();

        if (contentType == null) {
            JSONWithMessage results = new JSONWithMessage(400, "No file was uploaded. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

            return response;
        }

        if (!(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
            JSONWithMessage results = new JSONWithMessage(400, "Incorrect image format. Please only upload image of jpg/jpeg/png. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

            return response;
        }

        Optional<Attraction> optA = aRepository.findById(aId);
        if (optA.isEmpty()) {
            JSONWithMessage results = new JSONWithMessage(404, "Attraction not found. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

            return response;
        }

        Attraction a = optA.get();

        // Save barcode image as a file
        try {
            String newFileName = a.getName();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            newFileName += formatter.format(new Date(System.currentTimeMillis()));
            Path newFilePath = Paths.get(STATIC_FOLDER, "attractions", newFileName);

            Files.write(newFilePath, image.getBytes());

            a.setBarcodeImage("attractions/" + newFileName);

            aRepository.save(a);
        } catch (IOException e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to store image file.");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }

        return new ResponseEntity<JSONBody>(
            new JSONWithMessage(200, "Image saved for attraction id: " + aId), 
            HttpStatus.OK
        );
    }

    public ResponseEntity<JSONBody> deleteAttraction(Integer aId) {
        try {
            Attraction a = returnAttraction(aId);

            if (a == null) {
                JSONWithMessage results = new JSONWithMessage(404, "Attraction not found. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            }
            aRepository.delete(a);

            JSONWithData<Attraction> results = new JSONWithData<>(200, a);

            return new ResponseEntity<JSONBody>(results, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to delete attraction. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Attraction returnAttraction(Integer aId) {
        try {
            Optional<Attraction> optA = aRepository.findById(aId);

            if (optA.isEmpty()) {
                return null;
            }

            return optA.get();
        } catch (Exception e) {
            return null;
        }
    }
}
