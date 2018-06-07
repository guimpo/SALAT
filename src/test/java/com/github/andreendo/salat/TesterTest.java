package com.github.andreendo.salat;

import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author andreendo
 */
public class TesterTest {
    
    @Test
    public void testNoFireableEvents() {
        Driver driverMock = mock(Driver.class);
        when(driverMock.getCurrentFireableEvents())
                .thenReturn(new ArrayList<>());

        when(driverMock.isInInitialState())
                .thenReturn(Boolean.FALSE, Boolean.TRUE);

        
        StopCondition stopCondMock = mock(StopCondition.class);
        when(stopCondMock.hasReached())
                .thenReturn(Boolean.FALSE);
        
        Random randomMock = mock(Random.class);
        
        Tester tester = new Tester(driverMock, stopCondMock, randomMock);
        tester.executeRandomTest();
    }
    
    @Test
    public void testWithFireableEvents() {
        Driver driverMock = mock(Driver.class);
        ArrayList<FireableEvent> events = new ArrayList<>();
        events.add(new FireableEvent());
        events.add(new FireableEvent());
        events.add(new FireableEvent());
        when(driverMock.getCurrentFireableEvents())
                .thenReturn( events );

        when(driverMock.isInInitialState())
                .thenReturn(Boolean.FALSE, Boolean.TRUE);
        
        when(driverMock.isOut())
                .thenReturn(Boolean.FALSE, Boolean.TRUE, Boolean.FALSE);
        
        when(driverMock.isFaulty())
                .thenReturn(Boolean.FALSE, Boolean.FALSE, Boolean.TRUE);
        
        StopCondition stopCondMock = mock(StopCondition.class);
        when(stopCondMock.hasReached())
                .thenReturn(Boolean.FALSE);
        
        Random randomMock = mock(Random.class);
        
        Tester tester = new Tester(driverMock, stopCondMock, randomMock);
        tester.executeRandomTest();
    }    
}
