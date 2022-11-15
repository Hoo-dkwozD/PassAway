package sg.edu.sportsschool.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.sportsschool.DTO.Request.CompleteRegisterStaffDto;
import sg.edu.sportsschool.DTO.Request.CreateStaffDto;
import sg.edu.sportsschool.DTO.Request.RegisterStaffDto;
import sg.edu.sportsschool.DTO.Request.UpdatePasswordDto;
import sg.edu.sportsschool.DTO.Request.UpdateProfileDto;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Services.StaffService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class StaffController {

    private StaffService staffService;

    @Autowired
    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    /**
     * @api {get} /staffs Request information of all staffs
     * @apiName GetStaffs
     * @apiGroup Staff
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object[]} data Array of JSON objects representing all staff information.
     * @apiSuccess {Number} data[staffId] Staff ID.
     * @apiSuccess {String} data[email] Email of the staff.
     * @apiSuccess {String} data[firstName] First name of the staff.
     * @apiSuccess {String} data[lastName] Last name of the staff.
     * @apiSuccess {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": [
     *             {
     *                 "staffId": 1, 
     *                 "email": "test@sportsschool.edu.sg", 
     *                 "firstName": "test", 
     *                 "lastName": "testing", 
     *                 "contactNumber": null, 
     *                 "role": "ADMINISTRATOR", 
     *                 "cannotBook": true, 
     *                 "disabled": false, 
     *                 "registered": false
     *             }, 
     *             {
     *                 "staffId": 2, 
     *                 "email": "test1@sportsschool.edu.sg", 
     *                 "firstName": "test1", 
     *                 "lastName": "testing1", 
     *                 "contactNumber": null, 
     *                 "role": "BORROWER", 
     *                 "cannotBook": true, 
     *                 "disabled": false, 
     *                 "registered": false
     *             },
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
     *         "message": "Server unable to retrieve staff details. "
     *     }
     * 
     * @apiDescription Gets all staff and their details, including soft-deleted ones. 
     */
    @GetMapping(path = "/staffs")
    public ResponseEntity<JSONBody> getAllStaff() {
        return staffService.getAllStaff();
    }

    /**
     * @api {get} /staff/:staffId Request information of all staffs
     * @apiName GetStaff
     * @apiGroup Staff
     * 
     * @apiParam {Number} staffId Staff ID of desired staff.
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data JSON object representing target staff information.
     * @apiSuccess {Number} data[staffId] Staff ID.
     * @apiSuccess {String} data[email] Email of the staff.
     * @apiSuccess {String} data[firstName] First name of the staff.
     * @apiSuccess {String} data[lastName] Last name of the staff.
     * @apiSuccess {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": {
     *             "staffId": 1, 
     *             "email": "test@sportsschool.edu.sg", 
     *             "firstName": "test", 
     *             "lastName": "testing", 
     *             "contactNumber": null, 
     *             "role": "ADMINISTRATOR", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
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
     *         "message": "Server unable to retrieve staff detail. "
     *     }
     * 
     * @apiDescription Gets the details of the specified staff. 
     */
    @GetMapping(path = "/staff/{staffId}")
    public ResponseEntity<JSONBody> getStaff(@PathVariable int staffId) {
        return staffService.getStaff(staffId);
    }

    /**
     * @api {post} /staffs Upload staffs using CSV file
     * @apiName UploadStaffs
     * @apiGroup Staff
     * 
     * @apiBody {CSV} file CSV file to upload. The content-type of the request is not JSON but the respective file format. 
     * 
     * @apiSuccess {Number} code HTTP status code.
     * @apiSuccess {Object} data The users that have successfully been uploaded.
     * @apiSuccess {Object[]} data[staffs] Staffs created.
     * @apiSuccess {Number} staffs[staffId] Staff ID.
     * @apiSuccess {String} staffs[email] Email of the staff.
     * @apiSuccess {String} staffs[firstName] First name of the staff.
     * @apiSuccess {String} staffs[lastName] Last name of the staff.
     * @apiSuccess {String} staffs[contactNumber] Contact number of the staff.
     * @apiSuccess {String} staffs[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess {Boolean} staffs[cannotBook] Whether the staff can book.
     * @apiSuccess {String} staffs[disabled] Whether the staff is soft-deleted.
     * @apiSuccess {String} staffs[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *         "code": 201,
     *         "data": {
     *             "staffs": [
     *                 {
     *                     "staffId": 1, 
     *                     "email": "test@sportsschool.edu.sg", 
     *                     "firstName": "test", 
     *                     "lastName": "testing", 
     *                     "contactNumber": null, 
     *                     "role": "ADMINISTRATOR", 
     *                     "cannotBook": true, 
     *                     "disabled": false, 
     *                     "registered": false
     *                 }, 
     *                 {
     *                     "staffId": 2, 
     *                     "email": "test1@sportsschool.edu.sg", 
     *                     "firstName": "test1", 
     *                     "lastName": "testing1", 
     *                     "contactNumber": null, 
     *                     "role": "BORROWER", 
     *                     "cannotBook": true, 
     *                     "disabled": false, 
     *                     "registered": false
     *                 },
     *             ]
     *         }
     *     }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 400,
     *         "message": "The uploaded CSV file is not valid. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to parse file as CSV file. "
     *     }
     * 
     * @apiDescription Upload staffs through a CSV file. 
     */
    @PostMapping("/staffs")
    public ResponseEntity<JSONBody> createStaffs(@RequestParam("file") MultipartFile file) {
        return staffService.createStaffs(file);
    }

    /**
     * @api {post} /staff Create new staff
     * @apiName PostStaff
     * @apiGroup Staff
     * 
     * @apiBody {String} email Email of new staff.
     * @apiBody {String} firstName First name of new staff.
     * @apiBody {String} lastName Last name of new staff.
     * @apiBody {String} contactNumber Contact number of new staff.
     * @apiBody {String} role Role of new staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiBody {Boolean} cannotBook Whether new staff can book.
     * @apiBody {Boolean} isDisabled Whether new staff is present or soft-deleted.
     * 
     * @apiSuccess (Success 201) {Number} code HTTP status code.
     * @apiSuccess (Success 201) {Object} data JSON object representing the new staff's information.
     * @apiSuccess (Success 201) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 201) {String} data[email] Email of the staff.
     * @apiSuccess (Success 201) {String} data[firstName] First name of the staff.
     * @apiSuccess (Success 201) {String} data[lastName] Last name of the staff.
     * @apiSuccess (Success 201) {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess (Success 201) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess (Success 201) {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess (Success 201) {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess (Success 201) {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *         "code": 201, 
     *         "data": {
     *             "staffId": 3, 
     *             "email": "test2@sportsschool.edu.sg", 
     *             "firstName": "test2", 
     *             "lastName": "testing2", 
     *             "contactNumber": "12345678", 
     *             "role": "BORROWER", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
     *         }
     *     }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 400,
     *         "message": "The staff to add already exists or has invalid fields. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable create new staff. "
     *     }
     * 
     * @apiDescription Creates new staff. 
     */
    @PostMapping("/staff")
    public ResponseEntity<JSONBody> createStaff(@RequestBody CreateStaffDto dto) {
        return staffService.createStaff(dto);
    }

    /**
     * @api {post} /staff/register Register new staff
     * @apiName RegisterStaff
     * @apiGroup Staff
     * 
     * @apiBody {String} email Email of staff.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 204 No Content
     * 
     * @apiError (Error 401) {Number} code HTTP status code.
     * @apiError (Error 401) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *         "code": 401,
     *         "message": "The specified user is not authorised to use this service. "
     *     }
     * 
     * @apiError (Error 403) {Number} code HTTP status code.
     * @apiError (Error 403) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 403 Forbidden
     *     {
     *         "code": 403,
     *         "message": "The specified user is not authorised to use this service. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server could not register the user. "
     *     }
     * 
     * @apiDescription Registers new staff (first-time login, some information such as contact number and password may not be registred yet).
     */
    @PostMapping("/staff/register")
    public ResponseEntity<JSONBody> registerStaff(@RequestBody RegisterStaffDto dto) {
        return staffService.registerStaff(dto);
    }

    /**
     * @api {post} /staff/register/complete Complete registration of new staff
     * @apiName CompleteRegisterStaff
     * @apiGroup Staff
     * 
     * @apiBody {String} email Email of staff.
     * @apiBody {String} firstName First name of new staff.
     * @apiBody {String} lastName Last name of new staff.
     * @apiBody {String} contactNumber Contact number of new staff.
     * @apiBody {String} password Desired new password.
     * @apiBody {String} registerKey Registration key sent to email.
     * 
     * @apiSuccess (Success 201) {Number} code HTTP status code.
     * @apiSuccess (Success 201) {Object} data JSON object representing the new staff's information.
     * @apiSuccess (Success 201) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 201) {String} data[email] Email of the staff.
     * @apiSuccess (Success 201) {String} data[firstName] First name of the staff.
     * @apiSuccess (Success 201) {String} data[lastName] Last name of the staff.
     * @apiSuccess (Success 201) {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess (Success 201) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess (Success 201) {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess (Success 201) {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess (Success 201) {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *         "code": 201, 
     *         "data": {
     *             "staffId": 3, 
     *             "email": "test2@sportsschool.edu.sg", 
     *             "firstName": "test2", 
     *             "lastName": "testing2", 
     *             "contactNumber": "12345678", 
     *             "role": "BORROWER", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
     *         }
     *     }
     * 
     * @apiError (Error 401) {Number} code HTTP status code.
     * @apiError (Error 401) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *         "code": 401,
     *         "message": "The specified user is not authorised to use this service. "
     *     }
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *         "code": 401,
     *         "message": "Unauthorised account creation. "
     *     }
     * 
     * @apiError (Error 403) {Number} code HTTP status code.
     * @apiError (Error 403) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 403 Forbidden
     *     {
     *         "code": 403,
     *         "message": "The specified user is not authorised to use this service. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server could not register the user. "
     *     }
     * 
     * @apiDescription Complete the registration of the user, continuing from the email sent by the user registration API. 
     */
    @PostMapping("/staff/register/complete")
    public ResponseEntity<JSONBody> completeStaffRegistration(@RequestBody CompleteRegisterStaffDto dto) {
        return staffService.completeStaffRegistration(dto);
    }

    /**
     * @api {put} /staff/:staffId Update staff information
     * @apiName UpdateStaffDetails
     * @apiGroup Staff
     * 
     * @apiParam {Number} staffId Staff ID.
     * 
     * @apiBody {String} email Email of new staff.
     * @apiBody {String} firstName First name of new staff.
     * @apiBody {String} lastName Last name of new staff.
     * @apiBody {String} contactNumber Contact number of new staff.
     * @apiBody {String} role Role of new staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiBody {Boolean} cannotBook Whether new staff can book.
     * @apiBody {Boolean} isDisabled Whether new staff is present or soft-deleted.
     * 
     * @apiSuccess (Success 201) {Number} code HTTP status code.
     * @apiSuccess (Success 201) {Object} data JSON object representing the staff's information.
     * @apiSuccess (Success 201) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 201) {String} data[email] Email of the staff. If email changes, verification email will be sent. 
     * @apiSuccess (Success 201) {String} data[firstName] First name of the staff.
     * @apiSuccess (Success 201) {String} data[lastName] Last name of the staff.
     * @apiSuccess (Success 201) {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess (Success 201) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess (Success 201) {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess (Success 201) {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess (Success 201) {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 201 Created
     *     {
     *         "code": 201, 
     *         "data": {
     *             "staffId": 3, 
     *             "email": "test2@sportsschool.edu.sg", 
     *             "firstName": "test2", 
     *             "lastName": "testing2", 
     *             "contactNumber": "12345678", 
     *             "role": "BORROWER", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
     *         }
     *     }
     * 
     * @apiSuccessExample {json} Success-Reset-Email:
     *     HTTP/1.1 204 No Content
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *         "code": 404,
     *         "message": "User not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server could not change user details. "
     *     }
     * 
     * @apiDescription Updates staff's details. If email is changed, an email with a new password will be sent to the new email. 
     */
    @PutMapping("/staff/{staffId}")
    public ResponseEntity<JSONBody> updateStaffProfile(@PathVariable Integer staffId, @RequestBody UpdateProfileDto dto) {
        return staffService.updateStaffProfile(staffId, dto);
    }

    /**
     * @api {put} /staff/:staffId/password Update staff password
     * @apiName UpdateStaffPassword
     * @apiGroup Staff
     * 
     * @apiParam {Number} staffId Staff ID.
     * 
     * @apiBody {Number} staffId Staff ID.
     * @apiBody {String} oldPassword Old password of staff.
     * @apiBody {String} newPassword New password of staff.
     * @apiBody {String} confirmPassword Double-confirm new password of staff.
     * 
     * @apiSuccess (Success 200) {Number} code HTTP status code.
     * @apiSuccess (Success 200) {Object} data JSON object representing the staff's information.
     * @apiSuccess (Success 200) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 200) {String} data[email] Email of the staff. If email changes, verification email will be sent. 
     * @apiSuccess (Success 200) {String} data[firstName] First name of the staff.
     * @apiSuccess (Success 200) {String} data[lastName] Last name of the staff.
     * @apiSuccess (Success 200) {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess (Success 200) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess (Success 200) {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess (Success 200) {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess (Success 200) {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": {
     *             "staffId": 3, 
     *             "email": "test2@sportsschool.edu.sg", 
     *             "firstName": "test2", 
     *             "lastName": "testing2", 
     *             "contactNumber": "12345678", 
     *             "role": "BORROWER", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
     *         }
     *     }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 400,
     *         "message": "The specified new password does not match the confirmation password. "
     *     }
     * 
     * @apiError (Error 401) {Number} code HTTP status code.
     * @apiError (Error 401) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 401 Unauthorized
     *     {
     *         "code": 401,
     *         "message": "The specified user is not authorised to use this service. "
     *     }
     * 
     * @apiError (Error 404) {Number} code HTTP status code.
     * @apiError (Error 404) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 404 Not Found
     *     {
     *         "code": 404,
     *         "message": "The specified new password does not match the confirmation password. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server could not change user password. "
     *     }
     * 
     * @apiDescription Update staff password through email. 
     */
    @PutMapping("/staff/{staffId}/password")
    public ResponseEntity<JSONBody> updateStaffPassword(@PathVariable String staffId, @RequestBody UpdatePasswordDto dto) {
        return staffService.updateStaffPassword(dto);
    }

    /**
     * @api {put} /staff/:staffId/password/reset Reset staff password
     * @apiName ResetStaffPassword
     * @apiGroup Staff
     * 
     * @apiParam {Number} staffId Staff ID.
     * 
     * @apiSuccess (Success 200) {Number} code HTTP status code.
     * @apiSuccess (Success 200) {Object} data JSON object representing the staff's information.
     * @apiSuccess (Success 200) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 200) {String} data[email] Email of the staff. If email changes, verification email will be sent. 
     * @apiSuccess (Success 200) {String} data[firstName] First name of the staff.
     * @apiSuccess (Success 200) {String} data[lastName] Last name of the staff.
     * @apiSuccess (Success 200) {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess (Success 200) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess (Success 200) {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess (Success 200) {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess (Success 200) {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": {
     *             "staffId": 3, 
     *             "email": "test2@sportsschool.edu.sg", 
     *             "firstName": "test2", 
     *             "lastName": "testing2", 
     *             "contactNumber": "12345678", 
     *             "role": "BORROWER", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
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
     *         "message": "User not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server could not change user password. "
     *     }
     * 
     * @apiDescription Reset password through email. 
     */
    @PutMapping("/staff/{staffId}/password/reset")
    public ResponseEntity<JSONBody> resetStaffPassword(@PathVariable int staffId) {
        return staffService.resetPassword(staffId);
    }

    /**
     * @api {delete} /staff/:staffId Soft-delete staff
     * @apiName DeleteStaff
     * @apiGroup Staff
     * 
     * @apiParam {Number} staffId Staff ID.
     * 
     * @apiSuccess (Success 200) {Number} code HTTP status code.
     * @apiSuccess (Success 200) {Object} data JSON object representing the deleted staff's information.
     * @apiSuccess (Success 200) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 200) {String} data[email] Email of the staff. If email changes, verification email will be sent. 
     * @apiSuccess (Success 200) {String} data[firstName] First name of the staff.
     * @apiSuccess (Success 200) {String} data[lastName] Last name of the staff.
     * @apiSuccess (Success 200) {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess (Success 200) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess (Success 200) {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess (Success 200) {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess (Success 200) {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": {
     *             "staffId": 3, 
     *             "email": "test2@sportsschool.edu.sg", 
     *             "firstName": "test2", 
     *             "lastName": "testing2", 
     *             "contactNumber": "12345678", 
     *             "role": "BORROWER", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
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
     *         "message": "User not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server could not soft-delete user. "
     *     }
     * 
     * @apiDescription Soft-deletes the staff (user no longer able to login). 
     */
    @DeleteMapping("/staff/{staffId}")
    public ResponseEntity<JSONBody> disableStaff(@PathVariable int staffId) {
        return staffService.disableStaff(staffId);
    }

    /**
     * @api {put} /staff/:staffId/lock Lock the staff's account
     * @apiName LockStaff
     * @apiGroup Staff
     * 
     * @apiParam {Number} staffId Staff ID.
     * 
     * @apiSuccess (Success 200) {Number} code HTTP status code.
     * @apiSuccess (Success 200) {Object} data JSON object representing the locked staff's information.
     * @apiSuccess (Success 200) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 200) {String} data[email] Email of the staff. If email changes, verification email will be sent. 
     * @apiSuccess (Success 200) {String} data[firstName] First name of the staff.
     * @apiSuccess (Success 200) {String} data[lastName] Last name of the staff.
     * @apiSuccess (Success 200) {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess (Success 200) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess (Success 200) {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess (Success 200) {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess (Success 200) {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": {
     *             "staffId": 3, 
     *             "email": "test2@sportsschool.edu.sg", 
     *             "firstName": "test2", 
     *             "lastName": "testing2", 
     *             "contactNumber": "12345678", 
     *             "role": "BORROWER", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
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
     *         "message": "User not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server could not lock the user. "
     *     }
     * 
     * @apiDescription Locks the staff's account. 
     */
    @PutMapping("/staff/{staffId}/lock")
    public ResponseEntity<JSONBody> lockStaff(@PathVariable int staffId) {
        return staffService.lockStaff(staffId);
    }

    /**
     * @api {put} /staff/:staffId/unlock Unlock the staff's account
     * @apiName UnlockStaff
     * @apiGroup Staff
     * 
     * @apiParam {Number} staffId Staff ID.
     * 
     * @apiSuccess (Success 200) {Number} code HTTP status code.
     * @apiSuccess (Success 200) {Object} data JSON object representing the unlocked staff's information.
     * @apiSuccess (Success 200) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 200) {String} data[email] Email of the staff. If email changes, verification email will be sent. 
     * @apiSuccess (Success 200) {String} data[firstName] First name of the staff.
     * @apiSuccess (Success 200) {String} data[lastName] Last name of the staff.
     * @apiSuccess (Success 200) {String} data[contactNumber] Contact number of the staff.
     * @apiSuccess (Success 200) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * @apiSuccess (Success 200) {String} data[cannotBook] Whether the staff can book.
     * @apiSuccess (Success 200) {String} data[disabled] Whether the staff is soft-deleted.
     * @apiSuccess (Success 200) {String} data[registered] Whether the staff is registered already.
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": {
     *             "staffId": 3, 
     *             "email": "test2@sportsschool.edu.sg", 
     *             "firstName": "test2", 
     *             "lastName": "testing2", 
     *             "contactNumber": "12345678", 
     *             "role": "BORROWER", 
     *             "cannotBook": true, 
     *             "disabled": false, 
     *             "registered": false
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
     *         "message": "User not found. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server could not unlock the user. "
     *     }
     * 
     * @apiDescription Unlocks the staff's account. 
     */
    @PutMapping("/staff/{staffId}/unlock")
    public ResponseEntity<JSONBody> unlockStaff(@PathVariable int staffId) {
        return staffService.unlockStaff(staffId);
    }

    @GetMapping("/staffs/status/admin")
    public ResponseEntity<JSONBody> getAdmin() {
        return staffService.getAdmin();
    }

    @PutMapping("/staff/{staffId}/status/admin")
    public ResponseEntity<JSONBody> addAdmin(@PathVariable int staffId) {
        return staffService.addAdmin(staffId);
    }

    @PutMapping("/staff/{staffId}/status/borrower")
    public ResponseEntity<JSONBody> removeAdmin(@PathVariable int staffId) {
        return staffService.removeAdmin(staffId);
    }
}

