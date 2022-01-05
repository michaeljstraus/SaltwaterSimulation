package researchsim;
import java.awt.*;

public class Slider extends Block {
	int height = 20;
	
	public Slider() {
		
	}
	
	public Slider(int height, int vx) {
		this.height = height;
		this.vx = vx;
	}
	
	public void addVelocity() {
		xo+= vx;
	}
	
	@Override
	public void draw(Graphics2D g2d, int bottom) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, bottom-height, (int) xo, height);
	}
	
}
