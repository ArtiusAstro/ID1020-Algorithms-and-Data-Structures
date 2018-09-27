import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Q2{

    private static void arraySTest() throws FileNotFoundException {
        ArrayST arrayST = new ArrayST<String>();
        String word;

        Scanner sc2 = new Scanner(new File("98-0-filtered.txt"));
        while (sc2.hasNextLine()) {
            Scanner s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()){
                word = s2.next();
                if (!arrayST.contains(word)){
                    System.out.println("!contains "+word);
                    arrayST.put(word, 1);
                }
                else {
                    System.out.println("contains " + word);
                    arrayST.put(word, arrayST.get(word) + 1);
                }
            }
        }

        for(Comparable key : arrayST.keys()){
            if(null==key) break;
            System.out.println(key+": "+arrayST.get(key));
        }

        // Find a key with the highest frequency count.
        Comparable max = "";
        arrayST.put(max, 0);
        for (Comparable key : arrayST.keys()) {
            if (null == key) break;
            if (arrayST.get(key) > arrayST.get(max)) max = key;
        }
        System.out.println("MAX: "+max + " " + arrayST.get(max));
    }

    private static void BSTest() throws FileNotFoundException {
        BST<String,Integer> bst = new BST<String, Integer>();
        String word;

        Scanner sc2 = new Scanner(new File("98-0-filtered.txt"));
        while (sc2.hasNextLine()) {
            Scanner s2 = new Scanner(sc2.nextLine());
            while (s2.hasNext()){
                word = s2.next();
                if (!bst.contains(word)){
                    System.out.println("!contains "+word);
                    bst.put(word, 1);
                }
                else {
                    System.out.println("contains " + word);
                    bst.put(word, bst.get(word) + 1);
                }
            }
        }

        for(Comparable key : bst.keys()){
            if(null==key) break;
            System.out.println(key+": "+bst.get(key));
        }

        // Find a key with the highest frequency count.
        Comparable max = "";
        bst.put(max, 0);
        for (Comparable key : bst.keys()) {
            if (null == key) break;
            if (bst.get(key) > bst.get(max)) max = key;
        }
        System.out.println("MAX: "+max + " " + bst.get(max));
    }

    public static void main(String[] args) throws FileNotFoundException {
        long start = System.currentTimeMillis();
        arraySTest();
        long time = System.currentTimeMillis() - start;
        System.out.println("ArrayST time: "+time);
        start = System.currentTimeMillis();
        BSTest();
        time = System.currentTimeMillis() - start;
        System.out.println("BST time: "+time);
    }
}

class ArrayST<Key extends Comparable<Key>>{
    private Key[] keys;
    private int[] vals;
    private int N;
    ArrayST() {
        keys = (Key[]) new Comparable[60000];
        vals = new int[60000];
    }

    private void xDouble(){
        Key[] keysClone = (Key[]) new Comparable[keys.length*2];
        int[] valsClone = new int[vals.length*2];
        int i = 0;

        while(i<keys.length){
            keysClone[i] = keys[i++];
            valsClone[i] = vals[i++];
        }

        keys = keysClone;
        vals = valsClone;
    }

    public int size() { return N; }

    public int rank(Key key) {
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

    public Key[] keys() {
        return this.keys;
    }

    public int get(Key key){
        if (this.size() == 0) return -1;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return -1;
    }

    public void put(Key key, int val) {
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

    public boolean contains(Key word) {
        return this.get(word) != -1;
    }
}

class BST<Key extends Comparable<Key>, Value> {
    private Node root; // root of BST

    public boolean contains(String word) {
        return false;
    }

    private class Node {
        private Key key; // key
        private Value val; // associated value
        private Node left, right; // links to subtrees
        private int N; // # nodes in subtree rooted here
        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        // Return value associated with key in the subtree rooted at x;
        // return null if key not present in subtree rooted at x.
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    public void put(Key key, Value val) {
        // Search for key. Update value if found; grow table if new.
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
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

    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
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
