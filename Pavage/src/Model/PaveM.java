package Model;

import java.util.ArrayList;

public class PaveM {

	private ArrayList<PointsM> pointList;
	private ArrayList<Integer> baseindice;	//Marque la position de base du sommet
	private ArrayList<PointsM> sym_hor;	//L'axe de symetrie horizontal	(peut-etre omis)
	private ArrayList<PointsM> sym_ver;	//L'axe de symetrie vertical (peut-etre omis)
	private PointsM centre;	//Point central
	private int longueur_pave;	//Longueur du cote du carre
	

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
