package paint;

import java.awt.Graphics;

import javax.swing.JPanel;

public class MainPanel extends JPanel {
	private static final long serialVersionUID = 1802850688203420469L;
	
	private MainData d;
	
	public MainPanel(MainData d) {
		super();
		this.d = d;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		//this.setOpaque(true);
		//this.setBackground(this.d.getBgColor());
		g.setColor(this.d.getBgColor());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		this.d.draw(g);
	}
}
