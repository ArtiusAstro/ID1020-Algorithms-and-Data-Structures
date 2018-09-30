import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Q3 {

    public static void main(String args[]) throws FileNotFoundException {
        System.out.println("--------------------\nQ3\n--------------------");

        int n=0,x=0;
        try(Scanner sc = new Scanner(System.in)){
            System.out.println("Input n for nth to n+xth most frequent words (n=1 most frequent): ");
            n = sc.nextInt();
            System.out.println("Input x: ");
            x = sc.nextInt();
        }
        catch (InputMismatchException e){
            e.printStackTrace();
        }

        LinkedArrayST lst = new LinkedArrayST(ArrayST.fullAST());

        if(n>0 && x>0)
            lst.frequencyRange(n,x);
    }
}

/**
 * An ArrayST where the values are Linked Lists
 *
 * @author Ayub Atif
 */
class LinkedArrayST {
    private int[] keys;
    private FIQueue<String>[] LLCircles;
    private int N;
    LinkedArrayST(ArrayST ast) {
        keys = new int[24];
        LLCircles = new FIQueue[24];
        for (String word : ast.keys()) {
            if (word == null) break;
            put(ast.get(word), word);
        }
    }

    LinkedArrayST() {
        keys = new int[24];
        LLCircles = new FIQueue[24];
    }

    /**
     * Double key and value arrays
     */
    private void xDouble(){
        int[] keysClone = new int[keys.length*2];
        FIQueue<String>[] circlesClone = new FIQueue[LLCircles.length*2];
        int i = 0;

        while(i<keys.length){
            keysClone[i] = keys[i];
            circlesClone[i] = LLCircles[i++];
        }

        keys = keysClone;
        LLCircles = circlesClone;
    }

    public int size() { return N; }

    public int rank(int key) {
        int lo = 0, hi = N-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key<keys[mid]) hi = mid - 1;
            else if (key>keys[mid]) lo = mid + 1;
            else return mid;
        }
        return lo;
    }

    public int[] keys() {
        return this.keys;
    }

    public FIQueue get(int key){
        if (this.size() == 0) return null;
        int i = rank(key);
        if ((i < N) && (keys[i]==key)) return LLCircles[i];
        else return null;
    }

    public void put(int key, String word) {
        int i = rank(key);
        if (i<N && keys[i]==key) { // Found
            LLCircles[i].addFirst(word);
            return;
        }
        for (int j = N; j > i; j--) { // New
            keys[j] = keys[j-1];
            LLCircles[j] = LLCircles[j-1];
        }
        keys[i] = key; LLCircles[i]=new FIQueue<>(); LLCircles[i].addFirst(word);
        N++;
        if(N==keys.length)    xDouble();
    }

    public boolean contains(int frequency) {
        return this.get(frequency) != null;
    }

    public void frequencyRange(int n, int x) {
        int i=0;
        while(i<=x)
            System.out.println(n+i+": "+LLCircles[N-n-i]+" "+keys[N-n-i++]);
    }
}

/**
 *  Implement a generic iterable circular linked list which allows
 *  the user to insert and remove elements to/from the front
 *  and back end of the queue.
 *
 *  @author Ayub Atif
 */

class FIQueue<T> implements Iterable{
    private Node head;
    private int size;

    /**
     * A node holds an item and info on prev or next
     *
     * @author Ayub Atif
     */
    private class Node {
        T item;
        Node next;

        Node(T item) {
            this.item = item;
        }
    }

    public FIQueue(){
        this.size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * Adds item at end of list
     *
     * @param item to be added
     */
    public void addFirst(T item) {
        Node node = new Node(item);

        node.next = head;
        head = node;
        size++;
    }

    /**
     * Iterates starting from head and until you loop back to head
     *
     * @return iterator that goes from head to tail
     */
    @Override
    public Iterator<T> iterator() {
        return new CircularIterator();
    }


    private class CircularIterator implements Iterator<T>{
        private Node current = head;
        int i = 0;

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public T next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            T item = current.item;
            current = current.next;
            i++;
            return item;
        }
    }

    /**
     * Returns a string representation of this list.
     *
     * @return the sequence of items in this list from head to tail
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = this.size-1;
        if(!this.isEmpty()) {
            for (Object item : this) {
                s.append('[').append(item).append("]");
                if(i-- > 0) {
                    s.append(", ");
                }
            }
        }
        else  s.append("[ ]");
        return s.toString();
    }
}

/**
 * a list of methods used by all ST that use <String,Integer>
 *
 * @author Ayub Atif
 */
abstract class ST{

    public abstract int get(String key);

    public abstract void put(String key, int val);

    public abstract boolean contains(String word);

    public static ST fillST(ST st) throws FileNotFoundException {
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
}

/**
 * Array that implements binary search
 * Used to fill the Linked Array ST
 *
 * @author Ayub Atif
 */
class ArrayST extends ST{
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

    @Override
    public int get(String key){
        if (this.size() == 0) return -1;
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0) return vals[i];
        else return -1;
    }

    @Override
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

    @Override
    public boolean contains(String key) {
        return this.get(key) != -1;
    }

    public static ArrayST fullAST() throws FileNotFoundException {
        ArrayST ast = new ArrayST();
        String word;

        Scanner sc = new Scanner(new File("98-0-filtered.txt"));
        while (sc.hasNextLine()) {
            Scanner sc2 = new Scanner(sc.nextLine());
            while (sc2.hasNext()){
                word = sc2.next();
                if (!ast.contains(word)) ast.put(word, 1);
                else ast.put(word, ast.get(word) + 1);
            }
            sc2.close();
        }
        sc.close();

        return ast;
    }
}