package ex01;

import java.io.*;
import java.util.*;

public class Similarity {

    private final String fileName1;
    private final String fileName2;

    private TreeMap<String, Integer> s1;
    private TreeMap<String, Integer> s2;
    private TreeSet<String> allWords;

    double similarity;
    public Similarity(String file1, String file2) {
        this.fileName1 = file1;
        this.fileName2 = file2;
        s1 = new TreeMap<>();
        s2 = new TreeMap<>();
        allWords = new TreeSet<>();
    }

    public void checkSimilarity() throws FileNotFoundException {
        readDataFromFile();
        countSimilarity();
        createDictionary();
        System.out.println(getSimilarity());
    }

    private void createDictionary() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"));
            for (String word : allWords) {
                writer.write(word);
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Couldn't create dictionary with words!");
            System.exit(1);
        }

    }

    public double getSimilarity() {
        return similarity;
    }

    private void countSimilarity() {
        double numerator = 0;
        double denominator = 0;

        double a = 0;
        double b = 0;

        for (String key : allWords) {
            if (s1.containsKey(key) && s2.containsKey(key)) {
                numerator += s1.get(key) * s2.get(key);
            }
            if (s1.containsKey(key)) {
                int res = s1.get(key);

                a += res * res;
            }
            if (s2.containsKey(key)) {
                int res = s2.get(key);

                b += res * res;
            }
        }
        denominator = (double) Math.sqrt(a) * Math.sqrt(b);

        similarity = Math.floor(numerator / denominator * 100) / 100;
    }

    private void readDataFromFile() throws FileNotFoundException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName1));
            BufferedReader reader2 = new BufferedReader(new FileReader(fileName2));

            readFile(reader, 1);
            readFile(reader2, 2);

            reader.close();
            reader2.close();

        } catch (IOException e) {
            System.out.println("Problem with compared files.");
            System.exit(1);
        }
    }

    private void readFile(BufferedReader reader, int num) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] words = line.split("(?U)[\\W_]+");
            for (String word : words) {
                if (word.length() < 1) {
                    continue;
                }
                allWords.add(word);
                if (num == 1) {
                    int amount = s1.getOrDefault(word, 0);
                    s1.put(word, ++amount);
                } else {
                    int amount = s2.getOrDefault(word, 0);
                    s2.put(word, ++amount);
                }
            }
        }
    }

    public void printSet() {
        System.out.println("------------------");
        for (String allWord : allWords) {
            System.out.println('\'' + allWord + '\'');
        }
        System.out.println("------------------");
    }

    public void printMap1() {
        for (Map.Entry<String, Integer> entry : s1.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public void printMap2() {
        System.out.println("==================");
        for (Map.Entry<String, Integer> entry : s2.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("==================");
    }

}
