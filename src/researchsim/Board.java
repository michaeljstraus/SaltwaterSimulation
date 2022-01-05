package researchsim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Button;

public class Board extends JPanel  {

    private final int B_WIDTH = 1000;
    private final int B_HEIGHT = 500;  
    private final int INITIAL_DELAY = 100;
    private final int PERIOD_INTERVAL = 25;
    public final double pctBoardCovered = .7;
	public final int MWL/*maxWaterLevel*/ = (int) (B_HEIGHT * (1-pctBoardCovered));
	public final int waterWidth = 5;
	public final int ionWidth = 8;
    private ArrayList<Block> bls = new ArrayList<Block>();
    private int midBoard = ((B_HEIGHT - MWL) / 2) + MWL;
    private Timer timer;
    
    private int numTop = 0;
    private int numBot = 0;
	int f = 0;
	public double avgY = midBoard;
	public double HavgY /* where the Hall Effect is pushing the average ion location */ = (2 * (B_HEIGHT - MWL) / 3) + MWL;
	public double numWater = 0;
	public double numIons = 0;
	public double density = 0;
	Slider s = new Slider(20, B_WIDTH / 200);
	boolean deleteFlag = false;
	int numStuck = 0;
	
    public Board() {
    	
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), INITIAL_DELAY, PERIOD_INTERVAL); 
        
    }
    @Override
    public void paint(Graphics g) {
    	f++;
    	
    	if (f % 2 == 0) {
    		super.paint(g);
//    		JButton button = new JButton();
//    		button.setBounds(B_WIDTH * (7/8),B_WIDTH * (3/4), B_HEIGHT * (7/8),B_HEIGHT * (3/4));
//    		button.setVisible(true);
//    		button.paint(g);
    		
    		g.drawLine(0, MWL, B_WIDTH, MWL);
    		g.drawLine(0, midBoard, B_WIDTH, midBoard);
    		g.drawString("Number of Ions in Top Half: " + numTop, 0, 10);
    		g.drawString("Number of Ions in Bottom Half: " + numBot, 0, 30);
    		density = ((numIons / (numWater + numIons))) * 100;
    		String dString = String.format("%.3f", density);
    		g.drawString("Molar Ion Density: " + dString + "%", 0, 50);
    		String aString = String.format("%.3f", this.calculateIonay(s));
			g.drawString("Acceleration: " + aString, 0, 70);
//    		g.drawString("Number Stuck: " + numStuck, 0, 70);
            Graphics2D g2d = (Graphics2D) g;
//            this.addBlock(s);
            s.addVelocity();
            
//            g2d.drawRect(5, 10, 3, 2);
//            System.out.println(bls);
        	if (s.xo >= B_WIDTH){
        		deleteFlag = true;
        		s.xo = 0;
        	}
            avgY = 0;
            if (!bls.isEmpty()) {
            	for (Block b : bls) {
               	 
                	if (b.water == false) {
                		avgY += b.yo / (numIons);
                	}
                	
                	if ((b.xo) >= B_WIDTH - b.width) {
                		b.xo = 1;
                		b.vx = Math.abs(b.vx);
                	}
                	if ((b.xo <= 0)) {
                		b.xo = B_WIDTH;
                		b.vx = -Math.abs(b.vx);
                	}
                	
                	if ((b.yo + b.vy) >= (B_HEIGHT) || (b.yo + b.vy) <= (MWL)) {
                		if ((!b.water && (randomInRange(0,100) - numStuck) > 0) && (b.yo + b.vy) >= (B_HEIGHT)) {
                			b.stick();
                			b.yo = B_HEIGHT - b.width;
                			numStuck++;
                		} 
//                		else {
////                			System.out.println("Nothing done because " + (int) (randomInRange(0,100) - numStuck) + " is less than 0 or " + (b.yo + b.vy) + " is less than " + (B_HEIGHT));
//                			
//                		}
                		
                		b.vy = -b.vy;

                	}

                	if (deleteFlag && b.yo >= this.B_HEIGHT-s.height) {
                		b.c = Color.MAGENTA;
//                		bls.remove(b);
                	}
                	

                }
            	if (numIons > 0) {
            		 for (Block b : bls) {

                     	if (b.water == false) {
                         	b.vy += calculateIonay(b);
                     	
         		        	if (b.yo + b.vy > midBoard && b.yo < midBoard) {
         		//            		b.c = Color.GREEN;
         		        		numTop--;
         		        		numBot++;
//         		        		System.out.println("Moved to bottom because initial = " +  b.yo + " and final = " +  (b.yo + b.vy));
         		        	}
         		        	else if (b.yo + b.vy <= midBoard && b.yo >= midBoard) {
         		//            		b.c = Color.ORANGE;
         		        		numBot--;
         		        		numTop++;
//         		        		System.out.println("Moved to top because initial = " +  b.yo + " and final = " +  (b.yo + b.vy));
         		        	}
                     	}

                     	
                     }
            	}
               

                
                for (Block b : bls) {
                	b.xo += b.vx;
                	b.yo += b.vy;
                	b.draw(g2d, f);
                }
//                g2d.setColor(Color.PINK);
//                g.drawLine(0, (int) avgY, B_WIDTH, (int) avgY);
                s.draw(g2d, B_HEIGHT);
        	}
            }

        if (deleteFlag) {
        	for (int i = 0; i <= bls.size()-1; i++) {
            	if (bls.get(i).c.equals(Color.MAGENTA)){
            		this.removeBlock(bls, i);
            		i--;
            		
            	}
            }
        	deleteFlag = false;

        }
        if (numIons == 0) {
        	s.vx = 0;
        }
        // code to draw rectangles goes here...
    }
    public void addBlock(Block b) {

    	bls.add(b);
    	
    }
    
    public void removeBlock(ArrayList<Block> bls, Block b) {
    	int i = bls.indexOf(b);
    	this.removeBlock(bls, i);

    }
    
    public void removeBlock (ArrayList<Block> bls, int i) {
    	Block b = bls.get(i);
		if (!b.water) {
			numIons--;
			numBot--;
			if (b.stuck) {
				numStuck--;
			}
		}
		else {
			numWater--;
		}
		bls.remove(b);

    }
    
    private class ScheduleTask extends TimerTask {

        @Override
        public void run() {
        	
            repaint();
            
        }
        
    }
    public double randomInRange(double min, double max) {
    	double randomnum = Math.random();
    	return (double) (randomnum * (max-min) + min);
    }
    
    public void propagateWater (int n) {


    	int i = 0;
    	numWater += n;
    	while (i < n) {
    		double xo = this.randomInRange(0, B_WIDTH - waterWidth);
    		double yo = this.randomInRange(B_HEIGHT - waterWidth, MWL);
        	double waterVx = this.randomInRange(1, 3);
        	Block w = new Block(Color.BLUE, xo, yo, 0, waterVx, waterWidth, true);
        	w.vy = this.randomInRange(-3,3);
        	w.water = true;
    		this.addBlock(w);
    		i++;
    		
    	}
    	
    }
    
    public void propagateIons (int n) {

    	int i = 0;
    	Color c = Color.RED;
    	numIons += n;
    	while (i < n) {
    		double xo = this.randomInRange(0, B_WIDTH - waterWidth);
    		double yo = this.randomInRange(B_HEIGHT - waterWidth, MWL);
    		if (yo >= midBoard) {
    			numBot++;
//    			c = Color.GREEN;
    		}
    		else {
    			numTop++;
//    			c = Color.ORANGE;
    		}
    		
        	double ionvx = this.randomInRange(1, 3);
        	Block io = new Block(c, xo, yo, 0, ionvx, waterWidth, true);
        	io.vy = this.randomInRange(-5,5);
    		this.addBlock(io);
    		i++;
    		
    	}
    }
    
    public double calculateIonay(Block b) {
//    	System.out.println("HavgY: " + HavgY);
//    	System.out.println("AvgY: " + avgY);
//    	System.out.println(HavgY-avgY / (HavgY));
    	if (!b.stuck) {
    		return ((HavgY-avgY) / (HavgY));
    	} else {
    		return 0;
    	}
    	
    }
    

}