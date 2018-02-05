package com.dev.rapture.fileparser.format.reader;

import com.dev.rapture.fileparser.format.FormatReader;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by amram on 2/4/2018.
 */
public class CsvFormatReader extends FormatReader
{
    private CSVReader reader;
    private boolean start = false;

    public CsvFormatReader(FileInputStream fis, String[] header)
    {
        super(header);

        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        this.reader = new CSVReader(br);
    }

    public String[] readLine()
    {
        return this.readNext();
    }

    private String[] readNext()
    {
        String[] line = null;

        try {
            line = this.reader.readNext();

            if (!start) {
                this.header = line;
                line = this.reader.readNext();
                this.start = true;
            }
        }
        catch (IOException e) { e.printStackTrace(); }

        return line;
    }

    public void close()
    {
        try { this.reader.close(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public String[] getHeader()
    {
        return this.header;
    }
}
