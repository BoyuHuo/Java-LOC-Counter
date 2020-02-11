import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner cin = new Scanner(System.in);
        System.out.println("Please input your dir or file path:");
        FileManager fileManager = new FileManager(cin.nextLine());
        fileManager.findFile(new File(fileManager.basePath));
        fileManager.printResult();
    }
}
