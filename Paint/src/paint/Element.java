package paint;
/**
 * Basis-Interface fuer alle Zeichenobjekte
 */


import java.awt.Color;
import java.awt.Graphics;

public interface Element {
	/**
	 * Zeichnet eine Figur auf dem Graphics-Kontext
	 * @param g Grafics-Kontext
	 */
	public void draw(Graphics g);
	/**
	 * Erzeugt eine Kopie vom eigenen Objekt
	 */
	public Element clone();
	/**
	 * Einen neuen Punkt hinzuf�gen<br>
	 * Einige Zeichenmethoden benoetigen mehrere Punkte:<br>
	 * z.b.: Freihand, Line (Start- und Endpunkt),...
	 * wird auch fuer die Methode setHomePosition benoetigt
	 * 
	 * @param x x-Koordinate
	 * @param y y-Koordinate
	 */
	public void addPoint(int x, int y);
	/**
	 * entfernt den letzten Punkt
	 */
	public void removeLastPoint();
	
	/**
	 * Element wird auf Position (0/0) gesetzt
	 */
	public void setHomePosition();
	
	/**
	 * Gibt die aktuelle Farbe (Vordergrund) zurueck
	 * @return Color
	 */
	public Color getColor();
	
	/**
	 * Setzt die Farbe des Vordergrundes
	 * @param c the c to set
	 */
	public void setColor(Color c);
	
	/**
	 * Verschiebt das Element nach links/rechts bzw. oben/unten
	 * @param x Die Anzahl an Pixel, um die das Element nach links (x < 0) oder rechts (x > 0) verschoben werden soll
	 * @param y Die Anzahl an Pixel, um die das Element nach oben (x < 0) oder unten (x > 0) verschoben werden soll
	 */
	public void move(int x, int y);
	
}
