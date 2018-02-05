package com.dev.rapture.fileparser.format;

/**
 * Created by amram on 2/5/2018.
 */
public enum FormatType
{
    XML("xml"), CSV("csv");

    private String type;

    FormatType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return this.type;
    }
}
