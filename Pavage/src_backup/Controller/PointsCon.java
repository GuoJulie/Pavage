package Controller;

import Model.PointsM;

public class PointsCon {

	/**
	 * Calcule la distance euclidienne entre deux points
	 * @param points1
	 * @param points2
	 * @return double distance
	 */
    public double lineSpace(PointsM points1, PointsM points2) {

    	int x1 = points1.getXpos();
    	int y1 = points1.getYpos();
    	int x2 = points2.getXpos();
    	int y2 = points2.getYpos();
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)* (y1 - y2));
        return lineLength;
    }
    
    /**
     * Calculer la distance d'un point par rapport a une ligne
     * @param points0 Le point qui n'est pas sur la ligne
     * @param points1 Un point de la ligne
     * @param points2 Un autre point de la ligne
     * @return double distance
     */
    public double pointToLine (PointsM points0, PointsM points1, PointsM points2) {
    	
		double space = 0;
        double a, b, c;
        
        a = lineSpace(points1, points2);//Longueur du segment de droite
        b = lineSpace(points1, points0);//la distance entre le points1 et points0
        c = lineSpace(points2, points0);//la distance entre le points2 et points0
        
        if (c <= 0.000001 || b <= 0.000001) {
            space = 0;
            return space;
         }

         if (a <= 0.000001) {
            space = b;
            return space;
         }

         if (c * c >= a * a + b * b) {
            space = b;
            return space;
         }

         if (b * b >= a * a + c * c) {
            space = c;
            return space;
         }

         double p = (a + b + c) / 2;//Demi circonference
         double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));//Helen formule pour la zone
         space = 2 * s / a; // Renvoie la distance entre le point et la ligne (en utilisant la formule de la zone de triangle pour trouver la hauteur)
         return space;
	}
    
    
}
