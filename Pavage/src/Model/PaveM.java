package Model;

import java.util.ArrayList;

public class PaveM {

	private ArrayList<PointsM> pointList;
	private ArrayList<Integer> baseindice;	//记录pave的基本顶点位置 	Marque la position de base du sommet
	private ArrayList<PointsM> sym_hor;	//橫对称轴	L'axe de symetrie horizontal	//可省略	 peut-etre omis
	private ArrayList<PointsM> sym_ver;	//纵对称轴	L'axe de symetrie vertical 	  	//可省略	 peut-etre omis
	private PointsM centre;	//中心点	Point central
	private int longueur_pave;	//carre的边长	Longueur du cote du carre
	

	public PaveM() {
		pointList = new ArrayList<PointsM>();
		baseindice = new ArrayList<Integer>();
		sym_hor = new ArrayList<PointsM>();
		sym_ver = new ArrayList<PointsM>();
		centre = new PointsM();
		longueur_pave = 0;
	}
	
	
	public ArrayList<Integer> getBaseindice() {
		return baseindice;
	}

	public void setBaseindice(ArrayList<Integer> baseindice) {
		this.baseindice = baseindice;
	}

	public PointsM getCentre() {
		return centre;
	}


	public void setCentre(PointsM centre) {
		this.centre = centre;
	}


	public int getLongueur_pave() {
		return longueur_pave;
	}

	public void setLongueur_pave(int longueur_pave) {
		this.longueur_pave = longueur_pave;
	}

	public ArrayList<PointsM> getSym_hor() {
		return sym_hor;
	}

	public void setSym_hor(ArrayList<PointsM> sym_hor) {
		this.sym_hor = sym_hor;
	}

	public ArrayList<PointsM> getSym_ver() {
		return sym_ver;
	}

	public void setSym_ver(ArrayList<PointsM> sym_ver) {
		this.sym_ver = sym_ver;
	}

	public ArrayList<PointsM> getPointList() {
		return pointList;
	}

	public void setPointList(ArrayList<PointsM> pointList) {
		this.pointList = pointList;
	}
	
}
