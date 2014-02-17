import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Concrete implementation of the ChargenClient.
 *
 * @author Tim Sizemore
 * @author Chris Ward
 * @verison 10/15/12
 */
public class ChargenTcpClient extends AbstractChargenClient {
    /** The connection socket. */
    private Socket conSock;
    
    /** Creates a new socket with the host and port specified.
     *
     * @param host - the host for the socket to connect
     * @throws SecurityException if the a sercurity manager on the server
     * @throws IOException if there is an error with the input or output
     */
    public ChargenTcpClient(InetAddress host) throws SecurityException, 
                                                                  IOException {
        super(host);
    }
    
    /**
     * Creates a new socket with the host and port specified.
     *
     * @param host - the host for the socket to connect
     * @param port - the port for the socket to connect
     * @throws SecurityException if the a sercurity manager on the server
     * @throws IOException if there is an error with the input or output
     */
    public ChargenTcpClient(InetAddress host, int port) 
                                        throws SecurityException, IOException {
        super(host, port);
    }
    
    /**
     * Creates a new socket with the host and port specified and the flags.
     *
     * @param host - the host for the socket to connect
     * @param port - the port for the socket to connect
     * @param flag - the flag for the socket to pass
     *
     * @throws SecurityException if the a sercurity manager on the server
     * @throws IOException if there is an error with the input or output
     */
    public ChargenTcpClient(InetAddress host, int port, String flag) 
                                        throws SecurityException, IOException {
        super(host, port, flag);
    }
    /**
     * Concrete implementation of the printToStream method from the absract
     * class.
     *
     * @param stream - the buffered stream that receives input
     */
    public void printToStream(PrintStream stream) {
        try {
            this.conSock = new Socket(getHost(), getPort());

            OutputStream dataOut = conSock.getOutputStream();
            dataOut.write("\r\n".getBytes());
            dataOut.flush();

            byte[] charIn = new byte[72];
            InputStream dataIn = conSock.getInputStream();
    
            while(-1 != dataIn.read(charIn, 0, 72)) {
                stream.write(charIn,0,72);
            }
            dataIn.close();
            dataOut.close();
            conSock.close();

        } catch(IOException ioe) {
            System.err.println("There was an error writing the output from the"
                                                                 + " server.");
        }
    }
}
