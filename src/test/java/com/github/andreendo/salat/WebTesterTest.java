package com.github.andreendo.salat;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author andreendo
 */
public class WebTesterTest {
    
    private WebDriver webDriver;
    
    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "/home/utfpr/install/selenium/chromedriver");
    }
    
    @Before
    public void before() {
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);   
        webDriver.manage().window().maximize();
    }
    
    @After
    public void after() {
        webDriver.close();
    }    
    
    @Test
    public void test01() {
        Driver driver = new WebAppDriver(webDriver, "http://portal.utfpr.edu.br/", "utfpr.edu.br");       
        //Driver driver = new WebAppDriver(webDriver, "https://www.wikipedia.org/", "wikipedia.org");   
        //Driver driver = new WebAppDriver(webDriver, "http://localhost:8080", "localhost:8080");       
        //Driver driver = new WebAppDriver(webDriver, "http://demo.redmine.org", "demo.redmine.org");       
        
        StopCondition stopCondition = new CounterStopCondition(200);
        Tester tester = new Tester(driver, stopCondition, new Random());
        tester.executeRandomTest();
    }
}
