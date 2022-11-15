package sg.edu.sportsschool.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.AddPassDto;
import sg.edu.sportsschool.DTO.Response.PassResponseDto;
import sg.edu.sportsschool.Entities.Attraction;
import sg.edu.sportsschool.Entities.Pass;
import sg.edu.sportsschool.Exceptions.InternalServerException;
import sg.edu.sportsschool.Helper.ReadCsv;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithData;
import sg.edu.sportsschool.Helper.Json.JSONWithMessage;
import sg.edu.sportsschool.Repositories.PassRepository;

@Service
public class PassService {

    private PassRepository pRepository;
    private AttractionService aService;

    @Autowired
    public PassService(PassRepository pRepository, AttractionService aService) {
        this.pRepository = pRepository;
        this.aService = aService;
    }

    public ResponseEntity<JSONBody> getAllPasses() {
        try {
            List<Pass> results = pRepository.findAll();
            List<PassResponseDto> response = createPassResponseDto(results);

            JSONWithData<List<PassResponseDto>> body = new JSONWithData<>(200, response);

            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve all passes.");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> getPassesByAttraction(Integer attractionId) {
        try {
            Attraction a = aService.returnAttraction(attractionId);

            if (a == null) {
                JSONWithMessage results = new JSONWithMessage(404, "Attraction not found.");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

                return response;
            }

            Set<Pass> results = pRepository.findAllPassesByAttrId(attractionId);
            List<PassResponseDto> response = createPassResponseDto(new ArrayList<>(results));
            JSONWithData<List<PassResponseDto>> body = new JSONWithData<>(200, response);

            return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to retrieve target passes.");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    public ResponseEntity<JSONBody> addPassesByCsv(Integer aId, MultipartFile cardNumbersCSVFile) {
        if (cardNumbersCSVFile == null || cardNumbersCSVFile.isEmpty()) {
            JSONWithMessage results = new JSONWithMessage(400, "Bad request. CSV file is null or CSV file is empty. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

            return response;
        }

        Attraction a = aService.returnAttraction(aId);
        if (a == null) {
            JSONWithMessage results = new JSONWithMessage(404, "Server unable to find attraction of id: " + aId + " from the database");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

            return response;
        }

        // Get all passIds from csv file, add a new pass for each passId
        List<String[]> passesList = new ArrayList<>();
        try {
            passesList = ReadCsv.read(cardNumbersCSVFile);

            if (passesList == null) {
                throw new InternalServerException("Exception occured when reading csv file");
            }
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to read uploaded file.");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }

        List<Pass> newPasses = new ArrayList<>();
        for (String[] line : passesList) {
            for (String passId : line) {
                if (!passId.equals("")) {
                    Pass pass = new Pass(passId, false, a);
                    pRepository.save(pass); // Add each passId for that attraction into passes table

                    newPasses.add(pass);
                }
            }
        }
        List<PassResponseDto> response = createPassResponseDto(newPasses);
        JSONWithData<List<PassResponseDto>> body = new JSONWithData<>(201, response);
        return new ResponseEntity<JSONBody>(body, HttpStatus.CREATED);
    }

    public ResponseEntity<JSONBody> addPass(AddPassDto dto) {
        Integer aId = dto.getAttractionId();
        String passId = dto.getPassId();
        if (passId.equals("")) {
            JSONWithMessage results = new JSONWithMessage(400, "Pass ID cannot be an empty string.");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

            return response;
        }

        Attraction a = aService.returnAttraction(aId);
        if (a == null) {
            JSONWithMessage results = new JSONWithMessage(404, "Invalid attraction ID.");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.BAD_REQUEST);

            return response;
        }

        Pass pass = new Pass(passId, false, a);

        pRepository.save(pass); // Add each passId for that attraction into passes table
        PassResponseDto response = createPassResponseDto(pass);
        JSONWithData<PassResponseDto> body = new JSONWithData<>(200, response);
        return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    }

    // public ResponseEntity<JSONBody> updatePassStatus(Integer aId, String passId, boolean isLost) {
    //     Attraction a = aService.returnAttraction(aId);

    //     if (a == null) {
    //         JSONWithMessage results = new JSONWithMessage(404, "Attraction not found.");
    //         ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.NOT_FOUND);

    //         return response;
    //     }

    //     Set<Pass> passes = pRepository.findAllPassesByAttrIdAndPassId(aId, passId);

    //     for (Pass p : passes) {
    //         p.setLost(isLost);

    //         pRepository.save(p); 
    //     }

    //     JSONWithData<List<Pass>> body = new JSONWithData<>(200, passes.stream().collect(Collectors.toList()));
    //     return new ResponseEntity<JSONBody>(body, HttpStatus.OK);
    // }

    // ------------------------------------------------------------------------------------------------
    // -- Non-JSON response Methods

    public Set<Pass> returnAllPassesByAttrId(Integer aId) {
        try {
            Set<Pass> passes = pRepository.findAllPassesByAttrId(aId);
            return passes;

        } catch (Exception e) {
            return null;
        }
    }

    public Set<Pass> returnLostPassesByAttrId(Integer aId) {
        Set<Pass> allPasses = returnAllPassesByAttrId(aId);
        if (allPasses == null) {
            return null;
        }
        return null;
    }
    
    public PassResponseDto createPassResponseDto(Pass p) {
        return new PassResponseDto(p.getPassId(), p.getAttraction().getAttractionId(), p.getAttraction().getName(), p.getAttraction().getPassType().toString(), p.isLost());
    }

    public List<PassResponseDto> createPassResponseDto(List<Pass> passes) {
        List<PassResponseDto> res = new ArrayList<>();
        for (Pass p : passes) {
            res.add(new PassResponseDto(p.getPassId(), p.getAttraction().getAttractionId(), p.getAttraction().getName(),
                    p.getAttraction().getPassType().toString(), p.isLost()));
        }
        return res;
    }

    // -- Following codes are used for testing only

    // ------------------------------------------------------------------------------------------------
}
