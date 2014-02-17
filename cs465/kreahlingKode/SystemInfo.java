import java.rmi.RemoteException;

public class SystemInfo implements SystemInfoInterface {

    private String name;
    private String arch;
    private String version;

    public SystemInfo() {
        name = "os.name";
        arch = "os.arch";
        version = "os.version";
    }

    public String whoAmI() {
        return System.getProperty(name) + " : " + System.getProperty(arch) +
               " : " + System.getProperty(version);
    }

}
