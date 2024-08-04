import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    bufferedWriter.write(username);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
        
                    Scanner scanner = new Scanner(System.in);
        
                    while (socket.isConnected()) {
                        String messageToSend = scanner.nextLine();
                        bufferedWriter.write(username+" : "+messageToSend);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    }
        
        
                } catch (IOException e) {
                    closeEverything(socket, bufferedReader, bufferedWriter);
                }
            }
            
        }).start();
    }

    public void listenForMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String messageFromGroupChat;
                while (socket.isConnected()) {
                    try {
                        messageFromGroupChat = bufferedReader.readLine();
                        System.out.println(messageFromGroupChat);
                    } catch (Exception e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
            
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if(socket != null) {
                socket.close();
            }
            if(bufferedReader != null) {
                bufferedReader.close();
            }
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the username for group chat : ");
        String username = scanner.nextLine();
        Socket socket;
        try {
            socket = new Socket("localhost", 6789);
            Client client = new Client(socket, username);
            client.sendMessage();
            client.listenForMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
