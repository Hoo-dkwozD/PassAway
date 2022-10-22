package sg.edu.sportsschool.helper;

import java.io.*;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ReadCsv {

    /*
     * Returns List of string arrays where each string array is one line (row) in
     * the csv
     * and each string in the string array (line) is the value.
     * E.g. 
     * [
            [
                "1",
                "2"
            ],
            [
                "3",
                "4"
            ]
        ]
     * Returns [[""]] if csv file is empty
     * Returns null if exception occurred.
     */
    public static List<String[]> read(MultipartFile file) {
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream(); // may throw IOException
            BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
            CSVReader reader = new CSVReaderBuilder(bReader).build();
            return reader.readAll();

        } catch (Exception e) {
            return null;
        }

    }
}
