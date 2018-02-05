package com.dev.rapture.fileparser.format;

/**
 * Created by amram on 2/5/2018.
 */
public abstract class FormatWriter
{
    protected String[] header;

    public FormatWriter(String[] header) { this.header = header; }

    public abstract void write(Object[] data);
    public abstract void close();
}
