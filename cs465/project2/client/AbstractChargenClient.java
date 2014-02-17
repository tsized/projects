import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;

/** An abstract class that modularizes the functionality of the ChargenClient.
 *
 * @author Tim Sizemore
 * @author Chris Ward
 * @version 10/15/12
 */
public abstract class AbstractChargenClient implements ChargenClient{

    /** IP address of the client. */
    private InetAddress host;

    /** Port of the client. */
    private int port;
    
    /** Flags sent to server*/
    private String flag;

    /** Default port of the client. */
    private final int DEFAULT_PORT = 19;

    /**
     * Sets the host and port for the client.
     *
     * @param host - the IP address of the client
     */
    public AbstractChargenClient(InetAddress host) {
        this.host = host;
        this.port = DEFAULT_PORT;
    }

    /**
     * Sets the host and port for the client.
     *
     * @param host - the IP address of the client
     * @param port - the port number for the client
     */
    public AbstractChargenClient(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Sets the host and port for the client.
     *
     * @param host - the IP address of the client
     * @param port - the port number for the client
     */
    public AbstractChargenClient(InetAddress host, int port, String flag) {
        this.host = host;
        this.port = port;
        this.flag = flag;
    }

    /**
     * Returns the host of the client.
     *
     * @return InetAddress - IP address of the client
     */
    protected InetAddress getHost() {
        return this.host;
    }

    /**
     * Returns the port of the client.
     *
     * @return int - the port of the client
     */
    protected int getPort() {
        return this.port;
    }

    /**
     * Abstract method that defaults the implementation of the printToStream
     * method to the subclasses.
     *
     * @param stream - the PrintStream buffer
     * @throws IOException if there is an error in input or output
     */
    public abstract void printToStream(PrintStream stream);
}
