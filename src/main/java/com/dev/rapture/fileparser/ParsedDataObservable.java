package com.dev.rapture.fileparser;

import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Queue;

/**
 * Created by amram on 2/4/2018.
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
        this.incomingData.add(data);
        this.setChanged();
        this.notifyObservers();
    }

    public String[] pullData()
    {
        return this.incomingData.poll();
    }

    public boolean hasData()
    {
        return !this.incomingData.isEmpty();
    }
}
