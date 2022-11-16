package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    /**
     * @api {get} /list Get all passes
     * @apiName GetPasses
     * @apiGroup Pass
     *        
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object[]} data List of JSON objects representing passes
     * @apiSuccess {String} data[passId] Pass ID.
     * @apiSuccess {Number} data[attractionId] AttractionId.
     * @apiSuccess {String} data[name] Attraction name for the pass.
     * @apiSuccess {String} data[passType] "PHYSICAL" or "DIGITAL".
     * @apiSuccess {Boolean} data[lost] Whether the pass is lost.
     * 
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *  {
        "code": 200,
        "data": [
            {
                "passId": "1",
                "attractionId": 3,
                "name": "Gardens by the bay",
                "passType": "DIGITAL",
                "lost": false
            },
            {
                "passId": "10",
                "attractionId": 5,
                "name": "Place A",
                "passType": "PHYSICAL",
                "lost": true
            }
            ]
        }
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *                  {
     *                  "code": 500,
     *                  "message": "Server unable to retrieve all passes."
     *                  }
     * 
     * @apiDescription Gets the details of all passes.
     */
    @GetMapping(path = "/list")
    public ResponseEntity<JSONBody> getAllPasses() {
        return pService.getAllPasses();
    }

    /**
     * @api {post} /add Add a pass
     * @apiName AddPass
     * @apiGroup Pass
     * 
     * @apiBody {Number} attractionId Attraction ID to add pass to.
     * @apiBody {String} passId Pass ID to be added to the attraction.
     *        
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON object representing pass details
     * @apiSuccess {String} data[passId] Pass ID.
     * @apiSuccess {Number} data[attractionId] AttractionId.
     * @apiSuccess {String} data[name] Attraction name for the pass.
     * @apiSuccess {String} data[passType] "PHYSICAL" or "DIGITAL".
     * @apiSuccess {Boolean} data[lost] Whether the pass is lost.
     * 
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 OK
     *  {
            "code": 200,
            "data": {
                "passId": "11",
                "attractionId": 5,
                "name": "Place A",
                "passType": "PHYSICAL",
                "lost": false
            }
        }
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad Request
     *                  {
     *                  "code": 400,
     *                  "message": "Pass ID cannot be an empty string."
     *                  }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad Request
     *                  {
     *                  "code": 400,
     *                  "message": "Invalid attraction ID."
     *                  }
     * 
     * @apiDescription Add a pass to an attraction.
     */
    @PostMapping(path = "/add")
    public ResponseEntity<JSONBody> addPass(@RequestBody AddPassDto dto) {
        return pService.addPass(dto);
    }

    /**
     * @api {post} /add Add passes to an attraction by csv file
     * @apiName AddPassesByCSV
     * @apiGroup Pass
     * 
     * @apiBody {Number} attractionId Attraction ID to add pass to.
     * @apiBody {CSV} file CSV file containing pass IDs for the given attraction ID. The content-type of the request is not JSON but the respective file format. 
     *        
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON object representing pass details
     * @apiSuccess {String} data[passId] Pass ID.
     * @apiSuccess {Number} data[attractionId] AttractionId.
     * @apiSuccess {String} data[name] Attraction name for the pass.
     * @apiSuccess {String} data[passType] "PHYSICAL" or "DIGITAL".
     * @apiSuccess {Boolean} data[lost] Whether the pass is lost.
     * 
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 201 CREATED
     *  {
        "code": 201,
        "data": [
                {
                    "passId": "1",
                    "attractionId": 4,
                    "name": "Universal Studios",
                    "passType": "PHYSICAL",
                    "lost": false
                },
                {
                    "passId": "2",
                    "attractionId": 4,
                    "name": "Universal Studios",
                    "passType": "PHYSICAL",
                    "lost": false
                },
                {
                    "passId": "3",
                    "attractionId": 4,
                    "name": "Universal Studios",
                    "passType": "PHYSICAL",
                    "lost": false
                }
            ]
        }
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 400 Bad Request
     *                  {
     *                  "code": 400,
     *                  "message": "Bad request. CSV file is null or CSV file is empty. "
     *                  }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 404 Not Found
     *                  {
     *                  "code": 404,
     *                  "message": "Server unable to find attraction of id: 5 from the database"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *  {
     *  "code": 500,
     *  "message": "Exception occured when reading csv file"
     *  }
     * 
     * 
     * @apiDescription Add passes to an attraction by csv file.
     */
    @PostMapping(path = "/add-csv/{attractionId}")
    public ResponseEntity<JSONBody> addPassesByCsv(@PathVariable Integer attractionId,
            @RequestParam("file") MultipartFile cardNumbersCSVFile) {
        return pService.addPassesByCsv(attractionId, cardNumbersCSVFile);
    }

    /**
     * @api {get} /list-by-attraction List passes by attraction
     * @apiName GetPassesByAttraction
     * @apiGroup Pass
     * 
     * @apiBody {Number} attractionId Attraction ID to add pass to.
     *        
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON object representing pass details
     * @apiSuccess {String} data[passId] Pass ID.
     * @apiSuccess {Number} data[attractionId] AttractionId.
     * @apiSuccess {String} data[name] Attraction name for the pass.
     * @apiSuccess {String} data[passType] "PHYSICAL" or "DIGITAL".
     * @apiSuccess {Boolean} data[lost] Whether the pass is lost.
     * 
     * @apiSuccessExample {json} Success-Response:
     *      HTTP/1.1 200 CREATED
     *  {
            "code": 200,
            "data": [
                {
                    "passId": "12345D",
                    "attractionId": 3,
                    "name": "Gardens by the bay",
                    "passType": "DIGITAL",
                    "lost": false
                },
                {
                    "passId": "12345E",
                    "attractionId": 3,
                    "name": "Gardens by the bay",
                    "passType": "DIGITAL",
                    "lost": false
                },
                {
                    "passId": "12345F",
                    "attractionId": 3,
                    "name": "Gardens by the bay",
                    "passType": "DIGITAL",
                    "lost": false
                }
            ]
        }
     *
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 404 Not Found
     *                  {
     *                  "code": 404,
     *                  "message": "Attraction not found"
     *                  }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *                  HTTP/1.1 500 Internal Server Error
     *  {
     *  "code": 500,
     *  "message": "Server unable to retrieve target passes."
     *  }
     * 
     * 
     * @apiDescription List passes of an attraction.
     */
    @GetMapping(path = "/list-by-attraction")
    public ResponseEntity<JSONBody> getPassesByAttraction(@RequestParam Integer attractionId) {
        return pService.getPassesByAttraction(attractionId);
    }

    

}
