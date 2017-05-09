package hashtables.phonebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Galushkin Pavel on 09.05.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] book = new String[9_999_999 + 1];
        int cnt = Integer.parseInt(reader.readLine());
        for (int i = 0; i < cnt; i++) {
            String[] command = reader.readLine().split(" ");
            int number = Integer.parseInt(command[1]);
            switch (command[0]) {
                case "add":
                    book[number] = command[2];
                    break;
                case "del":
                    book[number] = null;
                    break;
                default:
                    System.out.println((book[number] == null) ? "not found" : book[number]);
            }
        }
    }
}
