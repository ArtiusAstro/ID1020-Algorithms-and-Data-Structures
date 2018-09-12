

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 *  @author Ayub Atif
 */

public class AbstractStack<Item> implements Iterable<Item> {
    private int size;          // size of the stack
    private Node top;     // top of stack

    /**
     * A node holds an item and info on next
     *
     * @author Ayub Atif
     */
    protected class Node {
        private Item item;
        private Node next;

        public Node(Item item){
            this.item = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node node){
            this.next = node;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    /**
     * Initializes an empty stack.
     */
    public AbstractStack() {
        top = null;
        size = 0;
    }

    public Node getTopNode(){
        return top;
    }

    /**
     * Returns true if this stack is empty.
     *
     * @return true if this stack is empty; false otherwise
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns the number of items in this stack.
     *
     * @return the number of items in this stack
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to this stack.
     *
     * @param item the item to add
     */
    public void push(Item item) {
        Node tmp = top;
        top = new Node(item);
        top.next = tmp;
        size++;
    }

    /**
     * Removes and returns the item most recently added to this stack.
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = top.item;        // save item to return
        top = top.next;            // delete top node
        size--;
        return item;                   // return the saved item
    }


    /**
     * Returns the item at the top of the stack (most recently added)
     *
     * @return the item most recently added
     * @throws NoSuchElementException if this stack is empty
     */
    public Item getTop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return top.item;
    }

    /**
     * Returns an iterator that LIFO iterates
     *
     * @return an iterator that LIFO iterates
     */
    public Iterator<Item> iterator()  { return new ListIterator();  }

    private class ListIterator implements Iterator<Item> {
        private Node current = top;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return the sequence of items in this stack in LIFO order
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int i = this.size()-1;
        for (Item item : this) {
            s.append('[').append(item).append(']');
            if(i-- > 0) {
                s.append(", ");
            }
        }
        return s.toString();
    }


    /**
     * Unit testing the data type.
     */
    public static void main(String[] args) throws IOException {

        AbstractStack<Character> abstractStack = new AbstractStack<>();

        /* Collect the input from ./input.txt */
        try (FileReader inputStream = new FileReader("src/input.txt")) {
            int stream;
            System.out.print("Input is: ");
            while ((stream = inputStream.read()) != -1) {
                System.out.write(stream); // display input
                abstractStack.push((char)stream); // push chars to stack
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        /* String representation */
        System.out.print("\n\nmyStack.toString() returns: ");
        System.out.println(abstractStack.toString());

        /* Print a node's char, pop the node, and continue until stack is empty */
        System.out.print("\nReversed: ");
        while(!abstractStack.isEmpty()){
            System.out.print(abstractStack.pop());
        }
    }
}
