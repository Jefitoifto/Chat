
package chat.basic;

import java.io.*;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;
    
    public ClientSocket(Socket socket) throws IOException{ /*O socket do lado cliente é usado para se conectar com o servior, e virse versa*/
       this.socket = socket;
       System.out.println("Cliente "+ socket.getRemoteSocketAddress()+" conectou");
       this.in = new BufferedReader(
       new InputStreamReader(socket.getInputStream()));
       this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    public SocketAddress getRemoteSocketAddress(){
        return socket.getRemoteSocketAddress();
    }
    
    public String getMessage(){
        try {
            return in.readLine();
        } catch(IOException e){
            return null;
        }
    }
    
    public boolean sendMsg(String msg){
        out.println(msg);
        return !out.checkError();
    }
}
