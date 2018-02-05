package com.dev.rapture.fileparser.format.writer;

import com.dev.rapture.fileparser.format.FormatWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.util.Arrays;

/**
 * Created by amram on 2/4/2018.
 */
public class XMLFormatWriter implements FormatWriter
{
    private XMLStreamWriter writer;
    private boolean began = false;
    private boolean end = false;

    public XMLFormatWriter(FileOutputStream fos)
    {
        XMLOutputFactory xmlOutFact = XMLOutputFactory.newInstance();
        try { this.writer = xmlOutFact.createXMLStreamWriter(fos); }
        catch (XMLStreamException e) { e.printStackTrace(); }
    }

    @Override
    public void write(Object[] data)
    {
        if (!began)
            try { this.begin(); }
            catch (XMLStreamException e) { e.printStackTrace(); }

        this.writeToXML(data);
    }

    @Override
    public void close()
    {
        try {
            if (!began) this.begin();
            if (!end) this.endXMLDoc();
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
            String elementAsStringData = element.toString().trim();
            String fieldName = "field"+counter[0]++;
            try {
                if (!elementAsStringData.isEmpty()) this.writeElement(fieldName, elementAsStringData);
            }
            catch (XMLStreamException e) { e.printStackTrace(); }
        });
    }

    private void writeElement(String fieldName, String element) throws XMLStreamException
    {
        this.writer.writeStartElement(fieldName);
        this.writer.writeCharacters(element);
        this.writer.writeEndElement();
        this.writer.flush();
    }

    private void endXMLDoc() throws XMLStreamException
    {
        this.writer.writeEndElement();
        this.writer.flush();
        this.writer.close();
        this.end = true;
    }
}
