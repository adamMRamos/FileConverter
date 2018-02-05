package com.dev.rapture;

import com.dev.rapture.fileparser.DataReader;
import com.dev.rapture.fileparser.DataWriter;
import com.dev.rapture.fileparser.format.FormatType;
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

        Demo.runWriterReader(Demo.writeFilepath, Demo.readFilepath, FormatType.XML, FormatType.CSV);

        try {
            XMLFormatReader xmlReader = new XMLFormatReader(new FileInputStream(Demo.writeFilepath), Demo.header);
            String[] line;
            while((line = xmlReader.readLine()) != null) System.out.println(Arrays.toString(line));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Demo.runWriterReader(Demo.writeFilepath2, Demo.writeFilepath, FormatType.CSV, FormatType.XML);
    }

    private static void runWriterReader(String writeFilepath, String readFilepath, FormatType writeFormat, FormatType readFormat)
    {
        try {
            DataWriter writer = new DataWriter(new FileOutputStream(writeFilepath), Demo.header, writeFormat);
            DataReader reader = new DataReader(new FileInputStream(readFilepath), Demo.header, readFormat);

            reader.linkDataReceiver(writer);
            reader.readAllData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
