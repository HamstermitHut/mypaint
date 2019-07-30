package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Rect implements Element, java.io.Serializable {
	private static final long serialVersionUID = -7374117941549717561L;
	
	private int x; 
	private int y; //Koordinaten des oberen linken Eckpunktes
	private int width; //Breite (x-Richtung)
	private int height; //Höhe (y-Richtung)
	private boolean fill; //Ob die Form ausgefüllt werden soll
	private Color color;
	
	public Rect() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.fill = false;
		this.color = Color.BLACK;
	}
	
	public Rect(Point p1, Point p2) {
		//Berechnen der Koordinaten des linken oberen Eckpunkt
		this.x = (int) Math.min(p1.getX(), p2.getX());
		this.y = (int) Math.min(p1.getY(), p2.getY());
		
		//Berechnen der Höhe und Breite des Rechtecks
		this.width = (int) Math.abs(p1.getX() - p2.getX());
		this.height = (int) Math.abs(p1.getY() - p2.getY());
		
		this.fill = false;
		this.color = Color.BLACK;
	}
	
	
	public Rect(Point p1, Point p2, boolean fill, Color color) {
		//Berechnen der Koordinaten des linken oberen Eckpunkt
		this.x = (int) Math.min(p1.getX(), p2.getX());
		this.y = (int) Math.min(p1.getY(), p2.getY());
		
		//Berechnen der Höhe und Breite des Rechtecks
		this.width = (int) Math.abs(p1.getX() - p2.getX());
		this.height = (int) Math.abs(p1.getY() - p2.getY());
		
		this.fill = fill;
		this.color = color;
	}

	private Rect(int x, int y, int width, int height, boolean fill, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.fill = fill;
		this.color = new Color(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.color);
		if (fill) g.fillRect(this.x, this.y, this.width, this.height);
		else g.drawRect(this.x, this.y, this.width, this.height);
	}

	@Override
	public Element clone() {
		return new Rect(this.x, this.y, this.width, this.height, this.fill, this.color);
	}

	@Override
	public void addPoint(int x, int y) {
		//Wird für Rechtecke nicht benötigt
	}

	@Override
	public void removeLastPoint() {
		//Wird für Rechtecke nicht benötigt
	}

	@Override
	public void setHomePosition() {
		this.x = 0;
		this.y = 0;
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public void setColor(Color c) {
		color = c;
	}

	@Override
	public void move(int x, int y) {
		this.x = this.x + x;
		this.y = this.y + y;
	}
}
