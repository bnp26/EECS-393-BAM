/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jott.junit;

/**
 *
 * @author AlpGuvenir
 */
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
    
    public ArrayList<String> testGetNotebooks() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("ALP");
        testArray.add("BEN");
        testArray.add("Note1");
        testArray.add("Notebook1");
        testArray.add("Notebook2");
        testArray.add("local");
        return testArray;       
    }
    
    
    public ArrayList<String> testGetPages() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("trialPage");
        testArray.add("trialPage2");
        return testArray;
        } 
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
