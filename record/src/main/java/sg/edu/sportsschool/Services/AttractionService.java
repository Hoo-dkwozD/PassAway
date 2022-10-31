package sg.edu.sportsschool.Services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.CreateAttractionDto;
import sg.edu.sportsschool.DTO.Request.UpdateAttractionDto;
import sg.edu.sportsschool.DTO.Response.LoanResponseDto;
import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Exceptions.BadRequestException;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Repositories.AttractionRepository;
import sg.edu.sportsschool.helper.JSONBody;
import sg.edu.sportsschool.helper.JSONWithData;
import sg.edu.sportsschool.helper.JSONWithMessage;

@Service
public class AttractionService {
    
    private AttractionRepository aRepository;

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
            Date expiryDate = Date
                    .valueOf(String.format("%s-%s-%s", dto.getExpiryDateYYYY(), dto.getExpiryDateMM(), dto.getExpiryDateDD()));
            Attraction a = new Attraction(dto.getName(), dto.getDescription(), dto.getPassType(),
                    dto.getReplacementFee(), dto.getNumAccompanyingGuests(), dto.getMaxPassesPerLoan(),
                    dto.getMaxLoansPerMonth(), false, dto.getAddress(), dto.getMembershipId(), expiryDate,dto.getBenefits(), dto.getTermsConditions());
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
            a.setMembershipId(dto.getMembershipId());
            Date expiryDate = Date.valueOf(
                    String.format("%d-%d-%d", dto.getExpiryDateYYYY(), dto.getExpiryDateMM(), dto.getExpiryDateDD()));
            a.setExpiryDate(expiryDate);
            a.setBenefits(dto.getBenefits());
            a.setTermsConditions(dto.getTermsConditions());

            Attraction results = aRepository.save(a);
            JSONWithData<Attraction> body = new JSONWithData<Attraction>(200, results);
            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);

        } catch (NoSuchElementException e) {
            throw new InternalServerException("Attraction not found");
        } catch (Exception e) {
            throw new InternalServerException("Server unable to update attraction");
        }
    }

    // Add corporate barcode for an attraction
    public ResponseEntity<JSONBody> addBarcodeToAttr(Integer aId, MultipartFile barcodeImage) {

        // check barcodeImage to be jpg ("image/jpeg") or png ("image/png")
        String contentType = barcodeImage.getContentType();

        if (contentType == null) {
            throw new BadRequestException("No file was uploaded.");
        }

        if (!(contentType.equals("image/jpeg") || contentType.equals("image/png"))) {
            throw new BadRequestException("Incorrect image format. Please only upload barcode image of jpg/jpeg/png.");
        }

        Optional<Attraction> optA = aRepository.findById(aId);
        if (optA.isEmpty()) {
            throw new BadRequestException("Attraction not found");
        }

        Attraction a = optA.get();

        // serialise barcode image into bytes
        try {
            byte[] data = barcodeImage.getBytes();
            a.setBarcodeImage(data);
            aRepository.save(a);
        } catch (IOException e) {
            throw new InternalServerException("Server unable to serialise barcode image file");
        }

        return new ResponseEntity<JSONBody>(
                new JSONWithMessage(200, "Barcode image saved for attraction id " + aId), HttpStatus.OK);
    }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods
    public Attraction returnAttraction(Integer aId) {
        Optional<Attraction> optA = aRepository.findById(aId);
        if (optA.isEmpty()) {
            return null;
        }
        return optA.get();
    }

}
