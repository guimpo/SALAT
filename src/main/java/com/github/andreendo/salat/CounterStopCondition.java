package com.github.andreendo.salat;

/**
 *
 * @author andreendo
 */
public class CounterStopCondition implements StopCondition {
    int maxNumberOfEvents;
    int currentEvents;
    
    public CounterStopCondition(int maxNumberOfEvents) {
        this.maxNumberOfEvents = maxNumberOfEvents;
        this.currentEvents = 0;
    }

    @Override
    public boolean hasReached() {
        return currentEvents > maxNumberOfEvents;
    }

    @Override
    public void update() {
        currentEvents++;
    }
}
