package com.dev.rapture.fileparser;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Created by amram on 2/4/2018.
 */
public class DataFormatWriter
{
    private XMLStreamWriter writer;
    private boolean began = false;

    public DataFormatWriter(String writeFilepath) throws FileNotFoundException, XMLStreamException
    {
        FileOutputStream fos = new FileOutputStream(writeFilepath);
        XMLOutputFactory xmlOutFact = XMLOutputFactory.newInstance();
        this.writer = xmlOutFact.createXMLStreamWriter(fos);
    }

    public void write(Object[] data)
    {
        if (!began)
            try { this.begin(); }
            catch (XMLStreamException e) { e.printStackTrace(); }

        this.writeToXML(data);
    }

    public void end()
    {
        try {
            if (!began) this.begin();
            this.endXMLDoc();
        }
        catch (XMLStreamException e) { e.printStackTrace(); }
    }

    private void begin() throws XMLStreamException
    {
        this.writer.writeStartDocument();
        this.writer.writeStartElement("body");
        this.began = true;
    }

    private void writeToXML(Object[] data)
    {
        try {
            this.writer.writeStartElement("student");
            this.writeObjectFields(data);
            this.writer.writeEndElement();
            this.writer.flush();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }

    private void writeObjectFields(Object[] data)
    {
        final int[] counter = new int[]{0};
        Arrays.stream(data).forEach(element -> {
            try {
                this.writer.writeStartElement("field"+counter[0]++);
                this.writer.writeCharacters(element.toString());
                this.writer.writeEndElement();
                this.writer.flush();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        });
    }

    private void endXMLDoc() throws XMLStreamException
    {
        this.writer.writeEndElement();
        this.writer.flush();
        this.writer.close();
    }
}
