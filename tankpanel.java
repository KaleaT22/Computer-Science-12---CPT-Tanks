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
	Timer thetimer = new Timer(1000 / 60, this);
	
	//screen
	String strScreen = "start";
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		//home screen
		if(strScreen.equals("start")){
			System.out.println(strScreen);
			
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 1280, 720);
			
		//play screen
		}
		
		if(strScreen.equals("play")){
			System.out.println("play: " + strScreen);
			
		}
	}
	
	//constructor
	public tankpanel(){
		super();
		this.setPreferredSize(new Dimension(1280, 720));
	}
}
