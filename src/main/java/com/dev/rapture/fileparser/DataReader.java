package com.dev.rapture.fileparser;

import java.io.FileInputStream;

/**
 * Created by amram on 2/4/2018.
 */
public class DataReader
{
    private CsvFormatReader reader;
    private ParsedDataObservable observable;

    public DataReader(FileInputStream fis)
    {
        this.reader = new CsvFormatReader(fis);
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
