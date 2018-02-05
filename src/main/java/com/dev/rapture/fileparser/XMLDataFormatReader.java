package com.dev.rapture.fileparser;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amram on 2/4/2018.
 */
public class XMLDataFormatReader
{
    private final String filepath;
    private XMLStreamReader xmlStreamReader;
    private Map<String, Integer> fieldMap = new HashMap<>();
    private Map<Integer, XmlStateModifier> stateModifierMap = new HashMap<>();

    public XMLDataFormatReader(String filepath)
    {
        this.filepath = filepath;
        this.initializeAttributeMap();
        this.initializeXmlStateModifiers();

        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            this.xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(this.filepath));
        } catch (XMLStreamException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initializeAttributeMap()
    {
        for (int i = 0; i < 12; i++) this.fieldMap.put("field"+i, i);
        this.fieldMap.put("student", -1);
    }

    public String[] readLine()
    {
        System.out.println("readline");

        String[] arrayOfData = null;

        try { arrayOfData = this.readXMLObjectIntoArrayV2(); }
        catch (XMLStreamException e) { e.printStackTrace(); }

        return arrayOfData;
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

    private String[] readXMLObjectIntoArrayV2() throws XMLStreamException
    {
        XmlIteratorState iteratorState = new XmlIteratorState(new String[12]);

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

    private String[] readXMLObjectIntoArray() throws XMLStreamException
    {
        String[] objectData = new String[12];
        int event = this.xmlStreamReader.getEventType();

        Integer fieldLocation = null;
        while(this.xmlStreamReader.hasNext()) {
            switch(event) {
                case XMLStreamConstants.START_ELEMENT:
                    System.out.println("event is START element");
                    System.out.println(this.xmlStreamReader.getLocalName());
                    fieldLocation = this.getElementFieldLocation(this.xmlStreamReader.getLocalName());
                    break;
                case XMLStreamConstants.CHARACTERS:
                    System.out.println("event is characters");
                    if (this.isAnObjectField(fieldLocation)) {
                        objectData[fieldLocation] = this.xmlStreamReader.getText();
                        fieldLocation = null;
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    System.out.println("event is END element");
                    System.out.println(this.xmlStreamReader.getLocalName());
                    if (this.isAnObjectClosingDiv(this.getElementFieldLocation(this.xmlStreamReader.getLocalName()))) {
                        this.xmlStreamReader.next();
                        return objectData;
                    }
            }
            event = this.xmlStreamReader.next();
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

    private void begin()
    {

    }

    private void readAll()
    {
        boolean[] attributeList = new boolean[4];
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream(this.filepath));
            int event = xmlStreamReader.getEventType();
            while (true) {
                switch (event) {
                    case XMLStreamConstants.START_ELEMENT:
                        if (xmlStreamReader.getLocalName().equals("student")) {
                            xmlStreamReader.getAttributeValue(0);
                        } else if (xmlStreamReader.getLocalName().equals("name")) {
                            attributeList[0] = true;
                        } else if (xmlStreamReader.getLocalName().equals("age")) {
                            attributeList[1] = true;
                        } else if (xmlStreamReader.getLocalName().equals("role")) {
                            attributeList[2] = true;
                        } else if (xmlStreamReader.getLocalName().equals("gender")) {
                            attributeList[3] = true;
                        }
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        if (attributeList[0]) {
//                            emp.setName(xmlStreamReader.getText());
//                            bName = false;
                        } else if (attributeList[0]) {
//                            emp.setAge(Integer.parseInt(xmlStreamReader.getText()));
//                            bAge = false;
                        } else if (attributeList[0]) {
//                            emp.setGender(xmlStreamReader.getText());
//                            bGender = false;
                        } else if (attributeList[0]) {
//                            emp.setRole(xmlStreamReader.getText());
//                            bRole = false;
                        }
                        break;
                    case XMLStreamConstants.END_ELEMENT:
                        if (xmlStreamReader.getLocalName().equals("Employee")) {
//                            empList.add(emp);
                        }
                        break;
                }
                if (!xmlStreamReader.hasNext())
                    break;

                event = xmlStreamReader.next();
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
