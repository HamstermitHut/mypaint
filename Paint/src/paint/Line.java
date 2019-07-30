package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Line implements Element, java.io.Serializable {
	private static final long serialVersionUID = -2720346013335987582L;
	
	private int x1; 
	private int y1;
	private int x2; 
	private int y2;
	private Color color;
	
	public Line() {
		this.x1 = 0;
		this.y1 = 0;
		this.x2 = 0;
		this.y2 = 0;
		this.color = Color.BLACK;
	}
	public Line(Point p1, Point p2) {
		//Berechnen der Koordinaten des linken oberen Eckpunkt
		this.x1 = (int) p1.getX();
		this.y1 = (int) p1.getY();
		this.x2 = (int) p2.getX();
		this.y2 = (int) p2.getY();
		
		this.color = Color.BLACK;
	}
	public Line(Point p1, Point p2, Color c) {
		//Berechnen der Koordinaten des linken oberen Eckpunkt
		this.x1 = (int) p1.getX();
		this.y1 = (int) p1.getY();
		this.x2 = (int) p2.getX();
		this.y2 = (int) p2.getY();
		this.color = c;
	}

	public Line(int x1, int y1, int x2, int y2, Color c) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.color = new Color(c.getRed(), c.getGreen(), c.getBlue());
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.color);
		g.drawLine(x1, y1, x2, y2);
		
	}

	@Override
	public Element clone() {
		return new Line(this.x1, this.y1, this.x2, this.y2, this.color);
	}

	@Override
	public void addPoint(int x, int y) {
		//Wird für Line nicht benötigt		
	}

	@Override
	public void removeLastPoint() {
		//Wird für Line nicht benötigt
	}

	@Override
	public void setHomePosition() {
		int dx = Math.min(this.x1, this.x2);
		int dy = Math.min(this.y1, this.y2);
		
		this.x1 = this.x1 - dx;
		this.x2 = this.x2 - dx;
		this.y1 = this.y1 - dy;
		this.y2 = this.y2 - dy;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color c) {
		this.color = c;
	}
	
	@Override
	public void move(int x, int y) {
		this.x1 = this.x1 + x;
		this.x2 = this.x2 + x;
		this.y1 = this.y1 + y;
		this.y2 = this.y2 + y;
	}
	
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
}
