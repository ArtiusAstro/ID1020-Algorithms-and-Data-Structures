import java.util.Scanner;

public class ReverseADT {

    private static Scanner scanner = new Scanner(System.in);  // Reading from System.in

    private static void recursion(String s, int x){
        if(x++ < s.length()){
            recursion(s, x);
            System.out.print(s.charAt(x-1));
        }
    }

    private static void iteration(String s){

    }

    private static void initializeCharList(DoubleLinkedList doubleLinkedList){

    }

    public static void main(String[] args){

        DoubleLinkedList<Character> doubleLinkedList = new DoubleLinkedList<>();
        initializeCharList(doubleLinkedList);

        System.out.print("Enter a string: ");
        String s = scanner.next();
        int x = 0;

        System.out.print("||RECURSION REVERSAL||: ");
        recursion(s, x);
        System.out.print("\nEnter a string again: ");
        s = scanner.next();
        System.out.print("||ITERATION REVERSAL||: ");
        iteration(s);

        scanner.close();
    }
}
