/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jott.junit;

import Jott.MongoDB;
import java.util.ArrayList;
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
public class MongoDBTest {
    
    private MongoDB db;
    
    public MongoDBTest() {
        db = new MongoDB();
    }
    
    @Test
    public void testingGetNotebooks() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("Notebook1");
        testArray.add("Notebook2");
        testArray.add("Notebook3");
        testArray.add("local");
        assertEquals("Getting notebooks from the database", testArray, db.getNotebooks());
    }
    
    @Test
    public void testingGetPages() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("page3");
        testArray.add("page2");
        assertEquals("checking pages to return as the following: ", testArray, db.getPages("Notebook3"));
    } 

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
