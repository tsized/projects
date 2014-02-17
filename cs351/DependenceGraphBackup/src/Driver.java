import java.util.Scanner;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
    public static void main(String[] args) {
        try{
            File file                     = new File(args[0]);
            DependenceGraph graph         = new DependenceGraph(file);
            Map<Integer, String> map      = graph.getIdNameMap();
            Map<String, Integer> mapwards = graph.getNameIdMap();

            System.out.println("Name-Id Map");
            printLine();
            System.out.println(map);
            System.out.println(mapwards);
            System.out.println();

            System.out.println("Dependence Graph");
            printLine();
            graph.printDependenceGraph();
            System.out.println();

            System.out.println("Transitive Graph");
            printLine();
            graph.printTransitiveGraph();
            System.out.println();

            System.out.println("Roots: " + graph.getRoots().toString());
            System.out.println(); 
            
            System.out.println("Leaves: " + graph.getLeaves().toString());
            System.out.println();
            
            int[][] transitiveGraph  = graph.getTransitiveDependenceGraph();

            for (int i = 0; i < transitiveGraph.length; i++) {
                System.out.println("Firewall for " + map.get(i) + ": " 
                        + graph.firewall(map.get(i)));
            }
            System.out.println();

            System.out.println("Parallel Groups:");
            graph.printParallelGroups();

            /*graph.removeLeaf("mno");
            graph.removeLeaf("bbb");
            graph.removeLeaf("mmm");
            graph.removeLeaf("ooo");*/
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void printLine() {
        System.out.println("----------------------------------------");
        System.out.println();
    }

}
