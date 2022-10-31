package sg.edu.sportsschool.Controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import sg.edu.sportsschool.DTO.Request.SignupDto;
import sg.edu.sportsschool.Repositories.StaffRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StaffRepository sRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        sRepository.deleteAll();
    }

    @Test
    public void signUp() throws Exception {
        // given - precondition or setup
        // SignupDto signupDto = new SignupDto("Zhiwei", "Thean", "zwthean.2021@scis.smu.edu.sg", "91234567", "admin",
        //         "password");

        
        // when - action or the behaviour we are going to test
        // ResultActions response = mockMvc.perform(post("/api/staff/signup")
        //         .contentType(MediaType.APPLICATION_JSON_VALUE)
        //         .content(objectMapper.writeValueAsString(signupDto)));

        // then verify the output
        // response.andDo(print())
        //         .andExpect(status().isOk())
                // .andExpect(jsonPath("$.", null))

    }
}
