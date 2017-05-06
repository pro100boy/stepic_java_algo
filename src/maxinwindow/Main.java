package maxinwindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Galushkin Pavel on 30.04.2017.
 */
public class Main {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String n = reader.readLine();
            List<Integer> array = Arrays.stream(reader.readLine().split("\\s"))
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());
            int m = Integer.valueOf(reader.readLine());

            TreeMap<Integer, Integer> map = new TreeMap<>();
            for (int i = 0; i < m - 1; i++) {
                map.put(array.get(i), i);
            }
            int k = 0;
            for (int i = m - 1; i < array.size(); i++) {
                map.put(array.get(i), i);
                System.out.println(map.lastKey());
                map.remove(array.get(k), k);
                k++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
