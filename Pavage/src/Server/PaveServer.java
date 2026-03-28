package Server;

import Model.SerializablePaveM;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PaveServer {
    private static final int PORT = 8888;
    private static List<SerializablePaveM> operationHistory = new ArrayList<>();
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());
                
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        @Override
        public void run() {
            try {
                out = new ObjectOutputStream(clientSocket.getOutputStream());
                in = new ObjectInputStream(clientSocket.getInputStream());
                
                String request;
                while ((request = (String) in.readObject()) != null) {
                    System.out.println("Received request: " + request);
                    
                    switch (request) {
                        case "GET_HISTORY":
                            sendHistory();
                            break;
                        case "SAVE_OPERATION":
                            saveOperation();
                            break;
                        case "GET_LATEST_STATE":
                            sendLatestState();
                            break;
                        default:
                            out.writeObject("UNKNOWN_REQUEST");
                            break;
                    }
                    out.flush();
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Client handler error: " + e.getMessage());
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (clientSocket != null) clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing resources: " + e.getMessage());
                }
            }
        }
        
        private void sendHistory() throws IOException {
            out.writeObject(operationHistory);
        }
        
        private void saveOperation() throws IOException, ClassNotFoundException {
            SerializablePaveM pavem = (SerializablePaveM) in.readObject();
            operationHistory.add(pavem);
            System.out.println("Saved operation: " + pavem.getPointList().size() + " points");
            out.writeObject("OPERATION_SAVED");
        }
        
        private void sendLatestState() throws IOException {
            if (!operationHistory.isEmpty()) {
                out.writeObject(operationHistory.get(operationHistory.size() - 1));
            } else {
                out.writeObject(null);
            }
        }
    }
}
