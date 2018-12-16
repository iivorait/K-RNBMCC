package com.raitahila.k.rnbmcc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private DocumentRepository repository;
    @Autowired
    private NaiveBayes naivebayes;
    
    
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
    String ocrtest2(@RequestBody Document newDocument) throws Exception {
        return ocr.ocrBase64(newDocument.getImage());
    }
    
    @GetMapping("/documents")
    List<Document> all() {
        return repository.findAll();
    }
    
    @PostMapping("/documents")
    Document newDocument(@RequestBody Document newDocument) {
        return repository.save(newDocument);
    }
    
    @PostMapping("/train")
    String train(@RequestBody Document newDocument) throws Exception {
        String rawText = ocr.ocrBase64(newDocument.getImage());
        naivebayes.train(newDocument.getLabel(), rawText);
        return "done: " + Arrays.toString(naivebayes.filterWords(rawText).toArray());
    }
    
    @PostMapping("/classify")
    String classify(@RequestBody Document newDocument) throws Exception {
        String rawText = ocr.ocrBase64(newDocument.getImage());
        return naivebayes.classify(rawText);
    }
    
}