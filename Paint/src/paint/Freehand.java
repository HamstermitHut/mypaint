package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Freehand implements Element, java.io.Serializable {
	private static final long serialVersionUID = 6928293048323916317L;
	
	//ArrayLists für die Punkt-Koordinaten
	//(Graphics.drawPolygon fordert Arrays für x- und y-Koordinaten)
	private ArrayList<Integer> xpoints;
	private ArrayList<Integer> ypoints;
	private Color color;
	
	public Freehand() {
		this.xpoints = new ArrayList<Integer>();
		this.ypoints = new ArrayList<Integer>();
		
		this.color = Color.BLACK;
	}
	
	public Freehand(Color color) {
		this.xpoints = new ArrayList<Integer>();
		this.ypoints = new ArrayList<Integer>();
		this.color = color;
	}
	public Freehand(ArrayList<Integer> xpoints, ArrayList<Integer> ypoints, Color color) {
		this.xpoints = new ArrayList<Integer>();
		for (Integer i: xpoints) this.xpoints.add(i);
		
		this.ypoints = new ArrayList<Integer>();
		for (Integer i: ypoints) this.ypoints.add(i);
		
		this.color = new Color(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(this.color);
		
		int[] xpointsInt = new int[xpoints.size()];
		for (int i = 0; i < xpoints.size(); i++) xpointsInt[i] = xpoints.get(i);
		int[] ypointsInt = new int[ypoints.size()];
		for (int i = 0; i < ypoints.size(); i++) ypointsInt[i] = ypoints.get(i);
		
		g.drawPolyline(xpointsInt, ypointsInt, xpointsInt.length);
	}
	
	@Override
	public Element clone() {
		return new Freehand(this.xpoints, this.ypoints, this.color);
	}
	
	@Override
	public void addPoint(int x, int y) {
		xpoints.add(x);
		ypoints.add(y);
	}
	
	@Override
	public void removeLastPoint() {
		xpoints.remove(xpoints.size() - 1);
		ypoints.remove(ypoints.size() - 1);
	}
	
	@Override
	public void setHomePosition() {
		//Suchen der x-Koordinate des linkesten Punktes der Linie
		int lowestX = xpoints.get(0);
		for (Integer x : xpoints) {
			if (x < lowestX) lowestX = x;
		}
		
		//Suchen der y-Koordinate des obersten Punktes des Polygons
		int lowestY = ypoints.get(0);
		for (Integer y : xpoints) {
			if (y < lowestY) lowestY = y;
		}
		
		//Verschieben aller Punkte des Polygons
		for (int i = 0; i < xpoints.size(); i++) xpoints.set(i, xpoints.get(i) - lowestX);
		for (int i = 0; i < ypoints.size(); i++) ypoints.set(i, ypoints.get(i) - lowestY);
	}

	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void move(int x, int y) {
		for (int a = 0; a < xpoints.size(); a++) xpoints.set(a, xpoints.get(a) + x);
		for (int a = 0; a < ypoints.size(); a++) ypoints.set(a, ypoints.get(a) + y);
	}
}