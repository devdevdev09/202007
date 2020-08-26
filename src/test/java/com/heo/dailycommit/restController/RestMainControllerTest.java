package com.heo.dailycommit.restController;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
// @AutoConfigureWebClient
public class RestMainControllerTest {
    
    @Autowired
    MockMvc mvc;

    @Test
    public void test_stringTest() throws Exception{
        mvc.perform(get("/test/string")).andExpect(status().isOk()).andExpect(content().string("test string"));
    }

    // @Test
    // public void test_stringTest(@Autowired WebTestClient webClient) {
    //     webClient.get().uri("/test/string").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("test string");
    // }



    // @Test
    // public void test_stringTest(@Autowired WebTestClient webClient) {
    //     webClient.get().uri("/test/string").exchange().expectStatus().isOk().expectBody(String.class).isEqualTo("test string");
    // }

}