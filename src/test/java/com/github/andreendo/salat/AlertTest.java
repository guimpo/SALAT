package com.github.andreendo.salat;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author andreendo
 */
@RunWith(Parameterized.class)
public class AlertTest {

    private WebDriver webDriver;

    @Parameter
    public String page;

    @Parameter(1)
    public String outCond;

    @Parameter(2)
    public int numberOfEvents;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"file:///" + System.getProperty("user.dir") + "/src/test/resources/pages/alert1.html", ".html", 3},
            {"file:///" + System.getProperty("user.dir") + "/src/test/resources/pages/prompt.html", ".html", 3},
            {"file:///" + System.getProperty("user.dir") + "/src/test/resources/pages/confirm.html", ".html", 3},
            {"file:///" + System.getProperty("user.dir") + "/src/test/resources/pages/popup.html", ".html", 3}
        });
    }

    @BeforeClass
    public static void beforeClass() {
        WebDriverManager.chromedriver().setup();
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
    public void test() {
        Driver driver = new WebAppDriver(webDriver, page, outCond);
        StopCondition stopCondition = new CounterStopCondition(numberOfEvents);
        Tester tester = new Tester(driver, stopCondition, new Random());
        tester.executeRandomTest();
    }
}