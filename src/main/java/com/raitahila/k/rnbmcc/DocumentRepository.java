package com.raitahila.k.rnbmcc;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findByLabelAndWord(String label, String word);
    
    @Query("SELECT DISTINCT d.label FROM Document d")
    List<String> findClasses();
    
    //TODO: summa counteista ja group by label
}