package paint;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Controller implements ActionListener, MouseListener, MouseMotionListener, KeyListener, java.io.Serializable {
	private static final long serialVersionUID = -7043197611481310925L;
	
	private MainData d;
	private MainFrame f;
	private JPanel p;
	private transient Graphics g;
	
	/* 
	 * Speichert die Koordinaten, an denen ein mousePressed-Ereignis aufgetreten ist.
	 * Diese Koordinaten sind der erste Punkt für Formen wie Rechteck, Oval, Linie...
	 */
	private Point mousedown;
	
	//Speichert, ob gerade eine Form gezeichnet wird
	private boolean drawing;
	
	public Controller() {
		this.d = new MainData();
		this.f = new MainFrame(this, this.d);
		this.d.setF(this.f);
		
		this.drawing = false;
	}
	
	/*
	 * Wird zum Zeichnen von Polygonen benötigt.
	 * Mit Linksklick werden die Ecken gezeichnet, mit Rechtsklick wird die Form abgeschlossen
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == this.p) {
			//Nur Polygone werden mit mehreren Klicks gezeichnet
			if (d.getCurrentShape() == MainData.POLYGON || d.getCurrentShape() == MainData.FILLEDPOLYGON) {
				if (e.getButton() == 1) {
					//Linksklick - zum Starten eines neuen Polygons bzw. hinzufügen eines Punktes
					if (!drawing) {
						this.d.startPolygon(e.getPoint());
						this.drawing = true;
					} else {
						this.d.addPolygonPoint(e.getPoint());
						this.f.draw();
					}
					
				} else if (e.getButton() == 3) {
					//Rechtsklick - zum Beenden des Polygons
					this.drawing = false;
				}
			}
		} else if (e.getSource() instanceof JMenu) { 
			this.f.draw();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		//Wird nicht benötigt
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Wird nicht benötigt
	}

	/*
	 * Wird zum Zeichnen von Rechtecken, Ellipsen, Linien und Freihand-Linien ben tigt
	 */
	@Override
	public void mousePressed(MouseEvent e) {		
		//Neue Form starten (außer bei Polygon, das wird durch mouseClicked gestartet)
		if (d.getCurrentShape() >= 0 && d.getCurrentShape() <= 6) {
			drawing = true;
			this.mousedown = e.getPoint();
			
		} else if (d.getCurrentShape() == MainData.FREEHAND) {
			drawing = true;
			d.startFreehand(e.getPoint());
		}
	}

	/*
	 * Wird zum Zeichnen von Rechtecken, Ellipsen, Linien und Freihand-Linien ben tigt
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		
		if (d.getCurrentShape() >= 0 && d.getCurrentShape() <= 6) {
			//Bei Rechtecken, Ovalen und Linien wird die Form gezeichnet
			d.addShape(mousedown, e.getPoint()); 	//Neue Form hinzuf gen
			this.drawing = false;
			this.f.draw(); //Den aktuellsten ZeichenStatus zeichnen
			
		} else if (d.getCurrentShape() == MainData.FREEHAND) {
			//Bei Freihandlinien wird das Zeichnen der Form abgeschlossen
			this.drawing = false;
			this.f.draw(); //Den aktuellsten ZeichenStatus zeichnen
		}
	}
	
	/*
	 * Wird zum Zeichnen von Freihand-Linien benötigt (Links klicken, halten und ziehen)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (this.d.getCurrentShape() == MainData.FREEHAND) {
			this.d.addFreehandPoint(e.getPoint());
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		//Wird nicht benötigt
	}

	/*
	 * Um beim Klick auf die JMenuItems die jeweilige Aktion auszuf hren
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		drawing = false;
		switch(e.getActionCommand()) {
			case "draw_free":	this.d.setCurrentShape(MainData.FREEHAND);
								break;
			case "draw_line":	this.d.setCurrentShape(MainData.LINE);
								break;
			case "draw_rect":	this.d.setCurrentShape(MainData.RECT);
								break;
			case "draw_rrect":	this.d.setCurrentShape(MainData.ROUNDRECT);
								break;
			case "draw_ell":	this.d.setCurrentShape(MainData.OVAL);
								break;
			case "draw_poly":	this.d.setCurrentShape(MainData.POLYGON);
								break;
			case "draw_rrectf":	this.d.setCurrentShape(MainData.FILLEDROUNDRECT);
								break;
			case "draw_rectf":	this.d.setCurrentShape(MainData.FILLEDRECT);
								break;
			case "draw_ellf":	this.d.setCurrentShape(MainData.FILLEDOVAL);
								break;
			case "draw_polyf":	this.d.setCurrentShape(MainData.FILLEDPOLYGON);
								break;
								
								
			case "edit_del":	this.d.undo(g);
								break;
			case "edit_rep":	this.d.redo(g);
								break;
			case "edit_dup":	this.d.dup(g);
								break;		
			case "edit_home":	this.d.homePos(g);
								break;
			case "edit_col": 	this.d.setElementColor(JColorChooser.showDialog(this.f, "Elementfarbe ändern", this.d.getElementColor()));
								break;
			case "file_quicksave":
								this.d.quicksave();
								break;
			case "file_quickload":
								this.d.quickload();
								this.f.draw();
								break;
			case "file_new":
								this.d.delete();
								break;
			case "file_save":	
								JFileChooser fsc = new JFileChooser();
								fsc.setFileFilter(new FileNameExtensionFilter("Paint", "pnt"));
								if (fsc.showSaveDialog(this.f) == JFileChooser.APPROVE_OPTION) this.d.save(fsc.getSelectedFile().getPath());
								break;
			case "file_load":	
								JFileChooser flc = new JFileChooser();
								flc.setFileFilter(new FileNameExtensionFilter("Paint", "pnt"));
								if (flc.showOpenDialog(this.f) == JFileChooser.APPROVE_OPTION) this.d.load(flc.getSelectedFile().getPath());
								break;
			case "color_fg":
								this.d.setFgColor(JColorChooser.showDialog(this.f, "Vordergrundfarbe auswählen", this.d.getFgColor()));
								break;
			case "color_bg":	
								this.d.setBgColor(JColorChooser.showDialog(this.f, "Hintergrundfarbe auswählen", this.d.getBgColor()));
								this.f.draw();
								break;
			//case "info_help":	f.showHelp();
								//break;
			default:			System.out.println(e.getActionCommand());
								return;
		}
	}
	
	/*
	 * Für das Verschieben des letzten Objekts mittels Pfeiltasten
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
			case KeyEvent.VK_KP_UP:
				this.d.move(0, -10);
				break;
			
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
				this.d.move(-10, 0);
				break;
				
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_KP_DOWN:
				this.d.move(0, 10);
				break;
				
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_KP_RIGHT:
				this.d.move(10, 0);
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		//Wird nicht benötigt
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//Wird nicht benötigt
	}
	
	public void setGraphicsContext(Graphics g) {
		this.g = g;
	}
	
	public void setPanel(JPanel p) {
		this.p = p;
	}
}
