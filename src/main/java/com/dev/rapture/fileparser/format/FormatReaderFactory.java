package com.dev.rapture.fileparser.format;

import com.dev.rapture.fileparser.format.reader.CsvFormatReader;
import com.dev.rapture.fileparser.format.reader.XMLFormatReader;

import java.io.FileInputStream;

/**
 * Created by amram on 2/5/2018.
 */
public class FormatReaderFactory
{
    public static FormatReader getInstance(FileInputStream fis, FormatType type)
    {
        switch(type) {
            case CSV: return new CsvFormatReader(fis);
            case XML: return new XMLFormatReader(fis);
            default: return null;
        }
    }
}
