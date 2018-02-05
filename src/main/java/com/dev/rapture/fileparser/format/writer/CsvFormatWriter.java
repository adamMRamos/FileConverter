package com.dev.rapture.fileparser.format.writer;

import com.dev.rapture.fileparser.format.FormatWriter;
import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/**
 * Created by amram on 2/5/2018.
 *
 * This class writes data in CSV Format
 */
public class CsvFormatWriter extends FormatWriter
{
    private CSVWriter writer;
    private boolean start = false;

    public CsvFormatWriter(FileOutputStream fos, String[] header)
    {
        super(header);

        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter bw = new BufferedWriter(osw);
        this.writer = new CSVWriter(bw);
    }

    public void write(Object[] data)
    {
        if (!start) this.startToWriteCsv();

        try { this.writeToCsv(data); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private void startToWriteCsv()
    {
        try { this.writeToCsv(this.header); }
        catch (IOException e) { e.printStackTrace(); }

        this.start = true;
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
