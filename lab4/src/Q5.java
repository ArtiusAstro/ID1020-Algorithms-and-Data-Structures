import java.io.FileNotFoundException;

/**
 * Check if two vertices are connected in a directed weighted graph
 *
 * @author Ayub Atif
 */
class Q5 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ5\n--------------------");

        DiGraphX<String> grX = new DiGraphX<>();
        grX.fillGraph("contiguous-usa.txt");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

    }
    /*

     */
}
