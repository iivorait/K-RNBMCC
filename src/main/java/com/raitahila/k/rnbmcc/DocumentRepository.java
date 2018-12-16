package com.raitahila.k.rnbmcc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
interface DocumentRepository extends JpaRepository<Document, Long> {

    Document findByLabelAndWord(String label, String word);
    
    @Query("SELECT DISTINCT d.label FROM Document d")
    List<String> findClasses();
    
    @Query("SELECT new com.raitahila.k.rnbmcc.Document(d.label, SUM(d.count))"
            + " FROM Document d GROUP BY d.label")
    List<Document> findTotalWordCounts();
}