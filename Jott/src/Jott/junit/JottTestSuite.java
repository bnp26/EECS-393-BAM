/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jott.junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import Jott.MongoDB;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 *
 * @author AlpGuvenir
 */
public class JottTestSuite {

    public static void main(String[] args) {
        MongoDBTest test = new MongoDBTest();
        test.testingGetNotebooks();
        test.testingGetPages();
    }
}
