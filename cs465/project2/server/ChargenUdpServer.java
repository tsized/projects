import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * The concrete UDP implementation of the <code>AbstractChargenServer</code>.
 *
 * @author Chris Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 */
public class ChargenUdpServer extends AbstractChargenServer {
    /** The default size of the packet. */    
    private final int DEFAULT_PACKET_SIZE = 600;

    /** The socket we use to communicate over UDP. */
    private DatagramSocket srvSock;

    /**
     * The default constructor that is called if no arguments are passed.
     *
     * @throws SocketException if there is a problem creating the socket.
     * @throws SecurityException if the computer's security manager disallows
     *         the server's creation.
     */
    public ChargenUdpServer() throws SocketException, SecurityException {
        super();
    }

    /**
     * Constructor which is called when a user specifies their own port.
     *
     * @param port The port on which the server listens.
     *
     * @throws SocketException if there is a problem creating the socket.
     * @throws SecurityException if the computer's security manager disallows
     *         the server's creation.
     */
    public ChargenUdpServer(int port) throws SocketException, 
           SecurityException {
               super(port);
           }

    /**
     * Constructor which is called when a user specifies their own 
     * <code>ChargenCharacterSource</code>.
     *
     * @param charSource The <code>ChargenCharacterSource</code>  to return
     *        chars to the client.
     *
     * @throws SocketException if there is a problem creating the socket.
     * @throws SecurityException if the computer's security manager disallows
     *         the server's creation.
     */
    public ChargenUdpServer(ChargenCharacterSource charSource) 
        throws SocketException, SecurityException {

            super(charSource);
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
     * @throws SocketException if there is a problem creating the socket.
     * @throws SecurityException if the computer's security manager disallows
     *         the server's creation.
     */
    public ChargenUdpServer(int port, ChargenCharacterSource charSource) 
        throws SocketException, SecurityException {

            super(port, charSource);
        }

    /**
     * Listens for connections to the server, and returns chars to the client
     * when a connection is established.
     *
     * @throws ChargenServerException when the server encounters an error.
     */
    public void listen() throws ChargenServerException {
        try {
            DatagramPacket dataPack = new DatagramPacket(new byte[72], 72);
            this.srvSock = new DatagramSocket(getPort());
            System.out.println("Server up and running, ready to send Messages!" 
                    + "Exit with Ctrl+C.");
            System.out.println("Is the server Socket closed? " 
                                                   + this.srvSock.isClosed());
            while(!this.srvSock.isClosed()) {
                System.out.println("inside while.. About to try");
                try {
                    System.out.println("inside try...about to receive packet");
                    this.srvSock.receive(dataPack);
                    System.out.println("dataPack received");
                    this.srvSock.connect(dataPack.getSocketAddress());
                    System.out.println("Connected to the client on address: " + dataPack.getSocketAddress());
                    serve(dataPack);
                    System.out.println("Serving data to: " + dataPack.getSocketAddress());
                } catch(IOException ioe) {
                    this.srvSock.close();
                    ioe.printStackTrace();
                    throw new ChargenServerException(ioe.getMessage());
                }
            }
        } catch (SocketException se) {
            throw new ChargenServerException(se.getMessage());
        }
    }

    /**
     * Creates the data from our CharacterSource generator, and attempts 
     * to send the datagram to the client.
     *
     * @throws IOException if host cannot be found.
     */
    private void serve(DatagramPacket dataPack) throws IOException {
        char[] dataLength = new char[72];
        System.out.println("In serve");
        while(this.srvSock.isConnected()) {
            System.out.println("Connected to clinet");
            for(int i = 0; i < dataLength.length; i++) {
                dataLength[i] = getCharacterSource().getNextChar();
            }
            dataPack.setData(new String(dataLength).getBytes());
            System.out.println("Data in packet set");
            try {
                this.srvSock.send(dataPack);
            } catch(PortUnreachableException pue) {
                this.srvSock.disconnect();
                System.out.println("Host was unreachable, closing connection");
            }
        }
    }
}

