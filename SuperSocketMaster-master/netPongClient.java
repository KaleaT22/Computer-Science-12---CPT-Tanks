//Name: Kalea
//Teacher: Mr. Cadawas
//Class: ICS4U1
//Game: Network Pong Client

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class netPongClient implements KeyListener, ActionListener{
	//properties
	//CLIENT
	//create client window
	JFrame clientframe = new JFrame("Client Pong");
	JPanel clientpanel = new JPanel();
	
	//client button
	JButton clientconnect = new JButton("Connect");
	
	//ip address
	JLabel theIP = new JLabel("IP Address");
	JTextField theipAdd = new JTextField("");
	
	//username
	JLabel theuserlabel = new JLabel("Username");
	JTextField theuser = new JTextField("");
	
	String strUser = "";

	//disconnect
	JButton thedisconnect = new JButton("Disconnect");
	
	SuperSocketMaster ssm;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		//if client connects
		if(evt.getSource() == clientconnect){
			clientconnect.setEnabled(false);
			theipAdd.setEnabled(false);
			
			ssm = new SuperSocketMaster(theipAdd.getText(), 8374, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				thedisconnect.setEnabled(true);
				theuser.setEnabled(true);
				
				ssm.sendText("connect, connect2, connect3");
			
				System.out.println("client");
					
			}else{
				clientconnect.setEnabled(true); 
				theipAdd.setEnabled(true);
			
			}
		
		//if server disconnects	
		}else if(evt.getSource() == thedisconnect){
			clientconnect.setEnabled(true);
			theipAdd.setEnabled(true);
			theuser.setEnabled(false);
			thedisconnect.setEnabled(false);
			
			ssm.sendText("disconnect, disconnect2, disconnect3");
		
			System.out.println("Disconnected");
			
			theuser.setText("");
			theipAdd.setText("");
			
			ssm.disconnect();
			
		//if username
		}else if(evt.getSource() == theuser){
			theuser.setEnabled(false);
			
			ssm.sendText("user, " + theuser.getText() + ", user");
			clientframe.requestFocus();

		}
	}
	
	public void keyReleased(KeyEvent evt){
		//PLAYER 2
		//move up
		if(evt.getKeyCode() == KeyEvent.VK_UP){
			System.out.println("PLAYER 2: up release");
			
			ssm.sendText("move, upRel, move");
			
		//move down
		}else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
			System.out.println("PLAYER 2: down release");
			
			ssm.sendText("move, downRel, move");
		}
	}
	
	public void keyPressed(KeyEvent evt){
		//PLAYER 2
		//move up
		if(evt.getKeyCode() == KeyEvent.VK_UP){
			System.out.println("PLAYER 2: up press");
			
			ssm.sendText("move, up, move");
			
		//move down
		}else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
			System.out.println("PLAYER 2: down press");
			
			ssm.sendText("move, down, move");
			
		}
	}
	
	public void keyTyped(KeyEvent evt){
		
	}
	
	//constructor
	public netPongClient(){
		//CLIENT
		//client window
		clientframe.addKeyListener(this);
		clientframe.requestFocus();
		
		//client connect button
		clientconnect.setSize(300, 25);
		clientconnect.setLocation(0, 10);
		clientconnect.addActionListener(this);
		clientpanel.add(clientconnect);
		
		//client ip address enter
		theIP.setSize(300, 25);
		theIP.setLocation(0, 45);
		theIP.setHorizontalAlignment(SwingConstants.CENTER);
		clientpanel.add(theIP);
		
		theipAdd.setSize(300, 25);
		theipAdd.setLocation(0, 70);
		theipAdd.addActionListener(this);
		clientpanel.add(theipAdd);
		
		//client username
		theuserlabel.setSize(300, 25);
		theuserlabel.setLocation(0, 95);
		theuserlabel.setHorizontalAlignment(SwingConstants.CENTER);
		clientpanel.add(theuserlabel);
		
		theuser.setSize(300, 25);
		theuser.setLocation(0, 120);
		theuser.setEnabled(false);
		theuser.addActionListener(this);
		clientpanel.add(theuser);
	
		//client disconnect button
		thedisconnect.setSize(300, 25);
		thedisconnect.setLocation(0, 200);
		thedisconnect.addActionListener(this);
		thedisconnect.setEnabled(false);
		clientpanel.add(thedisconnect);
		
		//client window
		clientpanel.setPreferredSize(new Dimension(300, 225));
		
		clientframe.setContentPane(clientpanel);
		clientpanel.setLayout(null);
		clientframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientframe.pack();
		clientframe.setVisible(true);
		
	}
	
	//main method
	public static void main(String[] args){
		new netPongClient();
	}
}
