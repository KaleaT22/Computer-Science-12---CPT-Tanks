import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class tankpanel extends JPanel implements ActionListener{
	//Properties
	int intTank1Pos=320;
	int intTank1Def=0;
	
	int intTank2Pos=960;
	int intTank2Def=0;
	
	Timer thetimer = new Timer(1000/60, this);
	String strScreen = "Start";
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource()==thetimer){
			this.repaint();
		}
	}//Repaints Panel
	
	public void paintComponent(Graphics g){
		//Home screen
		if(strScreen.equals("Start")){
			System.out.println("STARTED");
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, 1280, 720);
			
		//Game screen
		}else if(strScreen.equals("Play")){
			System.out.println("Game screen launched");
			g.setColor(Color.CYAN);
			g.fillRect(0, 0, 1280, 720);
			g.setColor(Color.GREEN);
			g.fillRect(0, 620, 1280, 100);
			
			g.setColor(Color.RED);
			intTank1Pos=intTank1Pos+intTank1Def;
			g.fillRect(intTank1Pos, 580, 80, 40);
			//TANK 1
			
			g.setColor(Color.ORANGE);
			intTank2Pos=intTank2Pos+intTank2Def;
			g.fillRect(intTank2Pos, 580, 80, 40);
			//TANK 2
			
		}
	}
	
	//constructor
	public tankpanel(){
		super();
		this.setPreferredSize(new Dimension(1280, 720));
		thetimer.start();
	}
}
