package com.dev.rapture.fileparser;

import com.dev.rapture.fileparser.format.FormatType;
import com.dev.rapture.fileparser.format.FormatWriterFactory;
import com.dev.rapture.fileparser.format.FormatWriter;
import com.dev.rapture.fileparser.observer.ParsedDataObserver;

import java.io.FileOutputStream;

/**
 * Created by amram on 2/4/2018.
 */
public class DataWriter
{
    private ParsedDataObserver observer;

    public DataWriter(FileOutputStream fos, String[] header, FormatType type)
    {
        FormatWriter writer = FormatWriterFactory.getInstance(fos, header, type);
        this.observer = new ParsedDataObserver(writer);
    }

    public ParsedDataObserver getLink()
    {
        return this.observer;
    }
}
