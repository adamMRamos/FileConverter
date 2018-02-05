package com.dev.rapture.fileparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main
{
    static String readFilepath = "data/sample_data.csv";
    static String writeFilepath = "data/test.xml";

    public static void main(String[] args)
    {
        System.out.println("read from file: "+Main.readFilepath);

        DataWriter writer = new DataWriter(Main.writeFilepath);

        DataReader reader;
        try {
            reader = new DataReader(new FileInputStream(Main.readFilepath));
            reader.linkDataReceiver(writer);
            reader.readAllData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            XMLFormatReader xmlReader = new XMLFormatReader(new FileInputStream(Main.writeFilepath));
            String[] line;
            while((line = xmlReader.readLine()) != null) System.out.println(Arrays.toString(line));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
