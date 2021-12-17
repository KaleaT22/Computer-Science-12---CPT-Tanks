import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class CPTtanks implements ActionListener, KeyListener{
	//properties
	JFrame theframe = new JFrame("Tanks");
	tankpanel thepanel = new tankpanel();
	
	
	//methods
	public void actionPerformed(ActionEvent evt){
		
	}
	
	public void keyReleased(KeyEvent evt){
		
	}
	
	public void keyPressed(KeyEvent evt){
		
	}
	
	public void keyTyped(KeyEvent evt){
		
	}
	
	//constructor
	public CPTtanks(){
		thepanel.setPreferredSize(new Dimension(1280, 720));
		
		theframe.setContentPane(thepanel);
		thepanel.setLayout(null);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		
	}
	
	//main method
	public static void main(String[] args){
		new CPTtanks();
	}
}
