package com.dev.rapture.fileparser;

import com.dev.rapture.fileparser.format.FormatReaderFactory;
import com.dev.rapture.fileparser.format.FormatType;
import com.dev.rapture.fileparser.format.FormatReader;
import com.dev.rapture.fileparser.observer.ParsedDataObservable;

import java.io.FileInputStream;

/**
 * Created by amram on 2/4/2018.
 */
public class DataReader
{
    private FormatReader reader;
    private ParsedDataObservable observable;

    public DataReader(FileInputStream fis, FormatType type)
    {
        this.reader = FormatReaderFactory.getInstance(fis, type);
        this.observable = new ParsedDataObservable();
    }

    public void readAllData()
    {
        String[] line;
        while((line = this.reader.readLine()) != null)
            this.observable.pushData(line);

        this.observable.end();
        this.reader.close();
    }

    public void linkDataReceiver(DataWriter receiver)
    {
        this.observable.addObserver(receiver.getLink());
    }
}
