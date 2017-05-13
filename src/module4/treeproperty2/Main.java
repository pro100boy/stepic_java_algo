package module4.treeproperty2;

import java.util.*;

public class Main {

    public static boolean flag = true;

    public static void checkingTree(HashMap<Integer, int[]> map, int parent) {
        int[] ar = map.get(parent);
        if (ar[1] == -1 && ar[2] == -1) {
            ar[2] = ar[1] = ar[0];
        } else {
            if (ar[1] != -1) {
                checkingTree(map, ar[1]);
                int leftMin = map.get(ar[1])[1] != -1 ? map.get(ar[1])[1] : ar[0];
                if (ar[0] <= map.get(ar[1])[0] || map.get(ar[1])[2] != -1 && ar[0] <= map.get(ar[1])[2]) flag = false;
                ar[1] = leftMin;
            }
            if (ar[2] != -1) {
                checkingTree(map, ar[2]);
                int rightMax = map.get(ar[2])[2] != -1 ? map.get(ar[2])[2] : ar[0];
                if (ar[0] > map.get(ar[2])[0] || map.get(ar[2])[1] != -1 && ar[0] > map.get(ar[2])[1]) flag = false;
                ar[2] = rightMax;
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        HashMap<Integer, int[]> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.put(i, new int[]{sc.nextInt(), sc.nextInt(), sc.nextInt()});
        }
        if (!map.isEmpty()) checkingTree(map, 0);
        String ans = flag ? "CORRECT" : "INCORRECT";
        System.out.println(ans);
    }
}