package ex00;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileTypeRecognizer {

    private final HashMap<String, String> signatures;

    FileTypeRecognizer(Path pathToSignatures) {
        signatures = new HashMap<>();
        parseSignatures(pathToSignatures);
    }

    private void parseSignatures(Path pathToSignatures) {
        Scanner sc;

        try {
            if (Files.exists(pathToSignatures)) {
                sc = new Scanner(pathToSignatures);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] data = line.split(", ");
                    signatures.put(data[0], data[1]);
                }
            } else {
                System.out.println("No file signatures.txt");
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("Problem with file signatures.txt");
            System.exit(1);
        }
    }

    public void printSignatures() {
        for (Map.Entry<String, String> entry : signatures.entrySet()) {
            System.out.println("----------");
            System.out.println('\'' + entry.getKey() + '\'' + " = " + '\'' + entry.getValue() + '\'');
            System.out.println("----------");
        }
    }

    public HashMap<String, String> getSignature() {
        return signatures;
    }

    public void recognizeFile() throws IOException {
        Scanner sc = new Scanner(System.in);
        File file = new File("results.txt");
        FileInputStream input;
        PrintWriter fw = null;

        try {
            fw = new PrintWriter(file);
        } catch (IOException e) {
            System.out.println("Couldn't create file results.txt");
            System.exit(1);
        }

        while (true) {
            boolean flag = false;
            String pathRes = sc.nextLine();
            if (pathRes.equals("42")) {
                break;
            }

            try {
                input = new FileInputStream(pathRes);
            } catch (FileNotFoundException e) {
                System.out.println("WRONG PATH TO FILE");
                continue;
            }

            byte[] code = new byte[20];
            StringBuilder builder = new StringBuilder();
            int amountOfRead = input.read(code);

            for (int i = 0; i < amountOfRead; i++) {
                String s = Integer.toHexString(code[i]).toUpperCase();
                if (s.length() < 2) {
                    builder.append('0').append(s).append(" ");
                } else {
                    builder.append(s).append(" ");
                }
            }

            for (Map.Entry<String, String> entry : signatures.entrySet()) {
                if (builder.toString().startsWith(entry.getValue())) {
                    System.out.println("PROCESSED");
                    flag = true;
                    fw.append(entry.getKey()).append(String.valueOf('\n'));
                    fw.flush();
                }
            }

            if (!flag) {
                System.out.println("UNDEFINED");
            }
            input.close();
        }
        fw.close();
    }

}
