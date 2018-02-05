package com.dev.rapture.fileparser;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by amram on 2/4/2018.
 */
public class ParsedDataObserver implements Observer
{
    private DataFormatWriter writer;

    public ParsedDataObserver(DataFormatWriter writer)
    {
        this.writer = writer;
    }

    @Override
    public void update(Observable observable, Object arg)
    {
        String[] data = ((ParsedDataObservable) observable).pullData();
        if (arg != null && arg.toString().equalsIgnoreCase("end"))
            writer.end();
        else
            writer.write(data);
    }
}
