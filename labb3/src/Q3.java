import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Q3 {

    private static ArrayST<String> fillAST() throws FileNotFoundException {
        ArrayST<String> ast = new ArrayST<>();
        String word;

        Scanner sc = new Scanner(new File("98-0-filtered.txt"));
        while (sc.hasNextLine()) {
            Scanner sc2 = new Scanner(sc.nextLine());
            while (sc2.hasNext()){
                word = sc2.next();
                if (!ast.contains(word)) ast.put(word, 1);
                else ast.put(word, ast.get(word) + 1);
            }
        }

        return ast;
    }

    public static void main(String args[]) throws FileNotFoundException {
        ArrayST<String> ast = fillAST();
        int n,x;
        try(Scanner sc = new Scanner(System.in)){
            System.out.print("Input [n-x] for nth to n+xth most frequent words (n=1 most frequent): ");
            String input = sc.next();
            n = Character.getNumericValue(input.charAt(0)); x = Character.getNumericValue(input.charAt(2));
        }

        LinkedArrayST lst = new LinkedArrayST(ast);

        if(n>0 && x>0) {
            lst.frequencyRange(n,x);
        }

    }
}

class LinkedArrayST{
    private int[] keys;
    private CircularQueue[] LLCircles;
    private int N;
    LinkedArrayST(ArrayST<String> ast) {
        keys = new int[20];
        LLCircles = new CircularQueue[20];
        for (String word : ast.keys()) {
            if (null == word) break;
            put();
        }
    }

    private void xDouble(){
        int[] keysClone = new int[keys.length*2];
        CircularQueue[] circlesClone = new CircularQueue[LLCircles.length*2];
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

    public CircularQueue get(int key){
        if (this.size() == 0) return null;
        int i = rank(key);
        if (i < N && keys[i]==key) return LLCircles[i];
        else return null;
    }

    public void put(int key, CircularQueue val) {
        // Search for key. Update value if found; grow table if new.
        int i = rank(key);
        if (i < N && keys[i]==key) {
            LLCircles[i] = val;
            return;
        }
        for (int j = N; j > i; j--) {
            keys[j] = keys[j-1];
            LLCircles[j] = LLCircles[j-1];
        }
        keys[i] = key; LLCircles[i] = val;
        N++;
        if(N==keys.length)    xDouble();
    }

    public boolean contains(int frequency) {
        return this.get(frequency) != null;
    }

    public void frequencyRange(int n, int x) {
    }
}

/**
 *  Implement a generic iterable circular linked list which allows
 *  the user to insert and remove elements to/from the front
 *  and back end of the queue.
 *
 *  @author Ayub Atif
 */

class CircularQueue<T> implements Iterable{
    private Node head;
    private Node tail;
    private Node current;
    private int size;

    /**
     * A node holds an item and info on prev or next
     *
     * @author Ayub Atif
     */
    private class Node {
        T item;
        Node next;
        Node prev;

        Node(T item) {
            this.item = item;
        }
    }

    public CircularQueue(){
        this.size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    /**
     * add a node at an index with an item parameter
     *
     * @param index the index to be added to. The most recently added element has index 1;
     * @param item generic item to be added
     * @throws IndexOutOfBoundsException if index is out of bounds
     */
    /*public void add(int index, T item) throws IndexOutOfBoundsException{
        int LIST_SIZE = this.size();
        int i;
        Node current = this.getHeadNode();
        Node newNode = new Node(item);

        if(index < 1 || index >= LIST_SIZE){
            throw new IndexOutOfBoundsException();
        }

        if(index == 1){
            newNode.setNext(current);
            this.setHeadNode(newNode);
        }

        for(i=1; i<LIST_SIZE - 1; i++){
            if(i == index-1) {
                newNode.setNext(current.getNext());
                current.setNext(newNode);
            }
            current = current.getNext();
        }

        this.setSize(this.size()+1);
    }*/

    /**
     * Adds item at end of list
     *
     * @param item to be added
     */
    public void addLast(T item) {
        Node node = new Node(item);

        if(isEmpty()){
            head = node;
            tail = node;
        }
        else{
            if(size == 1){
                head.next = node;
            }
            node.prev = tail;
            tail.next = node;
            tail = node;
        }
        tail.next = head;
        head.prev = tail;
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
