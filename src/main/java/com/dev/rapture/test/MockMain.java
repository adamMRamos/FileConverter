package com.dev.rapture.test;

import com.dev.rapture.Main;
import com.dev.rapture.fileparser.format.FormatType;

/**
 * Created by amram on 2/11/2018.
 */
public class MockMain extends Main
{
    static FormatType getFormatTypeForFilepathTest(String filepath)
    {
        return MockMain.getFormatTypeForFilepath(filepath);
    }

    static String[] readHeaderFileAndReturnHeaderValues(String filepath)
    {
        return MockMain.readHeaderFile(filepath);
    }

    static boolean[] getErrorMarkers(FormatType readFormat, FormatType writeFormat, String[] headers)
    {
        return MockMain.getErrors(readFormat, writeFormat, headers);
    }
}
