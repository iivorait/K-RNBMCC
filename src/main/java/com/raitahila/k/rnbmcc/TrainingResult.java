package com.raitahila.k.rnbmcc;

import java.util.ArrayList;

public class TrainingResult {
    private String label;
    private ArrayList<String> words;

    public TrainingResult(String label, ArrayList<String> words) {
        this.label = label;
        this.words = words;
    }

    public TrainingResult(String label) {
        this.label = label;
        this.words = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }
}
