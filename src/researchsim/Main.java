package researchsim;

import java.awt.*;

public class Main {
	Board bo = new Board();
	public void exec() {
        EventQueue.invokeLater(() -> {
            SwingTimerEx ex = new SwingTimerEx(this.bo);
            ex.setVisible(true);
        });
    }
	
    public static void main(String[] args) {
    	Board bo = new Board();
    	Main m = new Main();
    	m.bo = bo;
//    	Block b = new Block(Color.BLUE, 2, 100, 0, 1, 10, false);
//    	bo.addBlock(b);
    	bo.propagateWater(53600);
    	bo.propagateIons(550);
    	m.exec();
    	

	
}
}
