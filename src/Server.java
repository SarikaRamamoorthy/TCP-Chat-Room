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
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}