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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
	static private boolean statepolygon;	//Determine si le point est a l'interieur du polygone
	static private boolean statesommet;		//Determine si le point est au sommet
	static private boolean statecote;		//Determine si le point est sur le cote
	static private int indicesommet = -1;
	static private int indicecote = -1;
	static private int recordindicecote = -1;
	
	static PaveM pavem = new PaveM();
	static PaveCon pave = new PaveCon(pavem);	//Obtenir l'ensemble des points pave
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
	
	// Composants d'interface utilisateur
	private JPanel panel_2;
	private Mypanel panel_1;
	private JLabel lbl_title_changer;
	private JRadioButton rdbtn0;
	private JRadioButton rdbtn1;
	private JRadioButton rdbtn2;
	private JRadioButton rdbtn3;
	private JRadioButton rdbtn4;
	private JRadioButton btnSym1;
	private JRadioButton btnSym2;
	private JRadioButton btnSym3;
	private JRadioButton btnSym4;
	private JButton buttonTransfer;
	private JLabel lblRemarque;
	private JButton btnEnregistrerPave;
	
	// Constantes de mise en page
	private static final double RIGHT_PANEL_RATIO = 0.22;	// Ratio du panneau droit par rapport a la largeur totale
	private static final int MIN_RIGHT_PANEL_WIDTH = 250;
	private static final int MAX_RIGHT_PANEL_WIDTH = 350;
	private static final int MARGIN = 10;
	
	// Tailles de police adaptatives
	private Font titleFont;
	private Font labelFont;
	private Font buttonFont;
	private Font textAreaFont;
	
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
		jframe.setTitle("Pave - Editeur de Pavage");
		jframe.setResizable(true);
		jframe.setBounds(85, 50, 1400, 800);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.getContentPane().setBackground(SystemColor.window);
		jframe.getContentPane().setLayout(null);
		
		// Initialiser les polices
		updateFonts(jframe.getWidth());
		
		// Creer le panneau principal de dessin
		panel_1 = new Mypanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
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
				} else if(e.getButton() == MouseEvent.BUTTON3) {
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
		panel_1.addMouseMotionListener(panel_1);
		jframe.getContentPane().add(panel_1);
		
		// Creer le panneau d'options
		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.control);
		panel_2.setLayout(null);
		jframe.getContentPane().add(panel_2);
		
		// Titre du panneau d'options
		lbl_title_changer = new JLabel("Deformation de la Pave");
		lbl_title_changer.setForeground(new Color(0, 102, 204));
		lbl_title_changer.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_title_changer.setFont(titleFont);
		panel_2.add(lbl_title_changer);
		
		// Boutons radio pour les operations
		rdbtn0 = new JRadioButton("Aucune operation");
		rdbtn0.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 0;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("Les points du pave initial sont en bleu, \n" + 
						"Les points qui ont ete ajoutes sur le pave sont en rouge.");
			}
		});
		rdbtn0.setSelected(true);
		rdbtn0.setToolTipText("Aucune operation");
		rdbtn0.setFont(labelFont);
		panel_2.add(rdbtn0);
		
		rdbtn1 = new JRadioButton("Deplacer la pave");
		rdbtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 1;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("Deplacez votre souris sur la zone du pave et faites-la glisser.");
			}
		});
		rdbtn1.setFont(labelFont);
		rdbtn1.setToolTipText("Deplacer la pave");
		panel_2.add(rdbtn1);
		
		rdbtn2 = new JRadioButton("Deplacer le sommet");
		rdbtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 2;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("Deplacez votre souris au sommet et faites-la glisser.\n\n" +
						"Attention: Le sommet initial de base du Pave ne peut pas etre deplace!");
			}
		});
		rdbtn2.setToolTipText("Deplacer le sommet");
		rdbtn2.setFont(labelFont);
		panel_2.add(rdbtn2);
		
		rdbtn3 = new JRadioButton("Augmenter le sommet");
		rdbtn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 3;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("1. Deplacez votre souris sur un cote et faites un clic gauche pour selectionner le cote a modifier.\n\n" +
						"2. Deplacez votre souris sur une position et faites un clic droit pour creer un nouveau sommet.");
			}
		});
		rdbtn3.setToolTipText("Augmenter le sommet");
		rdbtn3.setFont(labelFont);
		panel_2.add(rdbtn3);
		
		rdbtn4 = new JRadioButton("Supprimer un sommet");
		rdbtn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 4;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("Deplacez votre souris au sommet et faites un clic.\n\n" +
						"Attention: Le sommet initial de base du Pave ne peut pas etre supprime!");
			}
		});
		rdbtn4.setToolTipText("Supprimer un sommet");
		rdbtn4.setFont(labelFont);
		panel_2.add(rdbtn4);
		
		// Groupe de boutons pour les operations
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(rdbtn0);
		buttonGroup1.add(rdbtn1);
		buttonGroup1.add(rdbtn2);
		buttonGroup1.add(rdbtn3);
		buttonGroup1.add(rdbtn4);
		
		// Boutons radio pour les symetries
		btnSym1 = new JRadioButton("Aucune symetrie");
		btnSym1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 5;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("Les deformations ne sont pas impactees par des symetries");
			}
		});
		btnSym1.setFont(labelFont);
		btnSym1.setToolTipText("Aucune symetrie");
		btnSym1.setSelected(true);
		panel_2.add(btnSym1);
		
		btnSym2 = new JRadioButton("Symetrie horizontale");
		btnSym2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 6;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("Les deformations seront effectuees d'apres un axe de symetrie horizontale");
			}
		});
		btnSym2.setFont(labelFont);
		btnSym2.setToolTipText("Symetrie horizontale");
		panel_2.add(btnSym2);
		
		btnSym3 = new JRadioButton("Symetrie verticale");
		btnSym3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 7;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("Les deformations seront effectuees d'apres une symetrie verticale");
			}
		});
		btnSym3.setFont(labelFont);
		btnSym3.setToolTipText("Symetrie verticale");
		panel_2.add(btnSym3);
		
		btnSym4 = new JRadioButton("Double symetrie");
		btnSym4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radiochoix = 8;
				statepolygon = false;
				statesommet = false;
				statecote = false;
				textAreaRemarque.setText("Les deformations seront effectuees d'apres une symetrie horizontale et verticale");
			}
		});
		btnSym4.setFont(labelFont);
		btnSym4.setToolTipText("Double symetrie");
		panel_2.add(btnSym4);
		
		// Groupe de boutons pour les symetries
		ButtonGroup buttonGroup2 = new ButtonGroup();
		buttonGroup2.add(btnSym1);
		buttonGroup2.add(btnSym2);
		buttonGroup2.add(btnSym3);
		buttonGroup2.add(btnSym4);
		
		// Bouton de transfert
		buttonTransfer = new JButton("Pave -> Pavage");
		buttonTransfer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PavageInterface PaveTransfer = new PavageInterface(pavem);
			}
		});
		buttonTransfer.setFont(buttonFont);
		buttonTransfer.setForeground(Color.WHITE);
		buttonTransfer.setBackground(new Color(153, 51, 51));
		panel_2.add(buttonTransfer);
		
		// Bouton d'enregistrement
		btnEnregistrerPave = new JButton("Enregistrer Pave");
		btnEnregistrerPave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage myImage = null;
				try {
					myImage = new Robot().createScreenCapture(
							new Rectangle(jframe.getX()+17, jframe.getY()+40, panel_1.getWidth(), panel_1.getHeight()));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
					String name = "Pave_" + sdf.format(new Date());
					File path = FileSystemView.getFileSystemView().getHomeDirectory();
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
		btnEnregistrerPave.setFont(buttonFont);
		btnEnregistrerPave.setBackground(SystemColor.scrollbar);
		panel_2.add(btnEnregistrerPave);
		
		// Label et zone de texte pour les remarques
		lblRemarque = new JLabel("Remarque :");
		lblRemarque.setFont(labelFont);
		panel_2.add(lblRemarque);
		
		textAreaRemarque = new JTextArea();
		textAreaRemarque.setFont(textAreaFont);
		textAreaRemarque.setBackground(SystemColor.inactiveCaptionBorder);
		textAreaRemarque.setLineWrap(true);
		textAreaRemarque.setWrapStyleWord(true);
		textAreaRemarque.setEditable(false);
		textAreaRemarque.setText("Les points du pave initial sont en bleu, \n" + 
				"Les points qui ont ete ajoutes sur le pave sont en rouge.");
		panel_2.add(textAreaRemarque);
		
		// Ajouter un ecouteur de redimensionnement
		jframe.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateLayout();
			}
		});
		
		// Disposition initiale
		updateLayout();
	}
	
	/**
	 * Mettre a jour les polices en fonction de la taille de la fenetre
	 */
	private void updateFonts(int windowWidth) {
		// Calculer la taille de base en fonction de la largeur de la fenetre
		int baseSize = Math.max(12, windowWidth / 80);
		
		titleFont = new Font("Arial", Font.BOLD, baseSize + 6);
		labelFont = new Font("Arial", Font.BOLD, baseSize + 2);
		buttonFont = new Font("Arial", Font.BOLD, baseSize + 3);
		textAreaFont = new Font("Arial", Font.PLAIN, baseSize + 2);
		
		// Mettre a jour les polices des composants existants
		if (lbl_title_changer != null) lbl_title_changer.setFont(titleFont);
		if (rdbtn0 != null) rdbtn0.setFont(labelFont);
		if (rdbtn1 != null) rdbtn1.setFont(labelFont);
		if (rdbtn2 != null) rdbtn2.setFont(labelFont);
		if (rdbtn3 != null) rdbtn3.setFont(labelFont);
		if (rdbtn4 != null) rdbtn4.setFont(labelFont);
		if (btnSym1 != null) btnSym1.setFont(labelFont);
		if (btnSym2 != null) btnSym2.setFont(labelFont);
		if (btnSym3 != null) btnSym3.setFont(labelFont);
		if (btnSym4 != null) btnSym4.setFont(labelFont);
		if (buttonTransfer != null) buttonTransfer.setFont(buttonFont);
		if (btnEnregistrerPave != null) btnEnregistrerPave.setFont(buttonFont);
		if (lblRemarque != null) lblRemarque.setFont(labelFont);
		if (textAreaRemarque != null) textAreaRemarque.setFont(textAreaFont);
	}
	
	/**
	 * Mettre a jour la disposition des composants lors du redimensionnement de la fenetre
	 */
	private void updateLayout() {
		int width = jframe.getContentPane().getWidth();
		int height = jframe.getContentPane().getHeight();
		
		// Calculer la largeur du panneau droit avec des limites min/max
		int panel2Width = (int) (width * RIGHT_PANEL_RATIO);
		panel2Width = Math.max(MIN_RIGHT_PANEL_WIDTH, Math.min(MAX_RIGHT_PANEL_WIDTH, panel2Width));
		
		// Calculer les dimensions
		int panel1Width = width - panel2Width - MARGIN * 3;
		int panelHeight = height - MARGIN * 2;
		
		// Mettre a jour les limites des panneaux
		if (panel_1 != null) {
			panel_1.setBounds(MARGIN, MARGIN, panel1Width, panelHeight);
		}
		if (panel_2 != null) {
			panel_2.setBounds(width - panel2Width - MARGIN, MARGIN, panel2Width, panelHeight);
		}
		
		// Mettre a jour les polices
		updateFonts(width);
		
		// Positionner les composants du panneau droit
		updateRightPanelComponents(panel2Width, panelHeight);
		
		// Forcer le rafraichissement
		jframe.getContentPane().revalidate();
		jframe.getContentPane().repaint();
	}
	
	/**
	 * Mettre a jour la position des composants dans le panneau droit
	 */
	private void updateRightPanelComponents(int panelWidth, int panelHeight) {
		int x = 10;
		int y = 10;
		int componentWidth = panelWidth - 20;
		int lineHeight = Math.max(25, panelHeight / 28);
		
		// Titre
		if (lbl_title_changer != null) {
			lbl_title_changer.setBounds(x, y, componentWidth, lineHeight + 5);
			y += lineHeight + 10;
		}
		
		// Operations
		if (rdbtn0 != null) {
			rdbtn0.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight;
		}
		if (rdbtn1 != null) {
			rdbtn1.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight;
		}
		if (rdbtn2 != null) {
			rdbtn2.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight;
		}
		if (rdbtn3 != null) {
			rdbtn3.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight;
		}
		if (rdbtn4 != null) {
			rdbtn4.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight + 10;
		}
		
		// Symetries
		if (btnSym1 != null) {
			btnSym1.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight;
		}
		if (btnSym2 != null) {
			btnSym2.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight;
		}
		if (btnSym3 != null) {
			btnSym3.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight;
		}
		if (btnSym4 != null) {
			btnSym4.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight + 15;
		}
		
		// Boutons
		int buttonHeight = Math.max(35, lineHeight + 5);
		if (buttonTransfer != null) {
			buttonTransfer.setBounds(x, y, componentWidth, buttonHeight);
			y += buttonHeight + 10;
		}
		if (btnEnregistrerPave != null) {
			btnEnregistrerPave.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight + 15;
		}
		
		// Remarques
		if (lblRemarque != null) {
			lblRemarque.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight;
		}
		if (textAreaRemarque != null) {
			int textAreaHeight = panelHeight - y - 10;
			textAreaRemarque.setBounds(x, y, componentWidth, Math.max(100, textAreaHeight));
		}
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
		pavem.setCentre(new PointsM(Sym_ver_x, Sym_hor_y));
		pavem.getSym_hor().add(new PointsM(-2*pavem.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		pavem.getSym_hor().add(new PointsM(2*pavem.getLongueur_pave() + Sym_ver_x, Sym_hor_y));
		pavem.getSym_ver().add(new PointsM(Sym_ver_x, -2*pavem.getLongueur_pave() + Sym_hor_y));
		pavem.getSym_ver().add(new PointsM(Sym_ver_x, 2*pavem.getLongueur_pave() + Sym_hor_y));
		
		for(int i = 0; i < 2; i++) {
			symhorx[i] = pavem.getSym_hor().get(i).getXpos() + Sym_ver_x;
			symhory[i] = Sym_hor_y;
			symverx[i] = Sym_ver_x;
			symvery[i] = pavem.getSym_ver().get(i).getYpos() + Sym_hor_y;
		}
	}
	
	
	static class Mypanel extends JPanel implements MouseMotionListener{
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D)g;
			g2d.translate(getWidth() / 2, getHeight() / 2);
			g2d.scale(1, -1);
			
			try {     
				updatexy();
				g2d.drawPolyline(x,y,cote+1);
				
				for(int i=0; i < x.length-1; i++ ) {
					int jurer = pavem.getBaseindice().indexOf(i);
					if(jurer == -1) {
						g2d.setColor(Color.RED);
						g2d.fillOval(x[i]-3, y[i]-3, 6, 6);
					} else {
						g2d.setColor(Color.BLUE);
						g2d.fillOval(x[i]-3, y[i]-3, 6, 6);
					}
				}
				
				repaint();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			BasicStroke stroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 3.5f, new float[] { 10, 5 }, 0f);
			g2d.setStroke(stroke);
			g2d.setColor(Color.BLACK);
			g2d.drawPolyline(symhorx, symhory, 2);
			g2d.drawPolyline(symverx, symvery, 2);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
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
					e1.printStackTrace();
				}
			}
			
			if(statesommet == true && radiochoix == 2) {
				try {
					pave.modifierPoint(indicesommet, e.getX() - (getWidth() / 2), (getHeight() / 2) - e.getY());
					updatexy();
					repaint();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			int cursorType = Cursor.DEFAULT_CURSOR;
			try {
				sourisx = e.getX() - (getWidth() / 2);
				sourisy = (getHeight() / 2) - e.getY();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			statepolygon = pave.isInPolygon((int)(e.getX() - (getWidth() / 2)), (int)((getHeight() / 2) - e.getY()), x, y);
			statesommet = false;
			statecote = false;
			
			if(statepolygon == true) {
				if(radiochoix == 1) {
					cursorType = Cursor.HAND_CURSOR;
				}
				statesommet = false;
				statecote = false;
				indicesommet = -1;
				indicecote = -1;
			} else {
				for(int i = 0; i < x.length-1; i++) {
					double distance = pc.lineSpace(new PointsM((int)(e.getX() - (getWidth() / 2)), (int)((getHeight() / 2) - e.getY())), new PointsM(x[i], y[i]));
					
					if(distance < 5) {
						statesommet = true;
						statecote = false;
						indicesommet = i;
						indicecote = -1;
						if(radiochoix == 2 || radiochoix == 4) {
							cursorType = Cursor.CROSSHAIR_CURSOR;
						}
					}
				}
				
				for(int i = 0; i < cote; i++) {
					double space = pc.pointToLine(new PointsM((int)(e.getX() - (getWidth() / 2)), (int)((getHeight() / 2) - e.getY())), new PointsM(x[i], y[i]), new PointsM(x[i+1], y[i+1]));
					
					if(statesommet == false && space < 3) {
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
