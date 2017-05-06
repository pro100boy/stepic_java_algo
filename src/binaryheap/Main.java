package binaryheap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static int HEAPSIZE;
    private static int[] H;
    private static List<String> rotateList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        HEAPSIZE = Integer.parseInt(reader.readLine());
        H = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();

        for (int i = HEAPSIZE / 2; i >= 0; i--) {
            siftDown(i);
        }

        System.out.println(rotateList.size());
        if (rotateList.size() > 0) rotateList.stream().forEach(System.out::println);
    }

    public static void siftDown(int i) {
        while (true) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int smallestChild = i;

            if (leftChild < HEAPSIZE && H[leftChild] < H[smallestChild])
                smallestChild = leftChild;

            if (rightChild < HEAPSIZE && H[rightChild] < H[smallestChild])
                smallestChild = rightChild;

            if (smallestChild == i) return;

            rotateList.add(i + " " + smallestChild);
            int temp = H[i];
            H[i] = H[smallestChild];
            H[smallestChild] = temp;
            i = smallestChild;
        }
    }
}
