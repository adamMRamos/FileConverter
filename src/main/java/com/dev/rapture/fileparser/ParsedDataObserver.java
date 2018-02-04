package com.dev.rapture.fileparser;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by amram on 2/4/2018.
 */
public class ParsedDataObserver implements Observer
{
    @Override
    public void update(Observable observable, Object arg)
    {
        System.out.println(Arrays.toString(((ParsedDataObservable) observable).pullData()));
    }
}
