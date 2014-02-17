import java.io.IOException;
/**
 * The <code>ChargenServerException</code> implementation, which handles any
 * errors that occur during a connection to the <code>Chargen</code> server.
 *
 * @author Chris Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012.
 */
public class ChargenServerException  extends Exception {
    
    /**
     * Throws an error, and a message describing the error, if an exception
     * occurs.
     */
    public ChargenServerException(String message) {
        super(message);
    }
}
