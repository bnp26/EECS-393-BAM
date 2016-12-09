package Jott;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
**/


public class MongoDBTest {
    
    public MongoDBTest() {}
    
    public ArrayList<String> testGetNotebooks() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("AAAA");
        testArray.add("ALP");
        testArray.add("BEN");
        testArray.add("Note1");
        testArray.add("Notebook2");
        return testArray;       
    }
    
    public ArrayList<String> testDeleteNotebooks() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("AAAA");
        testArray.add("ALP");
        testArray.add("BEN");
        testArray.add("Note1");
        testArray.add("Notebook2");
        return testArray;
    } 
    
    public ArrayList<String> testCreatePage() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("testPage");
        testArray.add("trialPage");
        testArray.add("trialPage0");
        testArray.add("trialPage2");
        testArray.add("trialPage3");
        testArray.add("trialPage4");
        return testArray;
    } 
    
    public ArrayList<String> testRenamePage() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("testPage");
        return testArray;
    } 
    
    public ArrayList<String> testGetPages() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("testPage");
        testArray.add("trialPage");
        testArray.add("trialPage0");
        testArray.add("trialPage2");
        testArray.add("trialPage3");
        testArray.add("trialPage4");
        return testArray;
    } 
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
