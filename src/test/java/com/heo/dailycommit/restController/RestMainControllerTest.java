package com.heo.dailycommit.restController;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
// import static
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RestMainControllerTest {

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    
    private MockMvc mockMvc;
    private RestDocumentationResultHandler document;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp(){
        this.document = document(
            "{class-name}/{method-name}",
            preprocessResponse(prettyPrint())
        );

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                    .apply(documentationConfiguration(this.restDocumentation)
                    .uris().withScheme("https").withHost("daily-commit-counter.herokuapp.com").withPort(443))
                    .alwaysDo(document)
                    .build();
    }

    @Test
    public void getDailyCommit() throws Exception {
        mockMvc.perform(
            RestDocumentationRequestBuilders.get("/dailycommit/{id}", "devdevdev09")  
                .param("year", "2020")  
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document.document(
                    pathParameters(
                        parameterWithName("id").description("github_id")
                    ),
                    requestParameters(
                        parameterWithName("year").description("요청년도").optional()
                    ),
                    responseFields(
                        fieldWithPath("user").description("github id"),
                        fieldWithPath("date").description("체크일자"),
                        fieldWithPath("daily").type(Boolean.class).description("커밋여부"),
                        fieldWithPath("continues").type(Integer.class).description("연속일수")
                    )
                ))
                .andExpect(jsonPath("user", is(notNullValue())))
                .andExpect(jsonPath("date", is(notNullValue())))
                .andExpect(jsonPath("daily", is(notNullValue())))
                .andExpect(jsonPath("continues", is(notNullValue())));
    }







    

}