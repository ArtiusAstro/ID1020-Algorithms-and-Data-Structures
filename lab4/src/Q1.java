import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Find shortest path between two unweighted graph vertices using DFS
 *
 * @author Ayub Atif
 */
public class Q1 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ1\n--------------------");

        UnDiGraph<String> grX = new UnDiGraph<>();
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
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        grX.DFShortsetNoWeight("CA","NY");
    }
}

