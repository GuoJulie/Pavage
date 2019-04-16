package View;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Controller.PaveCon;
import Controller.PointsCon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import Model.PaveM;
import Model.PointsM;

public class PaveInterface {

	private JFrame jframe;
	private JTextArea textAreaRemarque;
	
	static int radiochoix = 0;
	static private boolean statepolygon;	//判断点是否在多边形内	Déterminer si le point est à l'intérieur du polygone
	static private boolean statesommet;		//判断点是否在顶点上	Déterminer si le point est au sommet
	static private boolean statecote;		//判断点是否在边上	Déterminer si le point est sur le coté
	static private int indicesommet = -1;
	static private int indicecote = -1;
	static private int recordindicecote = -1;
	
	static PaveM pavem = new PaveM();
	static PaveCon pave = new PaveCon(pavem);	//获取pave的点集	Obtenez le jeu de points de pavé
	static PointsCon pc = new PointsCon();
	
	static int cote;
	static int longueur;
	static private int[] x;
	static private int[] y;
	static int centrex;
	static int centrey;
	static int sourisx;
	static int sourisy;
	static int[] symhorx;
	static int[] symhory;
	static int[] symverx;
	static int[] symvery;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaveInterface window = new PaveInterface();
					window.jframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PaveInterface() {
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jframe = new JFrame();
		jframe.setTitle("Pave");
		jframe.setResizable(false);
		jframe.setBounds(85, 50, 1366, 768);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.getContentPane().setBackground(SystemColor.window);
		jframe.getContentPane().setLayout(null);
		
		//panel_2 pave选项区
		//panel_2: Zone d'option Pave
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(1108, 10, 244, 720);
		jframe.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lbl_title_changer = new JLabel("Déformation de la Pave");
		lbl_title_changer.setForeground(Color.BLUE);
		lbl_title_changer.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_title_changer.setFont(new Font("Arial", Font.BOLD, 15));
		lbl_title_changer.setBounds(6, 10, 232, 23);
		panel_2.add(lbl_title_changer);
		
		JRadioButton rdbtn0 = new JRadioButton("Aucune opération");
		rdbtn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 0;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("");
			}
		});
		rdbtn0.setSelected(true);
		rdbtn0.setToolTipText("Aucune opération");
		rdbtn0.setFont(new Font("Arial", Font.BOLD, 13));
		rdbtn0.setBounds(6, 54, 232, 23);
		panel_2.add(rdbtn0);
		
		JRadioButton rdbtn1 = new JRadioButton("Déplacer la pave");
		rdbtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 1;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("S'il vous plait déplacer votre souris sur la zone de la pavé et faire-la glisser.");
			}
		});
		rdbtn1.setFont(new Font("Arial", Font.BOLD, 13));
		rdbtn1.setToolTipText("Déplacer la pave");
		rdbtn1.setBounds(6, 79, 232, 23);
		panel_2.add(rdbtn1);
		
		JRadioButton rdbtn2 = new JRadioButton("Déplacer le sommet");
		rdbtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 2;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("S'il vous plait déplacer votre souris au sommet et faire-la glisser." + "\n" + "\n");
				textAreaRemarque.append("Attention: Le sommet initial de base de Pave ne peut pas être déplacé!");
			}
		});
		rdbtn2.setToolTipText("Déplacer le sommet");
		rdbtn2.setFont(new Font("Arial", Font.BOLD, 13));
		rdbtn2.setBounds(6, 104, 232, 23);
		panel_2.add(rdbtn2);
		
		JRadioButton rdbtn3 = new JRadioButton("Augmenter le sommet");
		rdbtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 3;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("1. S'il vous plait déplacer votre souris sur un coté et faire un clic gauche pour sélectionner le coté que vous souhaitez modifier." + "\n"+ "\n");
				textAreaRemarque.append("2. S'il vous plait déplacer votre souris sur une position du panneau et faire un clic droit pour créer un nouveau sommet.");
				
			}
		});
		rdbtn3.setToolTipText("Augmenter le sommet");
		rdbtn3.setFont(new Font("Arial", Font.BOLD, 13));
		rdbtn3.setBounds(6, 129, 232, 23);
		panel_2.add(rdbtn3);
		
		JRadioButton rdbtn4 = new JRadioButton("Supprimer un seul sommet");
		rdbtn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 4;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				
				textAreaRemarque.setText("S'il vous plait déplacer votre souris au sommet et faire-la glisser." + "\n" + "\n");
				textAreaRemarque.append("Attention: Le sommet initial de base de Pave ne peut pas être supprimé!");
			}
		});
		rdbtn4.setToolTipText("Supprimer un seul sommet");
		rdbtn4.setFont(new Font("Arial", Font.BOLD, 13));
		rdbtn4.setBounds(6, 154, 232, 23);
		panel_2.add(rdbtn4);
		
		//设置单选	Mettre en place une seule sélection
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(rdbtn0);
		buttonGroup1.add(rdbtn1);
		buttonGroup1.add(rdbtn2);
		buttonGroup1.add(rdbtn3);
		buttonGroup1.add(rdbtn4);
		
		
		JButton buttonTransfer = new JButton("Pavé --> Pavage");
		buttonTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PavageInterface PaveTransfer = new PavageInterface( pavem);
			}
		});
		buttonTransfer.setFont(new Font("Arial", Font.BOLD, 14));
		buttonTransfer.setForeground(Color.WHITE);
		buttonTransfer.setBackground(new Color(153, 51, 51));
		buttonTransfer.setBounds(6, 265, 232, 43);
		panel_2.add(buttonTransfer);
		
		JLabel lblRemarque = new JLabel("Remarque :");
		lblRemarque.setFont(new Font("Arial", Font.BOLD, 13));
		lblRemarque.setBounds(6, 419, 91, 15);
		panel_2.add(lblRemarque);
		
		
		//panel_1 pave修改区
		//panel_1: la zone de modification de la pave
		Mypanel panel_1 = new Mypanel();
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					// 左键点击	Clic gauche
					//为创建新顶点获取边的值	Obtenir la valeur de l'arête pour créer un nouveau sommet
					if(statecote == true && radiochoix == 3) {
						recordindicecote = indicecote + 1;
						if(recordindicecote >= pavem.getPointList().size())
							recordindicecote = 0;
					}
					
					if(statesommet == true && radiochoix == 4) {
						pave.supprimerPoint(indicesommet);
						updatexy();
						new Mypanel();
					}
				
				}else if(e.getButton() == MouseEvent.BUTTON3) {
					// 右键点击	Clic droit
					//创建新顶点	Créer un nouveau sommet
					if(statecote == false && statesommet == false && radiochoix == 3) {
						
						sourisx = e.getX() - (panel_1.getWidth() / 2);
						sourisy = (panel_1.getHeight() / 2) - e.getY();
						
						if(recordindicecote != -1) {
							pave.addPoint(recordindicecote, sourisx, sourisy);
		        			updatexy();	        			
		        			new Mypanel();
		        			recordindicecote = -1;
						}
					}
				}
				
			}
		});
		panel_1.setBounds(10, 10, 1091, 720);
		panel_1.addMouseMotionListener(panel_1);
		jframe.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		
		textAreaRemarque = new JTextArea();
		textAreaRemarque.setFont(new Font("Arial", Font.PLAIN, 15));
		textAreaRemarque.setBackground(SystemColor.inactiveCaptionBorder);
		textAreaRemarque.setBounds(6, 444, 228, 266);
		textAreaRemarque.setLineWrap(true);
		panel_2.add(textAreaRemarque);
		
		JButton btnEnregistrerPave = new JButton("Enregistrer Pave");
		btnEnregistrerPave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage myImage = null;
				try {
					myImage = new Robot().createScreenCapture(
							new Rectangle(jframe.getX()+17, jframe.getY()+40, panel_1.getWidth(), panel_1.getHeight()));
//					private String path = System.getProperty("user.dir"); //存放在projet当前目录下
//					ImageIO.write(myImage, "jpg", new File(path + "/pavage.jpg"));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
					String name = "Pave_" + sdf.format(new Date());
					File path = FileSystemView.getFileSystemView().getHomeDirectory(); //存放在桌面
					String format = "jpg";
					File file = new File(path + File.separator + name + "." + format);
					ImageIO.write(myImage, format, file);
				} catch (AWTException e1) {
					e1.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnEnregistrerPave.setToolTipText("Enregistrer Pave");
		btnEnregistrerPave.setForeground(Color.BLACK);
		btnEnregistrerPave.setFont(new Font("Arial", Font.PLAIN, 14));
		btnEnregistrerPave.setBackground(SystemColor.scrollbar);
		btnEnregistrerPave.setBounds(6, 210, 232, 23);
		panel_2.add(btnEnregistrerPave);
		
		

	}
	
	
	public static void updatexy() {
		
		cote = pavem.getPointList().size();
		longueur = pavem.getLongueur_pave();
		
		x = new int[cote+1];
		y = new int[cote+1];

		symhorx = new int[2];
		symhory = new int[2];
		symverx = new int[2];
		symvery = new int[2];
		
		for(int i = 0; i < cote; i++) {
			x[i] = pavem.getPointList().get(i).getXpos();
			y[i] = pavem.getPointList().get(i).getYpos();
		}
		x[cote] = x[0];
		y[cote] = y[0];
		
		int Sym_hor_y = (pavem.getPointList().get(pavem.getBaseindice().get(0)).getYpos()+pavem.getPointList().get(pavem.getBaseindice().get(1)).getYpos())/2;
		int Sym_ver_x = (pavem.getPointList().get(pavem.getBaseindice().get(0)).getXpos()+pavem.getPointList().get(pavem.getBaseindice().get(3)).getXpos())/2;
		pavem.setCentre(new PointsM(Sym_ver_x, Sym_hor_y));	//中心点更新	Mise à jour du point central
		
		pavem.getSym_hor().add(new PointsM(-2*pavem.getLongueur_pave() + Sym_ver_x, Sym_hor_y));	//橫对称轴更新	Mise à jour de l'axe de symétrie horizontal
		pavem.getSym_hor().add(new PointsM(2*pavem.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		pavem.getSym_ver().add(new PointsM(Sym_ver_x, -2*pavem.getLongueur_pave() + Sym_hor_y));	//纵对称轴更新	Mise à jour de l'axe de symétrie vertical
		pavem.getSym_ver().add(new PointsM(Sym_ver_x, 2*pavem.getLongueur_pave() + Sym_hor_y));
		
		for(int i = 0; i < 2; i++) {
			symhorx[i] = pavem.getSym_hor().get(i).getXpos() + Sym_ver_x;
			symhory[i] = Sym_hor_y;
			symverx[i] = Sym_ver_x;
			symvery[i] = pavem.getSym_ver().get(i).getYpos() + Sym_hor_y;
		}
		
//		//打印 - 可删
//		for(int i = 0; i < cote; i++) {
//			System.out.println("Point[" + i + "]: x = " + x[i] + ", y = " + y[i]);
//		}
//		System.out.println("\n");
//
//		//打印 - 可删
//		for(int i = 0; i < cote; i++) {
//			System.out.println("baseindice: " + pavem.getBaseindice().get(i));
//		}
//		System.out.println("\n");
	}
	
	
	static class Mypanel extends JPanel implements MouseMotionListener{
		
		
		//重写的绘图函数，绘制平铺pave
		//Fonction de dessin réécrite, dessiner paver
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			//设置二维坐标系
			//Définir le système de coordonnées 2D
	        Graphics2D g2d = (Graphics2D)g;
	        g2d.translate(getWidth() / 2, getHeight() / 2);	//将坐标轴设置到panel_1的中心位置		Définir l'axe au centre du panneau_1
	        g2d.scale(1, -1); //翻转纵坐标轴		Retourner l'axe des ordonnées
	        
	        try {     
	        	updatexy();
				g2d.drawPolyline(x,y,cote+1);
				//Améliorer_2. la possibilité de mettre en évidence les points qui ont été ajoutés sur le pavé pour que ce soit plus simple de les manipuler. 
				for(int i=0; i < x.length-1; i++ ) {
					int jurer = pavem.getBaseindice().indexOf(i);
					if(jurer == -1) {
						g2d.setColor(Color.RED);
						g2d.fillOval(x[i]-2, y[i]-2, 4, 4);
					}else {
						g2d.setColor(Color.BLUE);
						g2d.fillOval(x[i]-2, y[i]-2, 4, 4);
					}
				}
				repaint();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        
	        //对称轴虚线设置
	        //Réglage de la ligne pointillée de l'axe de symétrie
	        BasicStroke stroke = new BasicStroke(0.5f, BasicStroke.CAP_BUTT ,BasicStroke.JOIN_MITER , 3.5f, new float[] { 10, 5 }, 0f);
	        g2d.setStroke(stroke);
	        g2d.setColor(Color.BLACK);
	        g2d.drawPolyline(symhorx,symhory,2); //画横轴	Dessin axe horizontal
	        g2d.drawPolyline(symverx,symvery,2); //画纵轴	Dessin axe vertical
	        
		}
		
		
		/**
         * @note 鼠标点击状态下拖动
         * Glisser avec un clic de souris
         * */
        @Override
        public void mouseDragged(MouseEvent e) {
        	//拖动图形	Glisser la pave
        	if(radiochoix == 1 && statepolygon == true) {
        		int centrex = 0;
        		int centrey = 0;
        		int ecartx = 0;
        		int ecarty = 0;
        		for(int i = 0; i < cote; i++) {
        			centrex += x[i];
        			centrey += y[i];
        		}
        		centrex = centrex / cote;
        		centrey = centrey / cote;
        		try {
    				ecartx = e.getX() - (getWidth() / 2) - centrex;
    				ecarty = (getHeight() / 2) - e.getY() - centrey;
        			
        		for(int i = 0; i < pavem.getPointList().size(); i++) {
        			pavem.getPointList().set(i, new PointsM(x[i] + ecartx, y[i] + ecarty));
        		}
        			updatexy();
					repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        	}

        	
        	//拖动顶点	Faites glisser le sommet
        	if(statesommet == true && radiochoix == 2) {
        		try {
        			pave.modifierPoint(indicesommet, e.getX() - (getWidth() / 2), (getHeight() / 2) - e.getY());
        			updatexy();
					
					repaint();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
        	
        }
		
        /**
         * @note 鼠标未点击状态下移动
         * La souris se déplace sans cliquer
         * */
        @Override
		public void mouseMoved(MouseEvent e) {
    	
        	int cursorType = Cursor.DEFAULT_CURSOR;
			try {
				sourisx = e.getX() - (getWidth() / 2);
				sourisy = (getHeight() / 2) - e.getY();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			
			//判断点是否在多边形内	Déterminer si le point est à l'intérieur du polygone
        	statepolygon = pave.isInPolygon((int)(e.getX() - (getWidth() / 2)),(int)((getHeight() / 2) - e.getY()),x,y);
        	//判断点是否在顶点上	Déterminer si le point est au sommet
        	statesommet = false;
        	//判断点是否在边上	Déterminer si le point est sur le coté
        	statecote = false;
        	
        	if(statepolygon == true) {
        		if(radiochoix == 1) {
        			//如果点在多边形内，则更改鼠标指针为"手型"
        			//Si le point est à l'intérieur du polygone, remplacez le pointeur de la souris par "hand"
        			cursorType = Cursor.HAND_CURSOR; 
        		}
    			statesommet = false;
    			statecote = false;
    			indicesommet = -1;
    			indicecote = -1;
    			
        	}else {
        		//如果点不在多边形内且点在多边形顶点上，则更改鼠标指针为"十字型"
        		//Si le point ne se trouve pas à l'intérieur du polygone et s'il se trouve sur les sommets du polygone, modifiez le pointeur de la souris en "type de croix".
        		
        		for(int i = 0; i < x.length-1; i++) {        			
            		double distance = pc.lineSpace(new PointsM((int)(e.getX() - (getWidth() / 2)), (int)((getHeight() / 2) - e.getY())), new PointsM(x[i], y[i]));

	        		if(distance < 3) {
	        			statesommet = true;
	        			statecote = false;
	        			indicesommet = i;
	        			indicecote = -1;
	        			if(radiochoix == 2 || radiochoix == 4) {
	            			cursorType = Cursor.CROSSHAIR_CURSOR; 
	            		}
	        		}
	        	
        		}	
        	
	        	//如果光标在边上，则更改鼠标指针为“移动型”
        		//Si le curseur est sur le coté, remplacez le pointeur de la souris par "mobile"
	    		for(int i = 0; i < cote; i++) {
        			
	        		double space = pc.pointToLine(new PointsM((int)(e.getX() - (getWidth() / 2)), (int)((getHeight() / 2) - e.getY())), new PointsM(x[i], y[i]), new PointsM(x[i+1], y[i+1]));
	        		
	        		if(statesommet == false && space < 2)
	        		{
	        			statecote = true;	        			
	        			indicesommet = -1;
	        			indicecote = i;
	        			if(radiochoix == 3) {
	            			cursorType = Cursor.MOVE_CURSOR; 
	            		}
	        		}
	    		}
        	}
        	setCursor(new Cursor(cursorType));		
        }
	}
}
