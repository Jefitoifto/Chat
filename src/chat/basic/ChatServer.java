package chat.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class ChatServer {
    public static final int PORT = 4000; /*Declarando a porta que irei utilizar*/
    private ServerSocket serverSocket;
    
    public void start() throws IOException{
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado na porta "+PORT);
        clientConnectionLoop();
    }
   
    private void clientConnectionLoop() throws IOException{
        while(true){
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            String msg = null;
            
            //new Thread(() -> ).start();
            System.out.println("Mensagem recebida do cliente "+ clientSocket.getRemoteSocketAddress()+
                    ": "+ msg);
        }
    }        /*Ficará aguardando a conexão com o cliente*/
    
    public void clientMessageLoop(ClientSocket clientSocket){
        String msg;
        while((msg = clientSocket.getMessage()) != null){
            System.out.printf("Msg recebida do cliente %s: %s\n", msg);
        }
    }
    
    public static void main(String[] args) {
        
        try {
            ChatServer server = new ChatServer();
            server.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o servidor: "+ ex.getMessage());
        }
    }
    
}

