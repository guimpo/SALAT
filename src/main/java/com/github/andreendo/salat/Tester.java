package com.github.andreendo.salat;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 *
 * @author andreendo
 */
class Tester {
    private final static Logger LOGGER = Logger.getLogger(Tester.class.getName());
    private final Driver driver;
    private final StopCondition stopCondition;
    private final Random random;

    public Tester(Driver driver, StopCondition stopCondition, Random random) {
        this.driver = driver;
        this.stopCondition = stopCondition;
        this.random = random;
    }
    
    public void executeRandomTest() {
        driver.restart();
        while(! stopCondition.hasReached()) {
            LOGGER.info("Finding fireable events");
            List<FireableEvent> events = driver.getCurrentFireableEvents();
           
            if(events.isEmpty()) { 
                if(driver.isInInitialState()) {
                    LOGGER.info("Initial state has no fireable events");
                    break;
                }
                else {
                    LOGGER.info("Reach a state with no fireable events. Restarting...");
                    driver.restart();
                }
            }
            else {  //it has events to be fired
                int randIndex = random.nextInt(events.size());
                if(driver.execute( events.get(randIndex) )) {
                    LOGGER.info("Select event " + randIndex + " out of " + events.size());
                    stopCondition.update();
                    LOGGER.info("Execute event " + events.get(randIndex).toString());
                    if(driver.isOut()) {
                        LOGGER.info("Reach a state outside of app under test. Restarting...");
                        driver.restart();                
                    }
                    else if(driver.isFaulty()) {
                        LOGGER.info("Find a bug in the app. Please check.");
                        break;
                    }
                }
            }
        }
    }
}
