package com.dev.rapture.fileparser;

import com.opencsv.CSVReader;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main
{
    static String filepath = "data/sample_data.csv";

    public static void main(String[] args)
    {
        System.out.println("hello world: "+Main.filepath);
        try {
            Main.parseCsv(Main.filepath);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            Main.writeToXML();
//        } catch (XMLStreamException | IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void parseCsv(String filepath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filepath);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             CSVReader reader = new CSVReader(isr)) {

            ParsedDataObservable observable = new ParsedDataObservable();
            ParsedDataObserver observer = new ParsedDataObserver();
            observable.addObserver(observer);

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) observable.pushData(nextLine);
        }
    }

    public static void writeToXML() throws XMLStreamException, IOException
    {
        try (FileOutputStream fos = new FileOutputStream("test.xml")){
            XMLOutputFactory xmlOutFact = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = xmlOutFact.createXMLStreamWriter(fos);
            writer.writeStartDocument();
            writer.writeStartElement("test");
            // write stuff
            writer.writeEndElement();
        }
    }
}
