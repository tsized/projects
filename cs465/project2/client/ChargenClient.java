import java.io.PrintStream;

/**
 * Interface for the ChargenClient
 *
 * @author Tim Sizemore
 * @author Chris Ward
 */
public interface ChargenClient {
   
    /**
     * Prints a message to the input stream.
     *
     * @param stream - a PrintStream that holds the buffer
     */
    public abstract void printToStream(PrintStream stream);
}
