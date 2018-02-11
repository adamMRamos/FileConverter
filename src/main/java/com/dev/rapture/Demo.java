package com.dev.rapture;

import com.dev.rapture.fileparser.format.*;
import com.dev.rapture.fileparser.format.exception.UnknownFileFormatException;
import com.dev.rapture.fileparser.format.reader.CsvFormatReader;
import com.dev.rapture.fileparser.format.reader.XMLFormatReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

public class Demo
{
    private static String readFilepath = "data/sample_data.csv";
    private static String writeFilepath = "data/test.xml";
    private static String writeFilepath2 = "data/test.csv";
    private static String headerFilepath = "data/header";
    private static String[] header;

    public static void test()
    {
        try {
            FormatReader reader = FormatReaderFactory.getInstance(new FileInputStream(Demo.readFilepath), Demo.header, FormatType.CSV);
            FormatWriter writer = FormatWriterFactory.getInstance(new FileOutputStream(Demo.writeFilepath), Demo.header, FormatType.XML);

            String[] line;
            while ((line = reader.readLine()) != null) writer.write(line);
            reader.close();
            writer.close();

        } catch (FileNotFoundException | UnknownFileFormatException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        try {
            CsvFormatReader reader = new CsvFormatReader(new FileInputStream(Demo.headerFilepath), null);
            reader.readLine();
            Demo.header = reader.getHeader();

            for (int i = 0; i < Demo.header.length; i++) Demo.header[i] = Demo.header[i].replaceAll(" ", "_");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Demo.test();

        try {
            XMLFormatReader xmlReader = new XMLFormatReader(new FileInputStream(Demo.writeFilepath), Demo.header);
            String[] line;
            while((line = xmlReader.readLine()) != null) System.out.println(Arrays.toString(line));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
