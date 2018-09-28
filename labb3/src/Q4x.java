import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Q4x {

    private static ST fillST(ST st) throws FileNotFoundException {
        String word;
        Scanner sc = new Scanner(new File("98-0-filtered.txt"));
        while (sc.hasNextLine()) {
            Scanner sc2 = new Scanner(sc.nextLine());
            while (sc2.hasNext()){
                word = sc2.next();
                if (!st.contains(word)) st.put(word, 1);
                else st.put(word, st.get(word) + 1);
            }
            sc2.close();
        }
        sc.close();

        return st;
    }

    private static void BSTest(BST bst){
        // Find a node with the highest frequency count.
        System.out.println("MAX: "+bst.getKey(bst.maxNode(bst.getRoot()))+" "+bst.getNodeVal(bst.maxNode(bst.getRoot())));
    }

    private static void RBBTest(RedBlackBST rbb){
        // Find a key with the highest frequency count.
        System.out.println("MAX: "+rbb.get(rbb.maxNode(rbb.getRoot()))+" "+rbb.get(rbb.maxNode(rbb.getRoot())));
    }

    public static void main(String[] args) throws FileNotFoundException {
        RedBlackBST rbb = new RedBlackBST();
        BST bst = new BST();
        rbb = (RedBlackBST) fillST(rbb);
        bst = (BST) fillST(bst);

        long start = System.currentTimeMillis();
        RBBTest(rbb);
        long time = System.currentTimeMillis() - start;
        System.out.println("RedBlackBST time: "+time+"ms");
        start = System.currentTimeMillis();
        BSTest(bst);
        time = System.currentTimeMillis() - start;
        System.out.println("BST time: "+time+"ms");
    }
}

class RedBlackBST<Key extends Comparable<Key>> extends ST {
    private static final boolean RED = true;
    private static final boolean BLACK = false;
    private Node root;

    public Node getRoot() {
        return root;
    }

    @Override
    public int get(String key) {
        return get(root, key);
    }

    private int get(Node x, String key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return -1;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;

        if (x == null) // Do standard insert, with red link to parent.
            return new Node(key, val, 1, RED);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return  get(x.right, key);
        return x;
    }

    @Override
    public void put(Key key, int val) {
        // Search for key. Update value if found; grow table if new.
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, int val) {
        if (h == null) // Do standard insert, with red link to parent.
            return new Node(key, val, 1, RED);
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    @Override
    public boolean contains(String key) {
        return get(key)!=-1;
    }

    public class Node {
        Key key; // key
        int val; // associated data
        Node left, right; // subtrees
        int N; // # nodes in this subtree
        boolean color; // color of link from
        // parent to this node
        Node(Key key, int val, int N, boolean color)
        {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == RED;
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left)
                + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = 1 + size(h.left)
                + size(h.right);
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    public int size() {
        return size(root);
    }
    private int size(Node x) {
        return (x == null) ? 0 : x.N;
    }

    public Node maxNode(Node root){
        if (root == null) return new Node(null, 0,0, BLACK);
        Node left = new Node(null, 0,0, BLACK);
        Node right = new Node(null, 0,0, BLACK);

        if (root.left != null) left = maxNode(root.left);
        if (root.right != null) right = maxNode(root.right);

        if (left.val>right.val) {
            if (left.val>root.val)
                return left;
            return root;
        }
        return (right.val>root.val) ? right : root;
    }
}