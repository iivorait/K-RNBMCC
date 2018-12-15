package com.raitahila.k.rnbmcc;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }
    
    @Test
    public void OCRBase64() throws Exception {
        String data = "{ \"image\": \"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\" }";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/ocrtest2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(data).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("No heei"));
    }
}