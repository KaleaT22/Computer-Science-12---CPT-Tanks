import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class tankpanel extends JPanel implements ActionListener{
	//properties
	//timer
	Timer thetimer = new Timer(1000/60, this);
	
	//screen
	String strScreen = "start";
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource()==thetimer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		//home screen
		if(strScreen.equals("start")){
			System.out.println("STARTED");
			
			//temporary, will use images later
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, 1280, 720);
			
		//play screen
		}else if(strScreen.equals("play")){
			System.out.println("PLAY SCREEN OPENED");
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, 1280, 720);
			
		}
	}
	
	//constructor
	public tankpanel(){
		super();
		this.setPreferredSize(new Dimension(1280, 720));
		thetimer.start();
	}
}
