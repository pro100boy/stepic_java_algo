package module4.setwithsum;

import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree {

    static Node root;

    static class Node {
        int key;
        int value;
        int sum;
        Node l;
        Node r;
        Node p;

        public Node(int key, int value, Node p) {
            this.key = key;
            this.value = value;
            this.p = p;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "key=" + key +
                    ", value=" + value +
                    ", sum=" + sum +
                    "}\n";
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

    public static Node minimum(Node t) {
        if (t.l == null) return t;
        return minimum(t.l);
    }

    public static Node maximum(Node t) {
        if (t.r == null) return t;
        return maximum(t.r);
    }

    static Node search(Node t, int key) {
        if (t == null || t.key == key)
            return t;
        if (key < t.key)
            return search(t.l, key);
        else
            return search(t.r, key);
    }

    public static Node search(int key) {
        return search(root, key);
    }

    static Node insert(Node t, Node p, int key, int value) {
        if (t == null) {
            t = new Node(key, value, p);
        } else {
            if (key < t.key)
                t.l = insert(t.l, t, key, value);
            else
                t.r = insert(t.r, t, key, value);
        }

        // поддерживаем пересчет суммы
        t.sum = t.value + (t.l == null ? 0 : t.l.sum) + (t.r == null ? 0 : t.r.sum);
        return t;
    }

    public static void insert(int key, int value) {
        root = insert(root, null, key, value);
    }

    static void replace(Node a, Node b) {
        if (a.p == null)
            root = b;
        else if (a == a.p.l)
            a.p.l = b;
        else
            a.p.r = b;
        if (b != null)
            b.p = a.p;
    }

    static void remove(Node t, int key) {
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
            t.value = m.value;
            replace(m, m.r);
        } else if (t.l != null) {
            replace(t, t.l);
        } else if (t.r != null) {
            replace(t, t.r);
        } else {
            replace(t, null);
        }
        // поддерживаем пересчет суммы
        t.sum = t.value + (t.l == null ? 0 : t.l.sum) + (t.r == null ? 0 : t.r.sum);
    }

    public static void remove(int key) {
        remove(root, key);
    }

    //in-order
    static void print(Node t) {
        if (t != null) {
            print(t.l);
            System.out.print(t.key + ":" + t.value + " ");
            print(t.r);
        }
    }

    static void printPreOrder(Node t) {
        if (t != null) {
            System.out.print(t);
            printPreOrder(t.l);
            printPreOrder(t.r);
        }
    }

    public static void print() {
        printPreOrder(root);
        System.out.println();
    }

    public static Long sum(Node t) {
        if (t == null) return 0L;
        return t.value + sum(t.l) + sum(t.r);
    }

    public static Long sum2(Node head) {
        Long mySum, leftSum, rightSum;

        if (head == null)
            return 0L;
        else {
            leftSum = sum2(head.l);      // Solve smaller problem 1
            rightSum = sum2(head.r);     // Solve smaller problem 2
            return head.value + leftSum + rightSum;     // Return solution
        }
    }

    public static Node mergeWithRoot(Node v1, Node v2, Node t) {
        t.l = v1;
        t.r = v2;
        v1.p = t;
        v2.p = t;
        return t;
    }

    public static Node v2, v3 = null;
    public static Node split(Node v, int k) {
        if (v == null) return null;
        if (k < v.key)
        {
            v2 = split(v.l, k);
            v3 = mergeWithRoot(v2, v.r, v);
        } else {
            v2 = split(v.r, k);
            v3 = mergeWithRoot(v2, v.l, v);
        }
        return v3;
    }

    public static void setChild(Node node, boolean toLeft, Node child){
        if (toLeft) {
            node.l = child;
        } else {
            node.r = child;
        }
    }

    public static Node splitTree(Node root, int k){
        Node root2 = null;
        Node parent1 = null;
        Node parent2 = null;
        // Determine at which side of the root we will travel
        boolean toLeft = root != null && k < root.key;

        while (root != null) {
            while (root != null && (k < root.key) == toLeft) {
                parent1 = root;
                root = toLeft ? root.l : root.r;
            }
            setChild(parent1, toLeft, null); // Cut out the edge. root is now detached
            toLeft = !toLeft; // toggle direction
            if (root2 == null) {
                root2 = root; // This is the root of the other tree.
            } else if (parent2 != null) {
                setChild(parent2, toLeft, root); // re-attach the detached subtree
            }
            parent2 = parent1;
            // поддерживаем пересчет суммы
            parent2.sum = parent2.value + (parent2.l == null ? 0 : parent2.l.sum) + (parent2.r == null ? 0 : parent2.r.sum);
            parent1 = null;
        }
        // поддерживаем пересчет суммы
        //root2.sum = root2.value + (root2.l == null ? 0 : root2.l.sum) + (root2.r == null ? 0 : root2.r.sum);
        return root2;
    }

    public static Node merge (Node v1, Node v2)
    {
        Node t = maximum(v1);
        remove(t, t.key);
        mergeWithRoot(v1, v2, t);
        return t;
    }

    /**
     * Returns all keys in the symbol table in the given range,
     * as an {@code Iterable}.
     *
     * @param  lo minimum endpoint
     * @param  hi maximum endpoint
     * @return all keys in the symbol table between {@code lo}
     *         (inclusive) and {@code hi} (inclusive)
     * @throws IllegalArgumentException if either {@code lo} or {@code hi}
     *         is {@code null}
     */
    public static long summa = 0L;
    public static Iterable<Integer> keys(Integer lo, Integer hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");

        Queue<Integer> queue = new LinkedList<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private static void keys(Node x, Queue<Integer> queue, Integer lo, Integer hi) {
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.l, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) {queue.add(x.key); summa += x.value;}
        if (cmphi > 0) keys(x.r, queue, lo, hi);
    }
    // Usage example
    public static void main(String[] args) {
        //BinarySearchTree tree = new BinarySearchTree();
        insert(3, 3);
        insert(2, 2);
        insert(4, 4);
        insert(1, 1);
        insert(0, 0);
        insert(8, 8);
        insert(5, 5);
        insert(10, 10);
        //keys(2,4);
        //System.out.println(summa);
        //split(root, 2);
        //root.print();
        Node root2 = splitTree(root, 2);
        // Output the resulting trees
        root.print();
        root2.print();
        //Node root3 = splitTree(root, 2);
        /*System.out.println(sum(root));
        System.out.println(sum(root2));*/
        //keys(2,4).forEach(System.out::println);
        Node d = merge(root2, root);
print(d);
        /*tree.print();
        System.out.println(tree.sum(tree.root));
        tree.remove(8);
        tree.print();
        System.out.println(tree.sum(tree.root));
        tree.remove(0);
        tree.print();
        System.out.println(tree.sum(tree.root));*/
        /*System.out.println(tree.sum2(tree.root));
        System.out.println(tree.search(2));
        System.out.println("min = " + tree.minimum(tree.root));
        System.out.println("max = " + tree.maximum(tree.root));
        tree.remove(2);
        tree.remove(3);
        tree.print();
        tree.remove(4);
        tree.insert(4, 100);
        tree.print();*/
    }
}