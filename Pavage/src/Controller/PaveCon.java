package Controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import Model.PaveM;
import Model.PointsM;

public class PaveCon {

	private PaveM pave;
	
	
	public PaveCon(PaveM pave) {
		this.pave = pave;
		pave.setLongueur_pave(100);
		carre();
	}
	
	public void carre() {
		int a = pave.getLongueur_pave()/2;
		pave.getPointList().add(new PointsM(-a, -a));
		pave.getPointList().add(new PointsM(-a, a));
		pave.getPointList().add(new PointsM(a, a));
		pave.getPointList().add(new PointsM(a, -a));
		
		for(int i = 0; i < pave.getPointList().size(); i++) {
			pave.getBaseindice().add(i);
		}
		
		int Sym_hor_y = (pave.getPointList().get(pave.getBaseindice().get(0)).getYpos()+pave.getPointList().get(pave.getBaseindice().get(1)).getYpos())/2;
		int Sym_ver_x = (pave.getPointList().get(pave.getBaseindice().get(0)).getXpos()+pave.getPointList().get(pave.getBaseindice().get(3)).getXpos())/2;
		pave.setCentre(new PointsM(Sym_ver_x, Sym_hor_y));
		
		pave.getSym_hor().add(new PointsM(-2*pave.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		pave.getSym_hor().add(new PointsM(2*pave.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		
		pave.getSym_ver().add(new PointsM(Sym_ver_x, -2*pave.getLongueur_pave() + Sym_hor_y));
		pave.getSym_ver().add(new PointsM(Sym_ver_x, 2*pave.getLongueur_pave() + Sym_hor_y));
		
	}
	

	public int chercherSommetIndice(int Xpos, int Ypos) {
		int indice = -1;
		for(int i = 0; i < pave.getPointList().size(); i++) {
			if(pave.getPointList().get(i).getXpos() == Xpos && pave.getPointList().get(i).getYpos() == Ypos)
				indice = i;
		}
		return indice;
	}
	
	public int chercherBaseSommetIndice(int Xpos, int Ypos) {
		int indice = -1;
		for(int i = 1; i < pave.getBaseindice().size(); i++) {
			int j = pave.getBaseindice().get(i);
			if(pave.getPointList().get(j).getXpos() == Xpos && pave.getPointList().get(j).getYpos() == Ypos)
				indice = j;
		}
		return indice;
	}
	
	public String symPoint(int indice) {
		String SymIndice = null;
		String SymIndice1 = null;
		String SymIndice2 = null;
		
		int Xpos = pave.getPointList().get(indice).getXpos();
		int Ypos = pave.getPointList().get(indice).getYpos();
		
		int tempindex1 = -1;
		if(chercherSommetIndice(Xpos, Ypos + pave.getLongueur_pave()) != -1) {
			tempindex1 = chercherSommetIndice(Xpos, Ypos + pave.getLongueur_pave());
			SymIndice1 = "h" + "_" + tempindex1 + "_" + "+";
		}else if(chercherSommetIndice(Xpos, Ypos - pave.getLongueur_pave()) != -1) {
			tempindex1 = chercherSommetIndice(Xpos, Ypos - pave.getLongueur_pave());
			SymIndice1 = "h" + "_" + tempindex1 + "_" + "-";
		}
		int tempindex2 = -1;
		if(chercherSommetIndice(Xpos + pave.getLongueur_pave(), Ypos) != -1) {
			tempindex2 = chercherSommetIndice(Xpos + pave.getLongueur_pave(), Ypos);
			SymIndice2 = "v" + "_" + tempindex2 + "_" + "+";
		}else if(chercherSommetIndice(Xpos - pave.getLongueur_pave(), Ypos) != -1) {
			tempindex2 = chercherSommetIndice(Xpos - pave.getLongueur_pave(), Ypos);
			SymIndice2 = "v" + "_" + tempindex2 + "_" + "-";
		}

		if(SymIndice1 != null && SymIndice2 != null) {
			int indice1 = -1;
			
			if(indice == 0) {
				indice1 = pave.getPointList().size();
			}else
				indice1 = indice;
			
			if(tempindex1 == indice1-1 && tempindex2 != indice1-1)
				SymIndice = SymIndice2;
			else if(tempindex1 != indice1-1 && tempindex2 == indice1-1)
				SymIndice = SymIndice1;
			else if(tempindex1 != indice1-1 && tempindex2 != indice1-1) {
				String temp[] = symPoint(indice1-1).split("_");
				if(temp[0].equals("h"))
					SymIndice = SymIndice1;
				else
					SymIndice = SymIndice2;
			}
		}else if(SymIndice1 != null && SymIndice2 == null) {
			SymIndice = SymIndice1;
		}else if(SymIndice1 == null && SymIndice2 != null) {
			SymIndice = SymIndice2;
		}
		
		return SymIndice;
	}
	
	public void addPoint(int indice, int Xpos, int Ypos) {
		
		int indice1 = indice;
		int indice11 = -1;
		
		String tempjurer[] = null;
		if(chercherSommetIndice(Xpos, Ypos) == -1 && symPoint(indice1) != null) {
			tempjurer = symPoint(indice1).split("_");
			
			if(indice1 == 0) {
				indice1 = pave.getPointList().size();			
				pave.getPointList().add(new PointsM(Xpos, Ypos));
				
			} else {
				
				for(int i = pave.getPointList().size() - 1; i > indice - 1; i--) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i + 1);
				}
				
				pave.getPointList().add(indice, new PointsM(Xpos, Ypos));
				
			}

			if(tempjurer[0].equals("h") && Integer.parseInt(tempjurer[1]) != -1 && Integer.parseInt(tempjurer[1]) != indice1 - 1) {
				
				if(Integer.parseInt(tempjurer[1]) > indice1){	

					indice11 = Integer.parseInt(tempjurer[1]) + 2;
					if(indice11 >= pave.getPointList().size()) {
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(new PointsM(Xpos, Ypos + pave.getLongueur_pave()));
						else
							pave.getPointList().add(new PointsM(Xpos, Ypos - pave.getLongueur_pave()));
					} else {
						
						for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
							int pos_i = pave.getBaseindice().indexOf(i);
							if(pos_i != -1)
								pave.getBaseindice().set(pos_i, i + 1);
						}
						
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));
						else
							pave.getPointList().add(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));
					}
					
				} else {
					indice11 = Integer.parseInt(tempjurer[1]) + 1;
					
					for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
						int pos_i = pave.getBaseindice().indexOf(i);
						if(pos_i != -1)
							pave.getBaseindice().set(pos_i, i + 1);
					}
					
					if(tempjurer[2].equals("+"))
						pave.getPointList().add(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));
					else
						pave.getPointList().add(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));
				}
				
				System.out.println("L'adjonction du sommet a reussi!" + "\n");
			
			} else if(tempjurer[0].equals("v") && Integer.parseInt(tempjurer[1]) != -1 && Integer.parseInt(tempjurer[1]) != indice1 - 1) {

				if(Integer.parseInt(tempjurer[1]) > indice1) {
					indice11 = Integer.parseInt(tempjurer[1]) + 2;
					if(indice11 >= pave.getPointList().size()) {
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(new PointsM(Xpos + pave.getLongueur_pave(), Ypos));
						else
							pave.getPointList().add(new PointsM(Xpos - pave.getLongueur_pave(), Ypos));
					}else {
						
						for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
							int pos_i = pave.getBaseindice().indexOf(i);
							if(pos_i != -1)
								pave.getBaseindice().set(pos_i, i + 1);
						}
						
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));
						else
							pave.getPointList().add(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));
					}
				}else {
					
					indice11 = Integer.parseInt(tempjurer[1]) + 1;
					
					for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
						int pos_i = pave.getBaseindice().indexOf(i);
						if(pos_i != -1)
							pave.getBaseindice().set(pos_i, i + 1);
					}
					
					if(tempjurer[2].equals("+"))
						pave.getPointList().add(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));
					else
						pave.getPointList().add(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));
				}
	
				System.out.println("L'adjonction du sommet a reussi!" + "\n");
				
			}else {
				System.out.println("L'adjonction du sommet a echoue!" + "\n");
			}
		}
	}
	
	public void modifierPoint(int indice, int Xpos, int Ypos) {

		int jurer = pave.getBaseindice().indexOf(indice);
		if(jurer != -1) {
			System.out.println("C'est le sommet initial de base de Pave et ne peut pas etre deplace!" + "\n");
		}else {
			int indice1 = indice;
			int indice11 = -1;
			
			String tempjurer[] = symPoint(indice1).split("_");
			
			if (tempjurer[0].equals("h") && tempjurer[2].equals("+")) {
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));
			}else if (tempjurer[0].equals("h") && tempjurer[2].equals("-")) {
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));
			}else if (tempjurer[0].equals("v") && tempjurer[2].equals("+")) {
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));
			}else if (tempjurer[0].equals("v") && tempjurer[2].equals("-")) {
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));
			}
			
			System.out.println("La deformation du sommet a reussi!" + "\n");
		}
	}
	
	public void supprimerPoint(int indice) {

		int jurer = pave.getBaseindice().indexOf(indice);
		if(jurer != -1) {
			System.out.println("C'est le sommet initial de base de Pave et ne peut pas etre supprime!" + "\n");
		} else {
			int indice1 = indice;
			int indice11 = -1;

			String tempjurer[] = symPoint(indice1).split("_");
			
			if (tempjurer[0].equals("h")) {
				indice11 = Integer.parseInt(tempjurer[1]);
			}else if (tempjurer[0].equals("v")) {
				indice11 = Integer.parseInt(tempjurer[1]);
			}
			
			if(indice1 < indice11) {
				for(int i = indice11; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice11);
				
				for(int i = indice1; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice1);
			}else {
				for(int i = indice1; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice1);
				
				for(int i = indice11; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice11);
			}
			System.out.println("La suppression du sommet a reussi!" + "\n");
		}
	}
	
    public boolean isInPolygon(int pointLon, int pointLat, int[] lon, int[] lat) {
        Point2D.Double point = new Point2D.Double(pointLon, pointLat);
        List<Point2D.Double> pointList = new ArrayList<Point2D.Double>();
        double polygonPoint_x = 0.0, polygonPoint_y = 0.0;
        for (int i = 0; i < lon.length; i++) {
            polygonPoint_x = lon[i];
            polygonPoint_y = lat[i];
            Point2D.Double polygonPoint = new Point2D.Double(polygonPoint_x, polygonPoint_y);
            pointList.add(polygonPoint);
        }
        return check(point, pointList);
    }
    
    private boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath peneralPath = new java.awt.geom.GeneralPath();
 
        Point2D.Double first = polygon.get(0);
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            peneralPath.lineTo(d.x, d.y);
        }
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        return peneralPath.contains(point);
    }
}
