package trees;
/**
 * мой сданный вариант
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Node {
    private int value;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        return value == node.value;
    }

    @Override
    public int hashCode() {
        return value;
    }
}

public class Main {
    static Map<Node, List<Node>> map = new HashMap<>();

    private static int getHeight(Node node) {
        int height = 1;

        List<Node> list = map.get(node);
        if (list != null)
            for (Node n : list)
                height = Math.max(height, 1 + getHeight(n));

        return height;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.valueOf(bufferedReader.readLine());
        String[] parents = bufferedReader.readLine().split(" ");

        int rootVal = 0;
        for (int i = 0; i < parents.length; i++) {
            if (Integer.valueOf(parents[i]) != -1) {
                Node node = new Node(Integer.valueOf(parents[i]));
                List<Node> list = (map.containsKey(node)) ? map.get(node) : new ArrayList<>();
                list.add(new Node(i));
                map.put(node, list);
            } else rootVal = i;
        }

        for (Node n : map.keySet())
            if (n.getValue() == rootVal) {
                System.out.println(getHeight(n));
                break;
            }
    }
}
