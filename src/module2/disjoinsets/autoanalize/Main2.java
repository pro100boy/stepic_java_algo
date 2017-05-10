package module2.disjoinsets.autoanalize;

import java.util.Scanner;

public class Main2 {
    static int[] parent;

    public static void MakeSet(int i) {
        parent[i] = i;
    }

    public static void Union(int i, int j) { // Из лекции
        int i_id = Find(i);
        int j_id = Find(j);
        parent[j_id] = i_id;
    }

    public static int Find(int i) { // Из лекции
        if (i != parent[i]) {
            parent[i] = Find(parent[i]);
        }
        return parent[i];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int e = scanner.nextInt();
        int d = scanner.nextInt();
        parent = new int[n];
        for (int index = 0; index < n; index++) {
            MakeSet(index); // Создаём множество 1...n
        }
        for (int i = 0; i < e; i++) { //
            Union(scanner.nextInt() - 1, scanner.nextInt() - 1); // Объединяем e элементов
        }
        for (int i = 0; i < d; i++) {
            if (Find(scanner.nextInt() - 1) == Find(scanner.nextInt() - 1)) // если неравенства в одном множестве с равенствами
            {
                System.out.println(0); // печатаем 0 и завершаем Main
                return;
            }
        }
        System.out.println(1); // Иначе всё хорошо, и печатаем 1
    }
}

