package com.dev.rapture;

import com.dev.rapture.fileparser.DataReader;
import com.dev.rapture.fileparser.DataWriter;
import com.dev.rapture.fileparser.format.*;
import com.dev.rapture.fileparser.format.exception.UnknownFileFormatException;
import com.dev.rapture.fileparser.format.reader.CsvFormatReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amram on 2/5/2018.
 *
 * This program takes in 3 arguments: 2 filepaths (read path & write path) and 1 header filepath.
 * The header file contains the full header list for the files to ease conversion from one format to the next.
 */
public class Main
{
    private static Map<String, FormatType> typeMap = new HashMap<>();
    public static void main(String[] args)
    {
        Arrays.stream(FormatType.values()).forEach(typeValue -> Main.typeMap.put(typeValue.getType(), typeValue));

        String readFilepath = args[0], writeFilepath = args[1], headerFilepath = args[2];
        FormatType
                readFormat = Main.typeMap.get(Main.getFileExtension(readFilepath)),
                writeFormat = Main.typeMap.get(Main.getFileExtension(writeFilepath));

        String[] header = Main.readHeaderFile(headerFilepath);

        Main.runWriterReader(header, writeFilepath, readFilepath, writeFormat, readFormat);
    }

    private static String getFileExtension(String filepath)
    {
        return filepath.substring(filepath.lastIndexOf(".")+1);
    }

    private static String[] readHeaderFile(String headerFilepath)
    {
        String[] header = null;
        try {
            CsvFormatReader reader = new CsvFormatReader(new FileInputStream(headerFilepath), null);
            reader.readLine();
            header = reader.getHeader();

            for (int i = 0; i < header.length; i++) header[i] = header[i].replaceAll(" ", "_");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return header;
    }

    private static void runWriterReader(String[] header, String writeFilepath, String readFilepath, FormatType writeFormat, FormatType readFormat)
    {
        try {
            FormatReader reader = FormatReaderFactory.getInstance(new FileInputStream(readFilepath), header, readFormat);
            FormatWriter writer = FormatWriterFactory.getInstance(new FileOutputStream(writeFilepath), header, writeFormat);

            String[] line;
            while ((line = reader.readLine()) != null) writer.write(line);
            reader.close();
            writer.close();
        } catch (FileNotFoundException | UnknownFileFormatException e) {
            e.printStackTrace();
        }
    }
}
