package chat.basic;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {

    public static final int PORT = 4000;
    /*Declarando a porta que irei utilizar*/
    private ServerSocket serverSocket;
    private final List<ClientSocket> clients = new LinkedList<>();

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado na porta " + PORT);
        clientConnectionLoop();
    }

    private void clientConnectionLoop() throws IOException {
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            clients.add(clientSocket);
            new Thread(() -> clientMessageLoop(clientSocket)).start();

        }
    }

    /*Ficará aguardando a conexão com o cliente*/
    private void clientMessageLoop(ClientSocket clientSocket) {
        String msg;
        try {
            while ((msg = clientSocket.getMessage()) != null) {
                if ("sair".equalsIgnoreCase(msg)) 
                    return;
                
                System.out.printf("Msg recebida do cliente %s: %s\n",
                        clientSocket.getRemoteSocketAddress(), msg);
                sendMsgToAll(clientSocket, msg);
            }
        } finally {
            clientSocket.close();
        }
    }

    public void sendMsgToAll(ClientSocket sender, String msg) {
        Iterator<ClientSocket> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ClientSocket clientSocket = iterator.next();
            if (!sender.equals(clientSocket)) {
                if (!clientSocket.sendMsg("cliente " + sender.getRemoteSocketAddress() + ": " + msg)) {
                    iterator.remove(); 
                }
            }
        }
        /*Se o sender não é igual ao cliente atual do loop, ele mandará a mensagem*/
    }

    public static void main(String[] args) {

        try {
            ChatServer server = new ChatServer();
            server.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o servidor: " + ex.getMessage());
        }
    }

}

