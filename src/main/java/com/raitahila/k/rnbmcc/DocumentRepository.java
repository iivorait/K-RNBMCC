package com.raitahila.k.rnbmcc;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findByLabelAndWord(String label, String word);
    
    /**
     * Get list of different classes that have been trained
     * @return List<String>
     */
    @Query("SELECT DISTINCT d.label FROM Document d")
    List<String> findClasses();
    
    /**
     * Get the total number of words in each class
     * @return List<Document> one document per class
     */
    @Query("SELECT new com.raitahila.k.rnbmcc.Document(d.label, SUM(d.count))"
            + " FROM Document d GROUP BY d.label")
    List<Document> findTotalWordCounts();
}