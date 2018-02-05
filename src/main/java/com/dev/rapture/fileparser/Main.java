package com.dev.rapture.fileparser;

public class Main
{
    static String readFilepath = "data/sample_data.csv";
    static String writeFilepath = "data/test.xml";

    public static void main(String[] args)
    {
        System.out.println("read from file: "+Main.readFilepath);

        DataWriter writer = new DataWriter(Main.writeFilepath);

        DataReader reader = new DataReader(Main.readFilepath);
        reader.linkDataReceiver(writer);
        reader.readAllData();
    }
}
