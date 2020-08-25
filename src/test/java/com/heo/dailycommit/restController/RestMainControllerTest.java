package com.heo.dailycommit.restController;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestMainControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    
    
    @Test
    public void hello() throws Exception{
        String result = testRestTemplate.getForObject("/test", String.class);
        assertEquals(result, "test");

    }
}