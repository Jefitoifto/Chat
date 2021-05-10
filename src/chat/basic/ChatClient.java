
package chat.basic;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private ClientSocket clientSocket;
    private Scanner scanner;

    public ChatClient() {
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        try {
            clientSocket = new ClientSocket(
                    new Socket(SERVER_ADDRESS, ChatServer.PORT));

            System.out.println("Cliente conectado ao servidor em " + SERVER_ADDRESS + ":" + ChatServer.PORT);
            new Thread(this).start();
            messageLoop();
        } finally {
            clientSocket.close();
        }
    }

    @Override
    public void run() {
        String msg;
        while ((msg = clientSocket.getMessage()) != null) {
            System.out.println("Msg recebida do servidor: \n"+msg);
        }
    }

    private void messageLoop() throws IOException {
        String msg;
        do {
            System.out.print("Digite uma mensagem ou sair para finalizar: ");
            msg = scanner.nextLine();
            clientSocket.sendMsg(msg);
            clientSocket.sendMsg(msg);

        } while (!msg.equalsIgnoreCase("sair"));
    }

    public static void main(String[] args) {

        try {
            ChatClient client = new ChatClient();
            /*Operação bloqueante*/
            client.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o cliente: " + ex.getMessage());
        }
        System.out.println("Cliente finaliado!");
    }
}
