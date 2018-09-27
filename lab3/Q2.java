import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Q2{

    public static void main(String[] args) throws FileNotFoundException {
        //int minlen = Integer.parseInt(args[0]); // key-length cutoff
        int minlen = 20;
        ST<String, Integer> st = new ST<String, Integer>(minlen);
        while (!StdIn.isEmpty()){
            // Build symbol table and count frequencies.
            String word = StdIn.readString();
            if (word.length() < minlen) continue; // Ignore short keys.
            if (!st.contains(word)) st.put(word, 1);
            else st.put(word, st.get(word) + 1);
        }
        // Find a key with the highest frequency count.
        String max = "";
        st.put(max, 0);
        for (String word : st.keys())
            if (st.get(word) > st.get(max)) max = word;
        StdOut.println(max + " " + st.get(max));
    }
}

class WordReader{
    String text;

    WordReader(String path) throws FileNotFoundException {
        FileReader fileReader = new FileReader(new File(path));
        this.text =
    }
}

class ST<Key extends Comparable<Key>, Value>
{
    private Key[] keys;
    private Value[] vals;
    private int N;
    ST(int capacity) {
        keys = (Key[]) new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
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

    public Value get(Key key){
        if (this.size() == 0) return null;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return null;
    }

    public void put(Key key, Value val) {
        // Search for key. Update value if found; grow table if new.
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) {
            vals[i] = val; return;
        }
        for (int j = N; j > i; j--) {
            keys[j] = keys[j-1]; vals[j] = vals[j-1];
        }
        keys[i] = key; vals[i] = val;
        N++;
    }

    public void delete(Key key){
        int i = rank(key);
        if(i < N && keys[i].compareTo(key) == 0) {
            // delete i
        }
        for (int j = N; j > i; j--) {
            keys[j] = keys[j-1]; vals[j] = vals[j-1];
        }
        N--;
    }

    public boolean contains(Key word) {
        if(this.get(word) != null) return true;
        else return false;
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
        public Node(Key key, Value val, int N)
        { this.key = key; this.val = val; this.N = N; }
    }
    public int size() {
        return size(root);
    }
    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }
    /*public Value get(Key key){
        return;
    }*/
    // See page 399.
    public void put(Key key, Value val){

    }
    public Key min()
    {
        return min(root).key;
    }
    private Node min(Node x)
    {
        if (x.left == null) return x;
        return min(x.left);
    }
    public Key floor(Key key)
    {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }
    private Node floor(Node x, Key key)
    {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }
}
