package ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        FileInputStream input = null;
        FileOutputStream output = null;

        final String path = "/home/myael/java-piscine/piscine-day-02/src/ex00/signatures.txt";

        try {
            input = new FileInputStream(path);
            output = new FileOutputStream("result.txt");

            FileTypeRecognizer file = new FileTypeRecognizer(input, output);
            file.recognize();
//            HashMap<String, String> n = file.getSignature().getSignatures();
//            System.out.println("|");
//            for (Map.Entry<String, String> k : n.entrySet()) {
//                System.out.println(k.getKey() + " = " + k.getValue());
//            }
//            System.out.println("|");

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("File signatures.txt isn't found!");
        }

        if (input != null) {
            input.close();
        }
        if (output != null) {
            output.close();
        }
    }

}
