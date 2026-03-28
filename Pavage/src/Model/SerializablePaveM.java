package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializablePaveM implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<SerializablePointsM> pointList;
    private ArrayList<Integer> baseindice;
    private ArrayList<SerializablePointsM> sym_hor;
    private ArrayList<SerializablePointsM> sym_ver;
    private SerializablePointsM centre;
    private int longueur_pave;
    
    public SerializablePaveM(PaveM pavem) {
        this.baseindice = new ArrayList<>(pavem.getBaseindice());
        this.longueur_pave = pavem.getLongueur_pave();
        this.pointList = new ArrayList<>();
        for (PointsM point : pavem.getPointList()) {
            this.pointList.add(new SerializablePointsM(point));
        }
        this.sym_hor = new ArrayList<>();
        for (PointsM point : pavem.getSym_hor()) {
            this.sym_hor.add(new SerializablePointsM(point));
        }
        this.sym_ver = new ArrayList<>();
        for (PointsM point : pavem.getSym_ver()) {
            this.sym_ver.add(new SerializablePointsM(point));
        }
        this.centre = new SerializablePointsM(pavem.getCentre());
    }
    
    public PaveM toPaveM() {
        PaveM pavem = new PaveM();
        pavem.setBaseindice(new ArrayList<>(this.baseindice));
        pavem.setLongueur_pave(this.longueur_pave);
        ArrayList<PointsM> points = new ArrayList<>();
        for (SerializablePointsM point : this.pointList) {
            points.add(point.toPointsM());
        }
        pavem.setPointList(points);
        ArrayList<PointsM> symHor = new ArrayList<>();
        for (SerializablePointsM point : this.sym_hor) {
            symHor.add(point.toPointsM());
        }
        pavem.setSym_hor(symHor);
        ArrayList<PointsM> symVer = new ArrayList<>();
        for (SerializablePointsM point : this.sym_ver) {
            symVer.add(point.toPointsM());
        }
        pavem.setSym_ver(symVer);
        pavem.setCentre(this.centre.toPointsM());
        return pavem;
    }
    
    // Getters for serialization
    public ArrayList<SerializablePointsM> getPointList() { return pointList; }
    public ArrayList<Integer> getBaseindice() { return baseindice; }
    public ArrayList<SerializablePointsM> getSym_hor() { return sym_hor; }
    public ArrayList<SerializablePointsM> getSym_ver() { return sym_ver; }
    public SerializablePointsM getCentre() { return centre; }
    public int getLongueur_pave() { return longueur_pave; }
}
