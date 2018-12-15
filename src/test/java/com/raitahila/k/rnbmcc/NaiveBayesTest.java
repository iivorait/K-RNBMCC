/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raitahila.k.rnbmcc;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author iivo
 */
public class NaiveBayesTest {
    
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
    }

    /**
     * Test of filterWords method, of class NaiveBayes.
     */
    @Test
    public void testFilterWords() {
        String rawInput = "a ab abc abcd abcde  abcdef";
        NaiveBayes instance = new NaiveBayes();
        ArrayList<String> result = instance.filterWords(rawInput);
        assertTrue(result.contains("abcde"));
        assertTrue(result.contains("abc"));
        assertFalse(result.contains("ab"));
    }
    
}
