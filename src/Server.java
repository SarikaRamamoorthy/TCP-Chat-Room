import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server
 */
public class Server {
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }
    
    public void startServer() {
        try {
            System.out.println("Server listening at port 6789");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                System.out.println(clientHandler.getClientUserName()+" has connected!");
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
            
        } catch (Exception e) {
            closeServerSocket();
        }
    }

    public void closeServerSocket() {
        try {
            if(serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(6789);
            Server server = new Server(serverSocket);
            server.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}