package com.raitahila.k.rnbmcc;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MainController {

    @Autowired
    private OCR ocr;
    @Autowired
    private DocumentRepository repository;
    @Autowired
    private NaiveBayes naivebayes;
    
    
    @GetMapping("/")
    public String index() {
            return "Hello!";
    }
    
    /**
     * Get all trained word, count and label combinations
     */
    @GetMapping("/documents")
    List<Document> all() {
        return repository.findAll();
    }
    
    /**
     * Train without OCR, for fast initialization
     * @param newDocument containing word, count and label
     */
    @PostMapping("/documents")
    Document newDocument(@RequestBody Document newDocument) {
        return repository.save(newDocument);
    }
    
    /**
     * Train with OCR
     * @param newDocument containing label and image (Base64 encoded)
     * @return 
     */
    @PostMapping("/train")
    TrainingResult train(@RequestBody Document newDocument) throws Exception {
        String rawText = ocr.ocrBase64(newDocument.getImage());
        naivebayes.train(newDocument.getLabel(), rawText);
        return new TrainingResult(newDocument.getLabel(), naivebayes.filterWords(rawText));
    }
    
    @PostMapping("/classify")
    ClassificationResult classify(@RequestBody Document newDocument) throws Exception {
        String rawText = ocr.ocrBase64(newDocument.getImage());
        return naivebayes.classify(rawText);
    }
    
}