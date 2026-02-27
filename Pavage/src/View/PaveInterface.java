package View;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

import Controller.PaveCon;
import Controller.PointsCon;
import Model.PaveM;
import Model.PointsM;

public class PaveInterface {

    private JFrame jframe;
    private JTextArea textAreaRemarque;
    private JPanel panel_1;
    private JPanel panel_2;
    
    static int radiochoix = 0;
    static private boolean statepolygon;
    static private boolean statesommet;
    static private boolean statecote;
    static private int indicesommet = -1;
    static private int indicecote = -1;
    static private int recordindicecote = -1;

    static PaveM pavem = new PaveM();
    static PaveCon pave = new PaveCon(pavem);
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

    public PaveInterface() {
        initialize();
    }

    private void initialize() {
        jframe = new JFrame();
        jframe.setTitle("Pave");
        jframe.setResizable(true);
        jframe.setBounds(85, 50, 1366, 768);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.getContentPane().setBackground(SystemColor.window);
        jframe.getContentPane().setLayout(new BorderLayout());

        panel_1 = new Mypanel();
        panel_1.setPreferredSize(new Dimension(1091, 720));

        panel_2 = new JPanel();
        panel_2.setPreferredSize(new Dimension(244, 720));
        panel_2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lbl_title_changer = new JLabel("D\u00e9formation de la Pave");
        lbl_title_changer.setForeground(Color.BLUE);
        lbl_title_changer.setHorizontalAlignment(SwingConstants.CENTER);
        lbl_title_changer.setFont(new Font("Arial", Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel_2.add(lbl_title_changer, gbc);
        gbc.gridwidth = 1;

        JRadioButton rdbtn0 = new JRadioButton("Aucune op\u00e9ration");
        rdbtn0.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 0;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("Les points du pav\u00e9 initial sont en bleu, \r\n" +
                        "Les points qui ont \u00e9t\u00e9 ajout\u00e9s sur le pav\u00e9 sont en rouge.");
            }
        });
        rdbtn0.setSelected(true);
        rdbtn0.setToolTipText("Aucune op\u00e9ration");
        rdbtn0.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridy = 1;
        panel_2.add(rdbtn0, gbc);

        JRadioButton rdbtn1 = new JRadioButton("D\u00e9placer la pav\u00e9");
        rdbtn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 1;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("S'il vous pla\u00eet d\u00e9placer votre souris sur la zone de la pav\u00e9 et faire-la glisser.");
            }
        });
        rdbtn1.setFont(new Font("Arial", Font.BOLD, 13));
        rdbtn1.setToolTipText("D\u00e9placer la pav\u00e9");
        gbc.gridy = 2;
        panel_2.add(rdbtn1, gbc);

        JRadioButton rdbtn2 = new JRadioButton("D\u00e9placer le sommet");
        rdbtn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 2;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("S'il vous pla\u00eet d\u00e9placer votre souris au sommet et faire-la glisser." + "\n" + "\n");
                textAreaRemarque.append("Attention: Le sommet initial de base de Pave ne peut pas \u00eatre d\u00e9plac\u00e9!");
            }
        });
        rdbtn2.setToolTipText("D\u00e9placer le sommet");
        rdbtn2.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridy = 3;
        panel_2.add(rdbtn2, gbc);

        JRadioButton rdbtn3 = new JRadioButton("Ajouter un sommet");
        rdbtn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 3;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("1. S'il vous pla\u00eet d\u00e9placer votre souris sur un c\u00f4t\u00e9 et faire un clic gauche pour s\u00e9lectionner le c\u00f4t\u00e9 que vous souhaitez modifier." + "\n"+ "\n");
                textAreaRemarque.append("2. S'il vous pla\u00eet d\u00e9placer votre souris sur une position du panneau et faire un clic droit pour cr\u00e9er un nouveau sommet.");
            }
        });
        rdbtn3.setToolTipText("Ajouter un sommet");
        rdbtn3.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridy = 4;
        panel_2.add(rdbtn3, gbc);

        JRadioButton rdbtn4 = new JRadioButton("Supprimer un seul sommet");
        rdbtn4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 4;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("S'il vous pla\u00eet d\u00e9placer votre souris au sommet et faire-la glisser." + "\n" + "\n");
                textAreaRemarque.append("Attention: Le sommet initial de base de Pave ne peut pas \u00eatre supprim\u00e9!");
            }
        });
        rdbtn4.setToolTipText("Supprimer un seul sommet");
        rdbtn4.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridy = 5;
        panel_2.add(rdbtn4, gbc);

        ButtonGroup buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rdbtn0);
        buttonGroup1.add(rdbtn1);
        buttonGroup1.add(rdbtn2);
        buttonGroup1.add(rdbtn3);
        buttonGroup1.add(rdbtn4);

        gbc.gridy = 6;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel_2.add(Box.createVerticalStrut(10), gbc);
        gbc.insets = new Insets(5, 5, 5, 5);

        JRadioButton btnSym1 = new JRadioButton("Aucune sym\u00e9trie");
        btnSym1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 5;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("Les d\u00e9formations ne sont pas impact\u00e9es par des sym\u00e9tries");
            }
        });
        btnSym1.setFont(new Font("Arial", Font.BOLD, 13));
        btnSym1.setToolTipText("Aucune sym\u00e9trie");
        btnSym1.setSelected(true);
        gbc.gridy = 7;
        panel_2.add(btnSym1, gbc);

        JRadioButton btnSym2 = new JRadioButton("Sym\u00e9trie horizontale");
        btnSym2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 6;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("Les d\u00e9formations seront effectu\u00e9es d'apr\u00e8s un axe de sym\u00e9trie horizontale");
            }
        });
        btnSym2.setFont(new Font("Arial", Font.BOLD, 13));
        btnSym2.setToolTipText("Sym\u00e9trie horizontale");
        gbc.gridy = 8;
        panel_2.add(btnSym2, gbc);

        JRadioButton btnSym3 = new JRadioButton("Sym\u00e9trie verticale");
        btnSym3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 7;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("Les d\u00e9formations seront effectu\u00e9es d'apr\u00e8s une sym\u00e9trie verticale");
            }
        });
        btnSym3.setFont(new Font("Arial", Font.BOLD, 13));
        btnSym3.setToolTipText("Sym\u00e9trie verticale");
        gbc.gridy = 9;
        panel_2.add(btnSym3, gbc);

        JRadioButton btnSym4 = new JRadioButton("Double sym\u00e9trie");
        btnSym4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radiochoix = 8;
                statepolygon = false;
                statesommet = false;
                statecote = false;
                textAreaRemarque.setText("Les d\u00e9formations seront effectu\u00e9es d'apr\u00e8s une sym\u00e9trie horizontale et verticale");
            }
        });
        btnSym4.setFont(new Font("Arial", Font.BOLD, 13));
        btnSym4.setToolTipText("Double sym\u00e9trie");
        gbc.gridy = 10;
        panel_2.add(btnSym4, gbc);

        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(btnSym1);
        buttonGroup2.add(btnSym2);
        buttonGroup2.add(btnSym3);
        buttonGroup2.add(btnSym4);

        JButton btnEnregistrerPave = new JButton("Enregistrer Pave");
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
        btnEnregistrerPave.setFont(new Font("Arial", Font.PLAIN, 14));
        btnEnregistrerPave.setBackground(SystemColor.scrollbar);
        gbc.gridy = 11;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel_2.add(btnEnregistrerPave, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton buttonTransfer = new JButton("Pav\u00e9 --> Pavage");
        buttonTransfer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new PavageInterface(pavem);
            }
        });
        buttonTransfer.setFont(new Font("Arial", Font.BOLD, 14));
        buttonTransfer.setForeground(Color.WHITE);
        buttonTransfer.setBackground(new Color(153, 51, 51));
        gbc.gridy = 12;
        gbc.insets = new Insets(15, 5, 5, 5);
        panel_2.add(buttonTransfer, gbc);
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel lblRemarque = new JLabel("Remarque :");
        lblRemarque.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridy = 13;
        panel_2.add(lblRemarque, gbc);

        textAreaRemarque = new JTextArea(10, 20);
        textAreaRemarque.setFont(new Font("Arial", Font.PLAIN, 15));
        textAreaRemarque.setBackground(SystemColor.inactiveCaptionBorder);
        textAreaRemarque.setLineWrap(true);
        textAreaRemarque.setWrapStyleWord(true);
        textAreaRemarque.setText("Les points du pav\u00e9 initial sont en bleu, \r\n" +
                "Les points qui ont \u00e9t\u00e9 ajout\u00e9s sur le pav\u00e9 sont en rouge.");
        JScrollPane scrollPane = new JScrollPane(textAreaRemarque);
        gbc.gridy = 14;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        panel_2.add(scrollPane, gbc);

        ((Mypanel)panel_1).addMouseListener(new MouseAdapter() {
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
                        panel_1.repaint();
                    }
                } else if(e.getButton() == MouseEvent.BUTTON3) {
                    if(statecote == false && statesommet == false && radiochoix == 3) {
                        sourisx = e.getX() - (panel_1.getWidth() / 2);
                        sourisy = (panel_1.getHeight() / 2) - e.getY();
                        if(recordindicecote != -1) {
                            pave.addPoint(recordindicecote, sourisx, sourisy);
                            updatexy();
                            panel_1.repaint();
                            recordindicecote = -1;
                        }
                    }
                }
            }
        });
        ((Mypanel)panel_1).addMouseMotionListener(((Mypanel)panel_1));

        jframe.getContentPane().add(panel_1, BorderLayout.CENTER);
        jframe.getContentPane().add(panel_2, BorderLayout.EAST);

        jframe.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                panel_1.revalidate();
                panel_2.revalidate();
            }
        });

        jframe.setVisible(true);
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

    static class Mypanel extends JPanel implements MouseMotionListener {
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
                        g2d.fillOval(x[i]-2, y[i]-2, 4, 4);
                    } else {
                        g2d.setColor(Color.BLUE);
                        g2d.fillOval(x[i]-2, y[i]-2, 4, 4);
                    }
                }
                repaint();
            } catch (Exception e) {
                e.printStackTrace();
            }
            BasicStroke stroke = new BasicStroke(0.5f, BasicStroke.CAP_BUTT ,BasicStroke.JOIN_MITER , 3.5f, new float[] { 10, 5 }, 0f);
            g2d.setStroke(stroke);
            g2d.setColor(Color.BLACK);
            g2d.drawPolyline(symhorx,symhory,2);
            g2d.drawPolyline(symverx,symvery,2);
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
            statepolygon = pave.isInPolygon((int)(e.getX() - (getWidth() / 2)),(int)((getHeight() / 2) - e.getY()),x,y);
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
                for(int i = 0; i < cote; i++) {
                    double space = pc.pointToLine(new PointsM((int)(e.getX() - (getWidth() / 2)), (int)((getHeight() / 2) - e.getY())), new PointsM(x[i], y[i]), new PointsM(x[i+1], y[i+1]));
                    if(statesommet == false && space < 2) {
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
