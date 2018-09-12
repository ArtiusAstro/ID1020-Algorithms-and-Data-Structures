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
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * f8O3ET
 * QFEF
 * ASD
 *
 * @author Ayub Atif
 */
public class Filter {

    /**
     * checks if the parenthesis (square, curly, and regular) are balanced
     * also prints the cause of non-balance at first encounter
     *
     * @param fileName
     * @return true if balanced
     */
    private static boolean isBalanced(String fileName) {

        char[] charSet = new char[] {')','(',']','[','}','{'};
        CharMap charMap = new CharMap(charSet);

        AbstractStack<Character> stack = new AbstractStack<>();
        char inputChar;

        /* Collect the input from ./input.txt */
        try (FileReader inputStream = new FileReader("src/" + fileName)) {
            int stream;
            while ((stream = inputStream.read()) != -1) {
                inputChar = (char)stream;
                if(charMap.containsValue(inputChar)){
                    stack.push(inputChar);
                    System.out.println("pushed "+inputChar);
                }
                else if(charMap.containsKey(inputChar)){
                    if (!stack.isEmpty()) {
                        if (charMap.get(inputChar) == stack.getTop()) {
                            System.out.println("popped " + stack.getTop());
                            stack.pop();
                        } else {
                            System.out.println("got " + inputChar + " but stack top is " + stack.getTop());
                            return false;
                        }
                    }
                    else {
                        System.out.println("\ngot " + inputChar + " but stack size is "+stack.size());
                        return false;
                    }
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }

        if(stack.isEmpty()){
            return true;
        }
        else {
            System.out.println("terminated search but stack size is "+stack.size());
            return false;
        }
    }

    /**
     * Unit testing the filter
     */
    public static void main(String[] args) {

        System.out.println("\n"+Filter.isBalanced("input.txt"));

    }
}

class CharMap {
    private int size;
    private char[] charSet;

    CharMap(char[] chars){
        this.charSet = chars;
        this.size = chars.length;
    }

    private char[] keySet(){
        char[] keys = new char[this.size/2];
        for(int i = 0; i<this.size/2; i++){
            keys[i] = this.charSet[i*2];
        }

        return keys;
    }

    public char[] valueSet(){
        char[] values = new char[this.size/2];
        for(int i = 0; i<this.size/2; i++){
            values[i] = this.charSet[i*2+1];
        }

        return values;
    }

    boolean containsKey(char c){
        for (Character key : keySet()){
            if(c == key)
                return true;
        }
        return false;
    }

    boolean containsValue(char c){
        for (Character value : valueSet()){
            if(c == value)
                return true;
        }
        return false;
    }

    char get(char key){
        for(int i = 0; i < this.size; i++){
            if(key == this.charSet[i])
                return this.charSet[i+1];
        }
        throw new NoSuchElementException();
    }
}
