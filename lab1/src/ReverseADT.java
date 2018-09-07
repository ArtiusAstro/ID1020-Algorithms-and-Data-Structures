import java.util.Scanner;

public class ReverseADT {

    private static Scanner scanner = new Scanner(System.in);  // Reading from System.in

    private static void recursion(String s, int x){
        if(x++ < s.length()){
            recursion(s, x);
            System.out.print(s.charAt(x-1));
        }
    }

    private static void iteration(DoubleLinkedList doubleLinkedList){
        for (char c : doubleLinkedList) {

        }
    }

    public static void main(String[] args){

        System.out.print("Enter a string: ");
        String s = scanner.next();

        DoubleLinkedList<Character> doubleLinkedList = new DoubleLinkedList<>(s.toCharArray());

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
