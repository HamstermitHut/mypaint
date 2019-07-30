package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class MainData implements java.io.Serializable {
	private static final long serialVersionUID = -8252333285696504977L;

	//Eine Referenz auf die View-Klasse
	private transient MainFrame f;
	
	//Enth lt alle Zustände, die im Laufe des Zeichnens waren
	private ArrayList<ZeichenStatus> verlauf;
	
	private int index;
		
	/* Speichert, welche Form gerade im JFrame im Menü "Zeichnen" ausgewählt ist
	 * Der Wert kann einer der folgenden statischen Werte sein */
	private int currentShape;
	public static final int LINE = 0;
	public static final int RECT = 1;
	public static final int ROUNDRECT = 2;
	public static final int OVAL = 3;
	public static final int FILLEDROUNDRECT = 4;
	public static final int FILLEDRECT = 5;
	public static final int FILLEDOVAL = 6;
	public static final int POLYGON = 7;
	public static final int FILLEDPOLYGON = 8;
	public static final int FREEHAND = 9;
	
	private static final String[] SHAPENAMES = {"Linie", "Rechteck", "Abgerundetes Rechteck", "Ellipse", "Ausgefülltes abgerundetes Rechteck", "Ausgefülltes Rechteck", "Ausgefüllte Ellipse", "Polygon", "Ausgefülltes Polygon", "Freihand"};
	
	/**
	 * Erstellt eine neue Instanz der Klasse MainData
	 */
	public MainData() {
		verlauf = new ArrayList<ZeichenStatus>();
		currentShape = 0; //Am Anfang ist "Line" ausgewählt
		index = -1;
	}
	
	/**
	 * Fügt eine neue Form zur Zeichenfläche hinzu.
	 * Diese Methode wird für Formen benutzt, die nur durch zwei Punkte definiert werden.
	 * Das sind LINE, RECT, ROUNDRECT, OVAL, FILLEDROUNDRECT, FILLEDRECT und FILLEDOVAL.
	 * @param start	Der Startpunkt, an dem der Benutzer die Maustaste gedrückt hat
	 * @param end	Der Endpunkt, an dem der Benutzer die Maustaste wieder losgelassen hat
	 */
	public void addShape(Point start, Point end) {
		//Wenn rückgängig gemachte Zust nde vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
				
		verlauf.get(index).addShape(start, end);
	}
	
	/**
	 * Beginnt das Zeichnen eines neuen Polygons (POLYGON und FILLEDPOLYGON)
	 * @param start	Der Startpunkt, an dem der Benutzer zum Zeichnen beginnt.
	 */
	public void startPolygon(Point start) {
		//Wenn rückgängig gemachte Zust nde vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
		
		verlauf.get(index).startPolygon(start);
	}
	
	/**
	 * Fügt einen weiteren Punkt zu einen begonnen Polygon (POLYGON oder FILLEDPOLYGON) hinzu.
	 * @param point	Der Punkt, der hinzugefügt werden soll.
	 */
	public void addPolygonPoint(Point point) {
		verlauf.get(index).addPolygonPoint(point);
	}
	
	/**
	 * Beginnt das Zeichnen einer neuen Freihand-Linie (FREEHAND).
	 * @param start	Der Punkt, an dem der Benutzer zu zeichnen beginnt.
	 */
	public void startFreehand(Point start) {
		//Wenn rückgängig gemachte Zustände vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
		
		verlauf.get(index).startFreehand(start);
	}
	
	/**
	 * Fügt einen neuen Punkt zu einer Freihand-Linie (FREEHAND) hinzu.
	 * @param point Der Punkt, der hinzugefügt werden soll.
	 */
	public void addFreehandPoint(Point point) {
		verlauf.get(index).addFreehandPoint(point);
	}
		
	/**
	 * Zeichnet die aktuellste der ZeichenStatus-Instanzen ins JPanel
	 */
	public void draw(Graphics g) {
		if (this.index >= 0) this.verlauf.get(index).draw(g);
	}
	
	/**
	 * Erzeugt einen neuen ZeichenStatus mit veränderter Vordergrundfarbe
	 */
	public void setFgColor(Color c) {
		//Wenn rückgängig gemachte Zustände vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
		
		verlauf.get(index).setFgColor(c);
		this.f.setFgColor(c);
	}
	
	/**
	 * Erzeugt einen neuen ZeichenStatus mit ver nderter Hintergrundfarbe
	 * @param c Die zu setzende Farbe
	 */
	public void setBgColor(Color c) {
		//Wenn rückgängig gemachte Zustände vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
		
		verlauf.get(index).setBgColor(c);
		this.f.setBgColor(c);
	}
	
	/**
	 * Liefert die im letzten Zeichenstatus gesetzte Vordergrundfarbe zurück
	 * @return Die aktuell gesetzte Vordergrundfarbe
	 */
	public Color getFgColor() {
		if (this.index >= 0) return this.verlauf.get(this.index).getFgColor();
		else return Color.BLACK;
	}
	
	/**
	 * Liefert die im letzten ZeichenStatus gesetzte Hintergrundfarbe zur ck
	 * @return Die aktuell gesetzte Hintergrundfarbe
	 */
	public Color getBgColor() {
		if (this.index >= 0) return this.verlauf.get(this.index).getBgColor();
		else return Color.WHITE;
	}
	
	/**
	 * Macht den zuletzt ausgeführten Schritt rückgängig. Dieser Schritt kann entweder 
	 * eine gezeichnete Form oder eine Änderung der Vorder-/Hintergrundfarbe sein.
	 * @param g Der Graphics-Kontext für das JPanel, auf das gezeichnet wird
	 */
	public void undo(Graphics g) {
		if (index > 0) {
			index--; //Es gibt Schritte, die rückgängig gemacht werden können
			f.draw();
		
		} else if (index == 0) {
			index--;
			f.draw();
		}
	}
	
	/**
	 * Stellt den zuletzt rückgängig gemachten Schritt wieder her.
	 * @param g Der Graphics-Kontext für das JPanel, auf das gezeichnet wird
	 */
	public void redo(Graphics g) {
		if (verlauf.size() > index + 1) index++;
		this.f.draw();
	}
	
	/**
	 * Fügt die zuletzt hinzugefügte Form nochmal hinzu
	 * @param g Der Graphics-Kontext für das JPanel, auf das gezeichnet wird
	 */
	public void dup(Graphics g) {
		//Wenn rückgängig gemachte Zustände vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
		
		//Das letzte gezeichnete Element
		this.verlauf.get(index).dup();
		this.f.draw();
	}
	
	/**
	 * Verschiebt das zuletzt gezeichnete Element ins linke obere Eck
	 * @param g Der Graphics-Kontext für das JPanel, auf das gezeichnet wird
	 */
	public void homePos(Graphics g) {
		//Wenn rückgängig gemachte Zustände vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
		
		//Das letzte gezeichnete Element
		this.verlauf.get(index).homePos();
		this.f.draw();
	}
	
	/**
	 * Setzt die Farbe des zuletzt gezeichneten Elements auf eine neue Farbe
	 * @param c Die neu zu setzende Farbe
	 */
	public void setElementColor(Color c) {
		//Wenn rückgängig gemachte Zustände vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
		
		this.verlauf.get(index).setElementColor(c);
		this.f.draw();
	}
	
	/**
	 * Gibt die Farbe des zuletzt gezeichneten Elements zurück
	 * @return Die Farbe des zuletzt gezeichneten Elements
	 */
	public Color getElementColor() {
		return this.verlauf.get(index).getElementColor();
	}
	
	/**
	 * Verschiebt das zuletzt gezeichnete Element nach links/rechts bzw. oben/unten
	 * @param x Die Anzahl an Pixel, um die das Element nach links (x < 0) oder rechts (x > 0) verschoben werden soll
	 * @param y Die Anzahl an Pixel, um die das Element nach oben (x < 0) oder unten (x > 0) verschoben werden soll
	 */
	public void move(int x, int y) {
		//Wenn rückgängig gemachte Zustände vorliegen, werden diese gelöscht
		if (index + 1 < verlauf.size()) verlauf.subList(index + 1, verlauf.size()).clear();
		
		if (index >= 0) verlauf.add(verlauf.get(index).clone());
		else verlauf.add(new ZeichenStatus(this));
		index++;
		
		this.verlauf.get(index).move(x, y);
		this.f.draw();
	}
	
	public void quicksave() {
		MainData msave = this;
		String directory;
		try {
			String OS = System.getProperty ("os.name").toLowerCase();
			directory = "";
			if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) directory = "/tmp/objects.ser";
			if(OS.indexOf("win") >= 0) directory = "quicksave.pnt";
			FileOutputStream fileOut = new FileOutputStream(directory);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(msave);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public void quickload() {
		MainData e = null;
		String directory;
		try {
			String OS = System.getProperty("os.name").toLowerCase();
			directory = "";
			if(OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 ) directory = "/tmp/objects.ser";
			if(OS.indexOf("win") >= 0) directory = "quicksave.pnt";
			FileInputStream fileIn = new FileInputStream(directory);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (MainData) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}
		
		this.verlauf = e.verlauf;
		this.index = e.index;
	}
	
	public void save(String s) {
		try {
			if (!s.endsWith(".pnt")) s += ".pnt";
			FileOutputStream fileOut = new FileOutputStream(s);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public void load(String s) {
		MainData e;
		try {
			FileInputStream fileIn = new FileInputStream(s);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			e = (MainData) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return;
		}
		
		this.verlauf = e.verlauf;
		this.index = e.index;
		this.currentShape = e.currentShape;
		
		this.f.draw();
	}
	
	
	/**
	 * Setzt die aktuell zu zeichnende Form auf den übergebenen Wert.
	 * Die möglichen Werte sind 0 bis 9, siehe den Konstanten oben
	 * @param s	Die zu setzende Form
	 */
	public void setCurrentShape(int s) {
		if ((s >= 0) || ( s <= 9)) this.currentShape = s;
	}
	
	/**
	 * Gibt die aktuell zu zeichnende Form zurück.
	 * @return	Die aktuell zu zeichnende Form. Die Werte sind 0-9, siehe Konstanten oben.
	 */
	public int getCurrentShape() {
		return this.currentShape;
	}
	
	/**
	 * Gibt die aktuell zu zeichnende Form als String zurück
	 * @return	Der Name der aktuell zu zeichnenden Form
	 */
	public String getCurrentShapeString() {
		return MainData.SHAPENAMES[this.currentShape];
	}
	
	public ArrayList<ZeichenStatus> getVerlauf() {
		return verlauf;
	}

	public void delete() {
		int i;
		if (this.verlauf.size() > 0) i = JOptionPane.showConfirmDialog(this.f, "Dadurch wird die gesamte nicht gespeicherte Arbeit gelöscht. Fortfahren?", "Neue Datei", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		else i = JOptionPane.YES_OPTION;
		
		if (i == JOptionPane.YES_OPTION) {
			this.verlauf =  new ArrayList<ZeichenStatus>();
			this.index = -1;
			this.currentShape = MainData.LINE;
			this.f.draw();
		}
	}
	
	/**
	 * Speichert eine Referenz auf die View-Klasse
	 * @param f Eine Instanz der Klasse MainFrame, die die View-Klasse darstellt
	 */
	public void setF(MainFrame f) {
		this.f = f;
	}
}
