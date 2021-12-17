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
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			this.repaint();
		}
	}
	
	public void paintComponent(Graphics g){
		
	}
	
	//constructor
	public tankpanel(){
		super();
		this.setPreferredSize(new Dimension(1280, 720));
	}
}
