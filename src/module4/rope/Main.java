package module4.rope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String string = "abcdef";// reader.readLine();
        /*int cnt = Integer.parseInt(reader.readLine());
        for (int i = 0; i < cnt; i++) {
            int[] tmp = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
            char[] string2 = new char[string.length];
            System.arraycopy(string, 0, string2, 0, tmp[0]);
            Arrays.asList(string2).stream().forEach(System.out::println);
        }*/
        StringBuilder stringBuilder = new StringBuilder(string);
        String substr = stringBuilder.substring(0, 2);
        stringBuilder.insert(3, substr);
        stringBuilder.delete(0, 2);
        //stringBuilder.replace(1, 1, substr);
        System.out.println(stringBuilder);

    }
}
