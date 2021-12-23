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
	
	int intX = 0;
	int intY = 400;
	int intY2 =0;
	int intDef = +4;
	int intDef2 = -4;
	double dblTime=0;
	double dblGravity=(-9.8);
	
	//Method
	///Override the method in JPanel
	///To draw this APanel differently
	///JPanel by default draws a grey background
	///APanel will draw whatever we want it to
	public void paintComponent(Graphics g){
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 1800, 1000);
		
		//Change this to change the visual speed of the projectile (not velocity)
		dblTime=dblTime+(3/60.0);
		
		dblAngleRadians=Math.toRadians(dblAngle);
		dblPowerX=((dblPower)*(Math.cos(dblAngleRadians)));
		dblPowerY=(1*((dblPower)*(Math.sin(dblAngleRadians))));
		
		//d = v*t
		intX=(int)(dblPowerX*dblTime);
		//intX=0;
		
		//d = Vi*t + 1/2*a*t^2
		intY=((((int)((dblPowerY*dblTime)+(0.5*dblGravity*(Math.pow(dblTime, 2)))))*-1)+400);
		
		g.setColor(Color.WHITE);
		g.drawString(dblPowerX+"TEST TEST",30,30);
		g.drawString(dblPowerY+"TEST TEST",30,60);
		System.out.println(dblTime+"");
		System.out.println(intY+"");
		
		g.setColor(Color.WHITE);
		g.fillOval(intX, intY, 40, 40);
	}
	
	//Constructor
	public getBullet(int intX, double dblPower, double dblAngle){
		super();
		this.intX=intX;
		this.dblPower=dblPower;
		this.dblAngle=dblAngle;
	}
	///Now the APanel and JPanel are exactly the same

}
