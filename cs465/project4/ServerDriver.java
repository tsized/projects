import java.rmi.Remote;                                                         
import java.rmi.RemoteException;                                                
import java.rmi.registry.LocateRegistry;                                        
import java.rmi.registry.Registry;                                              
import java.rmi.server.UnicastRemoteObject;  

public class ServerDriver {                                                           
    public static void main(String args[]) {                                    
        if (args.length != 1) {
           usage(); 
        }
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException ex) {
           usage(); 
        }
        System.out.println("Server program started...");                        
        try {                                                                   
            Server     server        = new Server(port);
            GradeInterface gradeBook = new GradeBook();
            server.bindObject(gradeBook);
            System.out.println("Handling requests...");                         
        } catch (RemoteException ex) {                                          
            System.out.println("Unexpected exception: " + ex);                  
            ex.printStackTrace();                                               
        }                                                                       
    }                                                                           
    /**
     * Prints the usage message then exits the program with error code 1.
     */
    private static void usage() {
        System.out.println("Usage: java ServerDriver <port>\n");
        System.exit(1);
    }
}                       

