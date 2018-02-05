package com.dev.rapture.fileparser;

import com.opencsv.CSVReader;
import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Created by amram on 2/4/2018.
 */
public class DataFormatReader
{
    private String filepath;
    private CSVReader reader;
    private boolean began = false;

    public DataFormatReader(String filepath)
    {
        this.filepath = filepath;
    }

    private void begin()
    {
        FileInputStream fis = null;
        try { fis = new FileInputStream(this.filepath); }
        catch (FileNotFoundException e) { e.printStackTrace(); }
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        this.reader = new CSVReader(isr);

        this.began = true;
    }

    public String[] readLine()
    {
        if (!began) this.begin();

        String[] line = this.readNext();

        if (line == null) this.end();

        return line;
    }

    private String[] readNext()
    {
        String[] line = null;

        try { line = this.reader.readNext(); }
        catch (IOException e) { e.printStackTrace(); }

        return line;
    }

    private void end()
    {
        try { this.reader.close(); }
        catch (IOException e) { e.printStackTrace(); }
    }
}
