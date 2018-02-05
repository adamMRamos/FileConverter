package com.dev.rapture.fileparser.observer;

import com.dev.rapture.fileparser.format.FormatWriter;
import com.dev.rapture.fileparser.observer.ParsedDataObservable;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by amram on 2/4/2018.
 */
public class ParsedDataObserver implements Observer
{
    private FormatWriter writer;

    public ParsedDataObserver(FormatWriter writer)
    {
        this.writer = writer;
    }

    @Override
    public void update(Observable observable, Object arg)
    {
        String[] data = ((ParsedDataObservable) observable).pullData();
        if (arg != null && arg.toString().equalsIgnoreCase("close"))
            writer.close();
        else
            writer.write(data);
    }
}
