package module3.chainhash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Galushkin Pavel on 09.05.2017.
 */
public class Main {
    private static BigInteger size;
    private static final BigInteger k = BigInteger.valueOf(263);
    private static final BigInteger p = BigInteger.valueOf(1_000_000_007);
    private static final BigInteger[] matrix = new BigInteger[15];

    // сразу заполняем степени 263
    static {
        matrix[0] = BigInteger.valueOf(1);
        for (int i = 1; i < matrix.length; i++) {
            matrix[i] = matrix[i - 1].multiply(k);
        }
        //Arrays.stream(matrix).forEach(System.out::println);
    }

    public static int h(String word) {
        BigInteger hash = BigInteger.valueOf(0);
        for (int i = 0; i < word.length(); i++) {
            BigInteger code = BigInteger.valueOf(word.codePointAt(i));
            hash = hash.add(matrix[i].multiply(code));
        }
        BigInteger tmp = hash.mod(p).mod(size);
        return tmp.intValue();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        size = new BigInteger(reader.readLine());
        Map<Integer, LinkedList<String>> map = new HashMap<>();
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
                    System.out.println(!Objects.isNull(list) ? list.stream().collect(Collectors.joining(" ")).toString() : "");
                    break;
            }
        }
    }
}
