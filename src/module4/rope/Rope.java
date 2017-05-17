package module4.rope;

/**
 * Rope Class
 *
 * @author Elias Haroun
 */

/**
 * Java Implementation of Rope
 *
 * @author Elias Haroun
 */
class RopeNode {
    public RopeNode left;
    public RopeNode right;
    public String data;
    public int weight;


    /**
     * Default Constructor
     */
    public RopeNode() {
        this.left = null;
        this.right = null;
        this.data = null;
        this.weight = 0;
    }


    /**
     * Overloaded Constructor
     *
     * @param data The string to create the Rope with
     */
    public RopeNode(String data) {
        this.left = null;
        this.right = null;
        this.data = data;
        this.weight = data.length();
    }
}

public class Rope {

    private RopeNode root;
    private int length;

    /**
     * Default Constructor
     */
    public Rope() {
        this.root = new RopeNode("");
        this.length = 0;
    }

    /**
     * Overloaded Constructor
     *
     * @param initial string
     */
    public Rope(String str) {
        this.root = new RopeNode("");
        this.concat(str);
        this.length = str.length();
    }

    /**
     * This function clears rope
     */
    public void makeEmpty() {
        this.root = new RopeNode("");
        this.length = 0;
    }

    /**
     * This function returns the length of Rope
     */
    public int length() {
        return this.length;
    }

    /**
     * This function concatenates an element
     *
     * @param string to append to existing string
     */
    public void concat(String str) {
        RopeNode newRopeNode = new RopeNode(str);
        RopeNode newRoot = new RopeNode();
        newRoot.left = root;
        newRoot.right = newRopeNode; //RopeNodes containing a string are always on the right
        newRoot.weight = newRoot.left.weight; //Weight of the root comes from its left children
        if (newRoot.left.right != null) {
            newRoot.weight += newRoot.left.right.weight;
        }
        this.root = newRoot;
        length += str.length();
    }

    /**
     * This function returns the character at a particular index
     *
     * @param index of desired character
     */
    public char charAt(int index) {
        RopeNode temp = this.root;
        if (index > temp.weight) {
            index -= temp.weight;
            return temp.right.data.charAt(index);
        }

        while (index < temp.weight) {
            temp = temp.left;// Go to left child
        }
        index -= temp.weight;
        return temp.right.data.charAt(index);
    }

    /**
     * This function returns the substring between two indices
     *
     * @param start index
     * @param end   index
     */
    public String substring(int start, int end) {
        String str = "";
        boolean found = false;
        RopeNode temp = this.root;
        if (end > temp.weight) {
            found = true;
            end -= temp.weight;
            if (start > temp.weight) {
                start -= temp.weight;
                str = temp.right.data.substring(start, end);
                return str;
            } else {
                str = temp.right.data.substring(0, end);
            }
        }
        if (!found) {
            while (end <= temp.weight) {
                temp = temp.left;// Go to left child
            }
            if (start > temp.weight) {
                start -= temp.weight;
                str = temp.right.data.substring(start, end);
                return str;
            } else {
                str = temp.right.data.substring(0, end);
            }
        }
        temp = temp.left;
        while (start < temp.weight) {
            str = temp.right.data + str;// Concat as you go down the tree
            temp = temp.left;
        }
        start -= temp.weight;
        if (temp.right != null) {
            str = temp.right.data.substring(start) + str;
        }
        return str;
    }

    /**
     * This function returns the substring between two indices
     *
     * @param start index
     */
    public String substring(int start) {
        return this.substring(start, length);
    }

    /**
     * This function prints a Rope
     */
    public void print() {
        print(this.root);
        System.out.println();
    }

    // Function only accessible from the public print() method
    private void print(RopeNode r) {
        if (r != null) // In-order traversal
        {
            print(r.left);
            if (r.data != null) {
                System.out.print(r.data);
            }
            print(r.right);
        }
    }

    public static void main(String[] args) {
        Rope rope = new Rope("abcdef");
        rope.concat("ttt");
        rope.print();
    }
}