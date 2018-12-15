package com.raitahila.k.rnbmcc;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class HelloController {

    @Autowired
    private OCR ocr;
    
    @RequestMapping("/")
    public String index() {
            return "Greetings from Spring Boot!";
    }
    
    @RequestMapping("/ocrtest")
    public String ocrtest() {
        
        try {
            return ocr.ocrFile("testfiles/fin-test.png");
        } catch (Exception ex) {
            return ex.getMessage();
        }
        
    }
}