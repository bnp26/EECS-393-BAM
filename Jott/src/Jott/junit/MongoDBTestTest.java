/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jott.junit;

import Jott.MongoDB;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author AlpGuvenir
 */
public class MongoDBTestTest {
    
    public MongoDBTestTest() {
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
    
    /*
    public static void main(String[] args) {
        testingGetNotebooks();
    }
    */
    
    @Test
    public void testingGetNotebooks() {
        System.out.println("getNotebooks() being tested...");
        MongoDBTest test = new MongoDBTest(); // test instance
        MongoDB mbdb = new MongoDB(); // real instance
        
        System.out.println("Test array");
        System.out.println(test.testGetNotebooks());
        
        System.out.println("Instance in db");
        System.out.println(mbdb.getNotebooks());

        assertEquals(test.testGetNotebooks(), mbdb.getNotebooks());
        System.out.println("TEST FINISHED FOR getNotebooks()");
    }
    
    @Test
    public void testingGetPages() {
        System.out.println("getPages(\"ALP\") being tested...");
        MongoDBTest test = new MongoDBTest(); // test instance
        MongoDB mbdb = new MongoDB(); // real instance
        
        System.out.println("Test array");
        System.out.println(test.testGetPages());
        
        System.out.println("Instance in db");
        System.out.println(mbdb.getPages("ALP"));

        assertEquals(test.testGetNotebooks(), mbdb.getNotebooks());
        System.out.println("TEST FINISHED FOR getPages(\"ALP\")");
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
