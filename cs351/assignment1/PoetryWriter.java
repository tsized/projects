import java.util.Random;
import java.lang.IllegalStateException;
/**
 * Constructs a data structure from <code>LinkedLists</code> that holds 
 * <code>String</code> values which will be used to populate a list as a poem.
 *
 * @author Tim Sizemore
 * @author Andrea Sloan
 * @version 12-9-11
 */
public class PoetryWriter { 

    /**
     * An inner class that populates when a unique <code>String</code> value
     * appears.
     */
    private static class MainNode {
        
        /** <code>String</code> that holds the value of the created node. */
        private String     input;

        /** A node that holds a reference to the next <code>MainNode</code>. */
        private MainNode   mainNext;

        /** A node that holds a reference to the next <code>NextNode</code>. */
        private NextNode   next;

        /** 
         * An <code>int</code> value that maintains the number of times a word
         * has appeared.
         */
        private int        wordCount;

        /**
         * Constructor for a node in the mainList.
         *
         * @param input The String value for the node created.
         * @param mainNext A reference to the node in the mainList.
         * @param next A reference to the node in the nextList.
         */
        public MainNode(String input, MainNode mainNext, NextNode next) {
            this.input     = input;
            this.mainNext  = mainNext; 
            this.next      = next;
            this.wordCount = 1;
        }
    }

    /**
     * An inner class that populates when a word is added that follows a word in
     * the read in file.
     */
    private static class NextNode {
        
        /** A String value that holds the value of the created node. */
        private String   input;

        /** A reference to the MainNode that has the equivalent value. */
        private MainNode mainNodePointer;

        /** A reference to the Nextnode that follows the current one. */
        private NextNode next;

        /** 
         * An int that maintains the number of times the word has appeared 
         * following the currently read in word.
         */
        private int      wordCount;

        /**
         * Constructor for a NextNode.
         *
         * @param input The String value of the word.
         * @param mainNodePointer A reference to the word in the mainList.
         * @param next A reference to the word that follows the current word.
         */
        public NextNode(String input, MainNode mainNodePointer, NextNode next) {
            this.input           = input;
            this.mainNodePointer = mainNodePointer;
            this.next            = next;
            this.wordCount       = 1;
        }
    }

    /** A reference to the first node in the mainList. */
    private MainNode  mainList;

    /** A reference to the previously read in word in the file. */
    private MainNode  previousWord;

    /** An int reference to the number of words added. */
    private int       total;
    
    /**
     * Constructor that initializes all the fields of PoetryWriter.
     */
    public PoetryWriter() {
        mainList     = null;
        previousWord = null;
        total        = 0;
    }

    /**
     * Returns a reference to the first node in the list.
     *
     * @return MainNode The reference to the first node in the list.
     */
    public MainNode getMainList() {
        return mainList;
    }

    /**
     * Returns the wordCount of the node passed.
     *
     * @param node A MainNode reference
     * @return int The number of times the node appears.
     */
    public int getMainNodeWordCount(MainNode node) {
        return node.wordCount;
    }

    /**
     * Returns an int that is the number of times the NextNode appears.
     *
     * @param node A reference to a NextNode
     * @return int Number of times the word appears.
     */
    public int getNextNodeWordCount(NextNode node) {
        return node.wordCount;
    }

    /**
     * Returns the word in the given node.
     *
     * @param node A MainNode
     * @return String The value of the input field in the given node.
     */
    public String getMainNodeInput(MainNode node) {
        return node.input;
    }

    /**
     * Returns the word in the given node.
     *
     * @param node A NextNode
     * @return String The value of the input field in the given node.
     */
    public String getNextNodeInput(NextNode node) {
        return node.input;
    }

    /**
     * Returns a reference to the given node.
     *
     * @param node A MainNode
     * @return MainNode A MainNode reference.
     */
    public MainNode getMainNodeNextPointer(MainNode node) {
        return node.mainNext;
    }

    /**
     * Returns a refernce to the given node's next.
     *
     * @param node A MainNode
     * @return NextNode a MainNode's next pointer.
     */
    public NextNode getNextNodeNextPointer(MainNode node) {
        return node.next;
    }
    

    /**
     * Adds a node to the data structure.
     *
     * @param word The word to add to the data structure.
     */
    public void addNode(String word) {
        total++;

        boolean mainFound = false;

        if (mainList == null) {
            mainList = new MainNode(word, null, null);
            mainFound = true;
        }

        for (MainNode n = mainList; !mainFound && n != null; n = n.mainNext) {
            if (word.equals(n.input)) {
                n.wordCount++;    
                mainFound = true;

                boolean nextFound = false;

                for (NextNode m = n.next; !nextFound && m != null; m = m.next) {
                    if (word.equals(m.input)) {
                        m.wordCount++;
                        nextFound = true;
                    }
                    if (!nextFound) {
                        m.next = new NextNode(word, null, null);
                    }
                }
            }
        }
        if (!mainFound) {
            MainNode current = mainList;
            while (current.mainNext != null) {
                current = current.mainNext;
            }
            current.mainNext = new MainNode(word, null, null);
        }
    }

    /**
     * Returns a random word to populate the poem.
     *
     * @param value an int that that is used to return the word.
     * @return String the word that will be used.
     */
    public String getWord(int value) {
        String nextWord = "";
        boolean found = false;

        for (MainNode n = mainList; n != null && !found; n = n.mainNext) {
            //System.out.println("wordCount: " + n.wordCount);
            if (n.wordCount >= value) {
                nextWord = n.input;
            }
        }

        return nextWord;
    }

    /**
     * Returns the value of the field total.
     *
     * @return int the number of items in the data structure.
     */
    public int getTotal() {
        return total;
   }


    /**
     * Prints out a String representation of all the nodes in the list.
     *
     * @return String The nodes in represented as Strings.
     */
   public String toString() {
       StringBuilder sb = new StringBuilder();
       sb.append("<" + mainList.input);
       for (MainNode n = mainList.mainNext; n != null; n = n.mainNext) {
           if (n.next == null) {
               sb.append(", " + n.input);
           } else {
               for (NextNode m = n.next; m != null; m = m.next) {
                   sb.append(", " + m.input);
               }
               sb.append(n.input);
           }
       }
       sb.append(">");
       return sb.toString();
   }
}
