package Network;

import java.io.*;
import java.net.*;
import java.util.List;

public class PavageClient {
    private String serverHost;
    private int serverPort;
    
    public PavageClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }
    
    public String savePaveData(PaveData data) {
        try (Socket socket = new Socket(serverHost, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("SAVE");
            out.writeObject(data);
            return (String) in.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            return "ERROR: " + e.getMessage();
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<PaveData> loadUserData(String userId) {
        try (Socket socket = new Socket(serverHost, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("LOAD");
            out.writeObject(userId);
            return (List<PaveData>) in.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading user data: " + e.getMessage());
            return null;
        }
    }
    
    public PaveData getLatestPaveData(String userId) {
        try (Socket socket = new Socket(serverHost, serverPort);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            
            out.writeObject("GET_LATEST");
            out.writeObject(userId);
            return (PaveData) in.readObject();
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error getting latest data: " + e.getMessage());
            return null;
        }
    }
}
