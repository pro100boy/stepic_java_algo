package hashtables.chainhash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Galushkin Pavel on 09.05.2017.
 */
public class Main {
    private static int size;

    /*public static int h(String word) {
        BigDecimal hash = BigDecimal.valueOf(0);
        int k = 263, p = 1_000_000_007;
        for (int i = 0; i < word.length(); i++) {
            int code = word.codePointAt(i);
            hash = hash.add(BigDecimal.valueOf(Math.pow(k, i)).multiply(BigDecimal.valueOf(code)));
        }

        return hash.intValue() % p % size;
    }*/

    public static int h(String word) {
        double hash = 0;
        int k = 263, p = 1_000_000_007;
        for (int i = 0; i < word.length(); i++) {
            int code = word.codePointAt(i);
            hash += code * Math.pow(k, i);
        }

        return (int) (hash % p % size);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        size = Integer.parseInt(reader.readLine());
        Map<Integer, LinkedList<String>> map = new HashMap<>(size + 1);
        int cnt = Integer.parseInt(reader.readLine());

        String word;
        int bucket;
        LinkedList<String> list;
        for (int i = 0; i < cnt; i++) {
            String[] command = reader.readLine().split(" ");
            switch (command[0]) {
                case "add":
                    word = command[1];
                    bucket = h(word);
                    list = map.getOrDefault(bucket, new LinkedList<>());
                    if (!list.contains(word)) list.addFirst(word);
                    map.put(bucket, list);
                    break;
                case "del":
                    word = command[1];
                    bucket = h(word);
                    list = map.getOrDefault(bucket, null);
                    if (!Objects.isNull(list)) {
                        for (ListIterator<String> iter = list.listIterator(); iter.hasNext(); ) {
                            String element = iter.next();
                            if (element.equals(word)) {
                                iter.remove();
                                break;
                            }
                        }
                        map.put(bucket, list);
                    }
                    break;
                case "find":
                    word = command[1];
                    bucket = h(word);
                    list = map.getOrDefault(bucket, null);
                    System.out.println((!Objects.isNull(list) && list.contains(word)) ? "yes" : "no");
                    break;
                default:
                    bucket = Integer.valueOf(command[1]);
                    list = map.getOrDefault(bucket, null);
                    System.out.println( !Objects.isNull(list) ? list.stream().collect(Collectors.joining(" ")).toString() : "");
                    break;
            }
        }
    }
}
