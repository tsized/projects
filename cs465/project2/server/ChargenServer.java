/**
 * The main interface for our <code>ChargenServer</code>.
 * 
 * @author Chris Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 */
public interface ChargenServer {
   /**
    * Allows the server to listen for connections.
    *
    * @throws ChargenServerException if an error occurs while a connection is
    *         established.
    */
    public void listen() throws ChargenServerException;
}
