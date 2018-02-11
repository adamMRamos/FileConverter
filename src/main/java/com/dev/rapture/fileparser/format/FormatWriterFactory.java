package com.dev.rapture.fileparser.format;

import com.dev.rapture.fileparser.format.exception.UnknownFileFormatException;
import com.dev.rapture.fileparser.format.writer.CsvFormatWriter;
import com.dev.rapture.fileparser.format.writer.XMLFormatWriter;

import java.io.FileOutputStream;

/**
 * Created by amram on 2/5/2018.
 */
public class FormatWriterFactory
{
    public static FormatWriter getInstance(FileOutputStream fos, String[] header, FormatType type) throws UnknownFileFormatException
    {
        switch(type) {
            case XML: return new XMLFormatWriter(fos, header);
            case CSV: return new CsvFormatWriter(fos, header);
            default: throw new UnknownFileFormatException();
        }
    }
}
