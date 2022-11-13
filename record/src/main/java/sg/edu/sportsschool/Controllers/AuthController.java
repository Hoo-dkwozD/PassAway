package sg.edu.sportsschool.Controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.DTO.Request.SignInDto;
import sg.edu.sportsschool.Helper.Json.JSONBody;
import sg.edu.sportsschool.Services.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * @api {post} /auth/signin Sign in to the system
     * @apiName SignIn
     * @apiGroup Auth
     * 
     * @apiBody {String} email Email of the user.
     * @apiBody {String} password Password of the user.
     * 
     * @apiSuccess (Success 200) {Number} code HTTP status code.
     * @apiSuccess (Success 200) {Object} data JSON object representing the staff's information.
     * @apiSuccess (Success 200) {Number} data[staffId] Staff ID.
     * @apiSuccess (Success 200) {String} data[role] Role of the staff. ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     * 
     * @apiSuccessExample {json} Success-Response:
     *     HTTP/1.1 200 OK
     *     {
     *         "code": 200, 
     *         "data": {
     *             "staffId": 3, 
     *             "role": "0"
     *         }
     *     }
     * 
     * @apiError (Error 400) {Number} code HTTP status code.
     * @apiError (Error 400) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 400 Bad Request
     *     {
     *         "code": 404,
     *         "message": "Authentication failed. "
     *     }
     * 
     * @apiError (Error 500) {Number} code HTTP status code.
     * @apiError (Error 500) {String} message Error message.
     * 
     * @apiErrorExample {json} Error-Response:
     *     HTTP/1.1 500 Internal Server Error
     *     {
     *         "code": 500,
     *         "message": "Server unable to authenticate. "
     *     }
     * 
     * @apiDescription Sets HTTP-Only Cookie as side-effect. 
     * Role value mapping is as follows: 
     * ("0": BORROWER, "1": ADMINISTRATOR, "2": GOP)
     */
    @PostMapping("/signin")
    public ResponseEntity<JSONBody> signin(@RequestBody SignInDto dto, HttpServletResponse response) {
        return this.authService.signin(dto, response);
    }
}
