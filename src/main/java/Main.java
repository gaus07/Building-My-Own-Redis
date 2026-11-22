import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final byte[] PONG_RESPONSE = "+PONG\r\n".getBytes(StandardCharsets.UTF_8);

    public static void main(String[] args) {
        int port = 6379;

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(port));

            System.out.println("Redis server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New Client Connected!");

                // Handling each client in a separate thread
                Thread clientThread = new Thread(() -> handleClient(clientSocket));

                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Server IOException: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                Socket socket = clientSocket;
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
        ) {

            byte[] buffer = new byte[1024];
            int byteRead;

            while ((byteRead = inputStream.read(buffer)) != -1) {
                outputStream.write(PONG_RESPONSE);
                outputStream.flush();
            }
        } catch (IOException e) {
            System.out.println("Client Connection Closed: " + e.getMessage());
        }
    }
}
