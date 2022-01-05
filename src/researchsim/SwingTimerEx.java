package researchsim;

//import java.awt.EventQueue;
import javax.swing.JFrame;

public class SwingTimerEx extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SwingTimerEx(Board bo) {
        add(bo);
        
        setResizable(false);
        pack();
        
        setTitle("Saltwater Purification Simulation");
        setLocationRelativeTo(null);        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}