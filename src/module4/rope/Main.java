package module4.rope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * A rope implementation in Java for Stanford's CS 166
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // исходная строка
        Rope r = new Rope(reader.readLine());
        // число запросов
        int rowCnt = Integer.parseInt(reader.readLine());
        for (int i = 0; i < rowCnt; i++) {
            int[] tmp = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
            int from = tmp[0];
            int to = tmp[1];
            int idx = tmp[2];

            // отделяем строку ДО куска
            Pair<Rope> pairBefore = r.split(from);

            // отделяем строку ПОСЛЕ куска
            Pair<Rope> pairAfter = pairBefore.two.split(to - from + 1);
            //System.out.println(pairAfter.one); // это вырезанный кусок
            //System.out.println(pairAfter.two); // это правая часть
            if (pairBefore.one != null)
                r = pairBefore.one.concat(pairAfter.two);
            else r = pairAfter.two;
            r = r.insert(pairAfter.one, idx);
        }
        System.out.println(r.toString());
    }
}
