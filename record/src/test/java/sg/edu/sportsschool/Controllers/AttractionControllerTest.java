package sg.edu.sportsschool.Controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

// import capital.scalable.restdocs.AutoDocumentation;
// import capital.scalable.restdocs.jackson.JacksonResultHandlers;
// import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import sg.edu.sportsschool.DTO.Request.CreateAttractionDto;
import sg.edu.sportsschool.Repositories.AttractionRepository;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class AttractionControllerTest {

    // @Autowired
    // private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AttractionRepository attractionRepository;

    @Autowired
    public AttractionControllerTest(MockMvc mockMvc, ObjectMapper objectMapper,
            AttractionRepository attractionRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.attractionRepository = attractionRepository;
    }

    @BeforeEach
    // public void setup() {
    public void setup(WebApplicationContext webApplicationContext, 
            RestDocumentationContextProvider restDocumentation) throws Exception {

        // this.mockMvc = MockMvcBuilders
        //         .webAppContextSetup(webApplicationContext)
        //         // .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
        //         .alwaysDo(MockMvcRestDocumentation.document("{method-name}",
        //                 Preprocessors.preprocessRequest(),
        //                 Preprocessors.preprocessResponse(
        //                         // ResponseModifyingPreprocessors.replaceBinaryContent(),
        //                         // ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
        //                         Preprocessors.prettyPrint())))
        //         .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                //         .uris()
                //         .withScheme("http")
                //         .withHost("localhost")
                //         .withPort(8080)
                //         .and().snippets()
                //         .withDefaults(CliDocumentation.curlRequest(),
                //                 HttpDocumentation.httpRequest(),
                //                 HttpDocumentation.httpResponse(),
                //                 AutoDocumentation.requestFields(),
                //                 AutoDocumentation.responseFields(),
                //                 AutoDocumentation.pathParameters(),
                //                 AutoDocumentation.requestParameters(),
                //                 AutoDocumentation.description(),
                //                 AutoDocumentation.methodAndPath(),
                //                 AutoDocumentation.section()))
                // .build();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .alwaysDo(document("{method-name}", Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(),
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse()))
                                // AutoDocumentation.requestFields(),
                                // AutoDocumentation.responseFields(),
                                // AutoDocumentation.pathParameters(),
                                // AutoDocumentation.requestParameters(),
                                // AutoDocumentation.description(),
                                // AutoDocumentation.methodAndPath(),
                                // AutoDocumentation.section()))
                .build();

        attractionRepository.deleteAll();
    }

    @Test
    void testAddAttraction() throws Exception {
        // given - precondition or setup
        CreateAttractionDto dto = new CreateAttractionDto("Singapore Zoo",
                "premium corporate friends of the zoo (cfoz) membership", 'd', 52.15F, 4, 2, 2,
                "80 Mandai Lake Rd, 729826", "123211233587418925", 2022, 12, 31,
                "Present this letter to enjoy up to 20% discount at selected Retail and F&B outlets.",
                "• All corporate members must secure a time slot via https://managebooking.mandai.com/ and comply to MWR Safe Management Measures for your safety and well-being, otherwise entry is not allowed. Time slot bookings are subject to availability, on a first come, first served basis. \n• Each letter allows complimentary admission to Singapore Zoo & River Wonders for up to four (4) persons, one of whom must be an employee of the corporate member named above. The employee must be present and produce valid staff pass or NRIC along with the signed letter for benefit to apply. \n• Each letter can only be used ONCE a day.\n• In the event of unauthorized use or copy of the letter, Singapore Zoo & River Wonders will deny entry and verification will be done with the company which will take appropriate action for any wilful violation. \n • In the event that the letter is detected to be presented more than once on the same day; the employee will be liable for the additional entry at Singapore Zoo & River Wonders prevailing walk-in rate accordingly.\n• Your existing Corporate Membership will expire once the validity period is over. In addition, you shall adhere to the Membership terms and conditions stated https://www.mandai.com/en/memberships/corporate-membership.html during your visit.");

        // when - action or behaviour that we are going test
        String requestBody = objectMapper.writeValueAsString(dto);
        ResultActions response = mockMvc.perform(post("/api/attraction/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        // then - verify the result or output using assert statements
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name", is(dto.getName())))
                .andExpect(jsonPath("$.data.description", is(dto.getDescription())))
                .andExpect(jsonPath("$.data.passType", is(dto.getPassType() + "")))
                .andExpect(jsonPath("$.data.address", is(dto.getAddress())));
        // .andDo(document("home"));
        // response.andExpect(status().isOk())
        // .andExpect(MockMvcResultMatchers.content().json(requestBody))

    }

    @Test
    void testAddBarcodeToAttraction() {

    }

    @Test
    void testGetAllAttractions() {

    }

    @Test
    void testUpdateAttraction() {

    }
}
