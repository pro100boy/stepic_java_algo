package module4.setwithsum;

import java.util.Scanner;

class Main_other {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int numberOfRequests = sc.nextInt();
            SplayTreeJava splayTree = new SplayTreeJava();
            long s = 0;
            for (int i = 0; i < numberOfRequests; i++) {
                String request = sc.next();
                switch (request) {
                    case "+":
                        int addNext = sc.nextInt();
                        int addNumber = f(addNext, s);
                        splayTree.insert(addNumber);
                        break;
                    case "-":
                        int removeNext = sc.nextInt();
                        int removeNumber = f(removeNext, s);
                        splayTree.remove(removeNumber);
                        break;
                    case "?":
                        int elem = sc.nextInt();
                        System.out.println(splayTree.exists(f(elem, s)) ? "Found" : "Not found");
                        break;
                    default:
                        int l = sc.nextInt();
                        int r = sc.nextInt();
                        int from = f(l, s);
                        int to = f(r, s);
                        long sum = splayTree.sumFromTo(from, to);
                        System.out.println(sum);
                        s = sum;
                }
            }
        }
    }

    private static int f(int x, long s) {
        long longSum = x + s;
        return (int)((longSum) % 1_000_000_001);
    }

    public static class SplayTreeJava {
        private Node root;

        public class Node {
            private Node left;
            private Node right;
            private Node parent;
            private int value;
            private long sum;

            private void reSum() {
                long leftSum = left != null ? left.sum : 0L;
                long rightSum = right != null ? right.sum : 0L;
                sum = leftSum + rightSum + value;
            }

            private Node(int value) {
                this.value = value;
                reSum();
            }

            private void setLeft(Node left) {
                this.left = left;
                reSum();
            }

            private void setRight(Node right) {
                this.right = right;
                reSum();
            }

            private void setParent(Node parent) {
                this.parent = parent;
                if (parent != null) {
                    this.parent.reSum();
                }
            }

            public int getValue() {
                return value;
            }

            public long getSum() {
                return sum;
            }
        }

        public void remove(int k) {
            Node removeNode = search(k);
            if (removeNode == null) {
                return;
            }
            if (removeNode.value == k) {
                splay(removeNode);

                if (root.left == null && root.right == null) {
                    root = null;
                } else if (root.left != null && root.right != null) {
                    Node left = root.left;
                    left.setParent(null);
                    root.right.setParent(null);
                    root = root.right;
                    mergeLeft(left);
                } else if (root.left == null) {
                    root = root.right;
                    root.setParent(null);
                } else {
                    root = root.left;
                    root.setParent(null);
                }
            }
        }

        public long sumFromTo(int from, int to) {
            if (root == null) {
                return 0L;
            }
            if (from > to) {
                throw new RuntimeException("FROM can not be more then TO");
            }
            SplayTreeJava.Node[] left = splitFromTo(from, to);
            long sum = root == null ? 0L : root.sum;
            merge(left[0], left[2]);
            return sum;
        }

        public void merge(Node left, Node right) {
            if (left != null)
                mergeLeft(left);
            if (right != null)
                mergeRight(right);
        }

        private void mergeLeft(Node v1) {
            if (root == null) {
                root = v1;
                return;
            }
            min(root);
            root.setLeft(v1);
            v1.setParent(root);
        }

        private void mergeRight(Node v2) {
            if (root == null) {
                root = v2;
                return;
            }
            max(root);
            root.setRight(v2);
            v2.setParent(root);
        }

        /**
         *
         * @param from
         * @param to
         * @return Node[0] is the left part, Node[1] is the root and Node[2] is the right part.
         */
        public Node[] splitFromTo(int from, int to) {
            Node left = splitLeft(from);
            Node right = splitRight(to);
            return new Node[]{left, root, right};
        }

        /**
         *
         * @param from
         * @return Node[0] is the left part and Node[1] is the root.
         */
        public Node[] splitFrom(int from) {
            Node left = splitLeft(from);
            return new Node[]{left, root};
        }

        /**
         *
         * @param to
         * @return Node[0] is the root and Node[1] is the right part.
         */
        public Node[] splitTo(int to) {
            Node right = splitLeft(to);
            return new Node[]{root, right};
        }

        private Node splitLeft(int k) {
            Node splitLeftNode = search(k);
            if (splitLeftNode == null) {
                return null;
            }
            if (splitLeftNode.value >= k) {
                splay(splitLeftNode);
                //if there is no root.left
                if (root.left == null) {
                    return null;
                }
                Node left = root.left;
                root.setLeft(null);
                left.setParent(null);
                return left;
            } else {
                splay(splitLeftNode);
                if (root.right == null) {
                    Node splitRoot = root;
                    root = null;
                    return splitRoot;
                }
                min(root.right);
                Node left = root.left;
                root.setLeft(null);
                left.setParent(null);
                return left;
            }
        }

        private Node splitRight(int k) {
            Node splitLeftNode = search(k);
            if (splitLeftNode == null) {
                return null;
            }
            if (splitLeftNode.value <= k) {
                splay(splitLeftNode);
                //if there is no root.left
                if (root.right == null) {
                    return null;
                }
                Node right = root.right;
                root.setRight(null);
                right.setParent(null);
                return right;
            } else {
                splay(splitLeftNode);
                if (root.left == null) {
                    Node splitRoot = root;
                    root = null;
                    return splitRoot;
                }
                max(root.left);
                Node right = root.right;
                root.setRight(null);
                right.setParent(null);
                return right;
            }
        }

        public void insert(int k) {
            Node insertNode = search(k);
            //if root == null
            if (insertNode == null) {
                root = new Node(k);
                return;
            }
            if (insertNode.value == k) {
                splay(insertNode);
                return;
            }
            if (insertNode.value < k) {
                insertNode.setRight(new Node(k));
                insertNode.right.setParent(insertNode);
                splay(insertNode.right);
            } else {
                insertNode.setLeft(new Node(k));
                insertNode.left.setParent(insertNode);
                splay(insertNode.left);
            }
        }

        public boolean exists(int k) {
            Node result = search(k);
            splay(result);
            return result != null && result.value == k;
        }

        public Node search(int k) {
            if (root == null) {
                return null;
            }
            Node findingNode = root;
            while (findingNode.value != k) {
                if (findingNode.value > k) {
                    if (findingNode.left == null) {
                        break;
                    }
                    findingNode = findingNode.left;
                } else {
                    if (findingNode.right == null) {
                        break;
                    }
                    findingNode = findingNode.right;
                }
            }
            return findingNode;
        }

        public void min(Node node) {
            if (node == null) {
                return;
            }
            Node min = node;
            while (min.left != null) {
                min = min.left;
            }
            splay(min);
        }

        public void max(Node node) {
            if (node == null) {
                return;
            }
            Node max = node;
            while (max.right != null) {
                max = max.right;
            }
            splay(max);
        }

        private void splay(Node u) {
            if (u == null) {
                return;
            }
            while (u.parent != null) {
                //if it's a left child
                if (u.parent.left == u) {
                    // if there's a grandfather
                    // u.parent != root
                    if (u.parent.parent != null) {
                        // if its father is the grandfather's left child
                        // ZIGZIG
                        if (u.parent.parent.left == u.parent) {
                            Node uRight = u.right;
                            Node p = u.parent;
                            Node pRight = u.parent.right;
                            Node gp = u.parent.parent;
                            Node gpParent = u.parent.parent.parent;
                            boolean gpIsLeft = u.parent.parent.parent != null
                                    && u.parent.parent.parent.left == u.parent.parent;

                            //disjunction
                            if (uRight != null) {
                                u.setRight(null);
                                uRight.setParent(null);
                            }

                            u.setParent(null);
                            p.setLeft(null);

                            if (pRight != null) {
                                pRight.setParent(null);
                                p.setRight(null);
                            }

                            p.setParent(null);
                            gp.setLeft(null);

                            //conjunction
                            if (pRight != null) {
                                gp.setLeft(pRight);
                                pRight.setParent(gp);
                            }

                            if (uRight != null) {
                                p.setLeft(uRight);
                                uRight.setParent(p);
                            }

                            p.setRight(gp);
                            gp.setParent(p);

                            u.setRight(p);
                            p.setParent(u);

                            if (gpParent != null) {
                                u.setParent(gpParent);
                                if (gpIsLeft) {
                                    gpParent.setLeft(u);
                                } else {
                                    gpParent.setRight(u);
                                }
                            } else {
                                u.setParent(null);
                            }
                        } else { //its father is the grandfather's right child //ZIGZAG
                            Node uLeft = u.left;
                            Node uRight = u.right;
                            Node p = u.parent;
                            Node gp = u.parent.parent;

                            Node gpParent = u.parent.parent.parent;
                            boolean gpIsRight = u.parent.parent.parent != null
                                    && u.parent.parent.parent.right == u.parent.parent;

                            //disjunction
                            if (uLeft != null) {
                                uLeft.setParent(null);
                                u.setLeft(null);
                            }

                            if (uRight != null) {
                                uRight.setParent(null);
                                u.setRight(null);
                            }

                            u.setParent(null);
                            p.setLeft(null);
                            p.setParent(null);
                            gp.setRight(null);

                            //conjunction
                            if (uLeft != null) {
                                gp.setRight(uLeft);
                                uLeft.setParent(gp);
                            }

                            if (uRight != null) {
                                p.setLeft(uRight);
                                uRight.setParent(p);
                            }

                            u.setLeft(gp);
                            gp.setParent(u);

                            u.setRight(p);
                            p.setParent(u);

                            if (gpParent != null) {
                                u.setParent(gpParent);
                                if (gpIsRight) {
                                    gpParent.setRight(u);
                                } else {
                                    gpParent.setLeft(u);
                                }
                            } else {
                                u.setParent(null);
                            }
                        }
                    } else { //u.parent == root      ZIG
                        Node uRight = u.right;
                        Node p = u.parent;

                        if (uRight != null) {
                            u.setRight(null);
                            uRight.setParent(null);
                        }

                        u.setParent(null);
                        p.setLeft(null);

                        if (uRight != null) {
                            p.setLeft(uRight);
                            uRight.setParent(p);
                        }

                        u.setRight(p);
                        p.setParent(u);
                    }

                } else { // if it's a right child

                    // if there's a grandfather
                    // u.parent != root
                    if (u.parent.parent != null) {
                        // if its father is the grandfather's right child
                        // ZAGZAG
                        if (u.parent.parent.right == u.parent) {
                            Node uLeft = u.left;
                            Node p = u.parent;
                            Node pLeft = u.parent.left;
                            Node gp = u.parent.parent;
                            Node gpParent = u.parent.parent.parent;
                            boolean gpIsRight = u.parent.parent.parent != null
                                    && u.parent.parent.parent.right == u.parent.parent;

                            //disjunction
                            if (uLeft != null) {
                                u.setLeft(null);
                                uLeft.setParent(null);
                            }

                            u.setParent(null);
                            p.setRight(null);

                            if (pLeft != null) {
                                pLeft.setParent(null);
                                p.setLeft(null);
                            }

                            p.setParent(null);
                            gp.setRight(null);

                            //conjunction
                            if (pLeft != null) {
                                gp.setRight(pLeft);
                                pLeft.setParent(gp);
                            }

                            if (uLeft != null) {
                                p.setRight(uLeft);
                                uLeft.setParent(p);
                            }

                            p.setLeft(gp);
                            gp.setParent(p);

                            u.setLeft(p);
                            p.setParent(u);

                            if (gpParent != null) {
                                u.setParent(gpParent);
                                if (gpIsRight) {
                                    gpParent.setRight(u);
                                } else {
                                    gpParent.setLeft(u);
                                }
                            } else {
                                u.setParent(null);
                            }
                        } else { //its father is the grandfather's left child //ZAGZIG
                            Node uLeft = u.left;
                            Node uRight = u.right;
                            Node p = u.parent;
                            Node gp = u.parent.parent;

                            Node gpParent = u.parent.parent.parent;
                            boolean gpIsLeft = u.parent.parent.parent != null
                                    && u.parent.parent.parent.left == u.parent.parent;

                            //disjunction
                            if (uRight != null) {
                                uRight.setParent(null);
                                u.setRight(null);
                            }


                            if (uLeft != null) {
                                uLeft.setParent(null);
                                u.setLeft(null);
                            }

                            u.setParent(null);
                            p.setRight(null);
                            p.setParent(null);
                            gp.setLeft(null);

                            //conjunction
                            if (uRight != null) {
                                gp.setLeft(uRight);
                                uRight.setParent(gp);
                            }


                            if (uLeft != null) {
                                p.setRight(uLeft);
                                uLeft.setParent(p);
                            }

                            u.setRight(gp);
                            gp.setParent(u);

                            u.setLeft(p);
                            p.setParent(u);

                            if (gpParent != null) {
                                u.setParent(gpParent);
                                if (gpIsLeft) {
                                    gpParent.setLeft(u);
                                } else {
                                    gpParent.setRight(u);
                                }
                            } else {
                                u.setParent(null);
                            }
                        }
                    } else { //u.parent == root      ZAG
                        Node uLeft = u.left;
                        Node p = u.parent;

                        if (uLeft != null) {
                            u.setLeft(null);
                            uLeft.setParent(null);
                        }

                        u.setParent(null);
                        p.setRight(null);

                        if (uLeft != null) {
                            p.setRight(uLeft);
                            uLeft.setParent(p);
                        }

                        u.setLeft(p);
                        p.setParent(u);
                    }
                }
            }
            root = u;
        }

        public void inorder() {
            inorder(root);
        }

        public void inorder(Node node) {
            if (node != null) {
                inorder(node.left);
//            System.out.print(node.value + "(" +  node.sum + ")" +" ");
                System.out.print(node.value + "(" +  (node.left == null ? "null" : node.left.value)
                        + ", " + (node.right == null ? "null" : node.right.value)
                        + ", " + (node.parent == null ? "null" : node.parent.value) + ")" + " ");

                inorder(node.right);
            }
        }

        public Node getRoot() {
            return root;
        }
    }
}