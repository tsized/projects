import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Concrete implementation of the ChargenUdpClient.
 *
 * @author Tim Sizemore
 * @author Chris Ward
 * @version 10/15/12
 */
public class ChargenUdpClient extends AbstractChargenClient {

    /** The lenght of data to be transmitted */
    private static final int DATA_LENGTH = 72;

    /** Datagram packet socket. */
    private DatagramSocket cliSock;

    /**
     * UDP client constructor.
     *
     * @param host - the host for the client to connect with
     */
    public ChargenUdpClient(InetAddress host) {
        super(host);
    }

    /**
     * UDP client constructor.
     *
     * @param host - the host for the client to connect with
     * @param port - the port for the client to connect with
     */
    public ChargenUdpClient(InetAddress host, int port) {
        super(host, port);
    }

    /**
     * Concrete implementation of printToStream for the UDP client.
     *
     * @param stream - PrintStream that is being used
     */
    public void printToStream(PrintStream stream) {
        try {
            cliSock = new DatagramSocket();
            byte[] dataLength = new byte[DATA_LENGTH];

            DatagramPacket dataPack = new DatagramPacket(dataLength,
                                                             dataLength.length); 
            dataPack.setAddress(getHost());
            dataPack.setPort(getPort());

            cliSock.send(dataPack);

            DatagramPacket receivePack = new DatagramPacket(dataLength,
                                                             dataLength.length);
            cliSock.receive(receivePack);

            stream.println(new String(dataPack.getData()));
            cliSock.close();

        } catch (UnknownHostException uhe) {
            System.err.println("Could not resolve host!");
        } catch (SocketException se) {
            System.err.println("Could not establish Socket!");
        } catch (IOException ex) {
            System.err.println("Could not establish Socket!");
        }
    }
}
