package com.raitahila.k.rnbmcc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;
import org.bytedeco.javacpp.tesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NaiveBayes {
    
    @Autowired
    private DocumentRepository repository;
    
    public ArrayList<String> filterWords(String rawInput) {
        String[] rawWords = rawInput.split("\\s+"); //Split on whitespace
        ArrayList<String> words = new ArrayList<>(Arrays.asList(rawWords));
        words.removeIf(x -> x.length() < 3);
        return words;
    }
    
    public void train(String label, String rawText) {
        for (String word : filterWords(rawText)) {
            Document doc = repository.findByLabelAndWord(label, word);
            if(doc == null) {
                doc = new Document(label, 1, word);
            } else {
                doc.incrementCount();
            }
            repository.save(doc);
        }
        //TODO: testi metodille
    }
    
    public List<Document> koe () {
        return repository.findAll();
    }
}
