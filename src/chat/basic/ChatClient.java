
package chat.basic;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient {
    private Socket clientSocket;
    private final String SERVER_ADDRESS = "127.0.0.1";
    private Scanner scanner;
    private PrintWriter out;
    
    public ChatClient(){
        scanner = new Scanner(System.in);
    }
    
    
    public void start() throws IOException{
        clientSocket = new Socket(SERVER_ADDRESS, ChatServer.PORT);
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        clientSocket.getOutputStream();
        messageLoop();
        System.out.println("Cliente conectado ao servidor em "+ SERVER_ADDRESS + ":"+ ChatServer.PORT);
    }
    
    private void messageLoop() throws IOException{
        String msg;
        do {
            System.out.print("Digite uma mensagem ou sair para finalizar: ");
            msg = scanner.nextLine();
            out.println(msg);  
        } while(!msg.equalsIgnoreCase("sair"));
    }
    
    public static void main(String[] args) {
        
        try {
            ChatClient client = new ChatClient(); /*Operação bloqueante*/
            client.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o cliente: "+ex.getMessage());
        }
        System.out.println("Cliente finaliado!");
    }
}