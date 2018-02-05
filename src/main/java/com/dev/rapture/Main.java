package com.dev.rapture;

import com.dev.rapture.fileparser.DataReader;
import com.dev.rapture.fileparser.DataWriter;
import com.dev.rapture.fileparser.format.FormatType;
import com.dev.rapture.fileparser.format.reader.XMLFormatReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

public class Main
{
    static String readFilepath = "data/sample_data.csv";
    static String writeFilepath = "data/test.xml";
    static String writeFilepath2 = "data/test.csv";

    public static void main(String[] args)
    {
        System.out.println("read from file: "+Main.readFilepath);

        Main.runWriterReader(Main.writeFilepath, Main.readFilepath, FormatType.XML, FormatType.CSV);

        try {
            XMLFormatReader xmlReader = new XMLFormatReader(new FileInputStream(Main.writeFilepath));
            String[] line;
            while((line = xmlReader.readLine()) != null) System.out.println(Arrays.toString(line));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Main.runWriterReader(Main.writeFilepath2, Main.writeFilepath, FormatType.CSV, FormatType.XML);
    }

    private static void runWriterReader(String writeFilepath, String readFilepath, FormatType writeFormat, FormatType readFormat)
    {
        try {
            DataWriter writer = new DataWriter(new FileOutputStream(writeFilepath), writeFormat);
            DataReader reader = new DataReader(new FileInputStream(readFilepath), readFormat);

            reader.linkDataReceiver(writer);
            reader.readAllData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
