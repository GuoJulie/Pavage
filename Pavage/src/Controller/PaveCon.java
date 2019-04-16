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
		
		//pave�Ļ�������λ��	Position du sommet du pave
		for(int i = 0; i < pave.getPointList().size(); i++) {
			pave.getBaseindice().add(i);
		}
		
		//��ʼ���ĵ�	Point central initial
		int Sym_hor_y = (pave.getPointList().get(pave.getBaseindice().get(0)).getYpos()+pave.getPointList().get(pave.getBaseindice().get(1)).getYpos())/2;
		int Sym_ver_x = (pave.getPointList().get(pave.getBaseindice().get(0)).getXpos()+pave.getPointList().get(pave.getBaseindice().get(3)).getXpos())/2;
		pave.setCentre(new PointsM(Sym_ver_x, Sym_hor_y));
		
		//��ʼ�M�Գ���	Axe de sym��trie transversal initial 	//��ʡ��	Peut ��tre omis
		pave.getSym_hor().add(new PointsM(-2*pave.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		pave.getSym_hor().add(new PointsM(2*pave.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		
		//��ʼ�ݶԳ���	Axe de sym��trie longitudinal initial	//��ʡ��	Peut ��tre omis
		pave.getSym_ver().add(new PointsM(Sym_ver_x, -2*pave.getLongueur_pave() + Sym_hor_y));
		pave.getSym_ver().add(new PointsM(Sym_ver_x, 2*pave.getLongueur_pave() + Sym_hor_y));
		
	}
	

	/**
	 * ��pave����б��в���λ��indice	
	 * Trouver l'emplacement dans la liste des points de pavage
	 * @param Xpos
	 * @param Ypos
	 * @return int indice
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
	 * ��baseindice�б��в����Ƿ��ж���indice
	 * Rechercher s'il y a un indice de sommet dans la list de baseindice
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
	 * ���ҶԳ���Ϣ + ����String��Ҫ���
	 * Trouver des informations sym��triques + apres la chaine doit ��tre divis��e
	 * @param indice
	 * @return	String des informations sym��triques
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
	 * ���һ���㼰�ԳƵ�
	 * Ajouter un point et un point de sym��trie
	 * @param indice
	 * @param Xpos
	 * @param Ypos
	 */
	public void addPoint(int indice, int Xpos, int Ypos) {
		
		int indice1 = indice;	//indice: �ӵ�1����ʱ��λ��; indice1: indice����ʱ����
								//indice: la position lorsque le premier point est ajout��; la variable temporaire de indice1: indice
		int indice11 = -1;	//�ӵ�2����(�ԳƵ�)ʱ��λ��
							//Position lorsque le deuxi��me point (point sym��trique) est ajout��
		
		String tempjurer[] = null;
		if(chercherSommetIndice(Xpos, Ypos) == -1 && symPoint(indice1) != null) {
			tempjurer = symPoint(indice1).split("_");
			
			if(indice1 == 0) {
				indice1 = pave.getPointList().size();			
				pave.getPointList().add(new PointsM(Xpos, Ypos));	//�����1����
				
			}else {
				
				//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
				for(int i = pave.getPointList().size() - 1; i > indice - 1; i--) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i + 1);
				}
				
				pave.getPointList().add(indice, new PointsM(Xpos, Ypos));	//�����1����	Rejoignez le premier point
				
			}

			//��ô�仯����Ϊ<indice1-1, indice1>
			//l'intervalle de changement est <indice1-1, indice1>
			
			//����Գ�	Sym��trie horizontale
			if(tempjurer[0].equals("h") && Integer.parseInt(tempjurer[1]) != -1 && Integer.parseInt(tempjurer[1]) != indice1 - 1) {
				
				//�Գ�λ��Ϊtempindex1
				//La position sym��trique est tempindex1
				if(Integer.parseInt(tempjurer[1]) > indice1){	
					//�ԳƱ仯����Ϊ<tempindex1,tempindex1 + 1>
					//L��intervalle de variation de sym��trie est <tempindex1,tempindex1 + 1>
					//�ԳƱ仯index = tempindex1 + 1
					//le changement sym��trique index = tempindex1 + 1

					indice11 = Integer.parseInt(tempjurer[1]) + 2;
					if(indice11 >= pave.getPointList().size()) {
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//�����2����	Rejoignez le deuxi��me point
						else
							pave.getPointList().add(new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//�����2����	Rejoignez le deuxi��me point
					}else {
						
						//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
						for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
							int pos_i = pave.getBaseindice().indexOf(i);
							if(pos_i != -1)
								pave.getBaseindice().set(pos_i, i + 1);
						}
						
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//�����2����	Rejoignez le deuxi��me point
						else
							pave.getPointList().add(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//�����2����	Rejoignez le deuxi��me point
					}
					
				}else {
					//�ԳƱ仯����Ϊ<tempindex1,tempindex1 + 1>
					//L��intervalle de variation de sym��trie est <tempindex1,tempindex1 + 1>
					//�ԳƱ仯index = tempindex1 + 1
					//le changement sym��trique index = tempindex1 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 1;
					
					//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
					for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
						int pos_i = pave.getBaseindice().indexOf(i);
						if(pos_i != -1)
							pave.getBaseindice().set(pos_i, i + 1);
					}
					
					if(tempjurer[2].equals("+"))
						pave.getPointList().add(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//�����2����	Rejoignez le deuxi��me point
					else
						pave.getPointList().add(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//�����2����	Rejoignez le deuxi��me point
				}
				
				System.out.println("L'adjonction du sommet a r��ussi!" + "\n");
			
			//����Գ�	Sym��trie verticale
			} else if(tempjurer[0].equals("v") && Integer.parseInt(tempjurer[1]) != -1 && Integer.parseInt(tempjurer[1]) != indice1 - 1) {

				//�Գ�λ��Ϊtempindex2
				//La position sym��trique est tempindex2
				if(Integer.parseInt(tempjurer[1]) > indice1) {
					//�ԳƱ仯����Ϊ<tempindex2,tempindex2 + 1>
					//L��intervalle de variation de sym��trie est <tempindex2,tempindex2 + 1>
					//�ԳƱ仯index = tempindex2 + 1
					//le changement sym��trique index = tempindex2 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 2;
					if(indice11 >= pave.getPointList().size()) {
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//�����2����	Rejoignez le deuxi��me point
						else
							pave.getPointList().add(new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//�����2����	Rejoignez le deuxi��me point
					}else {
						
						//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
						for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
							int pos_i = pave.getBaseindice().indexOf(i);
							if(pos_i != -1)
								pave.getBaseindice().set(pos_i, i + 1);
						}
						
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//�����2����	Rejoignez le deuxi��me point
						else
							pave.getPointList().add(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//�����2����	Rejoignez le deuxi��me point
					}
				}else {
					
					//�ԳƱ仯����Ϊ<tempindex2,tempindex2 + 1>
					//L��intervalle de variation de sym��trie est <tempindex2,tempindex2 + 1>
					//�ԳƱ仯index = tempindex2 + 1
					//le changement sym��trique index = tempindex2 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 1;
					
					//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
					for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
						int pos_i = pave.getBaseindice().indexOf(i);
						if(pos_i != -1)
							pave.getBaseindice().set(pos_i, i + 1);
					}
					
					if(tempjurer[2].equals("+"))
						pave.getPointList().add(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//�����2����	Rejoignez le deuxi��me point
					else
						pave.getPointList().add(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//�����2����	Rejoignez le deuxi��me point
				}
	
				System.out.println("L'adjonction du sommet a r��ussi!" + "\n");
				
			}else {
				System.out.println("L'adjonction du sommet a ��chou��!" + "\n");
			}
		}
	}
	
	
	/**
	 * �ƶ�һ���㼰�ԳƵ�
	 * D��placer un point et un point de sym��trie
	 * @param indice
	 * @param Xpos
	 * @param Ypos
	 */
	public void modifierPoint(int indice, int Xpos, int Ypos) {

		int jurer = pave.getBaseindice().indexOf(indice);
		if(jurer != -1) {
			System.out.println("C'est le sommet initial de base de Pave et ne peut pas ��tre d��plac��!" + "\n");	//ע�⣺��Ϊpave��ʼ�������㣬�����ƶ���
		}else {
			int indice1 = indice;	//indice1: indice��ʱ�洢����
									//indice1: variable de stockage temporaire de l'indice
			int indice11 = -1;	//�ԳƵ������(indice)	Indice de points de sym��trie (indice)
			
			String tempjurer[] = symPoint(indice1).split("_");
			
			if (tempjurer[0].equals("h") && tempjurer[2].equals("+")) {
				//����Գ�	Sym��trie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));
			}else if (tempjurer[0].equals("h") && tempjurer[2].equals("-")) {
				//����Գ�	Sym��trie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));
			}else if (tempjurer[0].equals("v") && tempjurer[2].equals("+")) {
				//����Գ�	Sym��trie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));
			}else if (tempjurer[0].equals("v") && tempjurer[2].equals("-")) {
				//����Գ�	Sym��trie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));
			}
			
			System.out.println("La d��formation du sommet a r��ussi!" + "\n"); //������γɹ�
		}
	}
	
	
	
	/**
	 * ɾ��ĳ���㼰�ԳƵ�
	 * Supprimer un point et un point de sym��trie
	 * @param indice
	 */
	public void supprimerPoint(int indice) {

		int jurer = pave.getBaseindice().indexOf(indice);
		if(jurer != -1) {
			System.out.println("C'est le sommet initial de base de Pave et ne peut pas ��tre supprim��!" + "\n");	//ע�⣺��Ϊpave��ʼ�������㣬����ɾ����
		}else {
			int indice1 = indice;	//indice1: indice��ʱ�洢����
									//indice1: variable de stockage temporaire de l'indice
			int indice11 = -1;	//�ԳƵ������(indice)	Indice de points de sym��trie (indice)

			String tempjurer[] = symPoint(indice1).split("_");
			
			if (tempjurer[0].equals("h")) {
				//����Գ�	Sym��trie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
			}else if (tempjurer[0].equals("v")) {
				//����Գ�	Sym��trie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
			}
			
			if(indice1 < indice11) {
				//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
				for(int i = indice11; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice11);	//ɾ����2����	Supprimer le deuxi��me point
				
				//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
				for(int i = indice1; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice1);	//ɾ����1����	Supprimer le premier point
			}else {
				//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
				for(int i = indice1; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice1);	//ɾ����2����	Supprimer le deuxi��me point
				
				//����pave�Ļ�������λ���б�	Mettre �� jour la liste des positions de sommet de base des pav��s
				for(int i = indice11; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice11);	//ɾ����1����	Supprimer le premier point
			}
			System.out.println("La suppression du sommet a r��ussi!" + "\n"); //ɾ���ɹ�
		}
	}
	
	
	/**
	 * �ƶ�ͼ��
	 * D��placement de la pave
     * �жϵ��Ƿ��ڶ����������
     * D��terminer si le point est dans la zone du polygone
     * @param pointLon Ҫ�жϵĵ��������	L'ordonn��e du point �� juger
     * @param pointLat Ҫ�жϵĵ�ĺ�����	L'abscisse du point �� juger
     * @param lon ��������������������	Tableau d'ordonn��es pour chaque sommet de la r��gion
     * @param lat ���������ĺ���������	Un tableau d'abscisses pour chaque sommet de la r��gion
     * @return ��/�� true/false
     */
    public boolean isInPolygon(int pointLon, int pointLat, int[] lon, int[] lat) {
        // ��Ҫ�жϵĺ����������һ����
    	//Composer point selon des coordonn��es horizontales et verticales �� juger
        Point2D.Double point = new Point2D.Double(pointLon, pointLat);
        // �����������ĺ�������ŵ�һ���㼯������
        //Mettez les coordonn��es horizontales et verticales de chaque sommet de la surface en un ensemble de points
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
     * "һ�����Ƿ��ڶ������" - ���ú���
     * "isInPolygon" - fonction d'appel
     * @param point Ҫ�жϵĵ�ĺ�������	Les coordonn��es horizontales et verticales du point �� juger
     * @param polygon ��ɵĶ������꼯��	Ensemble de coordonn��es de sommet
     * @return ��/��	true/false
     */
    private boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath peneralPath = new java.awt.geom.GeneralPath();
 
        Point2D.Double first = polygon.get(0);
        // ͨ���ƶ���ָ�����꣨��˫����ָ��������һ������ӵ�·����
        //Ajouter un point �� la trajectoire en d��placant les coordonn��es sp��cifi��es (sp��cifi��es en double pr��cision)
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            // ͨ������һ���ӵ�ǰ���굽��ָ�����꣨��˫����ָ������ֱ�ߣ���һ������ӵ�·���С�
        	//Ajoutez un point au trac�� en tracant une ligne entre les coordonn��es actuelles et les nouvelles coordonn��es (sp��cifi��es en double pr��cision).
            peneralPath.lineTo(d.x, d.y);
        }
        // �����ζ���η��
        //Fermer le polygone g��om��trique
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        // ����ָ���� Point2D �Ƿ��� Shape �ı߽��ڡ�
        //Teste si le Point2D sp��cifi�� est dans les limites de la forme.
        return peneralPath.contains(point);
    }
}
