package parallelprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;

// БЕЗ использования кучи. На PriorityQueue
public class Main2 {
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        int[] inputs = Arrays.stream(readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        long[] times = Arrays.stream(readLine().split(" ")).mapToLong(Long::parseLong).toArray();
        LinkedList<Long> process = new LinkedList<>();
        Arrays.stream(times).forEach(process::add);
        PriorityQueue<Processor> processors = new PriorityQueue<>();
        for (int i = 0; i < inputs[0]; i++) {
            processors.add(new Processor(i));
        }
        while (!process.isEmpty()) {
            System.out.println(processors.peek().processor + " " + processors.peek().time);
            Processor current = processors.poll();
            current.time += process.poll();
            processors.add(current);
        }
    }

    private static String readLine() {
        String result = "";
        try {
            result = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class Processor implements Comparable<Processor> {
        int processor;
        long time;

        public Processor(int processor) {
            this.processor = processor;
        }

        @Override
        public int compareTo(Processor o) {
            if (this.time == o.time) {
                return this.processor - o.processor;
            }
            return Long.compare(this.time, o.time);
        }
    }
}