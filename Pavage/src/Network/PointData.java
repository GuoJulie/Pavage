package Network;

import java.io.Serializable;

public class PointData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private double x;
    private double y;
    private double xpos;
    private double ypos;
    
    public PointData() {}
    
    public PointData(double x, double y, double xpos, double ypos) {
        this.x = x;
        this.y = y;
        this.xpos = xpos;
        this.ypos = ypos;
    }
    
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    
    public double getXpos() { return xpos; }
    public void setXpos(double xpos) { this.xpos = xpos; }
    
    public double getYpos() { return ypos; }
    public void setYpos(double ypos) { this.ypos = ypos; }
}
