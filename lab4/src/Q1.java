import java.io.FileNotFoundException;

/**
 * Find shortest path between two unweighted graph vertices using DFS
 *
 * @author Ayub Atif
 */
class Q1 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ1\n--------------------");

        UnDiGraph<String> grX = new UnDiGraph<>();
        grX.fillGraph("contiguous-usa.txt");

        grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        //Symmetry
        System.out.println(grX.DFShortsetNoWeight("NY","OH").UnDiPath());
        System.out.println(grX.DFShortsetNoWeight("OH","NY").UnDiPath());
        //Empty path
        System.out.println(grX.DFShortsetNoWeight("NY","NY").UnDiPath());
        //Long path
        System.out.println(grX.DFShortsetNoWeight("NY","FL").UnDiPath());
        //Disconnected src & dst
        System.out.println(grX.DFShortsetNoWeight("NY","DXB").UnDiPath());
    }
}

