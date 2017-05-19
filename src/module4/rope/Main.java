package module4.rope;

import java.io.*;
import java.util.*;

/**
 * A rope implementation in Java for Stanford's CS 166
 */
class Pair<T> {
    T one;
    T two;

    public Pair() {
        this.one = null;
        this.two = null;
    }

    public Pair(T one, T two) {
        this.one = one;
        this.two = two;
    }
}

class Rope implements CharSequence {

    String data;
    Rope left;
    Rope right;
    int leftLen;

    public Rope(String data) {
        this.data = data;
        this.leftLen = data.length();
    }

    public Rope(Rope left, Rope right) {
        this.left = left;
        this.right = right;
        this.leftLen = length(left);
    }

    public int length() {
        return length(this);
    }

    private int length(Rope r) {
        int len = 0;
        for (; r != null; r = r.right) {
            len += r.leftLen;
        }
        return len;
    }

    public Rope concat(Rope r) {
        return concat(this, r);
    }

    private Rope concat(Rope one, Rope two) {
        if (one == null) {
            return two;
        } else if (two == null) {
            return one;
        }
        return new Rope(one, two);
    }

    private char charAt(Rope node, int i) {
        if (node.left == null) {
            assert i >= 0 && i < node.leftLen;
            return node.data.charAt(i);
        }

        if (node.leftLen > i) {
            return charAt(node.left, i);
        } else {
            return charAt(node.right, i - node.leftLen);
        }
    }

    public char charAt(int i) {
        return charAt(this, i);
    }

    public Pair<Rope> split(int index) {
        return split(this, index);
    }

    private Pair<Rope> split(Rope nd, int index) {
        if (nd.left == null) {
            assert index >= 0 && index <= nd.leftLen;
            Pair<Rope> nodes = new Pair<Rope>();
            if (index == 0) {
                nodes.one = null;
                nodes.two = nd;
            } else if (index == nd.leftLen) {
                nodes.one = nd;
                nodes.two = null;
            } else {
                nodes.one = new Rope(nd.data.substring(0, index));
                nodes.two = new Rope(nd.data.substring(index, nd.leftLen));
            }
            return nodes;
        } else if (index == nd.leftLen) {
            return new Pair<Rope>(nd.left, nd.right);
        } else if (index < nd.leftLen) {
            Pair<Rope> pair = split(nd.left, index);
            return new Pair<Rope>(pair.one, concat(pair.two, nd.right));
        } else {
            Pair<Rope> pair = split(nd.right, index - nd.leftLen);
            return new Pair<Rope>(concat(nd.left, pair.one), pair.two);
        }
    }

    public Rope subSequence(int start, int end) {
        Pair<Rope> sp1 = split(start);
        Pair<Rope> sp2 = sp1.two.split(end - start);
        return sp2.one;
    }

    public Rope insert(Rope r, int index) {
        Pair<Rope> pair = this.split(index);
        return concat(concat(pair.one, r), pair.two);
    }

    public String toString() {
        if (left == null) return data;
        return left.toString() + right.toString();
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // исходная строка
        Rope r = new Rope(reader.readLine());
        // число запросов
        int rowCnt = Integer.parseInt(reader.readLine());
        for (int i = 0; i < rowCnt; i++) {
            int[] tmp = Arrays.stream(reader.readLine().split("\\s")).mapToInt(Integer::parseInt).toArray();
            int from = tmp[0];
            int to = tmp[1];
            int idx = tmp[2];
            // отделяем строку ДО куска
            Pair<Rope> pairBefore = r.split(from);

            // отделяем строку ПОСЛЕ куска
            Pair<Rope> pairAfter = pairBefore.two.split(to - from + 1);
            //System.out.println(pairAfter.one); // это вырезанный кусок
            //System.out.println(pairAfter.two); // это правая часть
            if (pairBefore.one != null)
                r = pairBefore.one.concat(pairAfter.two);
            else r = pairAfter.two;

            if (r != null)
                r = r.insert(pairAfter.one, idx);
            else r = pairAfter.one;
        }
        System.out.println(r.toString());
    }

    /*
    public static void main(String[] args) throws Exception {
		try (Scanner in = new Scanner(System.in)) {
			Rope rope = new Rope(in.nextLine());
			int q = in.nextInt();
			while (q-- > 0) {
				int i = in.nextInt();
				int j = in.nextInt() + 1;
				int k = in.nextInt();
				Pair<Rope> p1 = rope.split(i);
				Pair<Rope> p2 = p1.two.split(j - i);
				rope = Rope.concat(p1.one, p2.two);
				rope = Rope.insert(rope, p2.one, k);
			}
			System.out.println(rope);
		}
	}
     */
}
