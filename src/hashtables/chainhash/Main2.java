package hashtables.chainhash;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main2 {
    private static List<Integer> list = new ArrayList<>();

    private static int hash(String s) {
        return s.chars().reduce(Integer::sum).getAsInt();
    }

    private static void find(String text, String pattern) {
        int hashPattern = hash(pattern);
        int hashPartOfText = hash(text.substring(0, pattern.length()));
        char first = pattern.charAt(0);

        for (int i = 0; i < text.length() - pattern.length() + 1; i++) {
            if (hashPartOfText == hashPattern && text.charAt(i) == first) {
                if (text.substring(i, pattern.length() + i).equals(pattern)) {
                    list.add(i);
                }
            }

            if (i != text.length() - pattern.length()) {
                hashPartOfText = hashPartOfText - (int)text.charAt(i) + (int)(text.charAt(i + pattern.length()));
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String pattern = sc.next();
            String text = sc.next();
            find(text, pattern);
            System.out.println(list.stream().map(i->String.valueOf(i)).collect(Collectors.joining(" ")).toString());
        }
    }
}