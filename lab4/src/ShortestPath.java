import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ShortestPath {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ1\n--------------------");

        GraphX<String> grX = new GraphX<>();
        String src; String dst;
        try(Scanner sc = new Scanner(new File("contiguous-usa.txt"))) {
            while(sc.hasNext()){
                src = sc.next();
                dst = sc.next();
                sc.nextLine();

                grX.addVertex(src);
                grX.addVertex(dst);
                grX.addEdge(src, dst);
            }
        }
        catch(InputMismatchException e) {
            e.printStackTrace();
        }

        grX.printGraph();
        System.out.println("\nN: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");


    }
}
