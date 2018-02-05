package com.dev.rapture.fileparser.format.reader;

import com.dev.rapture.fileparser.format.FormatReader;
import com.opencsv.CSVReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by amram on 2/4/2018.
 */
public class CsvFormatReader implements FormatReader
{
    private CSVReader reader;
    private boolean began = false;

    public CsvFormatReader(FileInputStream fis)
    {
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        this.reader = new CSVReader(isr);
    }

    public String[] readLine()
    {
        return this.readNext();
    }

    private String[] readNext()
    {
        String[] line = null;

        try { line = this.reader.readNext(); }
        catch (IOException e) { e.printStackTrace(); }

        return line;
    }

    public void close()
    {
        try { this.reader.close(); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
