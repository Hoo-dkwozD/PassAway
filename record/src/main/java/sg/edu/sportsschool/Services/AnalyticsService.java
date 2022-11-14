package sg.edu.sportsschool.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import sg.edu.sportsschool.DTO.Request.AnalyticsDto;
import sg.edu.sportsschool.Helper.Json.JSONBody;

@Service
public class AnalyticsService {
    public ResponseEntity<JSONBody> loanTrend(AnalyticsDto dto) {
        return null;
    }
}
