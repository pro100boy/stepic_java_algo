package parallelprocessing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Task implements Comparable<Task> {
    private int cpuN;
    private long processTime, endProcTime;

    public Task(int cpuN) {
        this.cpuN = cpuN;
    }

    public int getCpuN() {
        return cpuN;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }

    public long getEndProcTime() {
        return endProcTime;
    }

    public void setEndProcTime(long endProcTimePrev) {
        this.endProcTime = processTime + endProcTimePrev;

    }

    @Override
    public int compareTo(Task o) {
        int res = Long.compare(this.getEndProcTime(), o.getEndProcTime());
        return (res == 0) ? Integer.compare(this.getCpuN(), o.getCpuN()) : res;
    }

    @Override
    public String toString() {
        return cpuN + " " + endProcTime;

    }
}

public class Main {
    private static int CPUNUMBERS, TASKNUMBERS;
    private static int[] processTimes;
    private static Task[] H;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] tmp = reader.readLine().split("\\s");
        CPUNUMBERS = Integer.parseInt(tmp[0]);
        TASKNUMBERS = Integer.parseInt(tmp[1]);
        processTimes = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();

        H = new Task[CPUNUMBERS];

        // создаем кучу.
        for (int i = 0; i < CPUNUMBERS; i++) {
            H[i] = new Task(i);
        }

        // упорядочиваем по времени окончания обработки и по номеру процессора, чтобы определить процессор для след. задачи
        for (int i = CPUNUMBERS / 2; i >= 0; i--) {
            siftDown(i);
        }

        for (int i = 0; i < TASKNUMBERS; i++) {
            System.out.println(H[0]);

            Task t = H[0];
            t.setProcessTime(processTimes[i]);
            t.setEndProcTime(H[0].getEndProcTime());
            H[0] = t;

            siftDown(0);
        }
    }

    public static void siftDown(int i) {
        while (true) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int smallestChild = i;

            if (leftChild < CPUNUMBERS && H[leftChild].compareTo(H[smallestChild]) < 0)
                smallestChild = leftChild;

            if (rightChild < CPUNUMBERS && H[rightChild].compareTo(H[smallestChild]) < 0)
                smallestChild = rightChild;

            if (smallestChild == i) return;

            Task temp = H[i];
            H[i] = H[smallestChild];
            H[smallestChild] = temp;
            i = smallestChild;
        }
    }
}
    /*
    Тест 1
    Вход
5 8
9 8 7 6 5 4 3 2
    Выход
    0 0
    1 0
    2 0
    3 0
    4 0
    4 5
    3 6
    2 7

    Тест 2
    Вход
4 15
4 8 2 0 4 4 7 1 9 3 16 32 1 3 7
    Выход
    0 0
    1 0
    2 0
    3 0
    3 0
    2 2
    0 4
    3 4
    3 5
    2 6
    1 8
    2 9
    0 11
    0 12
    3 14

2 10
1 2 1 2 1 2 1 2 1 2
    Ответ должен быть
    0 0
    1 0
    0 1
    0 2
    1 2
    1 3
    0 4
    0 5
    1 5
    1 6
     */
/*
тонкий момент, который надо учесть, это то, что могут существовать задачи с временем 0,
т.е. процессор в этот момент становится свободным в ожидании других задач с временем > 0.
Небольшой тестик для данного кейса:
2 15
0 0 1 0 0 0 2 1 2 3 0 0 0 2 1
0 0
0 0
0 0
1 0
1 0
1 0
1 0
0 1
0 2
1 2
0 4
0 4
0 4
0 4
1 5

4 10
3 0 9 2 8 1 9 8 8 4
0 0 
1 0 
1 0
2 0
3 0
2 2
0 3
2 3
3 8
1 9

16 12
4 5 2 0 1 0 7 2 6 8 0 0
0 0
1 0
2 0
3 0
3 0
4 0
4 0
5 0
6 0
7 0
8 0
8 0

1 12
2 0 4 3 9 8 4 9 0 4 3 2
0 0
0 2
0 2
0 6
0 9
0 18
0 26
0 30
0 39
0 39
0 43
0 46
 */