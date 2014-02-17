import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;
/**
 * Runs the PoetryWriter class and creates a poem based on output.
 *
 * @author Tim Sizemore
 * @author Andrea Sloan
 * @version 12-9-11
 */
public class PoetryDriver {
    public static void main(String[] args) {
        String[] punct      = { ",", ".", "!", "?", ";", ":" };        
        PoetryWriter poem   = new PoetryWriter();
        File file           = new File(args[0]);
        Scanner scanner     = null; 
        try {
            scanner     = new Scanner(file);
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist.");
        }


        while (scanner.hasNext()) {
            String word = scanner.next();

            String other = word.substring( (word.length() - 1 ), word.length());
            boolean found = false;
            for (int i = 0; !found && i != punct.length; i++) {
                if (punct[i].equals(other)) {
                    found = true;
                }
            }

            if (found) {
                word = word.substring(0, (word.length() - 1) );
                poem.addNode(word);
                poem.addNode(other);
            } else {
                poem.addNode(word);
            }
        }

        

        printStanza(poem, args);
    }
    
    /**
     * A private helper method that prints the number of words specified.
     *
     * @param poem An object of PoetryWriter
     * @param args An array of Strings.
     */
    private static void printWord(PoetryWriter poem, String[] args) {
        int rndValue  = (int) (Math.random() * (poem.getTotal() / 2));
        int numWords     = Integer.parseInt(args[3]);

        for (int i = 0; i < numWords; i++) {
            System.out.print(poem.getWord(rndValue) + " ");
        }
        System.out.println();
    }

    /**
     * A private helper method that prints the number of lines specified.
     *
     * @param poem An object of PoetryWriter
     * @param args An array of Strings.
     */
    private static void printLine(PoetryWriter poem, String[] args) {
        int numLines   = Integer.parseInt(args[2]);

        for (int i = 0; i < numLines; i++) {
            printWord(poem, args);
        }
        System.out.println();
    }

    /**
     * A private helper method that prints the number of stanzas specified.
     *
     * @param poem An object of PoetryWriter
     * @param args An array of Strings.
     */
    private static void printStanza(PoetryWriter poem, String[] args) {
        int numStanza = Integer.parseInt(args[1]);

        for (int i = 0; i < numStanza; i++) {
            printLine(poem, args);
        }
        System.out.println();
        System.out.println();
    }
}
