package module4.treeorders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

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
    private static List<Integer> orderList = new LinkedList<>();

    public static void preOrder(Node node){
        if (node == null) return;
        orderList.add(node.value);
        preOrder(node.left);
        preOrder(node.right);
    }

    public static void inOrder(Node node){
        if (node == null) return;
        inOrder(node.left);
        orderList.add(node.value);
        inOrder(node.right);
    }

    public static void postOrder(Node node){
        if (node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        orderList.add(node.value);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int rowCnt = Integer.parseInt(reader.readLine());
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

        orderList.clear();
        inOrder(nodes[0]);
        System.out.println(orderList.stream().map(i->String.valueOf(i)).collect(Collectors.joining(" ")).toString());

        orderList.clear();
        preOrder(nodes[0]);
        System.out.println(orderList.stream().map(i->String.valueOf(i)).collect(Collectors.joining(" ")).toString());

        orderList.clear();
        postOrder(nodes[0]);
        System.out.println(orderList.stream().map(i->String.valueOf(i)).collect(Collectors.joining(" ")).toString());

        //Arrays.stream(nodes).forEach(System.out::println);
    }
}
