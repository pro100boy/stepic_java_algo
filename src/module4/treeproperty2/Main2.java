package module4.treeproperty2;

import java.util.Scanner;

public class Main2 {

    private static class Node {
        public Integer key;
        public Integer left;
        public Integer right;

        public Node(Integer key, Integer left, Integer right) {
            this.key = key;
            this.left = left;
            this.right = right;
        }
    }

    private static boolean check(Node[] tree, Integer node, Integer min, Integer max) {
        if (node >= 0) {
            Integer key = tree[node].key;
            return ((min == null) || (min <= key))
                    && ((max == null) || (key < max))
                    && check(tree, tree[node].left, min, key)
                    && check(tree, tree[node].right, key, max);
        } else
            return true;
    }

    public static void main(String[] args) throws Exception {
        try (Scanner in = new Scanner(System.in)) {
            int n = in.nextInt();
            Node[] tree = new Node[n];
            for (int i = 0; i < n; i++)
                tree[i] = new Node(in.nextInt(), in.nextInt(), in.nextInt());

            System.out.println((n <= 0) || check(tree, 0, null, null) ? "CORRECT" : "INCORRECT");
        }
    }

}
