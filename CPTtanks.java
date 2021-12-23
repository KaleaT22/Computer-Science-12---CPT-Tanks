import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class CPTtanks implements ActionListener, KeyListener{
	//properties
	JFrame theframe = new JFrame("Tanks");
	tankpanel thepanel = new tankpanel();
	
	//server, client
	JButton theserver = new JButton("Server");
	JButton theclient = new JButton("Client");
	
	//IP address
	JLabel theIP = new JLabel("IP");
	JTextField theipAdd = new JTextField("");
	
	//disconnect
	JButton thedisconnect = new JButton("Disconnect");
	
	//play button to start game
	JButton playbut = new JButton("Play");
	
	//theme button to change theme/background
	JButton themebut = new JButton("Theme");
	
	//methods
	public void actionPerformed(ActionEvent evt){
		//if player starts game
		if(evt.getSource() == playbut){
			thepanel.strScreen = "Play";
			playbut.setVisible(false);
			themebut.setVisible(false);
			thedisconnect.setVisible(false);
			theserver.setVisible(false);
			theclient.setVisible(false);
			theIP.setVisible(false);
			theipAdd.setVisible(false);
			theframe.requestFocus();
		}
	}
	
	public void keyReleased(KeyEvent evt){
		if(evt.getKeyChar()=='a'){
			thepanel.intTank1Def = 0;
			
		}else if(evt.getKeyChar()=='d'){
			thepanel.intTank1Def = 0;
		}
	}
	
	public void keyPressed(KeyEvent evt){
		if(evt.getKeyChar() == 'a'){
			System.out.println("Type A");
			thepanel.intTank1Def = -5;
		}
		if(evt.getKeyChar() == 'd'){
			System.out.println("Type D");
			thepanel.intTank1Def = 5;
		}
	}
	
	public void keyTyped(KeyEvent evt){
		
	}
	
	//constructor
	public CPTtanks(){
		theframe.addKeyListener(this);
		theframe.requestFocus();
		
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
		
		//server button
		theserver.setSize(200, 25);
		theserver.setLocation(1080, 0);
		theserver.addActionListener(this);
		thepanel.add(theserver);
		
		//client button
		theclient.setSize(200, 25);
		theclient.setLocation(1080, 25);
		theclient.addActionListener(this);
		thepanel.add(theclient);
		
		//IP ADDRESS
		//ip address label
		theIP.setSize(20, 25);
		theIP.setHorizontalAlignment(SwingConstants.CENTER);
		theIP.setLocation(400, 550);
		thepanel.add(theIP);
		
		//ip address enter
		theipAdd.setSize(180, 25);
		theipAdd.setLocation(420, 550);
		theipAdd.addActionListener(this);
		thepanel.add(theipAdd);
		
		//disconnect button
		thedisconnect.setSize(600, 25);
		thedisconnect.setLocation(0, 575);
		thedisconnect.addActionListener(this);
		thedisconnect.setEnabled(false);
		thepanel.add(thedisconnect);
		
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
