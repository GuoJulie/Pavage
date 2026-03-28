package Network;

import java.io.Serializable;
import java.util.ArrayList;

public class PaveData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<PointData> pointList;
    private ArrayList<Integer> baseindice;
    private PointData centre;
    private int longueur_pave;
    private String userId;
    private long timestamp;

    public PaveData() {
        this.pointList = new ArrayList<>();
        this.baseindice = new ArrayList<>();
        this.centre = new PointData();
        this.longueur_pave = 0;
        this.userId = "";
        this.timestamp = System.currentTimeMillis();
    }

    public ArrayList<PointData> getPointList() {
        return pointList;
    }

    public void setPointList(ArrayList<PointData> pointList) {
        this.pointList = pointList;
    }

    public ArrayList<Integer> getBaseindice() {
        return baseindice;
    }

    public void setBaseindice(ArrayList<Integer> baseindice) {
        this.baseindice = baseindice;
    }

    public PointData getCentre() {
        return centre;
    }

    public void setCentre(PointData centre) {
        this.centre = centre;
    }

    public int getLongueur_pave() {
        return longueur_pave;
    }

    public void setLongueur_pave(int longueur_pave) {
        this.longueur_pave = longueur_pave;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
