package com.raitahila.k.rnbmcc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class HelloController {

    @Autowired
    private OCR ocr;
    
    @GetMapping("/")
    public String index() {
            return "Greetings from Spring Boot!";
    }
    
    @GetMapping("/ocrtest")
    public String ocrtest() {
        
        try {
            return ocr.ocrFile("testfiles/long-test.jpg");
        } catch (Exception ex) {
            return ex.getMessage();
        }
        
    }
    
    @PostMapping("/ocrtest2")
    String ocrtest2(@RequestBody Document newDocument) throws FileNotFoundException, IOException, Exception {
        byte[] data = Base64.decodeBase64(newDocument.getImage());
        File image = File.createTempFile("img", ".temp");
        try (OutputStream stream = new FileOutputStream(image)) {
            stream.write(data);
        }
        return ocr.ocrFile(image.getAbsolutePath());
    }
}