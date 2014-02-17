/**
 * The abstract <code>ChargenServer</code> that implements methods that all 
 * <code>ChargenServers</code> can use.
 *
 * @author Chris Ward
 * @author Tim Sizemore
 *
 * @version Wednesday, 10 October, 2012
 *
 */
public abstract class AbstractChargenServer implements ChargenServer {
    /** The default port on which our server listens, as specified in RFC864.*/
    private final int DEFAULT_PORT = 19;
    /** The user-specified port. */
    private int port;
    /** The generator for the chars returned to client. */
    private ChargenCharacterSource charSource;
    
    /** 
     * The default constructor for <code>ChargenServer</code>. 
     * */
    public AbstractChargenServer() {
        this.charSource = new DefactoChargenCharacterSource();
        this.port = DEFAULT_PORT;
    }
    
    /**
     * Constructor which instantiates a server with only a user-given port.
     * 
     * @param port The port on which the server will listen.
     */
    public AbstractChargenServer(int port) {
        this.charSource = new DefactoChargenCharacterSource();
        this.port = port;
    }
    
    /**
     * Constructor which instantiates a server with a user-given 
     * <code>ChargenCharacterSource</code> and the default port.
     * 
     * @param charSource - The user-given <code>ChargenCharacterSource</code>.
     */
    public AbstractChargenServer(ChargenCharacterSource charSource) {
        this.charSource = charSource;
        this.port = DEFAULT_PORT;
    }
    
    /**
     * Constructor which instantiates a server with a user specified port and 
     * <code>ChargenCharacterSource</code>.
     *
     * @param port The user-specified port on which the server listens.
     * @param charSource The <code>ChargenCharacterSource</code> that will
     *        generate the chars returned to the client.
     */
    public AbstractChargenServer(int port, ChargenCharacterSource charSource) {
        this.charSource = charSource;
        this.port = port;
    }
    
    /**
     * Returns the current server's port.
     *
     * @return port The port on which the server is currently listening.
     */
    protected int getPort() {
        return this.port;
    }
    
    /**
     * Returns the <code>ChargenCharacterSource</code> the server is currently
     * using.
     *
     * @return charSource - The current char generator.
     */
    protected ChargenCharacterSource getCharacterSource() {
        return this.charSource;
    }
    
    /**
     * Listens for connections to the server.
     */
    public abstract void listen() throws ChargenServerException;
}
