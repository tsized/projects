import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The concrete TCP implementation of the <code>AbstractChargenServer</code>.
 *
 * @author Chris Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 */
public class ChargenTcpServer extends AbstractChargenServer {
    /** The socket on which our server will be listening. */ 
    private ServerSocket srvSock;
    
    /**
     * The default constructor for our <code>ChargenTcpServer</code>.
     *
     * @throws IOException if there is a problem reading/writing.
     * @throws SecurityException if the computer's security manager disallows
     *         the server's creation.
     */
    public ChargenTcpServer() throws IOException, SecurityException {
        super();
        srvSock = new ServerSocket(super.getPort());
    }
    
    /**
     * Constructor which is called when a user only specifies a port to create
     * the server.
     * 
     * @param port The port on which the server listens.
     *
     * @throws IOException if there is a problem reading/writing.
     * @throws SecurityException if the computer's security manager disallows
     *         the server's creation.
     */
    public ChargenTcpServer(int port) throws IOException, SecurityException {
        super(port);
        srvSock = new ServerSocket(super.getPort());
    }
    
    /**
     * Constructor which is called when a user specifies their own 
     * <code>ChargenCharacterSource</code>.
     * 
     * @param charSource The <code>ChargenCharacterSource</code>  to return
     *        chars to the client.
     *
     * @throws IOException if there is a problem reading/writing.
     * @throws SecurityException if the computer's security manager disallows
     *         the server's creation.
     */
    public ChargenTcpServer(ChargenCharacterSource charSource) 
                                        throws IOException, SecurityException {

        super(charSource);
        srvSock = new ServerSocket(super.getPort());
    }

    /**
     * Constructor which is called when a user specifies their own port and 
     * <code>ChargenCharacterSource</code>.
     *
     *
     * @param port The port on which the server listens.
     * @param charSource The <code>ChargenCharacterSource</code>  to return
     *        chars to the client.
     *
     * @throws IOException if there is a problem reading/writing.
     * @throws SecurityException if the computer's security manager disallows
     *         the server's creation.
     */
    public ChargenTcpServer(int port, 
            ChargenCharacterSource charSource) throws IOException, 
            SecurityException {

        super(port, charSource);
        srvSock = new ServerSocket(super.getPort());
    }
    
    /**
     * Listens for connections, and when one is established, output chars to
     * the clent.
     */
    public void listen() throws ChargenServerException{
        try {
            while(!this.srvSock.isClosed()) {
                    Socket connectionSocket = srvSock.accept();
                    
                    DataOutputStream outToClient = 
                            new DataOutputStream(
                                           connectionSocket.getOutputStream());
                    
                    ChargenCharacterSource source = super.getCharacterSource();
                    while(true) {
                        String output = "";
                        for (int i = 0; i < 70; i++) {
                            output += source.getNextChar();
                            outToClient.writeBytes(output);
                        }
                    }
            }
        } catch (IOException ex) {
            listen();
        }
    }
}
