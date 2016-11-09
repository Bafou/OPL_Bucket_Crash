/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.java.fr.univlille1.m2iagl.crashbucket.helpers;

import fr.univlille1.m2iagl.crashbucket.helpers.ParsingHelper;
import static fr.univlille1.m2iagl.crashbucket.helpers.ParsingHelper.listTestingFolder;
import java.io.File;
import java.util.List;
import static junit.framework.TestCase.assertEquals;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rahal Badr
 */
public class ParsingHelperTest {
    
    public ParsingHelperTest() {
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
     * Test of listFilesForFolder method, of class ParsingHelper.
     */
    @Test
    public void testListFilesForFolder() {
        System.out.println("listFilesForFolder");
        File folder = null;
        ParsingHelper.listFilesForFolder(folder);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of listTrainingFolder method, of class ParsingHelper.
     */
    @Test
    public void testListTrainingFolder() {
        System.out.println("listTrainingFolder");
        File folder = null;
        List<String> expResult = null;
        List<String> result = ParsingHelper.listTrainingFolder(folder);
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
        List<String> result = ParsingHelper.listTestingFolder(folder);
        //assertEquals(expResult, result);
        assertEquals("testfile", result.get(0));
    }   
}
