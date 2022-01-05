package researchsim;

import java.awt.*;
import java.util.Random;

public class Water extends Block{
	
	public Water() {
		
	}
	
	public Water(int xo, int yo, int vx, int vy, int width) {
		super(Color.BLUE, xo, yo, vx, vy, width, true);
	}
	
	public void propagate(int n, int top, int bottom, int start, int f) {
		if (n > 0) {
			Random rand = new Random();
			this.xo = rand.nextInt();
			super.draw(g2d, f);
			n --;
			propagate(n, top, bottom, start, f);
		}
	}

}
