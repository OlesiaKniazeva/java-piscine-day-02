package ex02;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Enter absolute path to folder, from which you want to start");
        } else {
            final String arg = "--current-folder=";
            if (!args[0].startsWith(arg) || args[0].length() == arg.length()) {
                System.out.println("Wrong argument");
            } else {
                Program program = new Program(args[0].substring(args[0].indexOf("=") + 1));
                program.start();
            }
        }

    }
}
