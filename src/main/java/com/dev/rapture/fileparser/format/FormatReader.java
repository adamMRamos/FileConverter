package com.dev.rapture.fileparser.format;

/**
 * Created by amram on 2/5/2018.
 */
public abstract class FormatReader
{
    protected String[] header;

    public FormatReader(String[] header) { this.header = header; }

    public abstract String[] readLine();
    public abstract void close();
}
