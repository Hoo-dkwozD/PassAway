package sg.edu.sportsschool.Helper;

import java.io.*;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

public class ReadCsv {
    /* 
     * Returns a List of string arrays where each string array is 
     * one line (row) in the csv and each string in the string 
     * array (line) is the value.
     * E.g. 
     * [
     *     {
     *          "1",
     *          "2"
     *     },
     *     {
     *          "3",
     *          "4"
     *     }
     * ]
     * Returns [ {""} ] if csv file is empty. 
     * 
     * @param file The MultipartFile to read as a CSV file. 
     * 
     * @return A List of String Arrays where each Array is a line of CSV data. 
     */
    public static List<String[]> read(MultipartFile file) throws IOException, CsvException {
        InputStream inputStream = file.getInputStream(); // May throw IOException

        BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
        CSVReader reader = new CSVReaderBuilder(bReader).build();

        return reader.readAll(); // May throw CsvException
    }
}
