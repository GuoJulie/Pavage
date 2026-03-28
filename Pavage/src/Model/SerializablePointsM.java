package Model;

import java.io.Serializable;

public class SerializablePointsM implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int Xpos;
    private int Ypos;
    
    public SerializablePointsM(PointsM point) {
        this.Xpos = point.getXpos();
        this.Ypos = point.getYpos();
    }
    
    public PointsM toPointsM() {
        return new PointsM(this.Xpos, this.Ypos);
    }
    
    // Getters for serialization
    public int getXpos() { return Xpos; }
    public int getYpos() { return Ypos; }
}
