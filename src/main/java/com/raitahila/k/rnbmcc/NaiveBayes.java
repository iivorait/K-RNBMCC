package com.raitahila.k.rnbmcc;

import static java.lang.Math.exp;
import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import static org.bytedeco.javacpp.lept.pixDestroy;
import static org.bytedeco.javacpp.lept.pixRead;
import org.bytedeco.javacpp.tesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service("NB")
@EnableAutoConfiguration
public class NaiveBayes {
    
    @Autowired( required = true )
    private DocumentRepository repository;
    
    public ArrayList<String> filterWords(String rawInput) {
        String[] rawWords = rawInput.split("\\s+"); //Split on whitespace
        ArrayList<String> words = new ArrayList<>(Arrays.asList(rawWords));
        words.removeIf(x -> x.length() < 3);
        return words.stream().map(String::toLowerCase)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    
    public void train(String label, String rawText) {
        for (String word : filterWords(rawText)) {
            Document doc = repository.findByLabelAndWord(label, word);
            if(doc == null) {
                doc = new Document(label, 1L, word);
            } else {
                doc.incrementCount();
            }
            repository.save(doc);
        }
        //TODO: testi metodille
    }
    
    public String classify(String rawText) {
        ArrayList<String> words = this.filterWords(rawText);
        HashMap<String, Long> counts = this.preprocessTotalCounts();
        double highestProbability = 0.0;
        String highestClass = "";
        
        for (String label : counts.keySet()) {
            double prob = this.calculateProbability(words, label, counts);
            System.out.println("Probability of class " + label + ": " + prob);
            if(prob > highestProbability) {
                highestProbability = prob;
                highestClass = label;
            }
        }
        
        System.out.println("------------------------");
        return highestClass;
    }
    
    public HashMap<String, Long> preprocessTotalCounts() {
        List<Document> rawTotals = repository.findTotalWordCounts();
        HashMap<String, Long> counts = new HashMap<>();
        for (Document total : rawTotals) {
            counts.put(total.getLabel(), total.getCount());
        }
        return counts;
    }
    
    public double calculateProbability(List<String> words, String label, HashMap<String, Long> counts) {
        //Using logarithms to avoid underflows and overflows
        double numerator = log(1.0 / counts.size());
        double denominator = log(1.0);
        
        //Calculate numerator
        //P(prior) * P(word1 | class) * P(word2 | class) * ...
        for (String word : words) {
            Document doc = repository.findByLabelAndWord(label, word);
            if(doc != null) {
                numerator += log(((double) doc.getCount()) / counts.get(label));
            } else {
                numerator += log(0.000001);
            }
        }

        //Calculate denominator
        //(P(prior) * P(word1 | class1) + P(prior) * P(word1 | class2)) * ...
        for (String word : words) {
            double wordProb = 0.000001;
            
            for (String key : counts.keySet()) {
                Document doc = repository.findByLabelAndWord(key, word);
                if(doc != null) {
                    wordProb += 1.0/counts.size() * doc.getCount() / counts.get(key);
                }
            }
            denominator += log(wordProb);
        }
        
        return exp(numerator - denominator);
    }
    
    public List<Document> koe () {
        return repository.findTotalWordCounts();
    }
}
