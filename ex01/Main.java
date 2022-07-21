package ex01;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 2) {
            System.out.println("You need to enter two files as input");
        } else {
            Similarity s = new Similarity(args[0], args[1]);
            s.checkSimilarity();
            s.printSet();
            s.printMap1();
            s.printMap2();
        }
    }
}
