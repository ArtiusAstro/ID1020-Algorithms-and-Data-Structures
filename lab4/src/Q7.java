import java.io.FileNotFoundException;

/**
 * Find a minimum spanning tree for the largest connected component in a weighted graph
 *
 * @author Ayub Atif
 */
class Q7 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ7\n--------------------");

        DiGraphX<String> grX = new DiGraphX<>();
        grX.fillGraph("contiguous-usa.txt");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");


    }
    /*

     */
}
