import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class CPTtanks implements ActionListener, KeyListener{
	//properties
	JFrame theframe = new JFrame("Tanks");
	tankpanel thepanel = new tankpanel();
	
	int intRow = 0;
	int intColumn = 0;
	
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
	
	//chat area
	JLabel thechatarealabel = new JLabel("CHAT");
	JTextArea thechatarea = new JTextArea("Come and chat here!");
	JScrollPane thechatscroll = new JScrollPane();
	
	//chat
	JLabel thechatlabel = new JLabel("Chat");
	JTextField thechat = new JTextField("");
	
	String strChat = "";
	
	//SSM
	SuperSocketMaster ssm;
	
	String strLine;
	String[][] strMessage = new String[10][10];
	String strLineSplit[];
	
	boolean blnisServer;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		//server connects
		if(evt.getSource() == theserver){
			theserver.setEnabled(false); 
			theclient.setEnabled(false);
			theipAdd.setEnabled(false);
			
			ssm = new SuperSocketMaster(8374, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				playbut.setEnabled(true);
				themebut.setEnabled(true);
				thedisconnect.setEnabled(true);
				theframe.requestFocus();
				
				System.out.println("server");
				
				blnisServer = true;
				
						
			}else{
				theserver.setEnabled(true); 
				theclient.setEnabled(true); 
				theipAdd.setEnabled(true);
				
			}
			
		//if client connects
		}else if(evt.getSource() == theclient){
			theclient.setEnabled(false);
			theserver.setEnabled(false);
			theipAdd.setEnabled(false);
			
			ssm = new SuperSocketMaster(theipAdd.getText(), 8374, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				playbut.setEnabled(false);
				themebut.setEnabled(false);
				thedisconnect.setEnabled(true);
			
				System.out.println("client");
				
				blnisServer = false;
				ssm.sendText("client, connect");
					
			}else{
				theclient.setEnabled(true); 
				theipAdd.setEnabled(true);
				theserver.setEnabled(true);
			
			}
		
		//if server disconnects	
		}else if(evt.getSource() == thedisconnect){
			theclient.setEnabled(true);
			theserver.setEnabled(true);
			theipAdd.setEnabled(true);
			thedisconnect.setEnabled(false);
		
			System.out.println("Disconnected");
	
			theipAdd.setText("");
			
			ssm.sendText("client, disconnect");
			
			ssm.disconnect();
		
		//servers connected
		}else if(evt.getSource() == ssm){
			System.out.println("SSM TEST: " + ssm.readText());
			if(ssm != null){	
				try{		
					for(intRow = 0; intRow < 1; intRow++){
						strLine = ssm.readText();
						
						//this actually does the splits string and puts in a 1D array
						strLineSplit = strLine.split(", ");
								
						for(intColumn = 0; intColumn < 3; intColumn++){
							strMessage[intRow][intColumn] = strLineSplit[intColumn];
							System.out.println("strMessage[" + intRow + "][" + intColumn + "]");
							System.out.println("Array: " + strMessage[intRow][intColumn]);
							
						}
						
						System.out.println("TEST 1: " + strMessage[0][0]);
					}
					
					//check if server
					if(strMessage[0][0].equals("server")){
						//if server starts game
						if(strMessage[0][1].equals("playstart")){
							thepanel.strScreen = "Play";
							playbut.setVisible(false);
							themebut.setVisible(false);
							thedisconnect.setVisible(false);
							theserver.setVisible(false);
							theclient.setVisible(false);
							theIP.setVisible(false);
							theipAdd.setVisible(false);
							
							theframe.requestFocus();
						
						//if stop movement
						}else if(strMessage[0][1].equals("stop")){
							thepanel.intTank1Def = 0;
						
						//if move	
						}else if(strMessage[0][1].equals("move")){
							//move left
							if(strMessage[0][2].equals("left")){
								thepanel.intTank1Def = -5;
							
							//move right
							}else if(strMessage[0][2].equals("right")){
								thepanel.intTank1Def = 5;
								
							}
						}
						
					//check if client
					}else if(strMessage[0][0].equals("client")){
						//if connected
						if(strMessage[0][1].equals("connect")){
							thechatarea.append("Player 2 connected");
							
						//if stop movement
						}else if(strMessage[0][1].equals("stop")){
							thepanel.intTank1Def = 0;
						
						//if move	
						}else if(strMessage[0][1].equals("move")){
							//move left
							if(strMessage[0][2].equals("left")){
								thepanel.intTank2Def = -5;
							
							//move right
							}else if(strMessage[0][2].equals("right")){
								thepanel.intTank2Def = 5;
								
							}
						}
					}
					
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("No string split");
						
				}
			}
		
		//if player starts game
		}else if(evt.getSource() == playbut){
			thepanel.strScreen = "Play";
			playbut.setVisible(false);
			themebut.setVisible(false);
			thedisconnect.setVisible(false);
			theserver.setVisible(false);
			theclient.setVisible(false);
			theIP.setVisible(false);
			theipAdd.setVisible(false);
			
			ssm.sendText("server, playstart");
			
			theframe.requestFocus();
			
		}
	}
	
	public void keyReleased(KeyEvent evt){
		//stop server movements
		if(blnisServer == true){
			//stop left
			if(evt.getKeyChar() == 'a'){
				thepanel.intTank1Def = 0;
				
				ssm.sendText("server, stop");
				
			//stop right
			}else if(evt.getKeyChar()== 'd'){
				thepanel.intTank1Def = 0;
				
				ssm.sendText("server, stop");
			}
			
		//stop client movements
		}else{
			//stop left
			if(evt.getKeyCode() == KeyEvent.VK_LEFT){
				thepanel.intTank2Def = 0;
				
				ssm.sendText("client, stop");
			
			//stop right
			}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
				thepanel.intTank2Def = 0;
				
				ssm.sendText("client, stop");
			}
		}
	}
	
	public void keyPressed(KeyEvent evt){
		//server movements
		if(blnisServer == true){
			//left
			if(evt.getKeyChar() == 'a'){
				System.out.println("Server: Left");
				thepanel.intTank1Def = -5;
				
				ssm.sendText("server, move, left");
			
			//right
			}else if(evt.getKeyChar() == 'd'){
				System.out.println("Server: Right");
				thepanel.intTank1Def = 5;
				
				ssm.sendText("server, move, right");
			}
			
			//shoot
			if(evt.getKeyChar() == ' '){
				thepanel.bullet1 = new getBullet((thepanel.intTank1Pos + 40), thepanel.intTank1Pow, thepanel.intTank1Ang, true);
				System.out.println("FIRED!");
				
				ssm.sendText("server, shoot");
				
			}
		
		//client movements
		}else{
			//left
			if(evt.getKeyCode() == KeyEvent.VK_LEFT){
				System.out.println("Client: Left");
				thepanel.intTank2Def = -5;
				ssm.sendText("client, move, left");
			
			//right
			}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
				System.out.println("Client: Right");
				thepanel.intTank2Def = 5;
				ssm.sendText("client, move, right");
				
			}
			
			//shoot
			if(evt.getKeyChar() == ' '){
				thepanel.bullet2 = new getBullet((thepanel.intTank1Pos - 40), thepanel.intTank2Pow, thepanel.intTank2Ang, true);
				ssm.sendText("client, shoot");
				System.out.println("FIRED!");
				
			}
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
		theIP.setSize(200, 25);
		theIP.setHorizontalAlignment(SwingConstants.CENTER);
		theIP.setLocation(1080, 50);
		thepanel.add(theIP);
		
		//ip address enter
		theipAdd.setSize(200, 25);
		theipAdd.setLocation(1080, 75);
		theipAdd.addActionListener(this);
		thepanel.add(theipAdd);
		
		//disconnect button
		thedisconnect.setSize(200, 25);
		thedisconnect.setLocation(1080, 100);
		thedisconnect.addActionListener(this);
		thedisconnect.setEnabled(false);
		thepanel.add(thedisconnect);
		
		//chat area
		thechatarealabel.setSize(200, 25);
		thechatarealabel.setLocation(1080, 125);
		thechatarealabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		thechatscroll = new JScrollPane(thechatarea);
		thechatscroll.setSize(200,450);
		thechatscroll.setLocation(1080, 150);
		thechatarea.setEnabled(false);
		thepanel.add(thechatscroll);  
		
		//chat
		thechatlabel.setSize(200, 25);
		thechatlabel.setLocation(1080, 600);
		thechatlabel.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(thechatlabel);
		
		thechat.setSize(200, 25);
		thechat.setLocation(1080, 625);
		thechat.addActionListener(this);
		thechat.setEnabled(false);
		thepanel.add(thechat);
		
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
