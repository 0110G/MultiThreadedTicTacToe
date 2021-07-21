import java.io.*;
import java.net.Socket;

public class Connector {
    private int port;
    private String ip;
    private Socket socket;
    private DataInputStream inStream;
    private DataOutputStream outStream;
    private BufferedReader bufferedReader;

    Connector(String ip, int port) {
        this.port = port;
        this.ip = ip;
        try {
            socket = new Socket(ip, port);
            inStream = new DataInputStream(socket.getInputStream());
            outStream = new DataOutputStream(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendToServer(String clientMessage) {
        try {
            outStream.writeUTF(clientMessage);
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendToServerWithResponse(String clientMessage) {
        String serverMessage = "";
        try {
            outStream.writeUTF(clientMessage);
            outStream.flush();
            serverMessage = inStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverMessage;
    }

    public void closeConnection() {
        try {
            outStream.close();
            outStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}