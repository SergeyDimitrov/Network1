import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileUtils {
    public static void writeToFile(String filepath, String message) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(filepath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.println(message);
        writer.close();
    }
}
