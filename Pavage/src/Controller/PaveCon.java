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
		
		//pave的基本顶点位置	Position du sommet du pave
		for(int i = 0; i < pave.getPointList().size(); i++) {
			pave.getBaseindice().add(i);
		}
		
		//初始中心点	Point central initial
		int Sym_hor_y = (pave.getPointList().get(pave.getBaseindice().get(0)).getYpos()+pave.getPointList().get(pave.getBaseindice().get(1)).getYpos())/2;
		int Sym_ver_x = (pave.getPointList().get(pave.getBaseindice().get(0)).getXpos()+pave.getPointList().get(pave.getBaseindice().get(3)).getXpos())/2;
		pave.setCentre(new PointsM(Sym_ver_x, Sym_hor_y));
		
		//初始橫对称轴	Axe de symetrie transversal initial 	//可省略	Peut etre omis
		pave.getSym_hor().add(new PointsM(-2*pave.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		pave.getSym_hor().add(new PointsM(2*pave.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		
		//初始纵对称轴	Axe de symetrie longitudinal initial	//可省略	Peut etre omis
		pave.getSym_ver().add(new PointsM(Sym_ver_x, -2*pave.getLongueur_pave() + Sym_hor_y));
		pave.getSym_ver().add(new PointsM(Sym_ver_x, 2*pave.getLongueur_pave() + Sym_hor_y));
		
	}
	

	/**
	 * 在pave点的列表中查找位置indice	
	 * Trouver l'emplacement dans la liste des points du pavage
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
	 * 在baseindice列表中查找是否有顶点indice
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
	 * 查找对称信息 + 后期String需要拆分
	 * Trouver des informations symetriques + apres la chaine doit être divisee
	 * @param indice
	 * @return	String des informations symetriques
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
	 * 添加一个点及对称点
	 * Ajouter un point et un point de symetrie
	 * @param indice
	 * @param Xpos
	 * @param Ypos
	 */
	public void addPoint(int indice, int Xpos, int Ypos) {
		
		int indice1 = indice;	//indice: 加第1个点时的位置; indice1: indice的临时变量
								//indice: la position lorsque le premier point est ajoute; la variable temporaire de indice1: indice
		int indice11 = -1;	//加第2个点(对称点)时的位置
							//Position lorsque le deuxième point (point symetrique) est ajoute
		
		String tempjurer[] = null;
		if(chercherSommetIndice(Xpos, Ypos) == -1 && symPoint(indice1) != null) {
			tempjurer = symPoint(indice1).split("_");
			
			if(indice1 == 0) {
				indice1 = pave.getPointList().size();			
				pave.getPointList().add(new PointsM(Xpos, Ypos));	//加入第1个点
				
			}else {
				
				//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des paves
				for(int i = pave.getPointList().size() - 1; i > indice - 1; i--) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i + 1);
				}
				
				pave.getPointList().add(indice, new PointsM(Xpos, Ypos));	//加入第1个点	Rejoignez le premier point
				
			}

			//那么变化区间为<indice1-1, indice1>
			//l'intervalle de changement est <indice1-1, indice1>
			
			//横轴对称	Symetrie horizontale
			if(tempjurer[0].equals("h") && Integer.parseInt(tempjurer[1]) != -1 && Integer.parseInt(tempjurer[1]) != indice1 - 1) {
				
				//对称位置为tempindex1
				//La position symetrique est tempindex1
				if(Integer.parseInt(tempjurer[1]) > indice1){	
					//对称变化区间为<tempindex1,tempindex1 + 1>
					//L'intervalle de variation de symetrie est <tempindex1,tempindex1 + 1>
					//对称变化index = tempindex1 + 1
					//le changement symetrique index = tempindex1 + 1

					indice11 = Integer.parseInt(tempjurer[1]) + 2;
					if(indice11 >= pave.getPointList().size()) {
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//加入第2个点	Rejoignez le deuxieme point
						else
							pave.getPointList().add(new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//加入第2个点	Rejoignez le deuxieme point
					}else {
						
						//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des paves
						for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
							int pos_i = pave.getBaseindice().indexOf(i);
							if(pos_i != -1)
								pave.getBaseindice().set(pos_i, i + 1);
						}
						
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//加入第2个点	Rejoignez le deuxieme point
						else
							pave.getPointList().add(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//加入第2个点	Rejoignez le deuxieme point
					}
					
				}else {
					//对称变化区间为<tempindex1,tempindex1 + 1>
					//L'intervalle de variation de sym'trie est <tempindex1,tempindex1 + 1>
					//对称变化index = tempindex1 + 1
					//le changement symetrique index = tempindex1 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 1;
					
					//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des paves
					for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
						int pos_i = pave.getBaseindice().indexOf(i);
						if(pos_i != -1)
							pave.getBaseindice().set(pos_i, i + 1);
					}
					
					if(tempjurer[2].equals("+"))
						pave.getPointList().add(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));	//加入第2个点	Rejoignez le deuxieme point
					else
						pave.getPointList().add(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));	//加入第2个点	Rejoignez le deuxieme point
				}
				
				System.out.println("L'adjonction du sommet a reussi!" + "\n");
			
			//纵轴对称	Symetrie verticale
			} else if(tempjurer[0].equals("v") && Integer.parseInt(tempjurer[1]) != -1 && Integer.parseInt(tempjurer[1]) != indice1 - 1) {

				//对称位置为tempindex2
				//La position symetrique est tempindex2
				if(Integer.parseInt(tempjurer[1]) > indice1) {
					//对称变化区间为<tempindex2,tempindex2 + 1>
					//L’intervalle de variation de symetrie est <tempindex2,tempindex2 + 1>
					//对称变化index = tempindex2 + 1
					//le changement symetrique index = tempindex2 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 2;
					if(indice11 >= pave.getPointList().size()) {
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//加入第2个点	Rejoignez le deuxieme point
						else
							pave.getPointList().add(new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//加入第2个点	Rejoignez le deuxieme point
					}else {
						
						//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des paves
						for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
							int pos_i = pave.getBaseindice().indexOf(i);
							if(pos_i != -1)
								pave.getBaseindice().set(pos_i, i + 1);
						}
						
						if(tempjurer[2].equals("+"))
							pave.getPointList().add(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//加入第2个点	Rejoignez le deuxieme point
						else
							pave.getPointList().add(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//加入第2个点	Rejoignez le deuxieme point
					}
				}else {
					
					//对称变化区间为<tempindex2,tempindex2 + 1>
					//L'intervalle de variation de symetrie est <tempindex2,tempindex2 + 1>
					//对称变化index = tempindex2 + 1
					//le changement symetrique index = tempindex2 + 1
					indice11 = Integer.parseInt(tempjurer[1]) + 1;
					
					//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des paves
					for(int i = pave.getPointList().size() - 1; i > indice11 - 1; i--) {
						int pos_i = pave.getBaseindice().indexOf(i);
						if(pos_i != -1)
							pave.getBaseindice().set(pos_i, i + 1);
					}
					
					if(tempjurer[2].equals("+"))
						pave.getPointList().add(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));	//加入第2个点	Rejoignez le deuxieme point
					else
						pave.getPointList().add(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));	//加入第2个点	Rejoignez le deuxieme point
				}
	
				System.out.println("L'adjonction du sommet a reussi!" + "\n");
				
			}else {
				System.out.println("L'adjonction du sommet a echoue!" + "\n");
			}
		}
	}
	
	
	/**
	 * 移动一个点及对称点
	 * Deplacer un point et un point de symetrie
	 * @param indice
	 * @param Xpos
	 * @param Ypos
	 */
	public void modifierPoint(int indice, int Xpos, int Ypos) {

		int jurer = pave.getBaseindice().indexOf(indice);
		if(jurer != -1) {
			System.out.println("C'est le sommet initial de base de Pave et ne peut pas etre deplace!" + "\n");	//注意：此为pave初始基本顶点，不能移动！
		}else {
			int indice1 = indice;	//indice1: indice临时存储变量
									//indice1: variable de stockage temporaire de l'indice
			int indice11 = -1;	//对称点的索引(indice)	Indice de points de symetrie (indice)
			
			String tempjurer[] = symPoint(indice1).split("_");
			
			if (tempjurer[0].equals("h") && tempjurer[2].equals("+")) {
				//横轴对称	Symetrie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos, Ypos + pave.getLongueur_pave()));
			}else if (tempjurer[0].equals("h") && tempjurer[2].equals("-")) {
				//横轴对称	Symetrie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos, Ypos - pave.getLongueur_pave()));
			}else if (tempjurer[0].equals("v") && tempjurer[2].equals("+")) {
				//纵轴对称	Symetrie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos + pave.getLongueur_pave(), Ypos));
			}else if (tempjurer[0].equals("v") && tempjurer[2].equals("-")) {
				//纵轴对称	Symetrie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
				pave.getPointList().set(indice1, new PointsM(Xpos, Ypos));
				pave.getPointList().set(indice11, new PointsM(Xpos - pave.getLongueur_pave(), Ypos));
			}
			
			System.out.println("La deformation du sommet a reussi!" + "\n"); //顶点变形成功
		}
	}
	
	
	
	/**
	 * 删除某个点及对称点
	 * Supprimer un point et un point de symetrie
	 * @param indice
	 */
	public void supprimerPoint(int indice) {

		int jurer = pave.getBaseindice().indexOf(indice);
		if(jurer != -1) {
			System.out.println("C'est le sommet initial de base de Pave et ne peut pas etre supprime!" + "\n");	//注意：此为pave初始基本顶点，不能删除！
		} else {
			int indice1 = indice;	//indice1: indice临时存储变量
									//indice1: variable de stockage temporaire de l'indice
			int indice11 = -1;	//对称点的索引(indice)	Indice de points de symetrie (indice)

			String tempjurer[] = symPoint(indice1).split("_");
			
			if (tempjurer[0].equals("h")) {
				//横轴对称	Symetrie horizontale
				indice11 = Integer.parseInt(tempjurer[1]);
			}else if (tempjurer[0].equals("v")) {
				//纵轴对称	Symetrie verticale
				indice11 = Integer.parseInt(tempjurer[1]);
			}
			
			if(indice1 < indice11) {
				//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des paves
				for(int i = indice11; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice11);	//删除第2个点	Supprimer le deuxieme point
				
				//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des pavs
				for(int i = indice1; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice1);	//删除第1个点	Supprimer le premier point
			}else {
				//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des paves
				for(int i = indice1; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice1);	//删除第2个点	Supprimer le deuxieme point
				
				//更新pave的基本顶点位置列表	Mettre a jour la liste des positions de sommet de base des paves
				for(int i = indice11; i < pave.getPointList().size(); i++) {
					int pos_i = pave.getBaseindice().indexOf(i);
					if(pos_i != -1)
						pave.getBaseindice().set(pos_i, i - 1);
				}
				pave.getPointList().remove(indice11);	//删除第1个点	Supprimer le premier point
			}
			System.out.println("La suppression du sommet a reussi!" + "\n"); //删除成功
		}
	}
	
	
	/**
	 * 移动图形
	 * Deplacement du pave
     * 判断点是否在多边形区域内
     * Determiner si le point est dans la zone du polygone
     * @param pointLon 要判断的点的纵坐标	L'ordonnee du point a juger
     * @param pointLat 要判断的点的横坐标	L'abscisse du point a juger
     * @param lon 区域各顶点的纵坐标数组	Tableau d'ordonnees pour chaque sommet de la region
     * @param lat 区域各顶点的横坐标数组	Un tableau d'abscisses pour chaque sommet de la region
     * @return 是/否 true/false
     */
    public boolean isInPolygon(int pointLon, int pointLat, int[] lon, int[] lat) {
        // 将要判断的横纵坐标组成一个点
    	//Composer point selon des coordonnees horizontales et verticales a juger
        Point2D.Double point = new Point2D.Double(pointLon, pointLat);
        // 将区域各顶点的横纵坐标放到一个点集合里面
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
     * "一个点是否在多边形内" - 调用函数
     * "isInPolygon" - fonction d'appel
     * @param point 要判断的点的横纵坐标	Les coordonnees horizontales et verticales du point a juger
     * @param polygon 组成的顶点坐标集合	Ensemble de coordonnees de sommet
     * @return 是/否	true/false
     */
    private boolean check(Point2D.Double point, List<Point2D.Double> polygon) {
        java.awt.geom.GeneralPath peneralPath = new java.awt.geom.GeneralPath();
 
        Point2D.Double first = polygon.get(0);
        // 通过移动到指定坐标（以双精度指定），将一个点添加到路径中
        //Ajouter un point a la trajectoire en deplacant les coordonnees specifiees (specifiees en double precision)
        peneralPath.moveTo(first.x, first.y);
        polygon.remove(0);
        for (Point2D.Double d : polygon) {
            // 通过绘制一条从当前坐标到新指定坐标（以双精度指定）的直线，将一个点添加到路径中。
        	//Ajoutez un point au trace en tracant une ligne entre les coordonnees actuelles et les nouvelles coordonnees (specifiees en double precision).
            peneralPath.lineTo(d.x, d.y);
        }
        // 将几何多边形封闭
        //Fermer le polygone geometrique
        peneralPath.lineTo(first.x, first.y);
        peneralPath.closePath();
        // 测试指定的 Point2D 是否在 Shape 的边界内。
        //Teste si le Point2D specifie est dans les limites de la forme.
        return peneralPath.contains(point);
    }
}
