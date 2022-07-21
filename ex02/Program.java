package ex02;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Program {

    private String currentPath;
    public Program(String startPath) {
        Path path = Paths.get(startPath);
        if (!path.isAbsolute() || !Files.exists(path) || !Files.isDirectory(path) || !Files.isReadable(path)) {
            System.out.println("Invalid absolute path");
            System.exit(1);
        }
        currentPath = String.valueOf(path);
        if (!path.endsWith("/")) {
            currentPath += "/";
        }
    }

    public void start() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            String command = sc.nextLine();

            if (command.equals("ls")) {
                ls();
            } else if (command.equals("exit")) {
                System.exit(0);
            } else {
                String[] data = command.split(" ");
                if (data[0].equals("mv")) {
                    makeMv(data);
                } else if (data[0].equals("cd")) {
                    changeDir(data);
                } else {
                    System.out.println("Wrong command");
                }
            }
        }
    }

    private void makeMv(String[] data) {
        if (data.length != 3) {
            System.out.println("Couldn't move file");
            return;
        }

        String file = data[1];
        String place = data[2];

        if (!place.endsWith("/")) {
            place += "/";
        }

        Path fileToMove = Paths.get(data[1]);

        if (fileToMove.isAbsolute() && Files.notExists(fileToMove) ||
            !fileToMove.isAbsolute() && Files.notExists(Paths.get(currentPath + file))) {
            System.out.println("No file found");
            return;
        }
        if (!fileToMove.isAbsolute()) {
            file = currentPath + file;
        }

        Path placeToMove = Paths.get(currentPath + place);
        Path pathToFile = Paths.get(file);

        if ((Files.exists(placeToMove) && Files.isDirectory(placeToMove))
                && placeToMove.isAbsolute()) {
            place = currentPath + place + pathToFile.getFileName().toString();
        } else if (Paths.get(place).isAbsolute()) {
            place = place +  pathToFile.getFileName().toString();
        } else {
            place = currentPath + place;
        }

        try {
            Files.move(pathToFile, Paths.get(place), REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void changeDir(String[] data)  {
        if (data.length != 2) {
            System.out.println("Couldn't change directory");
        } else {
            Path newPath = Paths.get(data[1]);

            if (newPath.isAbsolute() && Files.isDirectory(newPath) && Files.isReadable(newPath)) {
                currentPath = data[1];
            } else {
                String s = currentPath + data[1];
                Path p = Paths.get(s).normalize();
                if (Files.exists(p) && Files.isDirectory(p) && Files.isReadable(p)) {
                    currentPath = p.toString();
                } else {
                    System.out.println("No such directory");
                    return;
                }
            }
        }
        if (!currentPath.endsWith("/")) {
            currentPath += "/";
        }
    }

    private void ls() {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(currentPath))) {
            for (Path path : stream) {
                if (!Files.isHidden(path))
                    System.out.println(path.getFileName().toString() + " " + path.toFile().length() + "KB");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}
