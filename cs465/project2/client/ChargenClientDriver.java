import java.io.IOException;
import java.net.InetAddress;
import java.io.PrintStream;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Entry point for the CharGen client.
 *
 * @author Tim Sizemore
 * @author Chris Ward
 * @version 10/15/12
 */
public class ChargenClientDriver {
   
    /** Usage message if the command line arguments are incorrect. */
    protected final static String USAGE_STRING  = "Usage: java" +
      " ChargenClientDriver <TCP/UDP> <ip address/host name> |<port number>|.";
    
    /**
     * Entry method to specify what client the server accepts.
     * 
     * @param args - an array of command line arguments
     */
    public static void main(String[] args) {
        String protocolType;
        String flag;
        int argLength = args.length;
        ChargenClient myClient = null;

        try {
            if (argLength == 2) {
                protocolType = args[0];
                
                if (protocolType.equalsIgnoreCase("TCP")) {
                     myClient = createTcp(args);
                } else if (protocolType.equalsIgnoreCase("UDP")) {
                     myClient = createUdp(args);
                } else {
                    System.err.println(USAGE_STRING);
                }
            } else if (argLength == 3) {
                protocolType = args[0];
                
                if (protocolType.equalsIgnoreCase("TCP")) {
                     myClient = createTcp(args); 
                } else if (protocolType.equalsIgnoreCase("UDP")) {
                     myClient = createUdp(args);
                } else {
                    System.err.println(USAGE_STRING);
                }
            } else if (argLength == 4) {
                protocolType = args[0];
                
                if (protocolType.equalsIgnoreCase("TCP")) {
                     myClient = createTcp(args); 
                } else if (protocolType.equalsIgnoreCase("UDP")) {
                     myClient = createUdp(args);
                } else {
                    System.err.println(USAGE_STRING);
                }
            } else {
                System.err.println(USAGE_STRING);
            }
            myClient.printToStream(System.out);
        }
        catch (UnknownHostException e) {
            System.err.println("UnknownHostException: the IP address of the " + 
                    "host could not be resolved.");
            System.exit(1);
        }
        catch (SocketException e) {
            System.err.println("SocketException: the socket could not be " +
                    "opened, or created");
            System.exit(2);
        }
        catch (SecurityException e) {
            System.err.println("SecurityException: security manager does " +
                    "not allow operation.");
            System.exit(3);
        }
        catch (IOException e) {
            System.err.println("IOException: Trouble reading/writing to the" +
                    " server.");
            e.printStackTrace();
            System.exit(4);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception: An unknown exception has occured!");
            System.exit(5);
        }
    }

    /**
     * Helper method that modularizes the creation of the client.
     *
     * @param args the command line arguments
     * @return ChargenClient the client used to connect to the server
     * @throws UnknownHostException when the host cannot be found
     * @throws IOException when a read/write error has occured
     * @throws SocketException when the socket does not exist
     */
    private static ChargenClient createTcp(String[] args) throws 
                            UnknownHostException, IOException, SocketException {
        ChargenClient client = new ChargenTcpClient(
                                                InetAddress.getByName(args[1]));
        if (args.length == 3) {
            client = new ChargenTcpClient(InetAddress.getByName(args[1]), 
                                                     Integer.parseInt(args[2]));
        } else {
            client = new ChargenTcpClient(InetAddress.getByName(args[1]),
                                            Integer.parseInt(args[2]), args[3]);
        }

        return client;
    }

    /**
     * Helper method that modularizes the creation of the client.
     *
     * @param args the command line arguments
     * @return ChargenClient the client used to connect to the server
     * @throws UnknownHostException when the host cannot be found
     * @throws IOException when a read/write error has occured
     * @throws SocketException when the socket does not exist
     */
    private static ChargenClient createUdp(String[] args) throws 
                            UnknownHostException, IOException, SocketException {
        ChargenClient client = null;

        if (args[2] == null) {
            client = new ChargenUdpClient(InetAddress.getByName(args[1]));
        } else {
            client = new ChargenUdpClient(InetAddress.getByName(args[1]), 
                                                     Integer.parseInt(args[2]));
        } 
        return client;
    }
}
