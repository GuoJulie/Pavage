package Network;

import Model.PaveM;
import Model.PointsM;
import java.util.ArrayList;

public class DataConverter {
    
    public static PaveData toPaveData(PaveM paveM, String userId) {
        PaveData data = new PaveData();
        data.setUserId(userId);
        data.setTimestamp(System.currentTimeMillis());
        data.setLongueur_pave(paveM.getLongueur_pave());
        
        if (paveM.getCentre() != null) {
            PointData centre = new PointData(
                paveM.getCentre().getX(),
                paveM.getCentre().getY(),
                paveM.getCentre().getXpos(),
                paveM.getCentre().getYpos()
            );
            data.setCentre(centre);
        }
        
        ArrayList<PointData> pointList = new ArrayList<>();
        if (paveM.getPointList() != null) {
            for (PointsM point : paveM.getPointList()) {
                pointList.add(new PointData(
                    point.getX(),
                    point.getY(),
                    point.getXpos(),
                    point.getYpos()
                ));
            }
        }
        data.setPointList(pointList);
        
        data.setBaseindice(new ArrayList<>(paveM.getBaseindice()));
        
        return data;
    }
    
    public static PaveM toPaveM(PaveData data) {
        PaveM paveM = new PaveM();
        paveM.setLongueur_pave(data.getLongueur_pave());
        
        if (data.getCentre() != null) {
            PointsM centre = new PointsM();
            centre.x = (int) data.getCentre().getX();
            centre.y = (int) data.getCentre().getY();
            centre.setXpos((int) data.getCentre().getXpos());
            centre.setYpos((int) data.getCentre().getYpos());
            paveM.setCentre(centre);
        }
        
        ArrayList<PointsM> pointList = new ArrayList<>();
        if (data.getPointList() != null) {
            for (PointData pd : data.getPointList()) {
                PointsM point = new PointsM();
                point.x = (int) pd.getX();
                point.y = (int) pd.getY();
                point.setXpos((int) pd.getXpos());
                point.setYpos((int) pd.getYpos());
                pointList.add(point);
            }
        }
        paveM.setPointList(pointList);
        
        paveM.setBaseindice(data.getBaseindice());
        
        return paveM;
    }
}
