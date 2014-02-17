import java.rmi.Remote;                                                         
import java.rmi.RemoteException;                                                
import java.rmi.registry.LocateRegistry;                                        
import java.rmi.registry.Registry;                                              
import java.rmi.server.UnicastRemoteObject;  

public class Server {                                                           
    private GradeBook gradebook; 
    private Registry registry;                                                  
                                                                                
    public Server(int port) throws RemoteException {                          

        if (System.getSecurityManager() == null) {                              
            System.out.println("Installing security manager...");               
            System.setSecurityManager(new SecurityManager());                   
        }                                                                       
        System.out.println("Creating registry...");                             
        registry  = LocateRegistry.createRegistry(port);                       
        gradebook = new GradeBook();
    }                                                                           
                                                                                
    public void bindObject(Remote obj) throws RemoteException {
        // The static method UnicastRemoteObject.exportObject exports the
        // supplied remote object to receive incoming remote method
        // invocations on an anonymous TCP port and returns the stub for
        // the remote object to pass to clients.
        Remote      skeleton    = UnicastRemoteObject.exportObject(obj, 0);
        //rebinds the specific name to a new remote object.
        registry.rebind("sysInfo", skeleton); 
    }                                                                           
}                       
