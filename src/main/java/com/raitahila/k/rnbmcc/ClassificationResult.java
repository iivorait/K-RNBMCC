package com.raitahila.k.rnbmcc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.text.DecimalFormat;
import java.util.HashMap;

public class ClassificationResult {
    private String label;
    private HashMap<String, String> probabilities;
    @JsonIgnore
    private HashMap<String, Double> rawProbabilities;
    
    public ClassificationResult() {
        this.probabilities = new HashMap<>();
        this.rawProbabilities = new HashMap<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public HashMap<String, String> getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(HashMap<String, String> probabilities) {
        this.probabilities = probabilities;
    }

    public void addProbablity(String label, double probability) {
        this.rawProbabilities.put(label, probability);
        double total = 0.0;
        for (Double value : rawProbabilities.values()) {
            total += value;
        }
        
        DecimalFormat df2 = new DecimalFormat("0.00");
        this.probabilities.clear();
        for (String key : rawProbabilities.keySet()) {
            double prob = rawProbabilities.get(key) / total * 100;
            probabilities.put(key, df2.format(prob) + "%");
        }
    }
}
