import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SystemInfoInterface extends Remote {

    /**
    * Remotly invocable method of awesomeness.
    * @return the message from the remote object.
    */

    public String whoAmI() throws RemoteException;
}
