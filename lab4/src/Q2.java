import java.io.FileNotFoundException;

/**
 * Find shortest path between two unweighted graph vertices using BFS
 *
 * @author Ayub Atif
 */
class Q2 {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("--------------------\nQ2\n--------------------");

        UnDiGraph<String> grX = new UnDiGraph<>();
        grX.fillGraph("contiguous-usa.txt");

        //grX.printGraph();
        System.out.println("N: "+grX.getN());
        System.out.println("E: "+grX.getE()+"\n");

        //Symmetry
        for(String s : grX.BFShortsetNoWeight("NY","OH")) System.out.print(s+"-"); System.out.println("done");
        for(String s : grX.BFShortsetNoWeight("OH","NY")) System.out.print(s+"-"); System.out.println("done");
        //Empty path
        for(String s : grX.BFShortsetNoWeight("NY","NY")) System.out.print(s+"-"); System.out.println("done");
        //Long path
        for(String s : grX.BFShortsetNoWeight("NY","FL")) System.out.print(s+"-"); System.out.println("done");
        //Disconnected src & dst
        for(String s : grX.BFShortsetNoWeight("NY","DxB")) System.out.print(s+"-"); System.out.println("done");

    }
    /*
    --------------------
    Q2
    --------------------
    N: 49
    E: 107

    |NY->OH|
    Path already done: false
    Shortest Distance: 2
    Shortest Path: NY-PA-OH-done
    |OH->NY|
    Path already done: false
    Shortest Distance: 2
    Shortest Path: OH-PA-NY-done
    |NY->NY|
    Path already done: false
    Shortest Distance: 0
    Shortest Path: NY-done
    |NY->FL|
    Path already done: true
    Shortest Distance: 6
    Shortest Path: NY-PA-WV-VA-TN-GA-FL-done
    |NY->DxB|
    Path already done: false
    Disconnected src & dst
    done
     */
}

