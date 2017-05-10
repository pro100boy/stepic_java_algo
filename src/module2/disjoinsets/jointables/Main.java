package module2.disjoinsets.jointables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Table {
    private int parentIdx, records, tblIdx;

    public Table(int parentIdx, int records, int tblIdx) {
        this.parentIdx = parentIdx;
        this.records = records;
        this.tblIdx = tblIdx;
    }

    public int getParentIdx() {
        return parentIdx;
    }

    public void setParentIdx(int parentIdx) {
        this.parentIdx = parentIdx;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records += records;
    }

    public void clearRecords() {
        this.records = 0;
    }
}

public class Main {
    private static Table[] tables;
    private static int MAX = 0;

    private static int Find(int i) {
        if (tables[i].getParentIdx() != i)
            tables[i].setParentIdx(Find(tables[i].getParentIdx()));
        return tables[i].getParentIdx();
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // читаем число таблиц (tmp[0]) и число запросов (tmp[1])
        int[] tmp = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
        // размеры таблиц
        int[] tableSizes = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();

        // инициализация множеств. Изначально каждая таблица является родителем для самой себя
        tables = new Table[tmp[0]];
        for (int i = 0; i < tmp[0]; i++) {
            tables[i] = new Table(i, tableSizes[i], i);
            // определяем начальный максимальный размер
            if (tableSizes[i] > MAX) MAX = tableSizes[i];
        }

        // объединения
        for (int i = 0; i < tmp[1]; i++) {
            // сразу уменьшаем номер табл на единицу, чтобы совпадало с индексами массива
            int[] dest_src = (Arrays.stream(reader.readLine().split("\\s")).mapToInt(s -> Integer.valueOf(s) - 1).toArray());

            /*
            находим значения id для destination и source с помощью функции find.
            Если они не равны, увеличиваем кол-во строк в table destination и переприсваиваем родителя
            */
            Table dest = tables[dest_src[0]];
            Table src = tables[dest_src[1]];

            int destId = Find(dest.getParentIdx());
            int srcId = Find(src.getParentIdx());
            dest = tables[destId];
            src = tables[srcId];

            if (destId != srcId) {
                dest.setRecords(src.getRecords());
                src.clearRecords();
                src.setParentIdx(destId);
                if (dest.getRecords() > MAX) MAX = dest.getRecords();
                tables[destId] = dest;
                tables[srcId] = src;
            }

            System.out.println(MAX);
        }
    }
}
