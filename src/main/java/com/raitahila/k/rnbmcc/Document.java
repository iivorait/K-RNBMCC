package com.raitahila.k.rnbmcc;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Document {

	private @Id @GeneratedValue Long id;
	private String label;
	private String count;
        private String word;
        private String image;

    public Document(String label, String count, String word, String image) {
        this.label = label;
        this.count = count;
        this.word = word;
        this.image = image;
    }
    
    public Document(String label, String image) {
        this.label = label;
        this.image = image;
    }
}