package com.dev.rapture.fileparser;

/**
 * Created by amram on 2/4/2018.
 */
public class DataReader
{
    private DataFormatReader reader;
    private ParsedDataObservable observable;

    public DataReader(String readFilepath)
    {
        this.reader = new DataFormatReader(readFilepath);
        this.observable = new ParsedDataObservable();
    }

    public void readAllData()
    {
        String[] line;
        while((line = this.reader.readLine()) != null)
            observable.pushData(line);
        observable.end();
    }

    public void linkDataReceiver(DataWriter receiver)
    {
        this.observable.addObserver(receiver.getLink());
    }
}
