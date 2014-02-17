import java.rmi.Remote;                                                         
import java.rmi.RemoteException;                                                
import java.rmi.registry.LocateRegistry;                                        
import java.rmi.registry.Registry;                                              
import java.rmi.server.UnicastRemoteObject;  

public class Server {                                                           
    private Registry registry;                                                  
                                                                                
    public Server(int port) throws RemoteException {                          

        if (System.getSecurityManager() == null) {                              
            System.out.println("installing security manager...");               
            System.setSecurityManager(new SecurityManager());                   
        }                                                                       
        System.out.println("creating registry...");                             
        registry = LocateRegistry.createRegistry(port);                       
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
                                                                                
    public static void main(String args[]) {                                    
        System.out.println("server program started...");                        
        try {                                                                   
            Server      server      = new Server(25483);
            SystemInfo  info        = new SystemInfo();

            server.bindObject(info);
            System.out.println("handling requests...");                         
        } catch (RemoteException ex) {                                          
            System.out.println("unexpected exception: " + ex);                  
            ex.printStackTrace();                                               
        }                                                                       
    }                                                                           
}                       

