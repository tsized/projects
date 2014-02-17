import java.lang.String;
import java.io.IOException;
import java.net.SocketException;

public class ChargenServerDriver {
    private final static String USAGE_STRING = "ChargenServerDriver Usage: " +
        "java ChargenServerDriver <TCP/UDP> <port> |Defacto,AlphaNumeric,Numeric,NaN|";
    public static void main(String[] args) {
        int argLength = args.length;
        ChargenCharacterSource charSource = 
                                           new DefactoChargenCharacterSource();
        try{
            if (argLength == 2) {
                try {
                    if (args[0].equalsIgnoreCase("TCP")) {
                        ChargenTcpServer tcpServ = 
                                   new ChargenTcpServer(Integer.parseInt(args[1]));
                                   tcpServ.listen();
                    } else if (args[0].equalsIgnoreCase("UDP")) {
                        ChargenUdpServer udpServ = 
                                   new ChargenUdpServer(Integer.parseInt(args[1]));
                                   udpServ.listen();
                    } else {
                        System.out.println(USAGE_STRING);
                    } 
                } catch(IOException ex){
                    System.out.println(ex.toString());
                }
            }
            
            else  if (argLength == 3) {
                if(args[2].equalsIgnoreCase("Defacto")) {
                    charSource = new DefactoChargenCharacterSource();
                } else  if(args[2].equalsIgnoreCase("AlphaNumeric")) {
                    charSource = new AlphaNumericCharacterSource();
                } else  if(args[2].equalsIgnoreCase("Numeric")) {
                    charSource = new NumericCharacterSource();
                } else  if(args[2].equalsIgnoreCase("NaN")) {
                    charSource = new NonAlphaNumericCharacterSource();
                }
                try {
                    if (args[0].equalsIgnoreCase("TCP")) {
                        ChargenTcpServer tcpServ = 
                              new ChargenTcpServer(Integer.parseInt(args[1]), 
                                                                           charSource);
                        tcpServ.listen();
                    } else if (args[0].equalsIgnoreCase("UDP")) {
                        ChargenUdpServer udpServ = 
                              new ChargenUdpServer(Integer.parseInt(args[1]), 
                                                                           charSource);
                        udpServ.listen();
                    } else {
                        System.out.println(USAGE_STRING);
                    }
                } catch(IOException ex) {
                    System.out.println(ex.toString());
                }
            }
        }
        catch(ChargenServerException cse) {
            System.out.println(cse.getMessage());
        }
    }

    
}
