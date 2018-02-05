package com.dev.rapture.fileparser;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;

/**
 * Created by amram on 2/4/2018.
 */
public class DataWriter
{
    private ParsedDataObserver observer;

    public DataWriter(String writeFilepath)
    {
        try {
            DataFormatWriter writer = new DataFormatWriter(writeFilepath);
            this.observer = new ParsedDataObserver(writer);
        }
        catch (FileNotFoundException | XMLStreamException e) { e.printStackTrace(); }
    }

    public ParsedDataObserver getLink()
    {
        return this.observer;
    }
}
