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
	
	/**
	 * Création d'un carré, forme de base qui est par la suite déformée par l'utilisateur
	 */
	public void carre() {
		int a = pave.getLongueur_pave()/2;
		pave.getPointList().add(new PointsM(-a, -a));
		pave.getPointList().add(new PointsM(-a, a));
		pave.getPointList().add(new PointsM(a, a));
		pave.getPointList().add(new PointsM(a, -a));
		
		//Position du sommet du pave
		for(int i = 0; i < pave.getPointList().size(); i++) {
			pave.getBaseindice().add(i);
		}
		
		//Point central initial
		int Sym_hor_y = (pave.getPointList().get(pave.getBaseindice().get(0)).getYpos()+pave.getPointList().get(pave.getBaseindice().get(1)).getYpos())/2;
		int Sym_ver_x = (pave.getPointList().get(pave.getBaseindice().get(0)).getXpos()+pave.getPointList().get(pave.getBaseindice().get(3)).getXpos())/2;
		pave.setCentre(new PointsM(Sym_ver_x, Sym_hor_y));
		
		//Axe de symetrie transversal initial (Peut etre omis)
		pave.getSym_hor().add(new PointsM(-2*pave.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		pave.getSym_hor().add(new PointsM(2*pave.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		
		//Axe de symetrie longitudinal initial (Peut etre omis)
		pave.getSym_ver().add(new PointsM(Sym_ver_x, -2*pave.getLongueur_pave() + Sym_hor_y));
		pave.getSym_ver().add(new PointsM(Sym_ver_x, 2*pave.getLongueur_pave() + Sym_hor_y));
		
	}
	

	/**	
	 * Trouver l'indice d'un point dans la liste des points du pavage
	 * @param Xpos Abscisse du point à rechercher
	 * @param Ypos Ordonnée du point à rechercher
	 * @return int indice La position du sommet dans la liste si le point existe, -1 sinon
	 */
	public int chercherSommetIndice(int Xpos, int Ypos) {
		int indice = -1;
		for(int i = 0; i < pave.getPointList().size(); i++) {
			if(pave.getPointList().get(i).getXpos() == Xpos && pave.getPointList().get(i).getYpos() == Ypos)
				indice = i;
		}
		return indice;
	}
	
	
	/**
	 * Rechercher s'il y a un indice de sommet dans la liste de baseindice
	 * @param Xpos
	 * @param Ypos
	 * @return int indice
	 */
	public int chercherBaseSommetIndice(int Xpos, int Ypos) {
		int indice = -1;
		for(int i = 1; i < pave.getBaseindice().size(); i++) {
			int j = pave.getBaseindice().get(i);
			if(pave.getPointList().get(j).getXpos() == Xpos && pave.getPointList().get(j).getYpos() == Ypos)
				indice = j;
		}
		return indice;
	}
	
	
	
	/**
	 * Trouver des informations sur les symetries. La chaine de caractere doit ensuite etre segmentee afin de recuperer
	 *  les informations
	 * @param indice
	 * @return	String des informations sur les symetries
	 */
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
				else //"v"
					SymIndice = SymIndice2;
			}
		}else if(SymIndice1 != null && SymIndice2 == null) {
			SymIndice = SymIndice1;
		}else if(SymIndice1 == null && SymIndice2 != null) {
			SymIndice = SymIndice2;
		}
		
		return SymIndice;
	}
	
	
	/**
	 * Ajouter un point, et son image de translation afin d'obtenir une translation valide
	 * @param indice
	 * @param Xpos
	 * @param Ypos
	 */
	public void addPoint(int indice, int Xpos, int Ypos) {
		
		int indice1 = indice; //indice: la position lorsque le premier point est ajoute; la variable temporaire de indice1: indice
		int indice11 = -1;	//Position lorsque le deuxieme point (point symetrique) est ajoute
		
		String tempjurer[] = null;
		if(chercherSommetIndice(Xpos, Ypos) == -1 && symPoint(indice1) != null) {
			tempjurer = symPoint(indice1).split("_");
			
			if(indice1 == 0) {
				indice1 = pave.getPointList().size();			
				pave.getPointList().add(new PointsM(Xpos, Ypos));
				
			} else {
				
				//Mettre a jour la liste des positions de sommet de base des paves
				for(int i = pave.getPointList().size() - 1; i > indice - 1; i--) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i + 1);
				}
				
				pave.getPointList().add(indice, new PointsM(Xpos, Ypos));	//¼ÓÈëµÚ1¸öµã	Rejoignez le premier point
				
			}

			//l'intervalle de changement est <indice1-1, indice1>
			
			//Symetrie horizontale
			if(tempjurer[0].equals("h") && Integer.parseInt(tempjurer[1]) != -1 && Integer.parseInt(tempjurer[1]) != indice1 - 1) {
				
				//La position symetrique est tempindex1
				if(Integer.parseInt(tempjurer[1]) > indice1){	
					//L'intervalle de variation de symetrie est <tempindex1,tempindex1 + 1>
					//le changement symetrique index = tempindex1 + 1

					indice11 = Integer.parseInt(tempjurer[1]) + 2;
					if(indice11 >= pave.getPointList().size()) {
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//Rejoignez le deuxieme point
						else
							pave.getPointList().add(new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//Rejoignez le deuxieme point
					} else {
						
						//Mettre a jour la liste des positions de sommet de base des paves
						for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
							int pos_i = pave.getBaseindice().indexOf(i);
							if(pos_i != -1)
								pave.getBaseindice().set(pos_i, i + 1);
						}
						
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//¼ÓÈëµÚ2¸öµã	Rejoignez le deuxieme point
						else
							pave.getPointList().add(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//¼ÓÈëµÚ2¸öµã	Rejoignez le deuxieme point
					}
					
				} else {
					//L'intervalle de variation de sym'trie est <tempindex1,tempindex1 + 1>
					//le changement symetrique index = tempindex1 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 1;
					
					//Mettre a jour la liste des positions de sommet de base des paves
					for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
						int pos_i = pave.getBaseindice().indexOf(i);
						if(pos_i != -1)
							pave.getBaseindice().set(pos_i, i + 1);
					}
					
					if(tempjurer[2].equals("+"))
						pave.getPointList().add(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//Ajouter le deuxieme point
					else
						pave.getPointList().add(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//Ajouter le deuxieme point
				}
				
				System.out.println("L'adjonction du sommet a reussi!" + "\n");
			
			//Symetrie verticale
			} else if(tempjurer[0].equals("v") && Integer.parseInt(tempjurer[1]) != -1 && Integer.parseInt(tempjurer[1]) != indice1 - 1) {

				//La position symetrique est tempindex2
				if(Integer.parseInt(tempjurer[1]) > indice1) {
					//L¡¯intervalle de variation de symetrie est <tempindex2,tempindex2 + 1>
					//le changement symetrique index = tempindex2 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 2;
					if(indice11 >= pave.getPointList().size()) {
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//Rejoignez le deuxieme point
						else
							pave.getPointList().add(new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//Rejoignez le deuxieme point
					}else {
						
						//Mettre a jour la liste des positions de sommet de base des paves
						for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
							int pos_i = pave.getBaseindice().indexOf(i);
							if(pos_i != -1)
								pave.getBaseindice().set(pos_i, i + 1);
						}
						
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//Ajouter le deuxieme point
						else
							pave.getPointList().add(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//Ajouter le deuxieme point
					}
				}else {
					
					//L'intervalle de variation de symetrie est <tempindex2,tempindex2 + 1>
					//le changement symetrique index = tempindex2 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 1;
					
					//Mettre a jour la liste des positions de sommets de base des paves
					for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
						int pos_i = pave.getBaseindice().indexOf(i);
						if(pos_i != -1)
							pave.getBaseindice().set(pos_i, i + 1);
					}
					
					if(tempjurer[2].equals("+"))
						pave.getPointList().add(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//Ajouter le deuxieme point
					else
						pave.getPointList().add(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//Ajouter le deuxieme point
				}
	
				System.out.println("L'adjonction du sommet a reussi!" + "\n");
				
			}else {
				System.out.println("L'adjonction du sommet a echoue!" + "\n");
			}
		}
	}
	
	
	/**
	 * Deplacer un point et son 'symétrique'
	 * @param indice
	 * @param Xpos
	 * @param Ypos
	 */
	public void modifierPoint(int indice, int Xpos, int Ypos) {

		int jurer = pave.getBaseindice().indexOf(indice);
		if(jurer != -1) {
			System.out.println("C'est le sommet initial de base de Pave et ne peut pas etre deplace!" + "\n");
		}else {
			int indice1 = indice; //indice1: variable de stockage temporaire de l'indice
			int indice11 = -1;	//Indice de points de symetrie (indice)
			
			String tempjurer[] = symPoint(indice1).split("_");
			
			if (tempjurer[0].equals("h") && tempjurer[2].equals("+")) {
				//Symetrie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));
			}else if (tempjurer[0].equals("h") && tempjurer[2].equals("-")) {
				//Symetrie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));
			}else if (tempjurer[0].equals("v") && tempjurer[2].equals("+")) {
				//Symetrie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));
			}else if (tempjurer[0].equals("v") && tempjurer[2].equals("-")) {
				//Symetrie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));
			}
			
			System.out.println("La deformation du sommet a reussi!" + "\n");
		}
	}
	
	
	
	/**
	 * Supprimer un point et son point 'symetrique'
	 * @param indice
	 */
	public void supprimerPoint(int indice) {

		int jurer = pave.getBaseindice().indexOf(indice);
		if(jurer != -1) {
			System.out.println("C'est le sommet initial de base de Pave et ne peut pas etre supprime!" + "\n");
		} else {
			int indice1 = indice;	//indice1: variable de stockage temporaire de l'indice
			int indice11 = -1;	//Indice de points de symetrie (indice)

			String tempjurer[] = symPoint(indice1).split("_");
			
			if (tempjurer[0].equals("h")) {
				//Symetrie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
			}else if (tempjurer[0].equals("v")) {
				//Symetrie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
			}
			
			if(indice1 < indice11) {
				//Mettre a jour la liste des positions de sommet de base des paves
				for(int i = indice11; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice11);	//Supprimer le deuxieme point
				
				//Mettre a jour la liste des positions de sommet de base des paves
				for(int i = indice1; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice1);	//Supprimer le premier point
			}else {
				//Mettre a jour la liste des positions de sommet de base des paves
				for(int i = indice1; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice1);	//Supprimer le deuxieme point
				
				//Mettre a jour la liste des positions de sommet de base des paves
				for(int i = indice11; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice11);	//Supprimer le premier point
			}
			System.out.println("La suppression du sommet a reussi!" + "\n");
		}
	}
	
	
	/**
	 * Deplacement du pave
     * Determiner si le point est dans la zone du polygone
     * @param pointLon  L'ordonnee du point a evaluer
     * @param pointLat	L'abscisse du point a avaluer
     * @param lon 	Tableau d'ordonnees pour chaque sommet de la region
     * @param lat 	Un tableau d'abscisses pour chaque sommet de la region
     * @return true/false
     */
    public boolean isInPolygon(int pointLon, int pointLat, int[] lon, int[] lat) {
    	//Composer point selon des coordonnees horizontales et verticales a juger
        Point2D.Double point = new Point2D.Double(pointLon, pointLat);
        //Mettez les coordonnees horizontales et verticales de chaque sommet de la surface en un ensemble de points
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
    
    /**
     * "isInPolygon" - fonction d'appel
     * @param point 	Les coordonnees horizontales et verticales du point a juger
     * @param polygon 	Ensemble de coordonnees de sommet
     * @return 	true/false
     */
    private boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath peneralPath = new java.awt.geom.GeneralPath();
 
        Point2D.Double first = polygon.get(0);
        //Ajouter un point a la trajectoire en deplacant les coordonnees specifiees (specifiees en double precision)
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
        	//Ajoutez un point au trace en tracant une ligne entre les coordonnees actuelles et les nouvelles coordonnees (specifiees en double precision).
            peneralPath.lineTo(d.x, d.y);
        }
        //Fermer le polygone geometrique
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        //Teste si le Point2D specifie est dans les limites de la forme.
        return peneralPath.contains(point);
    }
}
