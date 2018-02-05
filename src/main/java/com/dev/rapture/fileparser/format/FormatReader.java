package com.dev.rapture.fileparser.format;

/**
 * Created by amram on 2/5/2018.
 */
public interface FormatReader
{
    String[] readLine();
    void close();
}
