import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * A class the creates a <code>DependenceGraph</code> that represents file
 * dependencies read from a text file.
 *
 * @author Tim Sizemore
 * @author Andrea Sloan
 * @version 10-10-11
 */
public class DependenceGraph {
    
    /** 
     * An <code>int</code> that represents a dependence between files.
     */
    private final int            DEPENDENCE = 1;

    /**
     * An <code>int</code> that represents a value that has been removed
     */
    private final int            REMOVED = -1;
    
    /**
     * A <code>Map</code> that holds the values of the files read in where the
     * <code>key</code> is an <code>int</code> and the <code>value</code> is a
     * <code>String</code>.
     */
    private Map<Integer, String> intStringMap;

    /**
     * A <code>Map</code> that holds the values of the files read in where the
     * <code>key</code> is a <code>String</code> and the <code>value</code> is
     * an <code>int</code>.
     */
    private Map<String, Integer> stringIntMap;

    /**
     * A two dimensional <code>array</code> that represents direct dependencies.
     */
    private int[][]              dependenceGraph;

    /**
     * A two dimensional <code>array</code> that represents direct and 
     * transitive dependencies.
     */
    private int[][]              transitiveGraph;

    /**
     * An <code>int</code> value holds a reference to the number of files passed
     * by the text file.
     */
    private int                  numArgs;

    /**
     * An <code>int</code> value that references the index in which to add the
     * file to the <code>Map</code>.
     */
    private int                  keyCount;

    /**
     * Creates an instance of <code>DependenceGraph</code> that initializes two
     * instances of <code>HashMap</code> and uses a <code>Scanner</code> to read
     * the provided <code>file</code>. With the input provided, 
     * <code>DependenceGraph</code> creates two instances of 
     * <code>int</code> 2D-array that are empty with size depending on the 
     * number of arguments that the <code>Scanner</code> reads in.
     *
     * @param file - The file which is to be read into the <code>Scanner</code>.
     * @throws FileNotFoundException when the <code>file</code> does not exist.
     */
    public DependenceGraph(File file) throws FileNotFoundException {
        intStringMap = new HashMap<Integer, String>();
        stringIntMap = new HashMap<String, Integer>();
        numArgs      = 0;
        keyCount     = 0;
        
        Scanner scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String firstValue  = scanner.next();
            String secondValue = scanner.nextLine().trim();        
            
            addKeyIfNotFound(firstValue);
            addKeyIfNotFound(secondValue);
        }
        numArgs = intStringMap.keySet().size();

        dependenceGraph = new int[numArgs][numArgs];
        transitiveGraph = new int[numArgs][numArgs];

        scanner = new Scanner(file);
        while (scanner.hasNext()) {
            String firstValue  = scanner.next();
            String secondValue = scanner.nextLine().trim();        

            buildDependenceGraph(firstValue, secondValue);
            buildTransitiveGraph(firstValue, secondValue);
        }
    }
    
    /**
     * Returns a <code>Map</code> with keys as <code>String</code> and values as
     * <code>Integer</code>.
     *
     * @return A <code>Map</code> with key, value pairs.
     */
    public Map<String, Integer> getNameIdMap() {
        return stringIntMap;
    }

    /**
     * Returns a <code>Map</code> with keys as <code>Integer</code> and values 
     * as <code>String</code>.
     *
     * @return A <code>Map</code> with key, value pairs.
     */
    public Map<Integer, String> getIdNameMap() {
        return intStringMap;
    }

    /**
     * Returns a 2D-array that contains the initial dependences of the input 
     * read in from the <code>Scanner</code>. 
     *
     * @return A 2D-array that illustrates initial dependencies.
     */
    public int[][] getDependenceGraph() {
        return dependenceGraph;
    }

    /**
     * Returns a 2D-array that contains the transitive depenences of the input 
     * read in from the <code>Scanner</code>.
     *
     * @return A 2D-array that illustrates transitive dependencies.
     */
    public int[][] getTransitiveDependenceGraph() {
        return transitiveGraph;
    }

    /**
     * Generates a <code>List</code> containing all of the roots.
     *
     * @return A <code>List</code> containing the roots.
     */
    public List<String> getRoots() {
        List<String> list = new ArrayList<String>();
        boolean     found = false;

        for (int i = 0; i < numArgs; i++) {
            found = false;
            for (int j = 0; j < numArgs && !found; j++) {
                if (transitiveGraph[j][i] == DEPENDENCE) {
                    found = true;
                } else if (j == numArgs - 1) {
                    String root = intStringMap.get(i);
                    list.add(root);
                }
            }
        }
        return list;
    }

    /**
     * Generates a <code>List</code> containing all of the leaves.
     *
     * @return A <code>List</code> containing the leaves.
     */
    public List<String> getLeaves() {
        List<String> list = new ArrayList<String>();
        boolean     found = false;
        boolean      leaf = false;
        for (int i = 0; i < numArgs; i++) {
            found = false;
            leaf  = false;
            String rowWord = intStringMap.get(i);
            for (int j = 0; j < numArgs && !found; j++) {
                if (transitiveGraph[i][j] == DEPENDENCE) { 
                    found = true;
                } else if (transitiveGraph[i][j] == 0) {
                    leaf = true;
                }
            } /*else if (j == numArgs - 1 && count != numArgs) {*/
            if (!found && leaf) {
                /*String leaf = intStringMap.get(i);*/
                list.add(rowWord);
            }
        }
        return list;    
    }

    /**
     * Removes a leaf from the 2D-array.
     *
     * @param leaf - The leaf that is to be removed.
     */
    public void removeLeaf(String leaf) {
        int index = stringIntMap.get(leaf);

        for (int i = 0; i < numArgs; i++) {
            transitiveGraph[index][i] = REMOVED;
            transitiveGraph[i][index] = REMOVED;
            
            dependenceGraph[index][i] = REMOVED;
            dependenceGraph[i][index] = REMOVED;
        }
        
    }

    /**
     * Generates a <code>List</code> of <code>String</code> that holds the
     * transitive dependencies of <code>node</code>.
     *
     * @param node - A <code>String</code> that serves as the root of the 
     *        dependencies.
     * @return A <code>List</code> containing the transitive dependencies as 
     *         <code>String</code> values.
     */
    public List<String> firewall(String node) {
        List<String> list = new ArrayList<String>();
        int         index = stringIntMap.get(node);

        for (int i = 0; i < numArgs; i++) {
            if (transitiveGraph[i][index] == DEPENDENCE) {
                String firewall = intStringMap.get(i);
                list.add(firewall);
            }
        }
        return list;
    }

    /**
     * Prints a <code>List</code> of <code>String</code> values that depend on
     * nothing. Iteratively calls <code>removeLeaf</code> removing each instance
     * of each leaf from the graph.
     */
    public void printParallelGroups() {
        List<String> leafList = getLeaves();

        int i = 0;
        while (leafList.size() != 0) {
            System.out.println("Group " + (i + 1) + ": " +  leafList);
            for (int j = 0; j < leafList.size(); j++) {
                removeLeaf(leafList.get(j));
            }
            leafList = getLeaves();
            i++;
        }
    }

    /**
     * Prints a 2D-array that holds the initial dependencies.
     */
    public void printDependenceGraph() {
        printGraph(dependenceGraph);
    }

    /**
     * Prints a 2D-array that holds the transitive dependencies.
     */
    public void printTransitiveGraph() {
        printGraph(transitiveGraph);
    }
    
    /**
     * A helper method that builds the two instances of <code>HashMap</code>.
     */
    private void addKeyIfNotFound(String key) {
        if (!stringIntMap.containsKey(key)) {
            int value = keyCount++;

            stringIntMap.put(key, value);
            intStringMap.put(value, key);
        } 
    }

    /**
     * A helper method that builds a 2D-array holding the initial dependencies. 
     */
    private void buildDependenceGraph(String firstValue, String secondValue) {
        int source      = stringIntMap.get(firstValue);
        int dependence  = stringIntMap.get(secondValue);

        dependenceGraph[source][dependence] = DEPENDENCE;
        transitiveGraph[source][dependence] = DEPENDENCE;
    }
    
    /**
     * A helper method that builds a 2D-array holding the transitive 
     * dependencies.
     */
    private void buildTransitiveGraph(String firstValue, String secondValue) {
        for (int k = 0; k < numArgs; k++) {
            for (int i = 0; i < numArgs; i++) {
                for (int j = 0; j < numArgs; j++) {
                    if (transitiveGraph[i][j] == DEPENDENCE || 
                            transitiveGraph[i][k] == DEPENDENCE &&
                            transitiveGraph[k][j] == DEPENDENCE) {
                        transitiveGraph[i][j] = DEPENDENCE;
                    }
                }    
            }
        }
    }

    /**
     * A helper method that prints a 2D-array.
     */
    private void printGraph(int[][] array) {
        for (int i = 0; i < numArgs; i++) {
            for (int j = 0; j < numArgs; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}
