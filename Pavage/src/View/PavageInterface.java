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
	private static boolean AffichierPave = false;
	private static Color c1 = Color.BLACK;
	private static Color c2 = Color.WHITE;
	

	//��ȡpave�ĵ㼯	Obtenez le jeu de points de pav��
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
		jframe.setResizable(false);
		jframe.setBounds(85, 50, 1366, 768);
		jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		jframe.setAlwaysOnTop(true);
		jframe.getContentPane().setBackground(SystemColor.window);
		jframe.getContentPane().setLayout(null);
		jframe.setVisible(true);
		
		Mypanel panel_1 = new Mypanel();
		panel_1.setBounds(10, 10, 1120, 720);
		jframe.getContentPane().add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(1140, 10, 212, 720);
		jframe.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JRadioButton rdbtnAffichierPave = new JRadioButton("Affichier le pave");
		rdbtnAffichierPave.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(AffichierPave == true)
					AffichierPave = false;
				else
					AffichierPave = true;
			}
		});

		rdbtnAffichierPave.setToolTipText("Afficher le pave");
		rdbtnAffichierPave.setFont(new Font("Arial", Font.BOLD, 13));
		rdbtnAffichierPave.setBounds(6, 52, 232, 23);
		panel_2.add(rdbtnAffichierPave);
		
		JLabel lblCouleurDeFond = new JLabel("Couleur de fond du pave :");
		lblCouleurDeFond.setFont(new Font("Arial", Font.BOLD, 13));
		lblCouleurDeFond.setBounds(10, 127, 192, 15);
		panel_2.add(lblCouleurDeFond);
		
		
		
		JPanel panelCouleur_1 = new JPanel();
		panelCouleur_1.setBounds(115, 152, 87, 23);
		panelCouleur_1.setBackground(c1);
		panel_2.add(panelCouleur_1);
		
		JPanel panelCouleur_2 = new JPanel();
		panelCouleur_2.setBounds(115, 188, 87, 23);
		panelCouleur_2.setBackground(c2);
		panel_2.add(panelCouleur_2);
		
		JButton btnCouleur_1 = new JButton("couleur 1");
		btnCouleur_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();    //ʵ������ɫѡ����
				Color color = chooser.showDialog(panelCouleur_1,"Couleur de fond du pave",Color.lightGray );  //�õ�ѡ�����ɫ
	            if (color != null)  //���ѡȡ��������ɫ�������ѡȡ��ά��ԭ��
	            	c1 = color;  
	            panelCouleur_1.setBackground(c1);  //�ı����ı���ɫ
	            new Mypanel(); 
			}
		});
		btnCouleur_1.setForeground(Color.BLACK);
		btnCouleur_1.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCouleur_1.setBackground(SystemColor.scrollbar);
		btnCouleur_1.setBounds(6, 152, 99, 23);
		panel_2.add(btnCouleur_1);
		
		JButton btnCouleur_2 = new JButton("couleur 2");
		btnCouleur_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser chooser = new JColorChooser();    //ʵ������ɫѡ����
	            Color color = chooser.showDialog(panelCouleur_2,"Couleur de fond de la pav��",Color.lightGray );  //�õ�ѡ�����ɫ
	            if (color != null)  //���ѡȡ��������ɫ�������ѡȡ��ά��ԭ��
	            	c2 = color;  
	            panelCouleur_2.setBackground(c2);  //�ı����ı���ɫ
	            new Mypanel(); 
			}
		});
		btnCouleur_2.setForeground(Color.BLACK);
		btnCouleur_2.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCouleur_2.setBackground(SystemColor.scrollbar);
		btnCouleur_2.setBounds(6, 188, 99, 23);
		panel_2.add(btnCouleur_2);
		
		JButton btnCapture = new JButton("Capture");
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String filepath ="C:\\WINDOWS\\system32\\SnippingTool.exe";
					File testFile = new File(filepath);
					if(!testFile .exists()) {
						System.out.println("\"snipping tool\" du systeme n'existe pas. Appeler la fonction de capture d'ecran personnalisee.");
//						testFile.createNewFile();
						
						//�Զ������	Capture d'ecran personnalisee
						//ȫ������	Fonctionnement en plein ecran
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
		btnCapture.setBounds(6, 282, 196, 23);
		panel_2.add(btnCapture);
		
		JButton btnEnregistrerPavage = new JButton("Enregistrer Pavage");
		btnEnregistrerPavage.setToolTipText("Enregistrer Pavage");
		btnEnregistrerPavage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BufferedImage myImage = null;
				try {
					myImage = new Robot().createScreenCapture(
							new Rectangle(jframe.getX()+17, jframe.getY()+40, panel_1.getWidth(), panel_1.getHeight()));
//					private String path = System.getProperty("user.dir"); //�����projet��ǰĿ¼�� Stock�� dans le repertoire courant du projet
//					ImageIO.write(myImage, "jpg", new File(path + "/pavage.jpg"));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
					String name = "Pavage_" + sdf.format(new Date());
					File path = FileSystemView.getFileSystemView().getHomeDirectory(); //���������	Stock�� sur le bureau
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
		
		
		
	}
	

	
	static class Mypanel extends JPanel{
		
		//��д�Ļ�ͼ����������ƽ��pave
		//Fonction de dessin reecrite, dessiner pave de tuiles
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//���ö�ά����ϵ
			//Definir le systeme de coordonnees 2D
	        Graphics2D g2d = (Graphics2D)g;
	        g2d.translate(0, getHeight());	//�����������õ�panel�����½�
	        								//Definir l'axe dans le coin inferieur gauche du panneau
	        g2d.scale(1, -1); //��ת��������	Retourner l'axe des cordonnees
	        
	        
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
					c = c1;	//�ı���ɫ	// 0. ż������<��ɫ>(0����) + ������(1����)
							// Changer la couleur // 0. Meme dessiner <vert> (0 debut) + ligne impaire (debut �� 1 ligne)
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
					c = c2;	//�ı���ɫ	// 1. ��������<��ɫ>(1����) + ������(1����)
							// Change la couleur / / 1. Odd to draw <Jaune> (1 d��but) + lignes impaires (commencant �� 1 ligne)
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
					c = c1;	//�ı���ɫ	// 2. ��������<��ɫ>(1����) + ż����(0����)
							//Changer de couleur // 2. Dessin impair <vert> (1 debut) + ligne paire (debut 0)
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
					c = c2;	//�ı���ɫ	// 3. ż������<��ɫ>(0����) + ż����(0����)
							//Changer de couleur // 3. M��me dessiner <jaune> (0 d��but) + ligne paire (d��but 0)
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
					
					//���ƶ����	Dessiner un polygone
					try {
						Polygon p = new Polygon(x,y,cote);
						
						g2d.setColor(c);
						g2d.fillPolygon(p);
						
						if(AffichierPave == true) {
							g2d.setColor(Color.RED);
							g2d.drawPolygon(p);
						}
						
						repaint();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// ���������ϣ��˳�ѭ��
					//Si le dessin est termine, quittez la boucle
					if(centrex > (getSize().width + pavem.getLongueur_pave()*2) && centrey > (getSize().height + pavem.getLongueur_pave()*2))
						break;
					
					// �������һ�У����л���	
					//Si vous avez fini de tracer une ligne, tracez une ligne
					if (centrex > (getSize().width + pavem.getLongueur_pave()*2)) {
						
						for(int i = 0; i < cote; i++) {
							x[i] = startx[i];
							y[i] += longueur*2;
						}
						centrex = startcentrex;
						centrey += longueur*2;
					}else {
						// ����ڵ�ǰ�У��õ���һ��ͼƬ������λ��
						//Si dans la ligne en cours, obtient la position de coordonnees de la prochaine image
						for(int i = 0; i < cote; i++) {
							x[i] += longueur*2;
						}
						centrex += longueur*2;
					}	
				}
			}

			
			
			
//			//��ӡ - ��ɾ
//			for(int i = 0; i < cote; i++) {
//				System.out.println("Point[" + i + "]: x = " + x[i] + ", y = " + y[i]);
//			}
//			System.out.println("\n");
//
//			//��ӡ - ��ɾ
//			for(int i = 0; i < cote; i++) {
//				System.out.println("baseindice: " + pavem.getBaseindice().get(i));
//			}
//			System.out.println("\n");

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
			                          //�������Ӻ�ƫ����	Facteur d'echelle et offset
			RescaleOp ro=new RescaleOp(0.8f, 0, null);
			tempImage=ro.filter(image, null);
			g.drawImage(tempImage, 0, 0,this);
		}
		
		public RectD(){
			snapshot();
			setVisible(true);
			//setSize(d);//��󻯴���
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			
			this.addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e){
					orgx=e.getX();
					orgy=e.getY();
				}
			});
			//����˶�������	��compteur de mouvement de souris
			this.addMouseMotionListener(new MouseMotionAdapter() {
				//�����ק�¼�	Souris glisser evenement
				public void mouseDragged(MouseEvent e) {
					endx=e.getX();
					endy=e.getY();
					g=getGraphics();
					g.drawImage(tempImage, 0, 0, RectD.this);
					int x=Math.min(orgx, endx);
					int y=Math.min(orgy,endy);
					//����1����ֹwidth,heightΪ0
					int width=Math.abs(endx-orgx)+1;
					int height=Math.abs(endy-orgy)+1;
					g.setColor(Color.BLUE);
					g.drawRect(x-1, y-1, width+1, height+1);
					//��1����1����Ϊ�˷�ֹͼƬ�����ο򸲸ǵ�
					saveImage=image.getSubimage(x, y, width, height);
					g.drawImage(saveImage, x, y,RectD.this);
				}
			});
			
			this.addKeyListener(new KeyAdapter() {
				@Override
				//�����ͷ�	Bouton de liberation
				public void keyReleased(KeyEvent e){
					//��Esc���˳�	Appuyez sur Esc pour quitter
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
