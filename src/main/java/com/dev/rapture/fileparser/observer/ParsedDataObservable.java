package com.dev.rapture.fileparser.observer;

import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Observer;
import java.util.Queue;

/**
 * Created by amram on 2/4/2018.
 *
 * This class pushes data onto a Queue and notify's any registered observer.
 * This class does not allow for any more than one observer at a time.
 * If another class attempts to register with it the new observer will replace the old.
 */
public class ParsedDataObservable extends Observable
{
    private Queue<String[]> incomingData;

    public ParsedDataObservable()
    {
        this.incomingData = new ArrayDeque<>();
    }

    public void pushData(String[] data)
    {
        this.pushDataToBuffer(data);
    }

    private void pushDataToBuffer(String[] data)
    {
        this.incomingData.add(data);
        this.setChanged();
        this.notifyObservers();
    }

    public void end()
    {
        this.setChanged();
        this.notifyObservers("close");
        this.incomingData.clear();
    }

    public String[] pullData()
    {
        return this.incomingData.poll();
    }

    public boolean hasData()
    {
        return !this.incomingData.isEmpty();
    }

    @Override
    public void addObserver(Observer o)
    {
        this.deleteObservers();
        super.addObserver(o);
    }
}
