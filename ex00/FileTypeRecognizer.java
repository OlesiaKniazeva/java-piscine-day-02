package ex00;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class FileTypeRecognizer {

    private FileInputStream input;
    private FileOutputStream output;

    private Signatures signatures;

    FileTypeRecognizer(FileInputStream input, FileOutputStream output) throws IOException {
        this.input = input;
        this.output = output;
        signatures = new Signatures(getRawData());
    }

    public Signatures getSignature() {
        return signatures;
    }

    private byte[] getRawData() throws IOException {
        if (input == null) {
            return null;
        }

        int size = input.available();
        byte[] data = new byte[size];

        input.read(data);
        return data;
    }

    public void recognize() throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Enter path to file: ");
                String path = sc.nextLine();

                FileInputStream file = new FileInputStream(path);

                byte[] fileArr = new byte[file.available()];
                file.read(fileArr);

                String data = new String(fileArr);

                checkIfSignatureFound(data);
            } catch (IOException e) {
                System.out.println("No file found, check if path to file is right!");
            }
        }
    }

    private void checkIfSignatureFound(String data) {
//        for (Map.Entry<String, String> entry : signatures.getSignatures().entrySet()) {
//            if ()
//        }
        System.out.println(data);
    }

}
