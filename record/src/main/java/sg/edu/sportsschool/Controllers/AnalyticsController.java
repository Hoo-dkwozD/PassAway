package sg.edu.sportsschool.Controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.sportsschool.DTO.Request.AnalyticsDto;
import sg.edu.sportsschool.Helper.Json.JSONBody;
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

    @GetMapping("/loan/csv")
    public void loanTrend(@RequestBody AnalyticsDto dto, HttpServletResponse response) {
        this.analyticsService.getLoanCSV(dto, response);
    }
}
