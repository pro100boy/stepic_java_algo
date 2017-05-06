package stack_with_max;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Galushkin Pavel on 30.04.2017.
 */
class StackWithMax {
    private final int[] stack_array;
    private final int[] max_array;
    private int idx = -1;

    public StackWithMax(int stack_array_size) {
        this.stack_array = new int[stack_array_size];
        this.max_array = new int[stack_array_size];
    }

    public void push(String v) {
        int val = Integer.valueOf(v);
        if (0 <= val && val <= 100_000) {
            int max = max();
            idx++;
            stack_array[idx] = val;
            max_array[idx] = (max > val) ? max : val;
        } else throw new NumberFormatException("Wrong value");
    }

    public void pop() {
        if (idx != -1) {
            max_array[idx] = 0;
            idx--;
        }
    }

    public int max() {
        if (idx == -1) return 0;
        else return max_array[idx];
    }
}

public class Main {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            int q = Integer.valueOf(reader.readLine()); // число запросов
            if (1 <= q && q <= 400_000) {
                StackWithMax stackWithMax = new StackWithMax(q);
                for (int i = 0; i < q; i++) {
                    String v = reader.readLine();
                    if (v.startsWith("push"))
                        stackWithMax.push(v.split(" ")[1]);
                    else if (v.startsWith("pop"))
                        stackWithMax.pop();
                    else System.out.println(stackWithMax.max());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
