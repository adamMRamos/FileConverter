package com.dev.rapture.fileparser.format.reader;

import com.dev.rapture.fileparser.format.FormatReader;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amram on 2/4/2018.
 *
 * This class writes data as XML Format.
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

        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try { this.xmlStreamReader = xmlInputFactory.createXMLStreamReader(br); }
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

    /**
     * Creates 4 behaviors to activate during parsing.
     * The first 3 are for START tags, Characters, and END tags.
     * The 4th is an empty behavior when the other 3 events are not present.
     */
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

    /**
     * Helper class to maintain state while parsing an xml file into individual objects.
     */
    private static class XmlIteratorState
    {
        Integer fieldLocation = null;
        String[] objectData;
        boolean fullObjectFound = false;

        private XmlIteratorState(String[] objectData) { this.objectData = objectData; }
    }

    /**
     * Helper functional interface. This acts as the framework behavior
     * for modifying the Iterator states while parsing.
     *
     */
    private interface XmlStateModifier
    {
        void modifyXmlIteratorState(XmlIteratorState state, XMLStreamReader streamReader);
    }
}
