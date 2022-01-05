package researchsim;
import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Block extends JPanel {
	
	public Color c = Color.WHITE;
	public double xo = 0;
	public double yo = 0;
	public double vy = 0;
	public double vx = 0;
	public double width = 0;
	public boolean filled = false;
	public boolean water = false;
	public boolean stuck = false;
	Graphics g;
	Graphics2D g2d = (Graphics2D) g;
	
	
	public Block() {
		
	}
	
	public Block(Color c, double xo, double yo, double width, boolean filled) {
		this.c = c;
		this.xo = xo;
		this.yo = yo;
		this.width = width;
		this.filled = filled;
	}
	
	public Block(Color c, double xo, double yo, double vx, double vy, double width, boolean filled) {
		this.c = c;
		this.xo = xo;
		this.yo = yo;
		this.vy = vx;
		this.vx = vy;
		this.width = width;
		this.filled = filled;
	}
	
	public void draw(Graphics2D g2d, int f/*frame*/) {
		g2d.setColor(this.c);
        if (this.filled = true) {
        	g2d.fillRect( (int) (xo), (int) (yo), (int) width, (int) (width));
        }
        else {
        	g2d.drawRect( (int) (xo), (int) (yo), (int) (width), (int) (width));
        }
        
	}
	
	public void stick() {
		this.vy = 0;
		this.vx = 0;
		stuck = true;
	}
	
	
	

}