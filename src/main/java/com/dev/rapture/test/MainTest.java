package com.dev.rapture.test;

import com.dev.rapture.fileparser.format.FormatType;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by amram on 2/11/2018.
 */
public class MainTest
{
    @Test
    public void testCsvExtension()
    {
        assertEquals(FormatType.CSV, MockMain.getFormatTypeForFilepathTest("myFile.csv"));
        assertEquals(FormatType.CSV, MockMain.getFormatTypeForFilepathTest("myFile.foo.csv"));
    }

    @Test
    public void testXMLExtension()
    {
        assertEquals(FormatType.XML, MockMain.getFormatTypeForFilepathTest("myFile.xml"));
        assertEquals(FormatType.XML, MockMain.getFormatTypeForFilepathTest("myFile.foo.xml"));
    }

    @Test
    public void testNoMatchingFormatTypeForExtension()
    {
        Assert.assertNull(MockMain.getFormatTypeForFilepathTest("myFile.txt"));
        Assert.assertNull(MockMain.getFormatTypeForFilepathTest("myFile.foo"));
        Assert.assertNull(MockMain.getFormatTypeForFilepathTest("myFile"));
        Assert.assertNull(MockMain.getFormatTypeForFilepathTest("."));
        Assert.assertNull(MockMain.getFormatTypeForFilepathTest(".xml"));
        Assert.assertNull(MockMain.getFormatTypeForFilepathTest(".csv"));
    }

    @Test
    public void readHeaderFileTest()
    {
        String[] expectedHeaderValues = {
                "classroom_id", "classroom_name", "teacher_1_id", "teacher_1_last_name", "teacher_1_first_name",
                "teacher_2_id", "teacher_2_last_name", "teacher_2_first_name", "student_id", "student_last_name",
                "student_first_name", "student_grade"
        };

        String[] headerValues = MockMain.readHeaderFileAndReturnHeaderValues("data/header");

        Assert.assertArrayEquals(expectedHeaderValues, headerValues);
    }

    @Test
    public void assertHeaderValuesDoNotContainSpaces()
    {
        String[] headerValues = MockMain.readHeaderFileAndReturnHeaderValues("data/header");

        for (String headerValue : headerValues) Assert.assertFalse(headerValue.contains(" "));
    }

    @Test
    public void testErrors()
    {
        boolean errors[] = MockMain.getErrorMarkers(null, FormatType.CSV, new String[]{});
        Assert.assertArrayEquals(new boolean[]{true, false, false}, errors);

        errors = MockMain.getErrorMarkers(FormatType.CSV, null, new String[]{});
        Assert.assertArrayEquals(new boolean[]{false, true, false}, errors);

        errors = MockMain.getErrorMarkers(FormatType.CSV, FormatType.CSV, null);
        Assert.assertArrayEquals(new boolean[]{false, false, true}, errors);

        errors = MockMain.getErrorMarkers(FormatType.CSV, FormatType.CSV, new String[]{});
        Assert.assertArrayEquals(new boolean[]{false, false, false}, errors);

        errors = MockMain.getErrorMarkers(null, null, null);
        Assert.assertArrayEquals(new boolean[]{true, true, true}, errors);
    }
}
