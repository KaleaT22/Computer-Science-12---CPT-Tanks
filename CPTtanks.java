import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class CPTtanks implements ActionListener, KeyListener{
	//properties
	JFrame theframe = new JFrame("Tanks");
	tankpanel thepanel = new tankpanel();
	
	//play button to start game
	JButton playbut = new JButton("Play");
	
	//theme button to change theme/background
	JButton themebut = new JButton("Theme");
	
	//methods
	public void actionPerformed(ActionEvent evt){
		//if player starts game
		if(evt.getSource() == playbut){
			thepanel.strScreen = "play";
			playbut.setEnabled(false);
			themebut.setEnabled(false);
			
		}
	}
	
	public void keyReleased(KeyEvent evt){
		
	}
	
	public void keyPressed(KeyEvent evt){
		//PLAYER 1
		//move left
		if(evt.getKeyChar() == 'a'){
			
		}
	}
	
	public void keyTyped(KeyEvent evt){
		
	}
	
	//constructor
	public CPTtanks(){
		//thepanel.setPreferredSize(new Dimension(1280, 720));
		
		//play button
		playbut.setSize(300, 25);
		playbut.setLocation(100, 5);
		playbut.addActionListener(this);
		thepanel.add(playbut);
		
		//theme button
		themebut.setSize(300, 25);
		themebut.setLocation(100, 35);
		themebut.addActionListener(this);
		thepanel.add(themebut);
		
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
