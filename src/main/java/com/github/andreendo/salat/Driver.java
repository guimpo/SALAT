package com.github.andreendo.salat;

import java.util.List;

/**
 *
 * @author andreendo
 */
public interface Driver {

    public void restart();

    public List<FireableEvent> getCurrentFireableEvents();

    public boolean isInInitialState();

    public boolean execute(FireableEvent event);

    public boolean isOut();

    public boolean isFaulty();
    
    public List<InputElement> getCurrentInputs();
    
}
