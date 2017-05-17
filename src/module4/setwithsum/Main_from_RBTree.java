package module4.setwithsum;
// Взял исходники красно-чёрных деревьев из "java.util.TreeMap" и доработал для получения суммы на отрезке.
import java.io.PrintStream;
import java.util.Scanner;

public class Main_from_RBTree {

    public static class Tree {

        private transient Node root;

        // Query Operations

        public boolean containsKey(Integer key) {
            return getEntry(key) != null;
        }

        public Long getSum(Integer from, Integer to) {
            return (root != null) ? root.getSum(from, to) : 0L;
        }

        final Node getEntry(Integer key) {
            if (key == null)
                throw new NullPointerException();
            Node p = root;
            while (p != null) {
                int cmp = key.compareTo(p.key);
                if (cmp < 0)
                    p = p.left;
                else if (cmp > 0)
                    p = p.right;
                else
                    return p;
            }
            return null;
        }

        public void put(Integer key) {
            Node t = root;
            if (t == null) {
                root = new Node(key, null);
                return;
            }
            int cmp;
            Node parent;
            if (key == null)
                throw new NullPointerException();
            do {
                parent = t;
                cmp = key.compareTo(t.key);
                if (cmp < 0)
                    t = t.left;
                else if (cmp > 0)
                    t = t.right;
                else
                    return;
            } while (t != null);
            Node e = new Node(key, parent);
            if (cmp < 0)
                parent.left = e;
            else
                parent.right = e;
            fixAfterInsertion(e);
        }

        public void remove(Integer key) {
            Node p = getEntry(key);
            if (p == null)
                return;

            deleteEntry(p);
        }

        // Red-black mechanics

        private static final boolean RED = false;
        private static final boolean BLACK = true;

        static final class Node {
            Integer key;
            Node left;
            Node right;
            Node parent;
            long sum;
            boolean color = BLACK;

            Node(Integer key, Node parent) {
                sum = key;
                this.key = key;
                this.parent = parent;

                while (parent != null) {
                    parent.sum += key;
                    parent = parent.parent;
                }
            }

            public Integer getKey() {
                return key;
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Node))
                    return false;
                Node e = (Node) o;

                return (key == e.getKey()) || (key != null && key.equals(e.getKey()));
            }

            @Override
            public int hashCode() {
                return (key == null ? 0 : key.hashCode());
            }

            @Override
            public String toString() {
                return key + " (" + sum + ")";
            }

            private Long getFromSum(Integer from) {
                int cmp = from.compareTo(key);
                if (cmp < 0) {
                    if (left != null) {
                        return left.getFromSum(from) + sum - left.sum;
                    } else
                        return sum;
                } else {
                    if (right != null) {
                        return right.getFromSum(from);
                    } else
                        return 0L;
                }
            }

            private Long getToSum(Integer to) {
                int cmp = key.compareTo(to);
                if (cmp < 0) {
                    if (right != null) {
                        return right.getToSum(to) + sum - right.sum;
                    } else
                        return sum;
                } else {
                    if (left != null) {
                        return left.getToSum(to);
                    } else
                        return 0L;
                }
            }

            public Long getSum(Integer from, Integer to) {
                return sum - getFromSum(to) - getToSum(from);
            }
        }

        private static boolean colorOf(Node p) {
            return (p == null ? BLACK : p.color);
        }

        private static Node parentOf(Node p) {
            return (p == null ? null : p.parent);
        }

        private static void setColor(Node p, boolean c) {
            if (p != null)
                p.color = c;
        }

        private static Node leftOf(Node p) {
            return (p == null) ? null : p.left;
        }

        private static Node rightOf(Node p) {
            return (p == null) ? null : p.right;
        }

        private void rotateLeft(Node p) {
            if (p != null) {
                Node r = p.right;
                Node l = r.left;

                Long ps = p.sum;
                p.sum -= r.sum;
                r.sum = ps;

                p.right = l;
                if (l != null) {
                    l.parent = p;
                    p.sum += l.sum;
                }
                r.parent = p.parent;
                if (p.parent == null)
                    root = r;
                else if (p.parent.left == p)
                    p.parent.left = r;
                else
                    p.parent.right = r;
                r.left = p;
                p.parent = r;
            }
        }

        private void rotateRight(Node p) {
            if (p != null) {
                Node l = p.left;
                Node r = l.right;

                Long ps = p.sum;
                p.sum -= l.sum;
                l.sum = ps;

                p.left = r;
                if (r != null) {
                    r.parent = p;
                    p.sum += r.sum;
                }
                l.parent = p.parent;
                if (p.parent == null)
                    root = l;
                else if (p.parent.right == p)
                    p.parent.right = l;
                else
                    p.parent.left = l;
                l.right = p;
                p.parent = l;
            }
        }

        private void fixAfterInsertion(Node x) {
            x.color = RED;

            while (x != null && x != root && x.parent.color == RED) {
                if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
                    Node y = rightOf(parentOf(parentOf(x)));
                    if (colorOf(y) == RED) {
                        setColor(parentOf(x), BLACK);
                        setColor(y, BLACK);
                        setColor(parentOf(parentOf(x)), RED);
                        x = parentOf(parentOf(x));
                    } else {
                        if (x == rightOf(parentOf(x))) {
                            x = parentOf(x);
                            rotateLeft(x);
                        }
                        setColor(parentOf(x), BLACK);
                        setColor(parentOf(parentOf(x)), RED);
                        rotateRight(parentOf(parentOf(x)));
                    }
                } else {
                    Node y = leftOf(parentOf(parentOf(x)));
                    if (colorOf(y) == RED) {
                        setColor(parentOf(x), BLACK);
                        setColor(y, BLACK);
                        setColor(parentOf(parentOf(x)), RED);
                        x = parentOf(parentOf(x));
                    } else {
                        if (x == leftOf(parentOf(x))) {
                            x = parentOf(x);
                            rotateRight(x);
                        }
                        setColor(parentOf(x), BLACK);
                        setColor(parentOf(parentOf(x)), RED);
                        rotateLeft(parentOf(parentOf(x)));
                    }
                }
            }
            root.color = BLACK;
        }

        public static Node successor(Node t) {
            if (t == null)
                return null;
            else if (t.right != null) {
                Node p = t.right;
                while (p.left != null)
                    p = p.left;
                return p;
            } else {
                Node p = t.parent;
                Node ch = t;
                while (p != null && ch == p.right) {
                    ch = p;
                    p = p.parent;
                }
                return p;
            }
        }

        private void deleteEntry(Node p) {
            // If strictly internal, copy successor's element to p and then make
            // p
            // point to successor.
            if (p.left != null && p.right != null) {
                Node s = successor(p);

                Integer ps = s.key - p.key;
                p.sum += ps;
                Node pp = p.parent;
                while (pp != null) {
                    pp.sum += ps;
                    pp = pp.parent;
                }

                p.key = s.key;
                p = s;
            } // p has 2 children

            // Start fixup at replacement node, if it exists.
            Node replacement = (p.left != null ? p.left : p.right);

            if (replacement != null) {
                Node pp = p.parent;
                while (pp != null) {
                    pp.sum -= p.key;
                    pp = pp.parent;
                }

                // Link replacement to parent
                replacement.parent = p.parent;
                if (p.parent == null)
                    root = replacement;
                else if (p == p.parent.left)
                    p.parent.left = replacement;
                else
                    p.parent.right = replacement;

                // Null out links so they are OK to use by fixAfterDeletion.
                p.left = p.right = p.parent = null;

                // Fix replacement
                if (p.color == BLACK)
                    fixAfterDeletion(replacement);
            } else if (p.parent == null) { // return if we are the only node.
                root = null;
            } else { // No children. Use self as phantom replacement and unlink.
                if (p.color == BLACK)
                    fixAfterDeletion(p);

                Node pp = p.parent;
                while (pp != null) {
                    pp.sum -= p.key;
                    pp = pp.parent;
                }

                if (p.parent != null) {
                    if (p == p.parent.left)
                        p.parent.left = null;
                    else if (p == p.parent.right)
                        p.parent.right = null;
                    p.parent = null;
                }
            }
        }

        private void fixAfterDeletion(Node x) {
            while (x != root && colorOf(x) == BLACK) {
                if (x == leftOf(parentOf(x))) {
                    Node sib = rightOf(parentOf(x));

                    if (colorOf(sib) == RED) {
                        setColor(sib, BLACK);
                        setColor(parentOf(x), RED);
                        rotateLeft(parentOf(x));
                        sib = rightOf(parentOf(x));
                    }

                    if (colorOf(leftOf(sib)) == BLACK && colorOf(rightOf(sib)) == BLACK) {
                        setColor(sib, RED);
                        x = parentOf(x);
                    } else {
                        if (colorOf(rightOf(sib)) == BLACK) {
                            setColor(leftOf(sib), BLACK);
                            setColor(sib, RED);
                            rotateRight(sib);
                            sib = rightOf(parentOf(x));
                        }
                        setColor(sib, colorOf(parentOf(x)));
                        setColor(parentOf(x), BLACK);
                        setColor(rightOf(sib), BLACK);
                        rotateLeft(parentOf(x));
                        x = root;
                    }
                } else { // symmetric
                    Node sib = leftOf(parentOf(x));

                    if (colorOf(sib) == RED) {
                        setColor(sib, BLACK);
                        setColor(parentOf(x), RED);
                        rotateRight(parentOf(x));
                        sib = leftOf(parentOf(x));
                    }

                    if (colorOf(rightOf(sib)) == BLACK && colorOf(leftOf(sib)) == BLACK) {
                        setColor(sib, RED);
                        x = parentOf(x);
                    } else {
                        if (colorOf(leftOf(sib)) == BLACK) {
                            setColor(rightOf(sib), BLACK);
                            setColor(sib, RED);
                            rotateLeft(sib);
                            sib = leftOf(parentOf(x));
                        }
                        setColor(sib, colorOf(parentOf(x)));
                        setColor(parentOf(x), BLACK);
                        setColor(leftOf(sib), BLACK);
                        rotateRight(parentOf(x));
                        x = root;
                    }
                }
            }

            setColor(x, BLACK);
        }

    }

    private static long s = 0;

    private static int f(int x) {
        return (int) ((x + s) % 1_000_000_001);
    }

    public static void main(String[] args) throws Exception {
        Tree m = new Tree();
        try (Scanner in = new Scanner(System.in); PrintStream out = System.out) {
            int n = in.nextInt();
            while (n-- > 0) {
                switch (in.next()) {
                    case "+":
                        m.put(f(in.nextInt()));
                        break;
                    case "-":
                        m.remove(f(in.nextInt()));
                        break;
                    case "?":
                        out.println(m.containsKey(f(in.nextInt())) ? "Found" : "Not found");
                        break;
                    case "s":
                        s = m.getSum(f(in.nextInt()), f(in.nextInt()));
                        out.println(s);
                        break;
                }
            }
        }
    }

}