import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Find shortest path between two unweighted graph vertices using BFS
 *
 * @author Ayub Atif
 */
public class Q2 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ2\n--------------------");

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

        //Symmetry
        System.out.println(grX.BFShortsetNoWeight("OH","NY").UnDiPath());
        System.out.println(grX.BFShortsetNoWeight("NY","OH").UnDiPath());
        //Empty path
        System.out.println(grX.BFShortsetNoWeight("CA","CA").UnDiPath());
        //Long path
        System.out.println(grX.BFShortsetNoWeight("CA","FL").UnDiPath());
        //Disconnected src & dst
        System.out.println(grX.BFShortsetNoWeight("CA","DXB").UnDiPath());
    }

    /*
    --------------------
    Q2
    --------------------
    N: 49
    E: 107

    |OH->NY|
    Shortest Distance: 2
    Shortest Path: [OH]-[PA]-[NY]
    |NY->OH|
    Shortest Distance: 2
    Shortest Path: [NY]-[PA]-[OH]
    |CA->CA|
    Shortest Distance: 0
    Shortest Path: [CA]
    |CA->FL|
    Shortest Distance: 7
    Shortest Path: [CA]-[AZ]-[NM]-[OK]-[AR]-[MS]-[AL]-[FL]
    |CA->DXB|
    Disconnected src & dst
     */
}

