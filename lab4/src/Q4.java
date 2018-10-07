import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Find a minimum spanning tree for the largest connected component in a weighted graph
 *
 * @author Ayub Atif
 */
public class Q4 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ3\n--------------------");

        GraphX<String> grX = new UnDiGraph<>();
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

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        System.out.println(grX.minSpanningTree("OH","NY").UnDiPath());
    }
    /*
    --------------------
    Q3
    --------------------
    N: 49
    E: 107

    Dijkstra activated
    |OH->NY|
    Shortest Distance: 196
    Shortest Path: [OH]-[PA]-[NY]
    |NY->OH|
    Shortest Distance: 196
    Shortest Path: [NY]-[PA]-[OH]
    |CA->CA|
    Shortest Distance: 0
    Shortest Path: [CA]
    |CA->FL|
    Shortest Distance: 81
    Shortest Path: [CA]-[AZ]-[NM]-[CO]-[OK]-[AR]-[MS]-[AL]-[FL]
    |CA->DXB|
    Disconnected src & dst
     */
}
