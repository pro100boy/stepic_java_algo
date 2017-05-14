package module4.setwithsum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void add(int[] t, int i, int value) {
        for (; i < t.length; i |= i + 1)
            t[i] += value;
    }

    // sum[0..i]
    public static int sum(int[] t, int i) {
        int res = 0;
        for (; i >= 0; i = (i & (i + 1)) - 1)
            res += t[i];
        return res;
    }

    // sum[a..b]
    public static int sum(int[] t, int a, int b) {
        return sum(t, b) - sum(t, a - 1);
    }

    public static void set(int[] t, int i, int value) {
        add(t, i, -get(t, i) + value);
    }

    public static int get(int[] t, int i) {
        int res = t[i];
        if (i > 0) {
            int lca = (i & (i + 1)) - 1;
            for (--i; i != lca; i = (i & (i + 1)) - 1)
                res -= t[i];
        }
        return res;
    }

    public static void main(String[] args) throws IOException {
        final int[] t = new int[1_000_000_000];
        final int MODULUS = 1_000_000_001;
        int sum_prev = 0;
        List<String> answersList = new LinkedList<>();
        //List<String> tmpVhod = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int rowCnt = Integer.parseInt(reader.readLine());

        for (int i = 0; i < rowCnt; i++) {
            //String vhod = reader.readLine();
            //tmpVhod.add(vhod);
            String[] row = reader.readLine().split(" ");
            int a = (sum_prev + Integer.parseInt(row[1])) % MODULUS;
            switch (row[0]) {
                case "?":
                    answersList.add( get(t, a) != 0 ? "Found" : "Not found");
                    //System.out.println(set.contains(a) ? "Found" : "Not found");
                    break;
                case "-":
                    set(t, a, 0);
                    break;
                case "+":
                    set(t, a, a);
                    break;
                default:
                    int b = (sum_prev + Integer.parseInt(row[2])) % MODULUS;
                    try {
                        sum_prev = sum(t, a, b);
                        answersList.add(String.valueOf(sum_prev));
                        //System.out.println(sum_prev);
                    } catch (Exception e) {
                        throw new RuntimeException(/*tmpVhod.toString()*/);
                    }
                    break;
            }
        }
        answersList.stream().forEach(System.out::println);
    }
}
