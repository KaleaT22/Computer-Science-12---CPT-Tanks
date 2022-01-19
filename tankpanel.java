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
	int intHealth1 = 100;
	
	
	int intTank2Pos = 960;
	int intTank2Def = 0;
	int intTank2Pow = 70;
	int intTank2Ang = 45;
	int intHealth2 = 100;
	
	boolean blnPanelServer;
	
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
			Font Scorefont10 = new Font("Sans Serif", Font.PLAIN, 27);
			if(blnPanelServer == true){
				g.setFont(Scorefont10);
				g.setColor(Color.BLACK);
				g.drawString("Launch angle: "+intTank1Ang, 50, 75);
				g.drawString("Health: "+intHealth1, 50, 150);
			}
			else{
				g.setFont(Scorefont10);
				g.setColor(Color.BLACK);
				g.drawString("Launch angle: "+intTank2Ang, 1000, 75);
				g.drawString("Health: "+intHealth2, 1000, 150);
			}
			
			//TANK 2
			g.setColor(Color.ORANGE);
			g.fillRect(intTank2Pos, 580, 80, 40);
			
			intTank2Pos = intTank2Pos + intTank2Def;
			
			bullet2.drawIt(g);
			if(new Rectangle(bullet1.intX, bullet1.intY, 20, 20).intersects(new Rectangle(intTank2Pos, 580, 80, 40))){
				bullet1.boolLaunched=false;
				bullet1.boolHitbox=true;
				intHealth2 = intHealth2 - 10;
			}else if(bullet1.intY>600){
				bullet1.boolLaunched=false;
				bullet1.boolHitbox=true;
				//If the bullet touches the floor, stop the bullet from moving and change it's color to red
			}else if(bullet1.intX<0 || bullet1.intX>1280){
				bullet1.boolLaunched=false;
				bullet1.boolHitbox=true;
				//If the bullet touches the sides of the screen, stop the bullet from moving and change it's color to red
			}
			//If server's bullet hits enemy tank (client), stop the bullet from moving and change it's color to red
			
			if(new Rectangle(bullet2.intX, bullet2.intY, 20, 20).intersects(new Rectangle(intTank1Pos, 580, 80, 40))){
				bullet2.boolLaunched=false;
				bullet2.boolHitbox=true;
				intHealth1 = intHealth1 - 10;
			}else if(bullet2.intY>600){
				bullet2.boolLaunched=false;
				bullet2.boolHitbox=true;
				//If the bullet touches the floor, stop the bullet from moving and change it's color to red
			}else if(bullet2.intX<0 || bullet2.intX>1280){
				bullet2.boolLaunched=false;
				bullet2.boolHitbox=true;
				//If the bullet touches the sides of the screen, stop the bullet from moving and change it's color to red
			}
			//If 
			//If client's bullet hits enemy tank (server), stop the bullet from moving and change it's color to red
			
		}
	}
	
	//constructor
	public tankpanel(){
		super();
		this.setPreferredSize(new Dimension(1280, 720));
		thetimer.start();
	}
}
