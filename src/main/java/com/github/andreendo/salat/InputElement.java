package com.github.andreendo.salat;

import java.util.AbstractMap.SimpleEntry;
import org.openqa.selenium.WebElement;

/**
 *
 * @author paulo
 */
public class InputElement {
    
    private static final String TEXT = "text";
    private static final String NUMBER = "number";
    
    private WebElement element;
    
    public void setElement(WebElement element) {
        this.element = element;
    }

    public WebElement getElement() {
        return element;
    }

    @Override
    public String toString() {
        return "INPUT ELEMENT " + element;
    }

    public SimpleEntry<String, String> getMaxLengthValidation() {
        String type = element.getAttribute("type");
        type = type == null ? TEXT : type;
        
        String maxKey = "max";
        String maxValue = null;
        
        SimpleEntry<String, String> pair =
                new SimpleEntry<>(maxKey, maxValue);
        
        switch(type) {
            
            case TEXT:
                maxValue = element.getAttribute("maxlength");
                break;
                
            case NUMBER:
                maxValue = element.getAttribute("max");
                break;
                
            default:
                return null;
        }
        
        boolean notExistAttributeOrValue = maxValue == null ||
                maxValue.equals("maxlength") || maxValue.equals("max") ||
                maxValue.equals("");
                
        if(notExistAttributeOrValue)
            return null;
            
        pair.setValue(maxValue);
        
        return pair;
    }
}
