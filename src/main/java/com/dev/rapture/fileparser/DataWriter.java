package com.dev.rapture.fileparser;

import com.dev.rapture.fileparser.format.FormatType;
import com.dev.rapture.fileparser.format.FormatWriterFactory;
import com.dev.rapture.fileparser.format.FormatWriter;

import java.io.FileOutputStream;

/**
 * Created by amram on 2/4/2018.
 */
public class DataWriter
{
    private ParsedDataObserver observer;

    public DataWriter(FileOutputStream fos, FormatType type)
    {
        FormatWriter writer = FormatWriterFactory.getInstance(fos, type);
        this.observer = new ParsedDataObserver(writer);
    }

    public ParsedDataObserver getLink()
    {
        return this.observer;
    }
}
