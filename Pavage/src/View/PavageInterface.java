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
		jframe.setTitle("Pavage");
		jframe.setResizable(true);
		jframe.setBounds(85, 50, 1366, 768);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jframe.getContentPane().setBackground(SystemColor.window);
		jframe.getContentPane().setLayout(null);
		jframe.setVisible(true);
		
		panel_1 = new Mypanel();
		panel_1.setBounds(10, 10, 1120, 720);
		jframe.getContentPane().add(panel_1);
		
		panel_2 = new JPanel();
		panel_2.setBounds(1140, 10, 212, 720);
		jframe.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		rdbtnAffichierPave = new JRadioButton("Afficher le pav\u00e9");
		rdbtnAffichierPave.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(AfficherPave == true)
					AfficherPave = false;
				else
					AfficherPave = true;
			}
		});

		rdbtnAffichierPave.setToolTipText("Afficher le pav\u00e9");
		rdbtnAffichierPave.setFont(new Font("Arial", Font.BOLD, 13));
		rdbtnAffichierPave.setBounds(6, 52, 232, 23);
		panel_2.add(rdbtnAffichierPave);
		
		lblCouleurDeFond = new JLabel("Couleur de fond du pav\u00e9 :");
		lblCouleurDeFond.setFont(new Font("Arial", Font.BOLD, 13));
		lblCouleurDeFond.setBounds(10, 127, 192, 15);
		panel_2.add(lblCouleurDeFond);
		
		
		
		panelCouleur_1 = new JPanel();
		panelCouleur_1.setBounds(115, 152, 87, 23);
		panelCouleur_1.setBackground(c1);
		panel_2.add(panelCouleur_1);
		
		panelCouleur_2 = new JPanel();
		panelCouleur_2.setBounds(115, 188, 87, 23);
		panelCouleur_2.setBackground(c2);
		panel_2.add(panelCouleur_2);
		
		btnCouleur_1 = new JButton("couleur 1");
		btnCouleur_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				Color color = chooser.showDialog(panelCouleur_1,"Couleur de fond du pav\u00e9",Color.lightGray );
	            if (color != null)
	            	c1 = color;  
	            panelCouleur_1.setBackground(c1);
	            new Mypanel(); 
			}
		});
		btnCouleur_1.setForeground(Color.BLACK);
		btnCouleur_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCouleur_1.setBackground(SystemColor.scrollbar);
		btnCouleur_1.setBounds(6, 152, 99, 23);
		panel_2.add(btnCouleur_1);
		
		btnCouleur_2 = new JButton("couleur 2");
		btnCouleur_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
	            Color color = chooser.showDialog(panelCouleur_2,"Couleur de fond du pav\u00e9",Color.lightGray );
	            if (color != null)
	            	c2 = color;  
	            panelCouleur_2.setBackground(c2);
	            new Mypanel(); 
			}
		});
		btnCouleur_2.setForeground(Color.BLACK);
		btnCouleur_2.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCouleur_2.setBackground(SystemColor.scrollbar);
		btnCouleur_2.setBounds(6, 188, 99, 23);
		panel_2.add(btnCouleur_2);
		
		btnCapture = new JButton("Capture");
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filepath ="C:\\WINDOWS\\system32\\SnippingTool.exe";
					File testFile = new File(filepath);
					if(!testFile .exists()) {
						System.out.println("\"snipping tool\" du syst\u00e8me n'existe pas. Appeler la fonction de capture d'\u00e9cran personnalis\u00e9e.");
						
						// Capture d'\u00e9cran personnalis\u00e9e
						// Fonctionnement en plein \u00e9cran
						RectD rd = new RectD();
					    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
					    gd.setFullScreenWindow(rd);
						
					}else {
						   java.lang.Runtime.getRuntime().exec(filepath);
						   System.out.println("\"snipping tool\" du syst\u00e8me existe.");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnCapture.setForeground(Color.BLACK);
		btnCapture.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCapture.setBackground(SystemColor.scrollbar);
		btnCapture.setBounds(6, 282, 196, 23);
		panel_2.add(btnCapture);
		
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
		btnEnregistrerPavage.setFont(new Font("Arial", Font.PLAIN, 14));
		btnEnregistrerPavage.setBackground(SystemColor.scrollbar);
		btnEnregistrerPavage.setBounds(6, 315, 196, 23);
		panel_2.add(btnEnregistrerPavage);
		
		// Ajouter un \u00e9couteur de redimensionnement pour mettre \u00e0 jour la disposition
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
	 * Mettre \u00e0 jour la disposition des composants lors du redimensionnement de la fen\u00eatre
	 */
	private void updateLayout() {
		int width = jframe.getContentPane().getWidth();
		int height = jframe.getContentPane().getHeight();
		
		// Largeur fixe pour le panneau d'options
		int panel2Width = 212;
		int margin = 10;
		
		// Calculer les nouvelles dimensions
		int panel1Width = width - panel2Width - margin * 3;
		int panelHeight = height - margin * 2;
		
		// Mettre \u00e0 jour les limites des panneaux
		if (panel_1 != null) {
			panel_1.setBounds(margin, margin, panel1Width, panelHeight);
		}
		if (panel_2 != null) {
			panel_2.setBounds(width - panel2Width - margin, margin, panel2Width, panelHeight);
		}
		
		// Forcer le rafra\u00eechissement
		jframe.getContentPane().revalidate();
		jframe.getContentPane().repaint();
	}
	

	
	static class Mypanel extends JPanel{

		// Fonction de dessin r\u00e9\u00e9crite, dessiner pav\u00e9 de tuiles
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			// D\u00e9finir le syst\u00e8me de coordonn\u00e9es 2D
	        Graphics2D g2d = (Graphics2D)g;
	        g2d.translate(0, getHeight());// D\u00e9finir l'axe dans le coin inf\u00e9rieur gauche du panneau
	        g2d.scale(1, -1); // Retourner l'axe des coordonn\u00e9es
	        
	        
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
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// Si le dessin est termin\u00e9, quittez la boucle
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
						// Si dans la ligne en cours, obtient la position de coordonn\u00e9es de la prochaine image
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
				                          // Facteur d'\u00e9chelle et offset
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
			// compteur de mouvement de souris
			this.addMouseMotionListener(new MouseMotionAdapter() {
				// Souris glisser \u00e9v\u00e9nement
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
				// Bouton de lib\u00e9ration
				public void keyReleased(KeyEvent e){
					// Appuyez sur Esc pour quitter
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
