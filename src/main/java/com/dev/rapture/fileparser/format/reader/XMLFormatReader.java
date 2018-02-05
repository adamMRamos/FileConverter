package com.dev.rapture.fileparser.format.reader;

import com.dev.rapture.fileparser.format.FormatReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amram on 2/4/2018.
 */
public class XMLFormatReader extends FormatReader
{
    private XMLStreamReader xmlStreamReader;
    private Map<String, Integer> fieldMap = new HashMap<>();
    private Map<Integer, XmlStateModifier> stateModifierMap = new HashMap<>();

    public XMLFormatReader(FileInputStream fis, String[] header)
    {
        super(header);

        this.initializeAttributeMap();
        this.initializeXmlStateModifiers();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try { this.xmlStreamReader = xmlInputFactory.createXMLStreamReader(fis); }
        catch (XMLStreamException e) { e.printStackTrace(); }
    }

    private void initializeAttributeMap()
    {
        for (int i = 0; i < this.header.length; i++) this.fieldMap.put(this.header[i], i);
        this.fieldMap.put("element", -1);
    }

    public String[] readLine()
    {
        String[] arrayOfData = null;

        try { arrayOfData = this.readXMLObjectIntoArray(); }
        catch (XMLStreamException e) { e.printStackTrace(); }

        return arrayOfData;
    }

    public void close()
    {
        try { this.xmlStreamReader.close(); }
        catch (XMLStreamException e) { e.printStackTrace(); }
    }

    private void initializeXmlStateModifiers()
    {
        this.stateModifierMap.put(XMLStreamConstants.START_ELEMENT, (state, streamReader) -> {
            state.fieldLocation = this.getElementFieldLocation(streamReader.getLocalName());
        });

        this.stateModifierMap.put(XMLStreamConstants.CHARACTERS, (state, streamReader) -> {
            if (this.isAnObjectField(state.fieldLocation)) {
                state.objectData[state.fieldLocation] = streamReader.getText();
                state.fieldLocation = null;
            }
        });

        this.stateModifierMap.put(XMLStreamConstants.END_ELEMENT, (state, streamReader) -> {
            if (this.isAnObjectClosingDiv(this.getElementFieldLocation(streamReader.getLocalName()))) {
                state.fullObjectFound = true;
            }
        });

        this.stateModifierMap.put(null, (state, streamReader) -> { });
    }

    private XmlStateModifier getXmlStateModifier(int event)
    {
        XmlStateModifier stateModifier = this.stateModifierMap.get(event);
        return stateModifier != null ? stateModifier : this.stateModifierMap.get(null);
    }

    private String[] readXMLObjectIntoArray() throws XMLStreamException
    {
        XmlIteratorState iteratorState = new XmlIteratorState(new String[this.header.length]);

        int event = this.xmlStreamReader.getEventType();

        while(this.xmlStreamReader.hasNext()) {
            XmlStateModifier stateModifier = this.getXmlStateModifier(event);
            stateModifier.modifyXmlIteratorState(iteratorState, this.xmlStreamReader);

            if (iteratorState.fullObjectFound) {
                this.xmlStreamReader.next();
                return iteratorState.objectData;
            }
            else event = this.xmlStreamReader.next();
        }
        return null;
    }

    private Integer getElementFieldLocation(String startElementName)
    {
        return this.fieldMap.get(startElementName);
    }

    private boolean isAnObjectField(Integer fieldPosition)
    {
        return fieldPosition != null && fieldPosition >= 0;
    }

    private boolean isAnObjectClosingDiv(Integer fieldPosition)
    {
        return fieldPosition != null && fieldPosition < 0;
    }

    private static class XmlIteratorState
    {
        Integer fieldLocation = null;
        String[] objectData;
        boolean fullObjectFound = false;

        private XmlIteratorState(String[] objectData) { this.objectData = objectData; }
    }

    private interface XmlStateModifier
    {
        void modifyXmlIteratorState(XmlIteratorState state, XMLStreamReader streamReader);
    }
}
