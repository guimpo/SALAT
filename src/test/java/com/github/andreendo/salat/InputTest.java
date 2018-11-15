package com.github.andreendo.salat;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
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
import static org.junit.Assert.assertEquals;

/**
 *
 * @author paulo
 */
@RunWith(Parameterized.class)
public class InputTest {

    private WebDriver webDriver;
    private int inputsWithoutValidation = 0;
    private int inputsWithValidation = 0;

    @Parameter
    public String page;

    @Parameter(1)
    public String outCond;

    @Parameter(2)
    public int numberOfEvents;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {"file:///" + System.getProperty("user.dir") + "/src/test/resources/pages/input.html", ".html", 3}
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
    public void testMaxLength() {
        Driver driver = new WebAppDriver(webDriver, page, outCond);
        driver.restart();
        List<InputElement> inputs = driver.getCurrentInputs();
        
        inputsWithoutValidation = 0;
        inputsWithoutValidation = 0;
        
        inputs.forEach(i -> {
            SimpleEntry<String, String> pair = i.getMaxLengthValidation();
            if(pair == null) {
                inputsWithoutValidation++;
            } else {
                inputsWithValidation++;
                System.out.println("Key: " + pair.getKey());
                System.out.println("Value: " + pair.getValue());
            }
        });
        
        System.out.println("Inputs: " + inputs.size());
        assertEquals(4, inputs.size());
        System.out.println("Inputs with validation: " + inputsWithValidation);
        assertEquals(2, inputsWithValidation);
        System.out.println("Inputs without validation: " + inputsWithoutValidation);
        assertEquals(2, inputsWithoutValidation);
    }
}