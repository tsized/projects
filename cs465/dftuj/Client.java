import java.rmi.NotBoundException;                                              
import java.rmi.RemoteException;                                                
import java.rmi.registry.LocateRegistry;                                        
import java.rmi.registry.Registry; 
import java.util.List;

/**
 * A class which models a client that conencts to a remote gradebook.
 *
 * @author Tim Sizemore
 * @author Jordan Chapman
 * @version 12-3-12
 */
public class Client {
    private Registry       registry;
    private GradeInterface stub;

    /**
     * Creates a new client with the given host and port.
     *
     * @param hostname           The host on which to connect
     * @param port               The port on which to connect
     * @throws RemoteException   if a connection-related error occurs.
     * @throws NotBoundException if port is not bound.
     */
    public Client(String hostname, int port) throws RemoteException, 
                                                             NotBoundException {
        registry = LocateRegistry.getRegistry(hostname, port);
        stub = (GradeInterface) registry.lookup("gradeBook");
    }
    
    /**
     * Retruns the GradeInterface so the caller can use it.
     *
     * @return The GradeInterface associated with this client
     */
    public GradeInterface getGradeInterface() {
        return stub;
    }
}
