package com.dev.rapture.fileparser.format.writer;

import com.dev.rapture.fileparser.format.FormatWriter;
import com.opencsv.CSVWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * Created by amram on 2/5/2018.
 */
public class CsvFormatWriter implements FormatWriter
{
    private CSVWriter writer;

    public CsvFormatWriter(FileOutputStream fos)
    {
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        this.writer = new CSVWriter(osw);
    }

    public void write(Object[] data)
    {
        try { this.writeToCsv(data); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private void writeToCsv(Object[] data) throws IOException
    {
        String[] arrayOfStrings = this.convertObjectArrayToArrayOfStrings(data);
        this.writer.writeNext(arrayOfStrings);
        this.writer.flush();
    }

    private String[] convertObjectArrayToArrayOfStrings(Object[] data)
    {
        String[] arrayOfStrings = new String[data.length];
        for (int i = 0; i < data.length; i++) arrayOfStrings[i] = data[i] != null ? data[i].toString() : null;
        return arrayOfStrings;
    }

    public void close()
    {
        try {
            this.writer.flush();
            this.writer.close();
        }
        catch (IOException e) { e.printStackTrace(); }
    }
}
