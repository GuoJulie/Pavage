package Controller;

import Model.PointsM;

public class PointsCon {

	/**
	 * Calcule la distance entre deux points	计算两点之间的距离
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
     * Calculer la distance point a ligne	计算点到线的距离
     * @param points0
     * @param points1
     * @param points2
     * @return double distance
     */
    public double pointToLine (PointsM points0, PointsM points1, PointsM points2) {
    	
		double space = 0;
        double a, b, c;
        
        a = lineSpace(points1, points2);// 线段的长度	Longueur du segment de droite
        b = lineSpace(points1, points0);// (x1,y1)到点的距离	la distance entre le points1 et points0
        c = lineSpace(points2, points0);// (x2,y2)到点的距离	la distance entre le points2 et points0
        
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

         double p = (a + b + c) / 2;// 半周长	Demi circonference
         double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积	Helen formule pour la zone
         space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
         					// Renvoie la distance entre le point et la ligne (en utilisant la formule de la zone de triangle pour trouver la hauteur)
         return space;
	}
    
    
}
