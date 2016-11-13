/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.fr.univlille1.m2iagl.crashbucket.helpers;

import fr.univlille1.m2iagl.crashbucket.helpers.FilesLoader;
import java.io.File;
import java.util.List;
import static junit.framework.TestCase.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertEquals;

/**
 *
 * @author Rahal Badr
 */
public class FilesLoaderTest {
    
    public FilesLoaderTest() {
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
     * Test of listTrainingFolder method, of class ParsingHelper.
     */
    @Test
    public void testListTrainingFolder() {
        System.out.println("listTrainingFolder");
        File folder = null;
        List<String> expResult = null;
        List<String> result = FilesLoader.listTrainingFolder(folder);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listTestingFolder method, of class ParsingHelper.
     * @throws java.lang.Exception
     */
    @Test
    public void testListTestingFolder() throws Exception{
        System.out.println("listTestingFolder");
        File folder = new File("fr/univlille1/m2iagl/crashbucket/ressources/testpackage");
        List<String> expResult = null;
        List<String> result = FilesLoader.listTestingFolder(folder);
        //assertEquals(expResult, result);
        assertEquals("testfile", result.get(0));
    }   
}
