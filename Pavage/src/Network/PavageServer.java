package Network;

import java.io.*;
import java.net.*;
import java.util.*;

public class PavageServer {
    private static final int PORT = 12345;
    private static Map<String, List<PaveData>> userData = new HashMap<>();
    
    public static void main(String[] args) {
        System.out.println("Pavage Server starting on port " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
    
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
                
                String command = (String) in.readObject();
                
                if ("SAVE".equals(command)) {
                    PaveData data = (PaveData) in.readObject();
                    savePaveData(data);
                    out.writeObject("SUCCESS");
                } else if ("LOAD".equals(command)) {
                    String userId = (String) in.readObject();
                    List<PaveData> userHistory = loadUserData(userId);
                    out.writeObject(userHistory);
                } else if ("GET_LATEST".equals(command)) {
                    String userId = (String) in.readObject();
                    PaveData latest = getLatestPaveData(userId);
                    out.writeObject(latest);
                }
                
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Client handler exception: " + e.getMessage());
            }
        }
        
        private synchronized void savePaveData(PaveData data) {
            String userId = data.getUserId();
            userData.computeIfAbsent(userId, k -> new ArrayList<>()).add(data);
            System.out.println("Saved data for user: " + userId + ", total records: " + userData.get(userId).size());
        }
        
        private synchronized List<PaveData> loadUserData(String userId) {
            return new ArrayList<>(userData.getOrDefault(userId, new ArrayList<>()));
        }
        
        private synchronized PaveData getLatestPaveData(String userId) {
            List<PaveData> userHistory = userData.get(userId);
            if (userHistory == null || userHistory.isEmpty()) {
                return null;
            }
            return userHistory.get(userHistory.size() - 1);
        }
    }
}
