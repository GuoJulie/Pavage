package Model;
import java.awt.Point;

public class PointsM extends Point{

	private int Xpos; 
	private int Ypos;
	
	
	public PointsM() {
		super();
	}

	public PointsM(int Xpos, int Ypos) {
		this.Xpos = Xpos;
		this.Ypos = Ypos;
	}
	
	public int getXpos() {
		return Xpos;
	}
	public void setXpos(int xpos) {
		Xpos = xpos;
	}
	public int getYpos() {
		return Ypos;
	}
	public void setYpos(int ypos) {
		Ypos = ypos;
	}
	
	
}
