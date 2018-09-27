import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Q2{

    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        arraySTest();
        long time = System.currentTimeMillis() - start;
        System.out.println("ArrayST time: "+time+"ms");
        start = System.currentTimeMillis();
        BSTest();
        time = System.currentTimeMillis() - start;
        System.out.println("BST time: "+time+"ms");
    }

    private static void arraySTest() throws FileNotFoundException {
        ArrayST arrayST = new ArrayST();
        String word;

        Scanner sc = new Scanner(new File("98-0-filtered.txt"));
        while (sc.hasNextLine()) {
            Scanner sc2 = new Scanner(sc.nextLine());
            while (sc2.hasNext()){
                word = sc2.next();
                if (!arrayST.contains(word)) arrayST.put(word, 1);
                else arrayST.put(word, arrayST.get(word) + 1);
            }
            sc2.close();
        }
        sc.close();

        // Find a key with the highest frequency count.
        String max = "";
        arrayST.put(max, 0);
        for (String key : arrayST.keys()) {
            if (null == key) break;
            System.out.println(key+": "+arrayST.get(key));
            if (arrayST.get(key) > arrayST.get(max)) max = (String)key;
        }
        System.out.println("MAX: "+max + " " + arrayST.get(max));
    }

    private static void BSTest() throws FileNotFoundException {
        BST<String> bst = new BST<>();
        String word;

        Scanner sc = new Scanner(new File("98-0-filtered.txt"));
        while (sc.hasNextLine()) {
            Scanner sc2 = new Scanner(sc.nextLine());
            while (sc2.hasNext()){
                word = sc2.next();
                if (!bst.contains(word)) bst.put(word, 1);
                else bst.put(word, bst.get(word) + 1);
            }
            sc2.close();
        }
        sc.close();

        // Find a node with the highest frequency count.
        System.out.println("MAX: "+bst.getKey(bst.maxNode(bst.getRoot()))+" "+bst.getVal(bst.maxNode(bst.getRoot())));
    }
}

class ArrayST{
    private String[] keys;
    private int[] vals;
    private int N;
    ArrayST() {
        keys = new String[24];
        vals = new int[24];
    }

    private void xDouble(){
        String[] keysClone = new String[keys.length*2];
        int[] valsClone = new int[vals.length*2];
        int i = 0;

        while(i<keys.length){
            keysClone[i] = keys[i];
            valsClone[i] = vals[i++];
        }

        keys = keysClone;
        vals = valsClone;
    }

    public int size() { return N; }

    public int rank(String key) {
        int lo = 0, hi = N-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp < 0) hi = mid - 1;
            else if (cmp > 0) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    public String[] keys() {
        return this.keys;
    }

    public int get(String key){
        if (this.size() == 0) return -1;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return -1;
    }

    public void put(String key, int val) {
        // Search for key. Update value if found; grow table if new.
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val;
            return;
        }
        for (int j = N; j > i; j--) {
            keys[j] = keys[j-1];
            vals[j] = vals[j-1];
        }
        keys[i] = key; vals[i] = val;
        N++;
        if(N==keys.length)    xDouble();
    }

    public boolean contains(String word) {
        return this.get(word) != -1;
    }
}

class BST<Key extends Comparable<Key>> {
    private Node root; // root of BST

    public Node getRoot(){
        return root;
    }

    public boolean contains(Key key) {
        return get(key)!=-1;
    }

    public int getVal(Node node){
        return node.getVal();
    }

    public Key getKey(Node node){
        return node.getKey();
    }

    private class Node {
        private Key key; // key
        private int val; // associated value
        private Node left, right; // links to subtrees
        private int N; // # nodes in subtree rooted here
        public Node(Key key, int val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
        public int getVal(){
            return this.val;
        }
        public Key getKey(){
            return this.key;
        }
    }
    public int size() {
        return size(root);
    }
    private int size(Node node) {
        if (node == null) return 0;
        else return node.N;
    }

    public int get(Key key) {
        return get(root, key);
    }

    private int get(Node x, Key key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return -1;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, int val) {
        // Search for key. Update value if found; grow table if new.
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, int val) {
        // Change key’s value to val if key in subtree rooted at x.
        // Otherwise, add new node to subtree associating key with val.
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Node maxNode(Node root) {
        if (root == null) return new Node(null, 1, 0);
        Node left = new Node(null, 0, 0);
        Node right = new Node(null, 0, 0);

        if (root.left != null) left = maxNode(root.left);
        if (root.right != null) right = maxNode(root.right);

        if (left.val > right.val) {
            if (left.val > root.val)
                return left;
            return root;
        }
        else
        if(right.val>root.val)
            return right;
        return root;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }
}
