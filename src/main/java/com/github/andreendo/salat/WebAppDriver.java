package com.github.andreendo.salat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author andreendo
 */
public class WebAppDriver implements Driver {

    String startingPage;
    String urlToCheck;
    WebDriver webDriver;

    public WebAppDriver(WebDriver webDriver, String startingPage, String urlToCheck) {
        this.webDriver = webDriver;
        this.startingPage = startingPage;
        this.urlToCheck = urlToCheck;
    }

    @Override
    public void restart() {
        webDriver.get(startingPage);
    }

    @Override
    public List<FireableEvent> getCurrentFireableEvents() {
        JSWaiter.setDriver(webDriver);
        JSWaiter.waitJQueryAngular();

        ArrayList<FireableEvent> fireableEvents = new ArrayList<>();

        //retrieve links
        List<WebElement> allLinks = webDriver.findElements(By.tagName("a"));

        //retrieve buttons
        List<WebElement> allButtons1 = webDriver.findElements(By.tagName("button"));
        List<WebElement> allButtons2 = webDriver.findElements(By.xpath("//input[@type='submit']"));

        allLinks.addAll(allButtons1);
        allLinks.addAll(allButtons2);

        for (WebElement e : allLinks) {
            if (isVisibleExperimental(e)) {
                FireableEvent event = new FireableEvent();
                event.setElement(e);
                event.setContent(e.getText());
                fireableEvents.add(event);
            }
        }

        return fireableEvents;
    }

    private boolean isVisibleExperimental(WebElement e) {
        if (!isVisible(e)) {
            return false;
        }

        Dimension d = e.getSize();
        if (d.getHeight() <= 0 || d.getWidth() <= 0) {
            return false;
        }

        String eStyle = e.getAttribute("style");
        if (eStyle == null) {
            eStyle = "";
        }

        if (eStyle.contains("display: none") || eStyle.contains("visibility: hidden")) {
            return false;
        }

        return true;
    }

    private boolean isVisible(WebElement e) {
        try {
            if (e.isDisplayed() && e.isEnabled()) {
                WebDriverWait wait = new WebDriverWait(webDriver, 1);
                wait.until(ExpectedConditions.elementToBeClickable(e));
                return true;
            }
        } catch (Exception exception) {
        }
        return false;
    }

    @Override
    public boolean isInInitialState() {
        return webDriver.getCurrentUrl().equals(startingPage);
    }

    @Override
    public boolean execute(FireableEvent event) {
        try {
            event.getElement().click();
            return true;
        } catch(WebDriverException e) {
            JavascriptExecutor js = (JavascriptExecutor) webDriver; 
            js.executeScript("arguments[0].click();", event.getElement());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isOut() {
        String currentWindow = webDriver.getWindowHandle();

        handleAlert();

        if (webDriver.getWindowHandles().size() > 1) {//two or more windows (popups) opened
            Set<String> windows = webDriver.getWindowHandles();
            for (String w : windows) {
                if (!w.equalsIgnoreCase(currentWindow)) {
                    webDriver.switchTo().window(w);
                    webDriver.close();
                }
            }
            webDriver.switchTo().window(currentWindow);
        }

        return !webDriver.getCurrentUrl().contains(urlToCheck);
    }

    @Override
    public boolean isFaulty() {
        //currently it does not detect faulty states
        return false;
    }

    private void handleAlert() {
        try {
            Alert alert = webDriver.switchTo().alert();
            alert.accept();
        } catch (NoAlertPresentException ex) {
        }
    }

    @Override
    public List<InputElement> getCurrentInputs() {
        List<WebElement> allInputs = webDriver.findElements(By.tagName("input"));
        List<InputElement> inputs = new ArrayList<>();
        for(WebElement e : allInputs) {
            InputElement input = new InputElement();
            input.setElement(e);
            inputs.add(input);
        }
        return inputs;
    }
}
