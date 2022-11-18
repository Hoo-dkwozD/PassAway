package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.auth0.jwt.exceptions.JWTVerificationException;

import sg.edu.sportsschool.DTO.Request.AddAttractionImageURLDto;
import sg.edu.sportsschool.DTO.Request.CreateAttractionDto;
import sg.edu.sportsschool.DTO.Request.UpdateAttractionDto;
import sg.edu.sportsschool.Entities.Staff;
import sg.edu.sportsschool.Helper.ImageType;
import sg.edu.sportsschool.Helper.StaffRole;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Helper.Json.JSONWithMessage;
import sg.edu.sportsschool.Services.AttractionService;
import sg.edu.sportsschool.Services.AuthService;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api")
public class AttractionController {

    private AttractionService aService;
    private AuthService authService;

    @Autowired
    public AttractionController(AttractionService aService, AuthService authService) {
        this.aService = aService;
        this.authService = authService;
    }

    /**
     * @api {get} /attractions Get all attractions
     * @apiName GetAttractions
     * @apiGroup Attraction
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object[]} data List of JSON objects representing attractions.
     * @apiSuccess {Number} data[attractionId] Attraction ID.
     * @apiSuccess {String} data[name] Attraction name.
     * @apiSuccess {String} data[description] Attraction description.
     * @apiSuccess {String} data[passType] Attraction pass type. ("0": PHYSICAL, "1": DIGITAL)
     * @apiSuccess {Number} data[replacementFee] Attraction replacement fee in SGD.
     * @apiSuccess {Number} data[numAccompanyingGuests] Attraction number of accompanying guests.
     * @apiSuccess {Number} data[maxPassesPerLoan] Attraction maximum passes per loan.
     * @apiSuccess {Number} data[maxLoansPerMonth] Attraction maximum loans per month.
     * @apiSuccess {Boolean} data[cannotBook] Attraction cannot book.
     * @apiSuccess {String} data[address] Attraction address.
     * @apiSuccess {String} data[membershipId] Attraction membership ID.
     * @apiSuccess {String} data[expiryDate] Attraction expiry date.
     * @apiSuccess {String} data[barcodeImage] Attraction barcode image. Relative path to static file from host.
     * @apiSuccess {String} data[backgroundImage] Attraction background image. Relative path to static file from host.
     * @apiSuccess {String} data[benefits] Attraction benefits.
     * @apiSuccess {String} data[termsConditions] Attraction terms and conditions.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200,
     *         "data": [
     *             {
     *                 "attractionId": 1,
     *                 "name": "Gardens by the bay",
     *                 "description": "Premium corporate friends of the gardens by the bay (cfoz) membership",
     *                 "passType": "PHYSICAL",
     *                 "replacementFee": 60.15,
     *                 "numAccompanyingGuests": 2,
     *                 "maxPassesPerLoan": 2,
     *                 "maxLoansPerMonth": 2,
     *                 "cannotBook": false,
     *                 "address": "18 Marina Gardens Dr, Singapore 018953",
     *                 "membershipId": "587418925",
     *                 "expiryDate": "2022-12-31",
     *                 "barcodeImage": "attractionBarcodes/1.jpeg",
     *                 "backgroundImage": "attractions/1.jpeg",
     *                 "benefits": "Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.",
     *                 "termsConditions": "abc"
     *             }
     *         ]
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to retrieve attraction details. "
     *     }
     * 
     * @apiDescription Gets the details of all attractions. 
     */
    @GetMapping(path = "/attractions")
    public ResponseEntity<JSONBody> getAllAttractions(@RequestHeader("Authorization") String token) {
        if (token == null) {
            JSONWithMessage results = new JSONWithMessage(401, "User is not logged in. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        }

        try {
            Staff user = authService.authenticate(token);

            if (user == null) {
                JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else {
                return aService.getAllAttractions();
            }
        } catch (JWTVerificationException e) {
            JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to verify user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    /**
     * @api {get} /attraction/:aId Get attraction by ID
     * @apiName GetAttraction
     * @apiGroup Attraction
     * 
     * @apiParam {Number} aId Attraction ID.
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON object representing attraction.
     * @apiSuccess {Number} data[attractionId] Attraction ID.
     * @apiSuccess {String} data[name] Attraction name.
     * @apiSuccess {String} data[description] Attraction description.
     * @apiSuccess {String} data[passType] Attraction pass type. ("0": PHYSICAL, "1": DIGITAL)
     * @apiSuccess {Number} data[replacementFee] Attraction replacement fee in SGD.
     * @apiSuccess {Number} data[numAccompanyingGuests] Attraction number of accompanying guests.
     * @apiSuccess {Number} data[maxPassesPerLoan] Attraction maximum passes per loan.
     * @apiSuccess {Number} data[maxLoansPerMonth] Attraction maximum loans per month.
     * @apiSuccess {Boolean} data[cannotBook] Attraction cannot book.
     * @apiSuccess {String} data[address] Attraction address.
     * @apiSuccess {String} data[membershipId] Attraction membership ID.
     * @apiSuccess {String} data[expiryDate] Attraction expiry date.
     * @apiSuccess {String} data[barcodeImage] Attraction barcode image. Relative path to static file from host.
     * @apiSuccess {String} data[backgroundImage] Attraction background image. Relative path to static file from host.
     * @apiSuccess {String} data[benefits] Attraction benefits.
     * @apiSuccess {String} data[termsConditions] Attraction terms and conditions.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200,
     *         "data": {
     *             "attractionId": 1,
     *             "name": "Gardens by the bay",
     *             "description": "Premium corporate friends of the gardens by the bay (cfoz) membership",
     *             "passType": "PHYSICAL",
     *             "replacementFee": 60.15,
     *             "numAccompanyingGuests": 2,
     *             "maxPassesPerLoan": 2,
     *             "maxLoansPerMonth": 2,
     *             "cannotBook": false,
     *             "address": "18 Marina Gardens Dr, Singapore 018953",
     *             "membershipId": "587418925",
     *             "expiryDate": "2022-12-31",
     *             "barcodeImage": "attractionBarcodes/1.jpeg",
     *             "backgroundImage": "attractions/1.jpeg",
     *             "benefits": "Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.",
     *             "termsConditions": "abc"
     *         }
     *     }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *         "code": 404,
     *         "message": "Attraction not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to retrieve attraction details. "
     *     }
     * 
     * @apiDescription Gets the details of the attraction. 
     */
    @GetMapping(path = "/attraction/{aId}")
    public ResponseEntity<JSONBody> getAttraction(@RequestHeader("Authorization") String token, 
            @PathVariable int aId) {
        if (token == null) {
            JSONWithMessage results = new JSONWithMessage(401, "User is not logged in. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        }

        try {
            Staff user = authService.authenticate(token);

            if (user == null) {
                JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else {
                return aService.getAttraction(aId);
            }
        } catch (JWTVerificationException e) {
            JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to verify user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    /**
     * @api {post} /attraction Create attraction
     * @apiName CreateAttraction
     * @apiGroup Attraction
     * 
     * @apiBody {String} name Attraction name.
     * @apiBody {String} description Attraction description.
     * @apiBody {String} passType Attraction pass type. ("0": PHYSICAL, "1": DIGITAL)
     * @apiBody {Number} replacementFee Attraction replacement fee in SGD.
     * @apiBody {Number} numAccompanyingGuests Attraction number of accompanying guests.
     * @apiBody {Number} maxPassesPerLoan Attraction maximum passes per loan.
     * @apiBody {Number} maxLoansPerMonth Attraction maximum loans per month.
     * @apiBody {String} address Attraction address.
     * @apiBody {String} membershipId Attraction membership ID.
     * @apiBody {Number} expiryDateYYYY Attraction expiry date's year.
     * @apiBody {Number} expiryDateMM Attraction expiry date's month.
     * @apiBody {Number} expiryDateDD Attraction expiry date's day.
     * @apiBody {String} benefits Attraction benefits.
     * @apiBody {String} termsConditions Attraction terms and conditions.
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON object representing attraction.
     * @apiSuccess {Number} data[attractionId] Attraction ID.
     * @apiSuccess {String} data[name] Attraction name.
     * @apiSuccess {String} data[description] Attraction description.
     * @apiSuccess {String} data[passType] Attraction pass type. ("0": PHYSICAL, "1": DIGITAL)
     * @apiSuccess {Number} data[replacementFee] Attraction replacement fee in SGD.
     * @apiSuccess {Number} data[numAccompanyingGuests] Attraction number of accompanying guests.
     * @apiSuccess {Number} data[maxPassesPerLoan] Attraction maximum passes per loan.
     * @apiSuccess {Number} data[maxLoansPerMonth] Attraction maximum loans per month.
     * @apiSuccess {Boolean} data[cannotBook] Attraction cannot book.
     * @apiSuccess {String} data[address] Attraction address.
     * @apiSuccess {String} data[membershipId] Attraction membership ID.
     * @apiSuccess {String} data[expiryDate] Attraction expiry date.
     * @apiSuccess {String} data[barcodeImage] Attraction barcode image. Relative path to static file from host.
     * @apiSuccess {String} data[backgroundImage] Attraction background image. Relative path to static file from host.
     * @apiSuccess {String} data[benefits] Attraction benefits.
     * @apiSuccess {String} data[termsConditions] Attraction terms and conditions.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200,
     *         "data": {
     *             "attractionId": 1,
     *             "name": "Gardens by the bay",
     *             "description": "Premium corporate friends of the gardens by the bay (cfoz) membership",
     *             "passType": "PHYSICAL",
     *             "replacementFee": 60.15,
     *             "numAccompanyingGuests": 2,
     *             "maxPassesPerLoan": 2,
     *             "maxLoansPerMonth": 2,
     *             "cannotBook": false,
     *             "address": "18 Marina Gardens Dr, Singapore 018953",
     *             "membershipId": "587418925",
     *             "expiryDate": "2022-12-31",
     *             "barcodeImage": "attractionBarcodes/1.jpeg",
     *             "backgroundImage": "attractions/1.jpeg",
     *             "benefits": "Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.",
     *             "termsConditions": "abc"
     *         }
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to add attraction. "
     *     }
     * 
     * @apiDescription Creates a new attraction. 
     */
    @PostMapping(path = "/attraction")
    public ResponseEntity<JSONBody> addAttraction(@RequestHeader("Authorization") String token, 
            @RequestBody CreateAttractionDto dto) {
        if (token == null) {
            JSONWithMessage results = new JSONWithMessage(401, "User is not logged in. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        }

        try {
            Staff user = authService.authenticate(token);

            if (user == null) {
                JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (user.getRole() != StaffRole.ADMINISTRATOR) {
                JSONWithMessage results = new JSONWithMessage(403, "User is forbidden from access. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.FORBIDDEN);

                return response;
            } else {
                return aService.addAttraction(dto);
            }
        } catch (JWTVerificationException e) {
            JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to verify user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    /**
     * @api {put} /attraction/:aId Update attraction
     * @apiName UpdateAttraction
     * @apiGroup Attraction
     * 
     * @apiParam {Number} aId Attraction ID.
     * 
     * @apiBody {Number} attractionId Attraction ID.
     * @apiBody {String} name Attraction name.
     * @apiBody {String} description Attraction description.
     * @apiBody {String} passType Attraction pass type. ("0": PHYSICAL, "1": DIGITAL)
     * @apiBody {Number} replacementFee Attraction replacement fee in SGD.
     * @apiBody {Number} numAccompanyingGuests Attraction number of accompanying guests.
     * @apiBody {Number} maxPassesPerLoan Attraction maximum passes per loan.
     * @apiBody {Number} maxLoansPerMonth Attraction maximum loans per month.
     * @apiBody {String} address Attraction address.
     * @apiBody {String} membershipId Attraction membership ID.
     * @apiBody {Number} expiryDateYYYY Attraction expiry date's year.
     * @apiBody {Number} expiryDateMM Attraction expiry date's month.
     * @apiBody {Number} expiryDateDD Attraction expiry date's day.
     * @apiBody {String} benefits Attraction benefits.
     * @apiBody {String} termsConditions Attraction terms and conditions.
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON object representing attraction.
     * @apiSuccess {Number} data[attractionId] Attraction ID.
     * @apiSuccess {String} data[name] Attraction name.
     * @apiSuccess {String} data[description] Attraction description.
     * @apiSuccess {String} data[passType] Attraction pass type. ("0": PHYSICAL, "1": DIGITAL)
     * @apiSuccess {Number} data[replacementFee] Attraction replacement fee in SGD.
     * @apiSuccess {Number} data[numAccompanyingGuests] Attraction number of accompanying guests.
     * @apiSuccess {Number} data[maxPassesPerLoan] Attraction maximum passes per loan.
     * @apiSuccess {Number} data[maxLoansPerMonth] Attraction maximum loans per month.
     * @apiSuccess {Boolean} data[cannotBook] Attraction cannot book.
     * @apiSuccess {String} data[address] Attraction address.
     * @apiSuccess {String} data[membershipId] Attraction membership ID.
     * @apiSuccess {String} data[expiryDate] Attraction expiry date.
     * @apiSuccess {String} data[barcodeImage] Attraction barcode image. Relative path to static file from host.
     * @apiSuccess {String} data[backgroundImage] Attraction background image. Relative path to static file from host.
     * @apiSuccess {String} data[benefits] Attraction benefits.
     * @apiSuccess {String} data[termsConditions] Attraction terms and conditions.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200,
     *         "data": {
     *             "attractionId": 1,
     *             "name": "Gardens by the bay",
     *             "description": "Premium corporate friends of the gardens by the bay (cfoz) membership",
     *             "passType": "PHYSICAL",
     *             "replacementFee": 60.15,
     *             "numAccompanyingGuests": 2,
     *             "maxPassesPerLoan": 2,
     *             "maxLoansPerMonth": 2,
     *             "cannotBook": false,
     *             "address": "18 Marina Gardens Dr, Singapore 018953",
     *             "membershipId": "587418925",
     *             "expiryDate": "2022-12-31",
     *             "barcodeImage": "attractionBarcodes/1.jpeg",
     *             "backgroundImage": "attractions/1.jpeg",
     *             "benefits": "Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.",
     *             "termsConditions": "abc"
     *         }
     *     }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *         "code": 404,
     *         "message": "Attraction not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to update attraction. "
     *     }
     * 
     * @apiDescription Updates the details of attraction. 
     */
    @PutMapping(path = "/attraction/{aId}")
    public ResponseEntity<JSONBody> updateAttraction(@RequestHeader("Authorization") String token, 
            @PathVariable int aId, @RequestBody UpdateAttractionDto dto) {
        if (token == null) {
            JSONWithMessage results = new JSONWithMessage(401, "User is not logged in. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        }

        try {
            Staff user = authService.authenticate(token);

            if (user == null) {
                JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (user.getRole() != StaffRole.ADMINISTRATOR) {
                JSONWithMessage results = new JSONWithMessage(403, "User is forbidden from access. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.FORBIDDEN);

                return response;
            } else {
                return aService.updateAttraction(aId, dto);
            }
        } catch (JWTVerificationException e) {
            JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to verify user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    /**
     * @api {post} /attraction/:aId/barcodeImage Upload attraction barcode image
     * @apiName UploadAttractionBarcodeImage
     * @apiGroup Attraction
     * 
     * @apiParam {Number} aId Attraction ID.
     * 
     * @apiBody {Image} file JPEG or PNG image to upload. The content-type of the request is not JSON but the respective file format. 
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {String} message Informs that photo was uploaded successfully.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200,
     *         "message": "Barcode image saved for attraction id: 1"
     *     }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response-No-File:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 400,
     *         "message": "No file was uploaded. "
     *     }
     * 
     * @apiErrorExample {json} Error-Response-Wrong-File:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 400,
     *         "message": "Incorrect image format. Please only upload barcode image of jpg/jpeg/png. "
     *     }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *         "code": 404,
     *         "message": "Attraction not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to store barcode image file. "
     *     }
     * 
     * @apiDescription Uploads the attraction's barcode image. 
     */
    @PostMapping(path = "/attraction/{aId}/barcodeImage")
    public ResponseEntity<JSONBody> addBarcodeToAttraction(@RequestHeader("Authorization") String token, 
            @PathVariable int aId,
            @RequestParam("file") MultipartFile barcodeImage) {
        if (token == null) {
            JSONWithMessage results = new JSONWithMessage(401, "User is not logged in. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        }

        try {
            Staff user = authService.authenticate(token);

            if (user == null) {
                JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (user.getRole() != StaffRole.ADMINISTRATOR) {
                JSONWithMessage results = new JSONWithMessage(403, "User is forbidden from access. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.FORBIDDEN);

                return response;
            } else {
                return aService.addImageToAttr(aId, barcodeImage, ImageType.BARCODE);
            }
        } catch (JWTVerificationException e) {
            JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to verify user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    /**
     * @api {post} /attraction/:aId/image Upload attraction background image
     * @apiName UploadAttractionImage
     * @apiGroup Attraction
     * 
     * @apiParam {Number} aId Attraction ID.
     * 
     * @apiBody {Image} file JPEG or PNG image to upload. The content-type of the request is not JSON but the respective file format. 
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {String} message Informs that photo was uploaded successfully.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200,
     *         "message": "Image saved for attraction id: 1"
     *     }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response-No-File:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 400,
     *         "message": "No file was uploaded. "
     *     }
     * 
     * @apiErrorExample {json} Error-Response-Wrong-File:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 400,
     *         "message": "Incorrect image format. Please only upload image of jpg/jpeg/png. "
     *     }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *         "code": 404,
     *         "message": "Attraction not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to store image file. "
     *     }
     * 
     * @apiDescription Uploads the attraction's background image. 
     */
    @PostMapping(path = "/attraction/{aId}/image")
    public ResponseEntity<JSONBody> addImageToAttraction(@RequestHeader("Authorization") String token, 
            @PathVariable int aId, 
            @RequestParam("file") MultipartFile image) {
        if (token == null) {
            JSONWithMessage results = new JSONWithMessage(401, "User is not logged in. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        }

        try {
            Staff user = authService.authenticate(token);

            if (user == null) {
                JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (user.getRole() != StaffRole.ADMINISTRATOR) {
                JSONWithMessage results = new JSONWithMessage(403, "User is forbidden from access. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.FORBIDDEN);

                return response;
            } else {
                return aService.addImageToAttr(aId, image, ImageType.BACKGROUND);
            }
        } catch (JWTVerificationException e) {
            JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to verify user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    /**
     * @api {post} /attraction/:aId/imageURL Upload attraction background image's URL
     * @apiName UploadAttractionImageURL
     * @apiGroup Attraction
     * 
     * @apiParam {Number} aId Attraction ID.
     * 
     * @apiBody {String} imageURL URL of the image to upload. 
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {String} message Informs that URL was uploaded successfully.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200,
     *         "message": "Image saved for attraction id: 1"
     *     }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *         "code": 404,
     *         "message": "Attraction not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to store image file URL. "
     *     }
     * 
     * @apiDescription Uploads the URL of the attraction's background image. 
     */
    @PostMapping(path = "/attraction/{aId}/imageURL")
    public ResponseEntity<JSONBody> addImageURLToAttraction(@RequestHeader("Authorization") String token, 
            @PathVariable int aId, 
            @RequestBody AddAttractionImageURLDto dto) {
        if (token == null) {
            JSONWithMessage results = new JSONWithMessage(401, "User is not logged in. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        }

        try {
            Staff user = authService.authenticate(token);

            if (user == null) {
                JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (user.getRole() != StaffRole.ADMINISTRATOR) {
                JSONWithMessage results = new JSONWithMessage(403, "User is forbidden from access. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.FORBIDDEN);

                return response;
            } else {
                return aService.addImageURLToAttr(aId, dto);
            }
        } catch (JWTVerificationException e) {
            JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to verify user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }

    /**
     * @api {delete} /attraction/:aId Delete attraction
     * @apiName UpdateAttraction
     * @apiGroup Attraction
     * 
     * @apiParam {Number} aId Attraction ID.
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON object representing attraction.
     * @apiSuccess {Number} data[attractionId] Attraction ID.
     * @apiSuccess {String} data[name] Attraction name.
     * @apiSuccess {String} data[description] Attraction description.
     * @apiSuccess {String} data[passType] Attraction pass type. ("0": PHYSICAL, "1": DIGITAL)
     * @apiSuccess {Number} data[replacementFee] Attraction replacement fee in SGD.
     * @apiSuccess {Number} data[numAccompanyingGuests] Attraction number of accompanying guests.
     * @apiSuccess {Number} data[maxPassesPerLoan] Attraction maximum passes per loan.
     * @apiSuccess {Number} data[maxLoansPerMonth] Attraction maximum loans per month.
     * @apiSuccess {Boolean} data[cannotBook] Attraction cannot book.
     * @apiSuccess {String} data[address] Attraction address.
     * @apiSuccess {String} data[membershipId] Attraction membership ID.
     * @apiSuccess {String} data[expiryDate] Attraction expiry date.
     * @apiSuccess {String} data[barcodeImage] Attraction barcode image. Relative path to static file from host.
     * @apiSuccess {String} data[backgroundImage] Attraction background image. Relative path to static file from host.
     * @apiSuccess {String} data[benefits] Attraction benefits.
     * @apiSuccess {String} data[termsConditions] Attraction terms and conditions.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200,
     *         "data": {
     *             "attractionId": 1,
     *             "name": "Gardens by the bay",
     *             "description": "Premium corporate friends of the gardens by the bay (cfoz) membership",
     *             "passType": "PHYSICAL",
     *             "replacementFee": 60.15,
     *             "numAccompanyingGuests": 2,
     *             "maxPassesPerLoan": 2,
     *             "maxLoansPerMonth": 2,
     *             "cannotBook": false,
     *             "address": "18 Marina Gardens Dr, Singapore 018953",
     *             "membershipId": "587418925",
     *             "expiryDate": "2022-12-31",
     *             "barcodeImage": "attractionBarcodes/1.jpeg",
     *             "backgroundImage": "attractions/1.jpeg",
     *             "benefits": "Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.",
     *             "termsConditions": "abc"
     *         }
     *     }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *         "code": 404,
     *         "message": "Attraction not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to delete attraction. "
     *     }
     * 
     * @apiDescription Deletes the attraction. 
     */
    @DeleteMapping(path = "/attraction/{aId}")
    public ResponseEntity<JSONBody> deleteAttraction(@RequestHeader("Authorization") String token, 
            @PathVariable int aId) {
        if (token == null) {
            JSONWithMessage results = new JSONWithMessage(401, "User is not logged in. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        }

        try {
            Staff user = authService.authenticate(token);

            if (user == null) {
                JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

                return response;
            } else if (user.getRole() != StaffRole.ADMINISTRATOR) {
                JSONWithMessage results = new JSONWithMessage(403, "User is forbidden from access. ");
                ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.FORBIDDEN);

                return response;
            } else {
                return aService.deleteAttraction(aId);
            }
        } catch (JWTVerificationException e) {
            JSONWithMessage results = new JSONWithMessage(401, "User is unauthorized. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.UNAUTHORIZED);

            return response;
        } catch (Exception e) {
            JSONWithMessage results = new JSONWithMessage(500, "Server unable to verify user. ");
            ResponseEntity<JSONBody> response = new ResponseEntity<JSONBody>(results, HttpStatus.INTERNAL_SERVER_ERROR);

            return response;
        }
    }
}
