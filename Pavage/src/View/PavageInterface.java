package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Robot;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Controller.PaveCon;
import Model.PaveM;
import javax.swing.JRadioButton;
import javax.swing.filechooser.FileSystemView;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ItemEvent;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.AWTException;

public class PavageInterface {

	private JFrame jframe;
	private static boolean AfficherPave = false;
	private static Color c1 = Color.BLACK;
	private static Color c2 = Color.WHITE;
	
	// Composants d'interface utilisateur
	private Mypanel panel_1;
	private JPanel panel_2;
	private JRadioButton rdbtnAffichierPave;
	private JLabel lblCouleurDeFond;
	private JPanel panelCouleur_1;
	private JPanel panelCouleur_2;
	private JButton btnCouleur_1;
	private JButton btnCouleur_2;
	private JButton btnCapture;
	private JButton btnEnregistrerPavage;
	
	// Constantes de mise en page
	private static final double RIGHT_PANEL_RATIO = 0.18;	// Ratio du panneau droit
	private static final int MIN_RIGHT_PANEL_WIDTH = 220;
	private static final int MAX_RIGHT_PANEL_WIDTH = 300;
	private static final int MARGIN = 10;
	
	// Tailles de police adaptatives
	private Font titleFont;
	private Font labelFont;
	private Font buttonFont;

	// Obtenir l'ensemble des points du pave
	static PaveM pavem = new PaveM();
	static PaveCon pave = new PaveCon(pavem);

	/**
	 * Create the application.
	 */
	public PavageInterface(PaveM pavem) {
		this.pavem = pavem;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		jframe = new JFrame();
		jframe.setTitle("Pavage - Visualisation");
		jframe.setResizable(true);
		jframe.setBounds(85, 50, 1400, 800);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.getContentPane().setBackground(SystemColor.window);
		jframe.getContentPane().setLayout(null);
		jframe.setVisible(true);
		
		// Initialiser les polices
		updateFonts(jframe.getWidth());
		
		// Panneau principal de visualisation
		panel_1 = new Mypanel();
		panel_1.setBackground(Color.WHITE);
		jframe.getContentPane().add(panel_1);
		
		// Panneau d'options
		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.control);
		panel_2.setLayout(null);
		jframe.getContentPane().add(panel_2);
		
		// Radio button pour afficher le pave
		rdbtnAffichierPave = new JRadioButton("Afficher le pave");
		rdbtnAffichierPave.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(AfficherPave == true)
					AfficherPave = false;
				else
					AfficherPave = true;
			}
		});
		rdbtnAffichierPave.setToolTipText("Afficher le pave");
		rdbtnAffichierPave.setFont(labelFont);
		panel_2.add(rdbtnAffichierPave);
		
		// Label couleur de fond
		lblCouleurDeFond = new JLabel("Couleur de fond :");
		lblCouleurDeFond.setFont(labelFont);
		panel_2.add(lblCouleurDeFond);
		
		// Panneaux de couleur
		panelCouleur_1 = new JPanel();
		panelCouleur_1.setBackground(c1);
		panel_2.add(panelCouleur_1);
		
		panelCouleur_2 = new JPanel();
		panelCouleur_2.setBackground(c2);
		panel_2.add(panelCouleur_2);
		
		// Bouton couleur 1
		btnCouleur_1 = new JButton("Couleur 1");
		btnCouleur_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				Color color = chooser.showDialog(panelCouleur_1, "Couleur de fond du pave", Color.lightGray);
				if (color != null)
					c1 = color;
				panelCouleur_1.setBackground(c1);
				new Mypanel();
			}
		});
		btnCouleur_1.setForeground(Color.BLACK);
		btnCouleur_1.setFont(buttonFont);
		btnCouleur_1.setBackground(SystemColor.scrollbar);
		panel_2.add(btnCouleur_1);
		
		// Bouton couleur 2
		btnCouleur_2 = new JButton("Couleur 2");
		btnCouleur_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				Color color = chooser.showDialog(panelCouleur_2, "Couleur de fond du pave", Color.lightGray);
				if (color != null)
					c2 = color;
				panelCouleur_2.setBackground(c2);
				new Mypanel();
			}
		});
		btnCouleur_2.setForeground(Color.BLACK);
		btnCouleur_2.setFont(buttonFont);
		btnCouleur_2.setBackground(SystemColor.scrollbar);
		panel_2.add(btnCouleur_2);
		
		// Bouton capture
		btnCapture = new JButton("Capture");
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filepath = "C:\\WINDOWS\\system32\\SnippingTool.exe";
					File testFile = new File(filepath);
					if(!testFile.exists()) {
						System.out.println("Outil de capture systeme non trouve. Utilisation de la capture personnalisee.");
						RectD rd = new RectD();
						GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
						gd.setFullScreenWindow(rd);
					} else {
						java.lang.Runtime.getRuntime().exec(filepath);
						System.out.println("Outil de capture systeme lance.");
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnCapture.setForeground(Color.BLACK);
		btnCapture.setFont(buttonFont);
		btnCapture.setBackground(SystemColor.scrollbar);
		panel_2.add(btnCapture);
		
		// Bouton enregistrer
		btnEnregistrerPavage = new JButton("Enregistrer Pavage");
		btnEnregistrerPavage.setToolTipText("Enregistrer Pavage");
		btnEnregistrerPavage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage myImage = null;
				try {
					myImage = new Robot().createScreenCapture(
							new Rectangle(jframe.getX()+17, jframe.getY()+40, panel_1.getWidth(), panel_1.getHeight()));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
					String name = "Pavage_" + sdf.format(new Date());
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
		btnEnregistrerPavage.setForeground(Color.BLACK);
		btnEnregistrerPavage.setFont(buttonFont);
		btnEnregistrerPavage.setBackground(SystemColor.scrollbar);
		panel_2.add(btnEnregistrerPavage);
		
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
		int baseSize = Math.max(12, windowWidth / 80);
		
		titleFont = new Font("Arial", Font.BOLD, baseSize + 6);
		labelFont = new Font("Arial", Font.BOLD, baseSize + 2);
		buttonFont = new Font("Arial", Font.BOLD, baseSize + 2);
		
		if (rdbtnAffichierPave != null) rdbtnAffichierPave.setFont(labelFont);
		if (lblCouleurDeFond != null) lblCouleurDeFond.setFont(labelFont);
		if (btnCouleur_1 != null) btnCouleur_1.setFont(buttonFont);
		if (btnCouleur_2 != null) btnCouleur_2.setFont(buttonFont);
		if (btnCapture != null) btnCapture.setFont(buttonFont);
		if (btnEnregistrerPavage != null) btnEnregistrerPavage.setFont(buttonFont);
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
		int y = 20;
		int componentWidth = panelWidth - 20;
		int lineHeight = Math.max(28, panelHeight / 25);
		
		// Radio button afficher pave
		if (rdbtnAffichierPave != null) {
			rdbtnAffichierPave.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight + 20;
		}
		
		// Label couleur
		if (lblCouleurDeFond != null) {
			lblCouleurDeFond.setBounds(x, y, componentWidth, lineHeight);
			y += lineHeight + 10;
		}
		
		// Boutons et panneaux de couleur
		int colorPanelSize = Math.max(25, lineHeight);
		if (btnCouleur_1 != null) {
			btnCouleur_1.setBounds(x, y, componentWidth / 2 - 5, lineHeight);
		}
		if (panelCouleur_1 != null) {
			panelCouleur_1.setBounds(x + componentWidth / 2 + 5, y + (lineHeight - colorPanelSize) / 2, 
				componentWidth / 2 - 5, colorPanelSize);
		}
		y += lineHeight + 10;
		
		if (btnCouleur_2 != null) {
			btnCouleur_2.setBounds(x, y, componentWidth / 2 - 5, lineHeight);
		}
		if (panelCouleur_2 != null) {
			panelCouleur_2.setBounds(x + componentWidth / 2 + 5, y + (lineHeight - colorPanelSize) / 2, 
				componentWidth / 2 - 5, colorPanelSize);
		}
		y += lineHeight + 30;
		
		// Boutons capture et enregistrer
		if (btnCapture != null) {
			btnCapture.setBounds(x, y, componentWidth, lineHeight + 5);
			y += lineHeight + 15;
		}
		if (btnEnregistrerPavage != null) {
			btnEnregistrerPavage.setBounds(x, y, componentWidth, lineHeight + 5);
		}
	}

	static class Mypanel extends JPanel{

		// Fonction de dessin reecrite, dessiner pave de tuiles
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// Definir le systeme de coordonnees 2D
	        Graphics2D g2d = (Graphics2D)g;
	        g2d.translate(0, getHeight());// Definir l'axe dans le coin inferieur gauche du panneau
	        g2d.scale(1, -1); // Retourner l'axe des coordonnees
	        
	        
			int cote = pavem.getPointList().size();
			int longueur = pavem.getLongueur_pave();
			int[] x = new int[cote];
			int[] y = new int[cote];
			int[] startx = new int[cote];
			int[] starty = new int[cote];
			int[] basestartx = new int[cote];
			int[] basestarty = new int[cote];
			int centrex = pavem.getCentre().getXpos();
			int centrey = pavem.getCentre().getYpos();
			int startcentrex = pavem.getCentre().getXpos();
			int startcentrey = pavem.getCentre().getYpos();
			int basestartcentrex = pavem.getCentre().getXpos();
			int basestartcentrey = pavem.getCentre().getYpos();
			int ecart, ecart1 = 0;
			Color c = null;
			String tempjurer = null;
			
			for(int i = 0; i < cote; i++) {
				x[i] = pavem.getPointList().get(i).getXpos();
				startx[i] = pavem.getPointList().get(i).getXpos();
				basestartx[i] = startx[i];
				y[i] = pavem.getPointList().get(i).getYpos();
				starty[i] = pavem.getPointList().get(i).getYpos();
				basestarty[i] = starty[i];
			}
			
			for(int k = 0; k < 4; k++) {
				if(k == 0) {
					c = c1;// Changer la couleur
					for(int i = 0; i < cote; i++) {
						startx[i] = basestartx[i];
						starty[i] = basestarty[i];
						x[i] = basestartx[i];
						y[i] = basestarty[i];
						centrex = basestartcentrex;
						centrey = basestartcentrey;
					}
				}
				else if(k == 1){
					c = c2;	// Changer la couleur
					for(int i = 0; i < cote; i++) {
						startx[i] = basestartx[i] - longueur;
						starty[i] = basestarty[i];
						x[i] = startx[i];
						y[i] = basestarty[i];
						centrex = basestartcentrex - longueur;
						centrey = basestartcentrey;
					}
				}
				else if(k == 2){
					c = c1;	// Changer de couleur
					for(int i = 0; i < cote; i++) {
						startx[i] = basestartx[i] - longueur;
						starty[i] = basestarty[i] - longueur;
						x[i] = basestartx[i];
						y[i] = starty[i];
						centrex = basestartcentrex - longueur;
						centrey = basestartcentrey - longueur;
					}
				}
				else if(k == 3){
					c = c2;	// Changer de couleur
					for(int i = 0; i < cote; i++) {
						startx[i] = basestartx[i];
						starty[i] = basestarty[i] - longueur;
						x[i] = startx[i];
						y[i] = starty[i];
						centrex = basestartcentrex;
						centrey = basestartcentrey - longueur;
					}
				}
				
				while (true) {
					
					// Dessiner un polygone
					try {
						Polygon p = new Polygon(x,y,cote);
						
						g2d.setColor(c);
						g2d.fillPolygon(p);
						
						if(AfficherPave == true) {
							g2d.setColor(Color.RED);
							g2d.drawPolygon(p);
						}
						
						repaint();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					// Si le dessin est termine, quittez la boucle
					if(centrex > (getSize().width + pavem.getLongueur_pave()*2) && centrey > (getSize().height + pavem.getLongueur_pave()*2))
						break;
						
					// Si vous avez fini de tracer une ligne, tracez une ligne
					if (centrex > (getSize().width + pavem.getLongueur_pave()*2)) {
						
						for(int i = 0; i < cote; i++) {
							x[i] = startx[i];
							y[i] += longueur*2;
						}
						centrex = startcentrex;
						centrey += longueur*2;
					}else {
						// Si dans la ligne en cours, obtient la position de coordonnees de la prochaine image
						for(int i = 0; i < cote; i++) {
							x[i] += longueur*2;
						}
						centrex += longueur*2;
					}	
				}
			}
		}
	}
	
	
	
	class RectD extends JFrame{
		
		private static final long serialVersionUID = 1L;
		int orgx,orgy,endx,endy;
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		BufferedImage image;
		BufferedImage tempImage;
		BufferedImage saveImage;
		Graphics g;
		
		@Override
		public void paint(Graphics g) {
			RescaleOp ro=new RescaleOp(0.8f, 0, null);
			tempImage=ro.filter(image, null);
			g.drawImage(tempImage, 0, 0,this);
		}
		
		public RectD(){
			snapshot();
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			this.addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e){
					orgx=e.getX();
					orgy=e.getY();
				}
			});
			
			this.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent e) {
					endx=e.getX();
					endy=e.getY();
					g=getGraphics();
					g.drawImage(tempImage, 0, 0, RectD.this);
					int x=Math.min(orgx, endx);
					int y=Math.min(orgy,endy);
					int width=Math.abs(endx-orgx)+1;
					int height=Math.abs(endy-orgy)+1;
					g.setColor(Color.BLUE);
					g.drawRect(x-1, y-1, width+1, height+1);
					saveImage=image.getSubimage(x, y, width, height);
					g.drawImage(saveImage, x, y,RectD.this);
				}
			});
			
			this.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e){
					if(e.getKeyCode()==27){
						saveToFile();
						System.exit(0);
					}
				}
			});
		}
		public void saveToFile(){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyymmddHHmmss");
			String name=sdf.format(new Date());
			File path=FileSystemView.getFileSystemView().getHomeDirectory();
			String format="jpg";
			File f=new File(path+File.separator+name+"."+format);
			try {
				ImageIO.write(saveImage, format, f);
			} catch (IOException e) {
				e.printStackTrace();	
			}
		}
		
		public void snapshot(){
			
			try {
				Robot robot= new Robot();
				Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
				image=robot.createScreenCapture(new Rectangle(0,0,d.width,d.height));
				
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
	}
}
