package com.dev.rapture.fileparser;

import com.dev.rapture.fileparser.format.writer.CsvFormatWriter;
import com.dev.rapture.fileparser.format.FormatWriter;
import com.dev.rapture.fileparser.format.writer.XMLFormatWriter;

import java.io.FileOutputStream;

/**
 * Created by amram on 2/4/2018.
 */
public class DataWriter
{
    private ParsedDataObserver observer;

    public DataWriter(FileOutputStream fos, String format)
    {
        FormatWriter writer;
        if (format.equalsIgnoreCase("xml")) writer = new XMLFormatWriter(fos);
        else writer = new CsvFormatWriter(fos);

        this.observer = new ParsedDataObserver(writer);
    }

    public ParsedDataObserver getLink()
    {
        return this.observer;
    }
}
