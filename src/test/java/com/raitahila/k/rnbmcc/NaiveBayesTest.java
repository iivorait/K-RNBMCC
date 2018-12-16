/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raitahila.k.rnbmcc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author iivo
 */
@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class NaiveBayesTest {
    
    @Autowired
    private DocumentRepository repository;
    @Autowired
    @Qualifier("NB")
    private NaiveBayes inst;
    
    public NaiveBayesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        repository.deleteAll();
    }

    /**
     * Test of filterWords method, of class NaiveBayes.
     */
    @Test
    public void testFilterWords() {
        String rawInput = "a ab abc abcd abcde  abcdef ABCDISO";
        NaiveBayes instance = new NaiveBayes();
        ArrayList<String> result = instance.filterWords(rawInput);
        assertTrue(result.contains("abcde"));
        assertTrue(result.contains("abc"));
        assertFalse(result.contains("ab"));
        assertTrue(result.contains("abcdiso"));
    }

    @Test
    public void testPreprocessTotalCounts() {
        this.repository.save(new Document("lasku", 5L, "viitenumero"));
        this.repository.save(new Document("saft", 1L, "viitenumero"));

        HashMap<String, Long> result = inst.preprocessTotalCounts();
        
        assertTrue(result.get("lasku") == 5L);
        assertTrue(result.get("saft") == 1L);
    }

    /**
     * Test of calculateProbability method, of class NaiveBayes.
     */
    @Test
    public void testCalculateProbability() {
        this.repository.save(new Document("lasku", 5L, "viitenumero"));
        this.repository.save(new Document("saft", 1L, "viitenumero"));
        this.repository.save(new Document("saft", 4L, "selvitys"));
        List<String> words = new ArrayList<>(Arrays.asList("viitenumero"));
        
        HashMap<String, Long> counts = inst.preprocessTotalCounts();
        
        double result1 = inst.calculateProbability(words, "lasku", counts);
        double result2 = inst.calculateProbability(words, "saft", counts);
        assertTrue(result1 > result2);
    }

    /**
     * Test of calculateProbability method, of class NaiveBayes.
     */
    @Test
    public void testCalculateProbability2() {
        this.repository.save(new Document("lasku", 5L, "viitenumero"));
        this.repository.save(new Document("lasku", 5L, "IBAN"));
        this.repository.save(new Document("saft", 1L, "viitenumero"));
        this.repository.save(new Document("saft", 6L, "selvitys"));
        this.repository.save(new Document("saft", 4L, "annetusta"));
        List<String> words = new ArrayList<>(Arrays.asList(
                "IBAN", "selvitys"));
        
        HashMap<String, Long> counts = inst.preprocessTotalCounts();
        
        double result1 = inst.calculateProbability(words, "lasku", counts);
        double result2 = inst.calculateProbability(words, "saft", counts);
        assertTrue(result1 < result2);
    }
    
    /**
     * Test of calculateProbability method, of class NaiveBayes.
     */
    @Test
    public void testCalculateProbability3() {
        this.repository.save(new Document("lasku", 5L, "viitenumero"));
        this.repository.save(new Document("saft", 6L, "selvitys"));
        List<String> words = new ArrayList<>(Arrays.asList(
                "spurdo", "viitenumero", "sparde"));
        
        HashMap<String, Long> counts = inst.preprocessTotalCounts();
        
        double result1 = inst.calculateProbability(words, "lasku", counts);
        double result2 = inst.calculateProbability(words, "saft", counts);
        assertTrue(result1 > result2);
    }
    
    /**
     * Test of classify method, of class NaiveBayes.
     */
    @Test
    public void testClassify() {
        this.repository.save(new Document("lasku", 5L, "viitenumero"));
        this.repository.save(new Document("saft", 6L, "selvitys"));
        this.repository.save(new Document("saft", 6L, "annetusta"));
        this.repository.save(new Document("palaute", 6L, "palaute"));
        
        assertEquals("palaute", inst.classify("annetusta palaute sparde"));
    }
}
