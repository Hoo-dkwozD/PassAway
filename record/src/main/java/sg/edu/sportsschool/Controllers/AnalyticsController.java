package sg.edu.sportsschool.Controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.DTO.Request.AnalyticsDto;
import sg.edu.sportsschool.Services.AnalyticsService;

@CrossOrigin
@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private AnalyticsService analyticsService;

    @Autowired
    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    /**
     * @api {post} /analytics/loan/csv Retrieves CSV file of loan data
     * @apiName GetLoanCSV
     * @apiGroup Analytics
     * 
     * @apiBody {Number} fromDay Day of the date to start looking from. 
     * @apiBody {Number} fromMonth Month of the date to start looking from. 
     * @apiBody {Number} fromDay Year of the date to start looking from. 
     * @apiBody {Number} toDay Day of the date to look up till. 
     * @apiBody {Number} toMonth Month of the date to look up till. 
     * @apiBody {Number} toDay Year of the date to look up till. 
     * 
     * @apiSuccess {CSV} file The desired CSV file.
     * 
     * @apiSuccessExample {text/csv} Success-Response:
     *     HTTP/1.1 200 OK
     *     "Loan ID","Pass ID","Attraction ID","Attraction Name","Staff ID","Staff Name","Start Date","Has Collected","Has Returned"
     *     "1","a","1","a","1","testa testing2","2022-12-20","1","1"
     * 
     * @apiDescription Retrieves a CSV file containing loan data time-boxed within the specified dates. 
     */
    @PostMapping("/loan/csv")
    public void getLoanCSV(@RequestBody AnalyticsDto dto, HttpServletResponse response) {
        this.analyticsService.getLoanCSV(dto, response);
    }
}
