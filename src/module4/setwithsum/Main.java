package module4.setwithsum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    private Node root;
    public long sum = 0l;

    static class Node {
        long key;
        long sum;
        Node l;
        Node r;
        Node p;

        public Node(long key, Node p) {
            this.key = key;
            this.p = p;
        }

        public void print() {
            print("", true);
        }

        private void print(String prefix, boolean isTail) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + key + ":" + sum);
            String nextPrefix = prefix + (isTail ? "    " : "│   ");
            if (l != null || r != null) {
                if (l != null) {
                    l.print(nextPrefix, r == null);
                } else {
                    System.out.println(nextPrefix + (r == null ? "└── " : "├── "));
                }
                if (r != null) {
                    r.print(nextPrefix, true);
                } else {
                    System.out.println(nextPrefix + "└── ");
                }
            }
        }
    }

    private Node search(Node t, long key) {
        if (t == null || t.key == key)
            return t;
        if (key < t.key)
            return search(t.l, key);
        else
            return search(t.r, key);
    }

    public Node search(long key) {
        return search(root, key);
    }

    private Node insert(Node t, Node p, long key) {
        if (t == null) {
            t = new Node(key, p);
        } else {
            if (key < t.key)
                t.l = insert(t.l, t, key);
            else
                t.r = insert(t.r, t, key);
        }

        // поддерживаем пересчет суммы
        t.sum = t.key + (t.l == null ? 0 : t.l.sum) + (t.r == null ? 0 : t.r.sum);
        return t;
    }

    public void insert(long key) {
        root = insert(root, null, key);
    }

    private void replace(Node a, Node b) {
        if (a.p == null)
            root = b;
        else if (a == a.p.l)
            a.p.l = b;
        else
            a.p.r = b;
        if (b != null)
            b.p = a.p;
    }

    private void remove(Node t, long key) {
        if (t == null)
            return;
        if (key < t.key)
            remove(t.l, key);
        else if (key > t.key)
            remove(t.r, key);
        else if (t.l != null && t.r != null) {
            Node m = t.r;
            while (m.l != null)
                m = m.l;
            t.key = m.key;
            replace(m, m.r);
        } else if (t.l != null) {
            replace(t, t.l);
        } else if (t.r != null) {
            replace(t, t.r);
        } else {
            replace(t, null);
        }
        // поддерживаем пересчет суммы
        t.sum = t.key + (t.l == null ? 0 : t.l.sum) + (t.r == null ? 0 : t.r.sum);
    }

    public void remove(long key) {
        remove(root, key);
    }

    public long keys(Node x, long lo, long hi) {
        if (x != null) {
            long cmplo = Long.compare(lo, x.key);
            long cmphi = Long.compare(hi, x.key);
            if (cmplo < 0) keys(x.l, lo, hi);
            if (cmplo <= 0 && cmphi >= 0) {
                sum += x.key;
            }
            if (cmphi > 0) keys(x.r, lo, hi);
        }
        return sum;
    }

    public static void main(String[] args) throws IOException {
        final Main main = new Main();

        final int MODULUS = 1_000_000_001;
        long sum_prev = 0;
        List<String> tmpAnswers = new LinkedList<>();
        //List<String> tmpVhod = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int rowCnt = Integer.parseInt(reader.readLine());

        for (int i = 0; i < rowCnt; i++) {
            //String vhod = reader.readLine();
            //tmpVhod.add(vhod);
            String[] row = reader.readLine().split(" ");
            long a = (sum_prev + Long.parseLong(row[1])) % MODULUS;
            switch (row[0]) {
                case "?":
                    tmpAnswers.add(main.search(a) != null ? "Found" : "Not found");
                    //System.out.println(set.contains(a) ? "Found" : "Not found");
                    break;
                case "-":
                    main.remove(a);
                    break;
                case "+":
                    if (main.search(a) == null)
                        main.insert(a);
                    break;
                default:
                    long b = (sum_prev + Long.parseLong(row[2])) % MODULUS;
                    //try {
                    main.sum = 0L;
                    sum_prev = main.keys(main.root, a, b);
                    tmpAnswers.add(String.valueOf(sum_prev));
                    //System.out.println(sum_prev);
                    //} catch (Exception e) {
                    //throw new RuntimeException(/*tmpVhod.toString()*/);
                    //System.out.println(e.getMessage());
                    //}
                    break;
            }
        }
        tmpAnswers.stream().forEach(System.out::println);
    }
}
