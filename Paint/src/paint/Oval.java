package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Oval implements Element, java.io.Serializable  {
	private static final long serialVersionUID = -1990829847270150251L;
	
	private int x; 
	private int y; //Koordinaten des oberen linken "Eckpunktes"
	private int width; //Breite (x-Richtung)
	private int height; //H he (y-Richtung)
	private boolean fill; //Ob die Form ausgef llt werden soll
	private Color color;
	
	public Oval() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.fill = false;
		this.color = Color.BLACK;
	}
	
	public Oval(Point p1, Point p2) {
		//Berechnen der Koordinaten des linken oberen Eckpunkt
		this.x = (int) Math.min(p1.getX(), p2.getX());
		this.y = (int) Math.min(p1.getY(), p2.getY());
		
		//Berechnen der H he und Breite des Rechtecks
		this.width = (int) Math.abs(p1.getX() - p2.getX());
		this.height = (int) Math.abs(p1.getY() - p2.getY());
		
		this.fill = false;
		this.color = Color.BLACK;
	}
	
	public Oval(Point p1, Point p2, boolean fill, Color color) {
		//Berechnen der Koordinaten des linken oberen Eckpunkt
		this.x = (int) Math.min(p1.getX(), p2.getX());
		this.y = (int) Math.min(p1.getY(), p2.getY());
		
		//Berechnen der H he und Breite des Rechtecks
		this.width = (int) Math.abs(p1.getX() - p2.getX());
		this.height = (int) Math.abs(p1.getY() - p2.getY());
		
		this.fill = fill;
		this.color = color;
	}

	private Oval(int x, int y, int width, int height, boolean fill, Color color) {
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
		if (fill) g.fillOval(this.x, this.y, this.width, this.height);
		else g.drawOval(this.x, this.y, this.width, this.height);
	}

	@Override
	public Element clone() {
		return new Oval(this.x, this.y, this.width, this.height, this.fill, this.color);
	}

	@Override
	public void addPoint(int x, int y) {
		//Wird f r Oval nicht ben tigt
	}

	@Override
	public void removeLastPoint() {
		//Wird f r Oval nicht ben tigt
	}

	@Override
	public void setHomePosition() {
		this.x = 0;
		this.y = 0;
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void move(int x, int y) {
		this.x = this.x + x;
		this.y = this.y + y;
	}
}
