package com.dev.rapture;

import com.dev.rapture.fileparser.format.*;
import com.dev.rapture.fileparser.format.exception.UnknownFileFormatException;
import com.dev.rapture.fileparser.format.reader.CsvFormatReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    private static final String UNKNOWN_READ_FORMAT = "Unknown read filepath extension";
    private static final String UNKNOWN_WRITE_FORMAT = "Unknown write filepath extension";
    private static final String FAILED_TO_READ_HEADER_FILE = "Failed to read header file";

    private static Map<String, FormatType> typeMap = new HashMap<>();
    static {
        Arrays.stream(FormatType.values()).forEach(typeValue -> Main.typeMap.put(typeValue.getType(), typeValue));
    }

    public static void main(String[] args)
    {
        String readFilepath = args[0], writeFilepath = args[1], headerFilepath = args[2];
        Main.run(readFilepath, writeFilepath, headerFilepath);
    }

    private static void run(String readFilepath, String writeFilepath, String headerFilepath)
    {
        FormatType
                readFormat = Main.getFormatTypeForFilepath(readFilepath),
                writeFormat = Main.getFormatTypeForFilepath(writeFilepath);

        String[] header = Main.readHeaderFile(headerFilepath);

        boolean[] errors = Main.getErrors(readFormat, writeFormat, header);

        if (errors[0] || errors[1] || errors[2])
            Main.terminateProgramWithErrors(errors, new String[]{readFilepath, writeFilepath, headerFilepath});
        else
            Main.runWriterReader(header, writeFilepath, readFilepath, writeFormat, readFormat);
    }

    protected static FormatType getFormatTypeForFilepath(String filepath)
    {
        return Main.typeMap.get(Main.getFileExtension(filepath));
    }

    private static String getFileExtension(String filepath)
    {
        int lastIndexOfPeriod = filepath.lastIndexOf('.');
        String fileName = filepath.substring(0, lastIndexOfPeriod+1);
        if (fileName.length() < 2) return null;

        return filepath.substring(lastIndexOfPeriod+1);
    }

    protected static String[] readHeaderFile(String headerFilepath)
    {
        String[] header = null;
        try {
            CsvFormatReader reader = new CsvFormatReader(new FileInputStream(headerFilepath), null);
            reader.readLine();
            header = reader.getHeader();

            for (int i = 0; i < header.length; i++) header[i] = header[i].trim().replaceAll(" ", "_");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return header;
    }

    protected static boolean[] getErrors(FormatType readFormat, FormatType writeFormat, String[] header)
    {
        boolean[] errors = {false, false, false};

        if (readFormat == null) errors[0] = true;
        if (writeFormat == null) errors[1] = true;
        if (header == null) errors[2] = true;

        return errors;
    }

    private static void terminateProgramWithErrors(boolean[] errors, String[] args)
    {
        System.out.println("Sorry, the program encountered the following errors\n" +
                (errors[0] ? Main.UNKNOWN_READ_FORMAT +": "+args[0]+"\n" : "") +
                (errors[1] ? Main.UNKNOWN_WRITE_FORMAT +": "+args[1]+"\n" : "") +
                (errors[2] ? Main.FAILED_TO_READ_HEADER_FILE+": "+args[2]+"\n" : "") +
                "Terminating program...");
    }

    private static void runWriterReader(String[] header, String writeFilepath, String readFilepath, FormatType writeFormat, FormatType readFormat)
    {
        try(FileInputStream inputStream = new FileInputStream(readFilepath);
            FileOutputStream outputStream = new FileOutputStream(writeFilepath)) {

            FormatReader reader = FormatReaderFactory.getInstance(inputStream, header, readFormat);
            FormatWriter writer = FormatWriterFactory.getInstance(outputStream, header, writeFormat);

            String[] line;
            while ((line = reader.readLine()) != null) writer.write(line);
            reader.close();
            writer.close();
        } catch (UnknownFileFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
