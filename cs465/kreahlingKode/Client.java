import java.rmi.NotBoundException;                                              
import java.rmi.RemoteException;                                                
import java.rmi.registry.LocateRegistry;                                        
import java.rmi.registry.Registry; 

public class Client {
    private Registry            registry;
    private SystemInfoInterface stub;

    public Client(String hostname) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(hostname, 25483);
        stub = (SystemInfoInterface) registry.lookup("sysInfo");
    }

    public void go() throws RemoteException{
        System.out.println(stub.whoAmI());
    }

    // so much error checking, NOT done.
    public static void main(String[] args) {
        try {
            Client tester = new Client(args[0]);
            tester.go();
        } catch (Exception e) {
            System.out.println("Bad to ignore exceptions!");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
