package edu.dselent.control;

import edu.dselent.event.AttackEvent;
import edu.dselent.event.AttackEventShootTheMoon;
import edu.dselent.event.FightStartEvent;
import edu.dselent.event.RoundStartEvent;
import edu.dselent.observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class EventBus
{
    private List<Observer> observerList;

    public EventBus()
    {
        observerList = new ArrayList<>();
    }

    public void register(Observer observer)
    {
        observerList.add(observer);
    }

    public void unregister(Observer observer)
    {
        observerList.remove(observer);
    }

    // TODO Take object or BaseEvent?
    // If taking BaseEvent can cast by checking event type
    public void fireEvent(Object event)
    {
        observerList.forEach(observer -> observer.update(copyEvent(event)));
    }

    private Object copyEvent(Object event)
    {
        Object eventCopy = null;

        if(event instanceof AttackEventShootTheMoon)
        {
            AttackEventShootTheMoon attackEventShootTheMoon = new AttackEventShootTheMoon((AttackEventShootTheMoon)event);
            eventCopy = attackEventShootTheMoon;
        }
        else if(event instanceof AttackEvent)
        {
            AttackEvent attackEvent = new AttackEvent((AttackEvent)event);
            eventCopy = attackEvent;
        }
        else if(event instanceof FightStartEvent)
        {
            FightStartEvent fightStartEvent = new FightStartEvent((FightStartEvent)event);
            eventCopy = fightStartEvent;
        }
        else if(event instanceof RoundStartEvent)
        {
            RoundStartEvent roundStartEvent = new RoundStartEvent((RoundStartEvent)event);
            eventCopy = roundStartEvent;
        }
        else
        {
            // TODO throw an exception
        }

        return eventCopy;
    }

}
