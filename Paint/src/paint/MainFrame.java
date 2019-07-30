package paint;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class MainFrame extends JFrame implements java.io.Serializable {
	private static final long serialVersionUID = -3176753344670668798L;
	
	private Controller c;
	private MainData d;
	private MainPanel p;
	private JMenuItem color_fg;
	private JMenuItem color_bg;

	public MainFrame(Controller c, MainData d) {
		super("Zeichenbrett");
		
		this.c = c;
		this.d = d;
		
		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar mb = new JMenuBar();
		
		//DATEI-MENÜ
		JMenu file = new JMenu("Datei");
		
		JMenuItem file_new = new JMenuItem("Neu");
		file_new.setMnemonic('N');
		file_new.addActionListener(this.c);
		file_new.setActionCommand("file_new");
		file_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
		file.add(file_new);
		
		JMenuItem file_load = new JMenuItem("Laden...");
		file_load.addActionListener(this.c);
		file_load.setActionCommand("file_load");
		file_load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
		file.add(file_load);
		
		JMenuItem file_save = new JMenuItem("Speichern...");
		file_save.addActionListener(this.c);
		file_save.setActionCommand("file_save");
		file_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		file.add(file_save);

		file.addSeparator();
		
		JMenuItem file_quickload = new JMenuItem("Schnellspeicherung laden");
		file_quickload.addActionListener(this.c);
		file_quickload.setActionCommand("file_quickload");
		file.add(file_quickload);
		
		JMenuItem file_quicksave = new JMenuItem("Schnellspeichern");
		file_quicksave.addActionListener(this.c);
		file_quicksave.setActionCommand("file_quicksave");
		file.add(file_quicksave);
		
		mb.add(file);
		
		
		
		//BEARBEITEN-MENÜ
		JMenu edit = new JMenu("Bearbeiten");
		
		JMenuItem edit_del = new JMenuItem("Schritt rückgängig machen");
		edit_del.addActionListener(this.c);
		edit_del.setActionCommand("edit_del");
		edit_del.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		edit.add(edit_del);
		
		JMenuItem edit_rep = new JMenuItem("Schritt wiederholen");
		edit_rep.addActionListener(this.c);
		edit_rep.setActionCommand("edit_rep");
		edit_rep.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK));
		edit.add(edit_rep);
		
		JMenuItem edit_dup = new JMenuItem("Form duplizieren");
		edit_dup.addActionListener(this.c);
		edit_dup.setActionCommand("edit_dup");
		edit_dup.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
		edit.add(edit_dup);
		
		edit.addSeparator();
		
		JMenuItem edit_home = new JMenuItem("Form nach links oben verschieben");
		edit_home.addActionListener(this.c);
		edit_home.setActionCommand("edit_home");
		edit_home.setMnemonic('L');
		edit.add(edit_home);
		
		JMenuItem edit_col = new JMenuItem("Farbe der Form ändern");
		edit_col.setMnemonic('F');
		edit_col.addActionListener(this.c);
		edit_col.setActionCommand("edit_col");
		edit.add(edit_col);
		
		mb.add(edit);
		
		//ZEICHNEN-MEN 
		JMenu draw = new JMenu("Zeichnen");
		ButtonGroup bgrp = new ButtonGroup();
		
		JRadioButtonMenuItem draw_free = new JRadioButtonMenuItem("Freihand zeichnen");
		draw_free.addActionListener(this.c);
		draw_free.setActionCommand("draw_free");
		bgrp.add(draw_free);
		draw.add(draw_free);
		
		JRadioButtonMenuItem draw_line = new JRadioButtonMenuItem("Linien zeichnen", true);
		draw_line.addActionListener(this.c);
		draw_line.setActionCommand("draw_line");
		bgrp.add(draw_line);
		draw.add(draw_line);
		
		JRadioButtonMenuItem draw_rect = new JRadioButtonMenuItem("Rechtecke zeichnen");
		draw_rect.addActionListener(this.c);
		draw_rect.setActionCommand("draw_rect");
		bgrp.add(draw_rect);
		draw.add(draw_rect);
		
		JRadioButtonMenuItem draw_rrect = new JRadioButtonMenuItem("Rechtecke abger. zeichnen");
		draw_rrect.addActionListener(this.c);
		draw_rrect.setActionCommand("draw_rrect");
		bgrp.add(draw_rrect);
		draw.add(draw_rrect);
		
		JRadioButtonMenuItem draw_ell = new JRadioButtonMenuItem("Ellipsen zeichnen");
		draw_ell.addActionListener(this.c);
		draw_ell.setActionCommand("draw_ell");
		bgrp.add(draw_ell);
		draw.add(draw_ell);
		
		JRadioButtonMenuItem draw_poly = new JRadioButtonMenuItem("Polygone zeichnen");
		draw_poly.addActionListener(this.c);
		draw_poly.setActionCommand("draw_poly");
		bgrp.add(draw_poly);
		draw.add(draw_poly);
		
		draw.addSeparator();
		
		JRadioButtonMenuItem draw_rrectf = new JRadioButtonMenuItem("Rechtecke abger. ausmalen");
		draw_rrectf.addActionListener(this.c);
		draw_rrectf.setActionCommand("draw_rrectf");
		bgrp.add(draw_rrectf);
		draw.add(draw_rrectf);
		
		JRadioButtonMenuItem draw_rectf = new JRadioButtonMenuItem("Rechtecke ausmalen");
		draw_rectf.addActionListener(this.c);
		draw_rectf.setActionCommand("draw_rectf");
		bgrp.add(draw_rectf);
		draw.add(draw_rectf);
		
		JRadioButtonMenuItem draw_ellf = new JRadioButtonMenuItem("Ellipsen ausmalen");
		draw_ellf.addActionListener(this.c);
		draw_ellf.setActionCommand("draw_ellf");
		bgrp.add(draw_ellf);
		draw.add(draw_ellf);
		
		JRadioButtonMenuItem draw_polyf = new JRadioButtonMenuItem("Polygone ausmalen");
		draw_polyf.addActionListener(this.c);
		draw_polyf.setActionCommand("draw_polyf");
		bgrp.add(draw_polyf);
		draw.add(draw_polyf);
		
		mb.add(draw);
		
		//FARBE-MENÜ
		JMenu color = new JMenu("Farbe");
		
		color_fg = new JMenuItem("Stift");
		color_fg.setBackground(Color.BLACK);
		color_fg.setHorizontalAlignment(SwingConstants.CENTER);
		color_fg.addActionListener(this.c);
		color_fg.setActionCommand("color_fg");
		color.add(color_fg);
		
		color_bg = new JMenuItem("Hintergrund");
		color_bg.setBackground(Color.WHITE);
		color_bg.setHorizontalAlignment(SwingConstants.CENTER);
		color_bg.addActionListener(this.c);
		color_bg.setActionCommand("color_bg");
		color.add(color_bg);
		
		mb.add(color);
		
		//INFO-MENÜ
		JMenu info = new JMenu("Informationen");
		
		JMenuItem info_about = new JMenuItem("About");
		info_about.addActionListener(this.c);
		info_about.setActionCommand("info_about");
		info.add(info_about);
		
		JMenuItem info_help = new JMenuItem("Hilfe");
		info_help.addActionListener(this.c);
		info_help.setActionCommand("info_help");
		info.add(info_help);
		
		mb.add(info);
		
		this.add(mb);
		this.setJMenuBar(mb);
		
		this.p = new MainPanel(this.d);
		this.p.addMouseListener(this.c);
		this.p.addMouseMotionListener(this.c);
		this.addKeyListener(this.c);
		this.p.setOpaque(true);
		this.c.setPanel(this.p);
		this.setContentPane(this.p);
		
		this.setVisible(true);
		this.c.setGraphicsContext(this.p.getGraphics());
	}
    
	/**
	 * Ruft JPanel.repaint() auf, um die Inhalte des Fensters neu zu zeichnen
	 */
	public void draw() {
		this.p.repaint();
	}
	
	public void setFgColor(Color c) {
		this.color_fg.setBackground(c);
	}
	
	public void setBgColor(Color c) {
		this.color_bg.setBackground(c);
		this.p.repaint();
	}
	
	/**
	 * Zeigt das Hilfe-Fenster an
	 */
	public void showHelp() {
		JOptionPane.showMessageDialog(this, "Hilfe\n\nUm eine Form zu zeichnen, wählen Sie die Form im Menü "
				+ "\"Zeichnen\" aus.\n\n"
				+ "Rechtecke, Ellipsen und Linien: Klicken Sie, um die Form zu starten, ziehen Sie mit\n"
				+ "gedrückter Maustaste und lassen sie die Maustaste wieder los, um die Form zu zeichnen\n"
				+ "Polygon: Klicken Sie links, um ein Polygon zu beginnen und Punkte hinzuzufügen, und klicken\n"
				+ "Sie rechts, um das Polygon abzuschließen\n"
				+ "Freihand: Klicken Sie und ziehen Sie mit gedr ckter Maustaste, um die Linie zu zeichnen.", "Hilfe", JOptionPane.INFORMATION_MESSAGE);
	}
}
