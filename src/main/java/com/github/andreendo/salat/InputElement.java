package com.github.andreendo.salat;

import org.openqa.selenium.WebElement;

/**
 *
 * @author paulo
 */
public class InputElement {
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
        return "INPUT ELEMENT " + element + " " + content;
    }

    void setContent(String content) {
        this.content = content;
    }
}
