import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class tankpanel extends JPanel implements ActionListener{
	//Properties
	int intTank1Pos = 320;
	int intTank1Def = 0;
	int intTank1Pow = 70;
	int intTank1Ang = 45;
	
	
	int intTank2Pos = 960;
	int intTank2Def = 0;
	int intTank2Pow = 70;
	int intTank2Ang = 45;
	
	//Spawns bullet off screen in limbo waiting to be launched
	getBullet bullet1 = new getBullet(-30,0,0,false);
	
	getBullet bullet2 = new getBullet(1310, 0, 0, false);
	
	Timer thetimer = new Timer(1000/60, this);
	String strScreen = "Start";
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			this.repaint();
		}
	}//Repaints Panel
	
	public void paintComponent(Graphics g){
		//Home screen
		if(strScreen.equals("Start")){
			//System.out.println("STARTED");
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, 1280, 720);
			
		//Game screen
		}else if(strScreen.equals("Play")){
			//System.out.println("Game screen launched");
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, 1280, 720);
			g.setColor(Color.GREEN);
			g.fillRect(0, 620, 1280, 100);
			
			//TANK 1
			g.setColor(Color.RED);
			g.fillRect(intTank1Pos, 580, 80, 40);
			
			intTank1Pos = intTank1Pos + intTank1Def;
			
			bullet1.drawIt(g);
			
			//TANK 2
			g.setColor(Color.ORANGE);
			g.fillRect(intTank2Pos, 580, 80, 40);
			
			intTank2Pos = intTank2Pos + intTank2Def;
			
			bullet2.drawIt(g);
		}
	}
	
	//constructor
	public tankpanel(){
		super();
		this.setPreferredSize(new Dimension(1280, 720));
		thetimer.start();
	}
}
