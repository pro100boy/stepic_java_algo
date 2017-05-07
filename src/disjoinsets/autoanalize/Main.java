package disjoinsets.autoanalize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by Galushkin Pavel on 07.05.2017.
 */
public class Main {
    private static int[] parent;
    private static int[] rank;

    private static int Find(int i) {
        if (parent[i] != i)
            parent[i] = Find(parent[i]);
        return parent[i];
    }

    private static void Union(int i, int j) {
        i = Find(i);
        j = Find(j);

        if (rank[i] < rank[j])
            parent[i] = j;
        else {
            parent[j] = i;
            if (rank[i] == rank[j])
                ++rank[i];
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // tmp[0] - n
        // tmp[1] - e
        // tmp[2] - d
        int[] tmp = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();

        parent = new int[tmp[0]];
        for (int k = 0; k < tmp[0]; k++) parent[k] = k;

        rank = new int[tmp[0]];

        // читаем e пар i, j
        for (int k = 0; k < tmp[1]; k++) {
            int[] pair = Arrays.stream(reader.readLine().split("\\s")).mapToInt(s -> Integer.valueOf(s) - 1).toArray();
            Union(pair[0], pair[1]);
        }

        // читаем d пар p, q
        int res = 1;
        for (int k = 0; k < tmp[2]; k++) {
            int[] pair = Arrays.stream(reader.readLine().split("\\s")).mapToInt(s -> Integer.valueOf(s) - 1).toArray();
            if (Find(pair[0]) == Find(pair[1])) {
                res = 0;
            }
        }

        System.out.println(res);
    }
}
