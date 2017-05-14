package module4.setwithsum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main2 {
    public static void main(String[] args) throws IOException {
        final TreeMap<Long, Long> map = new TreeMap<>();
        final int MODULUS = 1_000_000_001;
        long sum_prev = 0;
        List<String> tmpAnswers = new LinkedList<>();
        //List<String> tmpVhod = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int rowCnt = Integer.parseInt(reader.readLine());

        for (int i = 0; i < rowCnt; i++) {
            //String vhod = reader.readLine();
            //tmpVhod.add(vhod);
            String[] row = reader.readLine().split(" ");
            long a = (sum_prev + Long.parseLong(row[1])) % MODULUS;
            switch (row[0]) {
                case "?":
                    tmpAnswers.add(map.containsKey(a) ? "Found" : "Not found");
                    //System.out.println(set.contains(a) ? "Found" : "Not found");
                    break;
                case "-":
                    map.remove(a);
                    break;
                case "+":
                    map.put(a, a);
                    break;
                default:
                    long b = (sum_prev + Long.parseLong(row[2])) % MODULUS;
                    try {
                        sum_prev = map.subMap(a,true, b, true).values().stream().mapToLong(Long::longValue).sum();
                        tmpAnswers.add(String.valueOf(sum_prev));
                        //System.out.println(sum_prev);
                    } catch (Exception e) {
                        throw new RuntimeException(/*tmpVhod.toString()*/);
                    }
                    break;
            }
        }
        tmpAnswers.stream().forEach(System.out::println);
    }
}
