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
	

	//Obtenir l'ensemble des points du pave
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
//		jframe.setAlwaysOnTop(true);
		jframe.getContentPane().setBackground(SystemColor.window);
		jframe.getContentPane().setLayout(new java.awt.BorderLayout(10, 10));
		jframe.setVisible(true);
		
		Mypanel panel_1 = new Mypanel();
		jframe.getContentPane().add(panel_1, java.awt.BorderLayout.CENTER);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new java.awt.GridBagLayout());
		javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(panel_2);
		scrollPane.setPreferredSize(new java.awt.Dimension(230, 720));
		jframe.getContentPane().add(scrollPane, java.awt.BorderLayout.EAST);
		
		java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
		gbc.insets = new java.awt.Insets(5, 5, 5, 5);
		gbc.anchor = java.awt.GridBagConstraints.WEST;
		gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		
		JRadioButton rdbtnAffichierPave = new JRadioButton("Afficher le pav\u00e9");
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
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		panel_2.add(rdbtnAffichierPave, gbc);
		
		JLabel lblCouleurDeFond = new JLabel("Couleur de fond du pave :");
		lblCouleurDeFond.setFont(new Font("Arial", Font.BOLD, 13));
		gbc.gridy = 1;
		panel_2.add(lblCouleurDeFond, gbc);
		
		JPanel panelCouleur_1 = new JPanel();
		panelCouleur_1.setBackground(c1);
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.weightx = 1.0;
		panel_2.add(panelCouleur_1, gbc);
		
		JPanel panelCouleur_2 = new JPanel();
		panelCouleur_2.setBackground(c2);
		gbc.gridy = 3;
		panel_2.add(panelCouleur_2, gbc);
		
		JButton btnCouleur_1 = new JButton("couleur 1");
		btnCouleur_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				Color color = chooser.showDialog(panelCouleur_1,"Couleur de fond du pave",Color.lightGray );
				if (color != null)
					c1 = color;  
				panelCouleur_1.setBackground(c1);
				new Mypanel(); 
			}
		});
		btnCouleur_1.setForeground(Color.BLACK);
		btnCouleur_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCouleur_1.setBackground(SystemColor.scrollbar);
		gbc.gridy = 2;
		gbc.gridx = 1;
		gbc.weightx = 0.0;
		panel_2.add(btnCouleur_1, gbc);
		
		JButton btnCouleur_2 = new JButton("couleur 2");
		btnCouleur_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();
				Color color = chooser.showDialog(panelCouleur_2,"Couleur de fond du pave",Color.lightGray );
				if (color != null)
					c2 = color;  
				panelCouleur_2.setBackground(c2);
				new Mypanel(); 
			}
		});
		btnCouleur_2.setForeground(Color.BLACK);
		btnCouleur_2.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCouleur_2.setBackground(SystemColor.scrollbar);
		gbc.gridy = 3;
		panel_2.add(btnCouleur_2, gbc);
		
		JButton btnCapture = new JButton("Capture");
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filepath ="C:\\WINDOWS\\system32\\SnippingTool.exe";
					File testFile = new File(filepath);
					if(!testFile .exists()) {
						System.out.println("\"snipping tool\" du systeme n'existe pas. Appeler la fonction de capture d'ecran personnalisee.");
//						testFile.createNewFile();
						//Capture d'ecran personnalisee
						//Fonctionnement en plein ecran
						RectD rd = new RectD();
						GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
						gd.setFullScreenWindow(rd);
						
					}else {
						java.lang.Runtime.getRuntime().exec(filepath);
						System.out.println("\"snipping tool\" du systeme existe.");
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
		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		panel_2.add(btnCapture, gbc);
		
		JButton btnEnregistrerPavage = new JButton("Enregistrer Pavage");
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
		gbc.gridy = 5;
		panel_2.add(btnEnregistrerPavage, gbc);
		
		gbc.gridy = 6;
		gbc.weighty = 1.0;
		gbc.fill = java.awt.GridBagConstraints.BOTH;
		panel_2.add(new JPanel(), gbc);
		
		
		
	}
	

	
	static class Mypanel extends JPanel{

		//Fonction de dessin reecrite, dessiner pave de tuiles
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//Definir le systeme de coordonnees 2D
	        Graphics2D g2d = (Graphics2D)g;
	        g2d.translate(0, getHeight());//Definir l'axe dans le coin inferieur gauche du panneau
	        g2d.scale(1, -1); //Retourner l'axe des cordonnees
	        
	        
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
					c = c1;// Changer la couleur // 0. Meme dessiner <vert> (0 debut) + ligne impaire (debut a 1 ligne)
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
					c = c2;	// Change la couleur / / 1. Odd to draw <Jaune> (1 debut) + lignes impaires (commencant a 1 ligne)
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
					c = c1;	//Changer de couleur // 2. Dessin impair <vert> (1 debut) + ligne paire (debut 0)
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
					c = c2;	//Changer de couleur // 3. Meme dessiner <jaune> (0 debut) + ligne paire (debut 0)
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
					
					//Dessiner un polygone
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
					
					//Si le dessin est termine, quittez la boucle
					if(centrex > (getSize().width + pavem.getLongueur_pave()*2) && centrey > (getSize().height + pavem.getLongueur_pave()*2))
						break;
						
					//Si vous avez fini de tracer une ligne, tracez une ligne
					if (centrex > (getSize().width + pavem.getLongueur_pave()*2)) {
						
						for(int i = 0; i < cote; i++) {
							x[i] = startx[i];
							y[i] += longueur*2;
						}
						centrex = startcentrex;
						centrey += longueur*2;
					}else {
						//Si dans la ligne en cours, obtient la position de coordonnees de la prochaine image
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
			                          //Facteur d'echelle et offset
			RescaleOp ro=new RescaleOp(0.8f, 0, null);
			tempImage=ro.filter(image, null);
			g.drawImage(tempImage, 0, 0,this);
		}
		
		public RectD(){
			snapshot();
			setVisible(true);
			//setSize(d);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			this.addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e){
					orgx=e.getX();
					orgy=e.getY();
				}
			});
			//compteur de mouvement de souris
			this.addMouseMotionListener(new MouseMotionAdapter() {
				//Souris glisser evenement
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
				//Bouton de liberation
				public void keyReleased(KeyEvent e){
					//Appuyez sur Esc pour quitter
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
