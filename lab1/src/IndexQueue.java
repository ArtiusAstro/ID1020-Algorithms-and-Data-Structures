/*#########################################################################
⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿      @Author: Ayub Atif
⣿⣿⣿⣿⣿⣿⣿⣿⡇⢀⢀⠍⠙⢿⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿
⠹⣿⣿⣿⣿⣿⣿⣿⠁⠈⢀⡤⢲⣾⣗⠲⣿⣿⣿⣿⣿⣿⣟⠻      Title: AbstractStack.java
⡀⢙⣿⣿⣿⣿⣿⣿⢀⠰⠁⢰⣾⣿⣿⡇⢀⣿⣿⣿⣿⣿⣿⡄      Compilation: javac AbstractStack.java
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷      Execution: java AbstractStack < input.txt
⣇⢀⢀⠙⠷⣍⠛⠛⢀⢀⢀⢀⠙⠋⠉⢀⢀⢸⣿⣿⣿⣿⣿⣷
⡙⠆⢀⣀⠤⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢀⢸⣿⣿⣿⣿⣿⣿
⣷⣖⠋⠁⢀⢀⢀⢀⢀⢀⣀⣀⣄⢀⢀⢀⢀⢸⠏⣿⣿⣿⢿⣿      > Description
⣿⣷⡀⢀⢀⢀⢀⢀⡒⠉⠉⢀⢀⢀⢀⢀⢀⢈⣴⣿⣿⡿⢀⡿      A generic stack implemented with linked
⣿⣿⣷⣄⢀⢀⢀⢀⠐⠄⢀⢀⢀⠈⢀⣀⣴⣿⣿⣿⡿⠁⢀⣡      list ADT
⠻⣿⣿⣿⣿⣆⠢⣤⣄⢀⢀⣀⠠⢴⣾⣿⣿⡿⢋⠟⢡⣿⣿⣿
⢀⠘⠿⣿⣿⣿⣦⣹⣿⣀⣀⣀⣀⠘⠛⠋⠁⡀⣄⣴⣿⣿⣿⣿
⢀⢀⢀⠈⠛⣽⣿⣿⣿⣿⣿⣿⠁⢀⢀⢀⣡⣾⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢀⢰⣿⣿⣿⣿⣿⣿⣿⣦⣤⣶⣿⣿⣿⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
⢀⢀⢀⢰⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
##########################################################################*/

import java.io.FileReader;
import java.io.IOException;

public class IndexQueue<T> extends AbstractQueue<T> {

    public void add(int index, T item) throws IndexOutOfBoundsException{
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
    }

    public void remove(int index) throws IndexOutOfBoundsException{
        int LIST_SIZE = this.size();
        int i;
        Node current = this.getHeadNode();

        if(index < 1 || index >= LIST_SIZE){
            throw new IndexOutOfBoundsException();
        }

        if(index == 1){
            this.setHeadNode(current.getNext());
        }

        for (i = 1; i < LIST_SIZE - 1; i++) {
            if (i == index-1) {
                current.setNext(current.getNext().getNext());
            }
            current = current.getNext();
        }

        this.setSize(this.size()-1);
    }
    
    public static void main(String[] args){
        IndexQueue<Character> indexQueue = new IndexQueue<Character>();

        /* Collect the input from ./input.txt */
        try (FileReader inputStream = new FileReader("src/input.txt")) {
            int stream;
            System.out.print("Input is: ");
            while ((stream = inputStream.read()) != -1) {
                System.out.write(stream); // display input
                indexQueue.addLast((char)stream); // push chars to stack
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("\nThe Queue: "+indexQueue.toString());

        indexQueue.add(1, 'x');
        System.out.println("\nThe Queue: "+indexQueue.toString());

        indexQueue.remove(1);
        System.out.println("\nThe Queue: "+indexQueue.toString());

    }
}
