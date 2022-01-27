//Doodle Tanks (Bullet Object)
//By: Atilla Awista, Kalea Tse, & Noor Qureshi
//Date: January 27, 2022

import java.awt.*;
import javax.swing.*;
import java.io.*;

/**
 * <h1>Doodle Tanks (getBullet)<br></h1>
 * By: Atilla Awista, Kalea Tse, Noor Qureshi<br>
 * Date: January 27, 2022<br>
 * <br> This is the main file for the bullet animation in the game
 */

public class getBullet{
	//Properties
	double dblPower;
	double dblAngle;
	double dblPowerX;
	double dblPowerY;
	double dblAngleRadians;
	boolean boolLaunched;
	boolean boolHitbox=false;
	boolean boolGetCoords=true;
	boolean boolServerTank;
	int intBulletOffset=0;
	
	int intX = -20;
	int intTankX;
	int intY = 580;
	int intCurrentX = -20;
	int intCurrentY = -20;
	int intDef = +4;
	int intDef2 = -4;
	double dblTime=0;
	double dblGravity=(-9.8);
	
	String strColor = "WHITE";
	
	//Method
	/**Overrides the method in JPanel. To draw this APanel differently,
	 * JPanel by default draws a grey background. APanel will draw whatever we want it to/
	 * This method is also used to determine the next coordinate where the bullet will be drawn to.
	 */
	 
	public void nextPos(){
		dblTime=dblTime+(10/60.0);
		
		dblAngleRadians=Math.toRadians(dblAngle);
		dblPowerX=((dblPower)*(Math.cos(dblAngleRadians)));
		dblPowerY=(1*((dblPower)*(Math.sin(dblAngleRadians))));
		//Uses kinematics to calculate cannonball velocity in the X and Y
		
		if(boolServerTank==true){
			intBulletOffset=40;
		}else{
			intBulletOffset=-40;
		}
		//This makes it so the cannonball fires out of the barrel side of the tank depending if its a client or server tank
		
		//d = v*t
		intX=((int)(dblPowerX*dblTime))+(intTankX+intBulletOffset);
		//Uses kinematics to calculate cannonball displacement in the X (displacement= velocity*time)
		
		//d = Vi*t + 1/2*a*t^2
		intY=((((int)((dblPowerY*dblTime)+(0.5*dblGravity*(Math.pow(dblTime, 2)))))*-1)+560);
		//Uses kinematic equation to calculate cannonball displacement in the Y
		
		if(boolLaunched==false){
			if(boolHitbox==true && boolGetCoords==true){
				//intCurrentX=intX;
				//intCurrentY=intY;
				intCurrentX=-20;
				intCurrentY=-20;
				boolGetCoords=false;
			}
			//If the ball hits something (hitbox), then gather the coordnates where it hits and change boolGetCoords to false, this ensures that
			//the coordnates are only gathered ONE time, otherwise it would just keep updating the coords and the ball would keep moving
			intX=intCurrentX;
			intY=intCurrentY;
			dblGravity=0;
			dblPowerY=-20;
			dblPowerX=-20;
		}
		//If the ball isn't considered "launched", stop the ball from moving
	}
	
	/**
	 * Draws the bullet that is being shot and draws it to the screen.
	 */
	 
	public void drawIt(Graphics g){
		g.setColor(Color.DARK_GRAY);
		if(dblPowerX ==0){
			g.setColor(Color.RED);
		}
		//Once the cannon stops moving, turn it red as a visual indicator (for testing purposes)
		g.fillOval(intX, intY, 20, 20);
		this.nextPos();
	}
	//This function visually draws out the cannonball
	
	//Constructor
	/**
	 * Constructs a bullet and draws it to the screen when called.
	 * The arc that the bullet is drawn in varies depending on the starting x-coordinate
	 * of the tank shooting, the power the gun is set to, and what angle to shoot at.
	 */
	 
	public getBullet(int intTankX, double dblPower, double dblAngle, boolean boolLaunched, boolean boolServerTank){
		super();
		this.intTankX=intTankX;
		this.dblPower=dblPower;
		this.dblAngle=dblAngle;
		this.boolLaunched=boolLaunched;
		this.boolServerTank=boolServerTank;
	}
	///Now the APanel and JPanel are exactly the same

}
