package com.raitahila.k.rnbmcc;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Entity
class Document {

    private @Id @GeneratedValue Long id;
    private String label;
    private int count;
    private String word;
    private @Transient String image;

    public Document(String label, int count, String word, String image) {
        this.label = label;
        this.count = count;
        this.word = word;
        this.image = image;
    }

    public Document(String label, int count, String word) {
        this.label = label;
        this.count = count;
        this.word = word;
    }
    
    public Document(String label, String image) {
        this.label = label;
        this.image = image;
    }
    
    public void incrementCount() {
        count++;
    }
}