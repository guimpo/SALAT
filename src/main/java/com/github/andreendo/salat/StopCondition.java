package com.github.andreendo.salat;

/**
 *
 * @author andreendo
 */
public interface StopCondition {

    public boolean hasReached();

    public void update();
    
}
