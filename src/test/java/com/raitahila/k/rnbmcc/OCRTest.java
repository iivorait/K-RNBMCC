/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.raitahila.k.rnbmcc;

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
public class OCRTest {
    
    public OCRTest() {
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
     * Test of ocrFile method, of class OCR.
     */
    @Test
    public void testOcrFile() throws Exception {
        System.out.println("ocrFile");
        OCR instance = new OCR();
        String result = instance.ocrFile("testfiles/fin-test.png");
        assertTrue(result.contains("Moi! Ääkköskoe"));
    }
    
    //Base64 test in controller's test
}
