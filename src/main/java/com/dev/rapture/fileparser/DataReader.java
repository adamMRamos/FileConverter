package com.dev.rapture.fileparser;

import com.dev.rapture.fileparser.format.reader.CsvFormatReader;
import com.dev.rapture.fileparser.format.FormatReader;
import com.dev.rapture.fileparser.format.reader.XMLFormatReader;

import java.io.FileInputStream;

/**
 * Created by amram on 2/4/2018.
 */
public class DataReader
{
    private FormatReader reader;
    private ParsedDataObservable observable;

    public DataReader(FileInputStream fis, String format)
    {
        if (format.equalsIgnoreCase("xml")) this.reader = new XMLFormatReader(fis);
        else this.reader = new CsvFormatReader(fis);

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
