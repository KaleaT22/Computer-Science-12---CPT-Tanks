import java.awt.*;
import javax.swing.*;
import java.io.*;

public class getBullet{
	//Properties
	double dblPower;
	double dblAngle;
	double dblPowerX;
	double dblPowerY;
	double dblAngleRadians;
	boolean boolLaunched;
	
	int intX;
	int intTankX;
	int intY = 580;
	int intDef = +4;
	int intDef2 = -4;
	double dblTime=0;
	double dblGravity=(-9.8);
	
	//Method
	///Override the method in JPanel
	///To draw this APanel differently
	///JPanel by default draws a grey background
	///APanel will draw whatever we want it to
	public void nextPos(){
		dblTime=dblTime+(10/60.0);
		
		dblAngleRadians=Math.toRadians(dblAngle);
		dblPowerX=((dblPower)*(Math.cos(dblAngleRadians)));
		dblPowerY=(1*((dblPower)*(Math.sin(dblAngleRadians))));
		
		//d = v*t
		intX=((int)(dblPowerX*dblTime))+intTankX;
		//intX=0;
		
		//d = Vi*t + 1/2*a*t^2
		intY=((((int)((dblPowerY*dblTime)+(0.5*dblGravity*(Math.pow(dblTime, 2)))))*-1)+580);
		//System.out.println(dblTime+"");
		//System.out.println(intY+"");
		
		if(boolLaunched==false){
			dblGravity=0;
			dblPowerY=0;
		}
		//If the ball wasn't launched, its in limbo (not moving) until it is launched
	}
	
	public void drawIt(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(intX, intY, 20, 20);
		this.nextPos();
	}
	
	//Constructor
	public getBullet(int intTankX, double dblPower, double dblAngle, boolean boolLaunched){
		super();
		this.intTankX=intTankX;
		this.dblPower=dblPower;
		this.dblAngle=dblAngle;
		this.boolLaunched=boolLaunched;
	}
	///Now the APanel and JPanel are exactly the same

}
