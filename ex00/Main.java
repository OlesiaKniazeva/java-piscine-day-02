package ex00;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        final String fileName = "signatures.txt";

        Path path = Paths.get(fileName);
        Path absPath = path.toAbsolutePath();

        FileTypeRecognizer file = new FileTypeRecognizer(absPath);
        file.recognizeFile();
    }
}
