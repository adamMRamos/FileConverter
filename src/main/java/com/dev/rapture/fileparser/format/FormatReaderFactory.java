package com.dev.rapture.fileparser.format;

import com.dev.rapture.fileparser.format.reader.CsvFormatReader;
import com.dev.rapture.fileparser.format.reader.XMLFormatReader;

import java.io.FileInputStream;

/**
 * Created by amram on 2/5/2018.
 */
public class FormatReaderFactory
{
    public static FormatReader getInstance(FileInputStream fis, String[] header, FormatType type)
    {
        switch(type) {
            case CSV: return new CsvFormatReader(fis, header);
            case XML: return new XMLFormatReader(fis, header);
            default: return null;
        }
    }
}
