package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class ZeichenStatus implements Cloneable, java.io.Serializable {
	private static final long serialVersionUID = 632993760179696557L;

	private MainData m;
	
	/**
	 * Enthält alle zu diesem Zeitpunkt auf der Zeichenfläche vorhandenen
	 * Elemente. Dabei ist das neueste Element immer an letzter Stelle. 
	 */
	private ArrayList<Element> elemente;
	private Color fgColor;
	private Color bgColor;
	
	/**
	 * Erstellt einen neuen ZeichenStatus ohne Elemente und mit der Standard-Vorder- und
	 * Hintergrundfarbe. Alle weiteren ZeichenStatus-Instanzen werden dann mit .clone() erzeugt.
	 */
	public ZeichenStatus(MainData m) {
		this.m = m;
		elemente = new ArrayList<>();
		this.fgColor = Color.BLACK;
		this.bgColor = Color.WHITE;
	}
	
	public ZeichenStatus clone() {
		ZeichenStatus z = new ZeichenStatus(this.m);
		z.bgColor = new Color(this.bgColor.getRed(), this.bgColor.getGreen(), this.bgColor.getBlue());
		z.fgColor = new Color(this.fgColor.getRed(), this.fgColor.getGreen(), this.fgColor.getBlue());
		
		for (Element e: this.elemente) {
			z.elemente.add(e.clone());
		}

		return z;
	}
	
	/**
	 * Zeichnet diesen ZeichenStatus auf die Zeichenfläche (JPanel). Die Zeichenfläche
	 * sollte vorher "gelöscht" werden.
	 */
	public void draw(Graphics g) {
		for (Element e: elemente) e.draw(g);
	}
	
	/**
	 * Fügt ein neues Element hinzu. Bevor diese Methode aufgerufen wird, sollte die Instanz
	 * des ZeichenStatus geklont werden und der geklonte ZeichenStatus verändert werden.
	 * Diese Methode wird für Formen benutzt, die nur durch zwei Punkte definiert werden.
	 * Das sind LINE, RECT, ROUNDRECT, OVAL, FILLEDROUNDRECT, FILLEDRECT und FILLEDOVAL.
	 * @param start	Der Startpunkt, an dem der Benutzer die Maustaste gedrückt hat
	 * @param end	Der Endpunkt, an dem der Benutzer die Maustaste wieder losgelassen hat
	 */
	public void addShape(Point start, Point end) {
		if(m.getCurrentShape()== MainData.LINE) {
			elemente.add(new Line(start, end, fgColor));
		}
		if(m.getCurrentShape() == MainData.RECT) {
			elemente.add(new Rect(start, end, false, fgColor));
		}
		if(m.getCurrentShape() == MainData.ROUNDRECT) {
			elemente.add(new RoundRect(start, end, false, fgColor));
		}
		if(m.getCurrentShape() == MainData.OVAL) {
			elemente.add(new Oval(start, end, false, fgColor));
		}
		if(m.getCurrentShape() == MainData.FILLEDROUNDRECT) {
			elemente.add(new RoundRect(start, end, true, fgColor));
		}
		if(m.getCurrentShape() == MainData.FILLEDRECT) {
			elemente.add(new Rect(start, end, true, fgColor));
		}
		if(m.getCurrentShape() == MainData.FILLEDOVAL) {
			elemente.add(new Oval(start, end, true, fgColor));
		}
	}
	
	/**
	 * Beginnt das Zeichnen eines neuen Polygons (POLYGON und FILLEDPOLYGON).
	 * Bevor diese Methode aufgerufen wird, sollte die Instanz des ZeichenStatus 
	 * geklont werden und der geklonte ZeichenStatus verändert werden.
	 * @param start	Der Startpunkt, an dem der Benutzer zum Zeichnen beginnt.
	 */
	public void startPolygon(Point start) {
		if (m.getCurrentShape() == MainData.FILLEDPOLYGON) elemente.add(new Polygon(this.fgColor, true));
		if (m.getCurrentShape() == MainData.POLYGON) elemente.add(new Polygon(this.fgColor, false));
		elemente.get(elemente.size() - 1).addPoint((int) start.getX(), (int) start.getY());
	}
	
	/**
	 * Fügt einen weiteren Punkt zu einen begonnen Polygon (POLYGON oder FILLEDPOLYGON) hinzu.
	 * @param point	Der Punkt, der hinzugefügt werden soll.
	 */
	public void addPolygonPoint(Point point) {
		Element e = elemente.get(elemente.size() - 1);
		if (e instanceof Polygon) e.addPoint((int)point.getX(), (int) point.getY());
	}
	
	/**
	 * Beginnt das Zeichnen einer neuen Freihand-Linie (FREEHAND).
	 * Bevor diese Methode aufgerufen wird, sollte die Instanz des ZeichenStatus
	 * geklont werden und der geklonte ZeichenStatus verändert werden.
	 * @param start	Der Punkt, an dem der Benutzer zu zeichnen beginnt.
	 */
	public void startFreehand(Point start) {
		if (m.getCurrentShape() == MainData.FREEHAND) elemente.add(new Freehand(this.fgColor));
		elemente.get(elemente.size() - 1).addPoint((int) start.getX(), (int) start.getY());
	}
	
	/**
	 * Fügt einen neuen Punkt zu einer Freihand-Linie (FREEHAND) hinzu.
	 * @param point Der Punkt, der hinzugefügt werden soll.
	 */
	public void addFreehandPoint(Point point) {
		Element e = elemente.get(elemente.size() - 1);
		if (e instanceof Freehand) e.addPoint((int) point.getX(), (int) point.getY());
	}
	
	/**
	 * Fügt das zuletzt hinzugefügte Element ein weiteres Mal hinzu
	 */
	public void dup() {
		this.elemente.add(this.elemente.get(this.elemente.size() - 1).clone());
	}
	
	/**
	 * Verschiebt das zuletzt gezeichnete Element in die linke obere Ecke
	 */
	public void homePos() {
		this.elemente.get(this.elemente.size() - 1).setHomePosition();
	}
	
	/**
	 * Setzt die Farbe des zuletzt gezeichneten Elements auf eine neue Farbe
	 * @param c Die neu zu setzende Farbe
	 */
	public void setElementColor(Color c) {
		this.elemente.get(this.elemente.size() - 1).setColor(c);
	}
	
	/**
	 * Gibt die Farbe des zuletzt gezeichneten Elements zurück
	 * @return Die Farbe des zuletzt gezeichneten Elements
	 */
	public Color getElementColor() {
		return this.elemente.get(this.elemente.size() - 1).getColor();
	}
	
	/**
	 * Verschiebt das zuletzt gezeichnete Element nach links/rechts bzw. oben/unten
	 * @param x Die Anzahl an Pixel, um die das Element nach links (x < 0) oder rechts (x > 0) verschoben werden soll
	 * @param y Die Anzahl an Pixel, um die das Element nach oben (x < 0) oder unten (x > 0) verschoben werden soll
	 */
	public void move(int x, int y) {
		this.elemente.get(this.elemente.size() - 1).move(x, y);
	}
	
	/**
	 * Setzt die Vordergrundfarbe auf den übergebenen Wert. Bevor diese Methode 
	 * aufgerufen wird, sollte die Instanz des ZeichenStatus geklont werden und 
	 * der geklonte ZeichenStatus verändert werden.
	 * @param c	Die Farbe, die gesetzt werden soll
	 */
	public void setFgColor(Color c) {
		this.fgColor = c;
	}
	
	/**
	 * Setzt die Hintergrundfarbe auf den übergebenen Wert. Bevor diese Methode 
	 * aufgerufen wird, sollte die Instanz des ZeichenStatus geklont werden und 
	 * der geklonte ZeichenStatus verändert werden.
	 * @param c	Die Farbe, die gesetzt werden soll
	 */
	public void setBgColor(Color c) {
		this.bgColor = c;
	}
	
	/**
	 * Liefert die aktuelle Vordergrundfarbe zur ck
	 * @return	Die Aktuelle Vordergrundfarbe
	 */
	public Color getFgColor() {
		return this.fgColor;
	}
	
	
	/**
	 * Liefert die aktuelle Hintergrundfarbe zur ck
	 * @return	Die Aktuelle Hintergrundfarbe
	 */
	public Color getBgColor() {
		return this.bgColor;
	}
	public ArrayList<Element> getElemente() {
		return elemente;
	}
}