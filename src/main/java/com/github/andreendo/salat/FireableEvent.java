package com.github.andreendo.salat;

import org.openqa.selenium.WebElement;

/**
 *
 * @author andreendo
 */
public class FireableEvent {
    private WebElement element;
    private String content;
    
    public void setElement(WebElement element) {
        this.element = element;
    }

    public WebElement getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "EVENT CLICK " + element + " " + content;
    }

    void setContent(String content) {
        this.content = content;
    }
}
