package file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileManager {

    private static String delimiter = " ";
    private static String newLine = "\n";

    public static List<String> readFile(String filePath) {
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = reader.readLine()) != null ) {
                list.add(line);
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public static Object[] parseLine(String line) {
        return line.split(delimiter);
    }

    public static void writeFile(String path, List<String> results) {
        try {
            Files.write(Paths.get(path), String.join(newLine, results).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String writeObjects(List<Object> objects) {
        return objects.stream().map(Object::toString).collect(Collectors.joining(delimiter));
    }
}
