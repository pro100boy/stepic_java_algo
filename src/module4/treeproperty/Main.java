package module4.treeproperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// A binary tree node
class Node {
    int value, idxLeft, idxRight;
    Node left, right;

    public Node(int value) {
        this.value = value;
        left = right = null;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", left=" + (left != null ? left.value : "null") +
                ", right=" + (right != null ? right.value : "null") +
                '}';
    }
}

public class Main {
    private static List<Node> orderList = new ArrayList<>();

    public static void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        orderList.add(node);
        inOrder(node.right);
    }

    // min и max — минимально и максимально допустимые значения в вершинах поддерева.
    public static boolean check(Node v, int min, int max) {
        if (v == null) return true;
        if (v.value <= min || max <= v.value) return false;
        return check(v.left, min, v.value) && check(v.right, v.value, max);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int rowCnt = Integer.parseInt(reader.readLine());
        if (rowCnt == 0) {
            System.out.print("CORRECT");
            System.exit(0);
        }

        Node[] nodes = new Node[rowCnt];
        // заполняем руты
        for (int i = 0; i < rowCnt; i++) {
            int[] tmp = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
            nodes[i] = new Node(tmp[0]);
            nodes[i].idxLeft = tmp[1];
            nodes[i].idxRight = tmp[2];
        }
        // заполняем детей
        for (int i = 0; i < rowCnt; i++) {
            Node n = nodes[i];
            n.left = (n.idxLeft != -1) ? nodes[n.idxLeft] : null;
            n.right = (n.idxRight != -1) ? nodes[n.idxRight] : null;
            nodes[i] = n;
        }

        System.out.println(check(nodes[0], Integer.MIN_VALUE, Integer.MAX_VALUE));
        /*Arrays.stream(nodes).forEach(System.out::println);
        System.exit(0);*/
        //inOrder(nodes[0]);
        //System.out.println(orderList.stream().map(i->String.valueOf(i.value)).collect(Collectors.joining(" ")).toString());
        //System.out.println(orderList.stream().map(i -> String.valueOf(i)).collect(Collectors.joining("\n")).toString());
        /*boolean correct = true;
        boolean leftPart = true;
        for (Node n : orderList) {
            if (n != nodes[0] && leftPart) {
                if (nodes[0].value < n.value) {
                    correct = false;
                    break;
                }
            }
            if (n == nodes[0]) {
                leftPart = false;
                continue;
            }
            if (n != nodes[0] && !leftPart) {
                if (nodes[0].value >= n.value) {
                    correct = false;
                    break;
                }
            }
        }
        System.out.print(correct ? "CORRECT" : "INCORRECT");*/
    }
}
