import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class CPTtanks implements ActionListener, KeyListener{
	//properties
	JFrame theframe = new JFrame("Tanks");
	tankpanel thepanel = new tankpanel(this);
	
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
	
	//username
	JLabel theUser = new JLabel("Username");
	JTextField theuserInput = new JTextField("");
	
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
	
	JButton chatBut = new JButton("Chat");
	
	String strChat = "";
	boolean blnChat = false;
	boolean blnConnected = false;
	
	String strUser = "";
	
	//SSM
	SuperSocketMaster ssm;
	
	String strLine;
	String[][] strMessage = new String[10][10];
	String strLineSplit[];
	
	boolean blnisServer;
	
	boolean blnserverTurn = true;
	boolean blnShotfreeze=false;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		//if player enters username
		if(evt.getSource() == theuserInput){
			strUser = theuserInput.getText();
			
			theserver.setEnabled(true);
			theclient.setEnabled(true);
			theipAdd.setEnabled(true);
			
			theuserInput.setEnabled(false);
		
		//server connects
		}else if(evt.getSource() == theserver){
			theserver.setEnabled(false); 
			theclient.setEnabled(false);
			theipAdd.setEnabled(false);
			
			ssm = new SuperSocketMaster(8374, this);
			
			blnConnected = true;
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				playbut.setEnabled(true);
				themebut.setEnabled(true);
				thedisconnect.setEnabled(true);
				theframe.requestFocus();
				
				//System.out.println("server");
				
				blnisServer = true;
				thepanel.blnPanelServer = true;
				
						
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
				thechat.setEnabled(true);
			
				//System.out.println("client");
				
				blnisServer = false;
				
				thechatarea.append("\nConnection Successful\n");
				ssm.sendText("client, connect, " + strUser);
				blnConnected=true;
			}else{
				theclient.setEnabled(true); 
				theipAdd.setEnabled(true);
				theserver.setEnabled(true);
			
			}
		
		//if player disconnects	
		}else if(evt.getSource() == thedisconnect){
			thedisconnect.setEnabled(false);
			theuserInput.setEnabled(true);
			thechat.setEnabled(false);
			
			theuserInput.setText("");
		
			System.out.println("Disconnected");
	
			theipAdd.setText("");
			
			if(blnisServer == true){
				ssm.sendText("server, disconnect");
				
			}else{
				ssm.sendText("client, disconnect");
			}
			ssm.disconnect();
		
		//servers connected
		}else if(evt.getSource() == ssm){
			//System.out.println("SSM TEST: " + ssm.readText());
			if(ssm != null){	
				try{		
					for(intRow = 0; intRow < 1; intRow++){
						strLine = ssm.readText();
						
						//this actually does the splits string and puts in a 1D array
						strLineSplit = strLine.split(", ");
								
						for(intColumn = 0; intColumn < 4; intColumn++){
							strMessage[intRow][intColumn] = strLineSplit[intColumn];
							//System.out.println("strMessage[" + intRow + "][" + intColumn + "]");
							//System.out.println("Array: " + strMessage[intRow][intColumn]);
							
						}
						
						//System.out.println("TEST 1: " + strMessage[0][0]);
						
					}
					
				}catch(ArrayIndexOutOfBoundsException e){
					//System.out.println("No string split");
					
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
						theUser.setVisible(false);
						theuserInput.setVisible(false);
						
						chatBut.setVisible(true);
						chatBut.setEnabled(true);
						
						theframe.requestFocus();
					
					//if stop movement
					}else if(strMessage[0][1].equals("stop")){
						thepanel.intTank1Def = 0;
						
					}
					
					//if move
					if(strMessage[0][1].equals("move")){
						thepanel.intTank1Pos = Integer.parseInt(strMessage[0][2]);
						
					//if depress / elevating cannon
					}else if(strMessage[0][1].equals("angle")){
						thepanel.intTank1Ang = Integer.parseInt(strMessage[0][2]);
						
					//if fires
					}else if(strMessage[0][1].equals("shoot")){
						thepanel.bullet1 = new getBullet((thepanel.intTank1Pos + 40), thepanel.intTank1Pow, thepanel.intTank1Ang, true);
						
						blnShotfreeze = true;
						
					//chat message	
					}else if(strMessage[0][1].equals("chat")){
						thechatarea.append("\n" + strMessage[0][2] + ": " + strMessage[0][3]);
						
					}
					
				//check if client
				}else if(strMessage[0][0].equals("client")){
					//if connected
					if(strMessage[0][1].equals("connect")){
						thechatarea.append("\n" + strMessage[0][2] + " connected!\n");
						thechat.setEnabled(true);
					
					}
					
					//if stop movement
					if(strMessage[0][1].equals("stop")){
						thepanel.intTank2Def = 0;
					
					}
					
					//if move	
					if(strMessage[0][1].equals("move")){
						thepanel.intTank2Pos = Integer.parseInt(strMessage[0][2]);	
						
					}
					
					//if depress / elevating cannon
					if(strMessage[0][1].equals("angle")){
						thepanel.intTank2Ang = Integer.parseInt(strMessage[0][2]);
					
					}
					
					//if fires
					if(strMessage[0][1].equals("shoot")){
						thepanel.bullet2 = new getBullet((thepanel.intTank2Pos + 40), thepanel.intTank2Pow, (180-thepanel.intTank2Ang), true);
						
						blnShotfreeze = true;
					
					}
					
					//chat message	
					if(strMessage[0][1].equals("chat")){
						thechatarea.append("\n" + strMessage[0][2] + ": " + strMessage[0][3]);
						
					}
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
			theUser.setVisible(false);
			theuserInput.setVisible(false);
			
			thechat.setVisible(true);
			chatBut.setVisible(true);
			chatBut.setEnabled(true);
			
			ssm.sendText("server, playstart");
			
			theframe.requestFocus();
		
		//if player clicks chat button
		}else if(evt.getSource() == chatBut){
			//if chatfield disabled, enable
			if(blnChat == false){
				thechat.setEnabled(true);
				
				blnChat = true;
			
			//if chatfield enabled, disable
			}else{
				thechat.setEnabled(false);
				
				blnChat = false;
				
				theframe.requestFocus();
				
			}
		
		//if player sends chat message	
		}else if(evt.getSource() == thechat){
			strChat = thechat.getText();
			
			//send for server
			if(blnisServer == true){
				thechatarea.append("\n" + strUser + ": " + strChat);
				
				ssm.sendText("server, chat, " + strUser + ", " + strChat);
				
				thechat.setText("");
			
			//send for client
			}else{
				thechatarea.append("\n" + strUser + ": " + strChat);
				
				ssm.sendText("client, chat, " + strUser + ", " + strChat);
				
				thechat.setText("");
			}
		}
	}
	
	public void keyReleased(KeyEvent evt){
		//stop server movements
		if(blnisServer == true){
			//stop left
			if(evt.getKeyChar() == 'a'){
				thepanel.intTank1Def = 0;
				
				ssm.sendText("server, stop");
				ssm.sendText("server, move, " + thepanel.intTank1Pos);
				
			//stop right
			}else if(evt.getKeyChar()== 'd'){
				thepanel.intTank1Def = 0;
				
				ssm.sendText("server, stop");
				ssm.sendText("server, move, " + thepanel.intTank1Pos);
			}
			
		//stop client movements
		}else{
			//stop left
			if(evt.getKeyCode() == KeyEvent.VK_LEFT){
				thepanel.intTank2Def = 0;
				
				ssm.sendText("client, stop");
				ssm.sendText("client, move, " + thepanel.intTank2Pos);
			
			//stop right
			}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
				thepanel.intTank2Def = 0;
				
				ssm.sendText("client, stop");
				ssm.sendText("client, move, " + thepanel.intTank2Pos);
			}
		}
	}
	
	public void keyPressed(KeyEvent evt){
		//server movements
		if(blnisServer == true && blnShotfreeze == false){
			//left
			if(evt.getKeyChar() == 'a'){
				System.out.println("Server: Left");
				thepanel.intTank1Def = -5;
				
				//ssm.sendText("server, move, " + thepanel.intTank1Pos);
				
				//call function to send ssm to move tank
				moveTank("server, move, " + thepanel.intTank1Pos);
				
			//right
			}else if(evt.getKeyChar() == 'd'){
				System.out.println("Server: Right");
				thepanel.intTank1Def = 5;
				
				//ssm.sendText("server, move, " + thepanel.intTank1Pos);
				
				//call function to send ssm to move tank
				moveTank("server, move, " + thepanel.intTank1Pos);
				
			}
			
			if(blnserverTurn == true && blnShotfreeze == false){
				//elevate the gun
				if(evt.getKeyChar() == 'w'){
					System.out.println("Server: gun elevating");
					thepanel.intTank1Ang = thepanel.intTank1Ang + 5;
					
					System.out.println(thepanel.intTank1Ang);
					
					ssm.sendText("server, angle, " + thepanel.intTank1Ang);

				//depress the gun
				}else if(evt.getKeyChar() == 's'){
					System.out.println("Server: gun depressing");
					thepanel.intTank1Ang = thepanel.intTank1Ang - 5;
					
					System.out.println(thepanel.intTank1Ang);
					
					ssm.sendText("server, angle, " + thepanel.intTank1Ang);
				}
				
				//shoot
				if(evt.getKeyChar() == ' '){
					thepanel.bullet1 = new getBullet((thepanel.intTank1Pos + 40), thepanel.intTank1Pow, thepanel.intTank1Ang, true);
					System.out.println("FIRED!");
					
					ssm.sendText("server, shoot");
					thepanel.intTank1Def = 0;
					blnShotfreeze = true;
				}
			}
		
		//client movements
		}else if(blnShotfreeze==false){
			//left
			if(evt.getKeyCode() == KeyEvent.VK_LEFT){
				System.out.println("Client: Left");
				thepanel.intTank2Def = -5;
				
				//ssm.sendText("client, move, " + thepanel.intTank2Pos);
				
				//call function to send ssm to move tank
				moveTank("client, move, " + thepanel.intTank2Pos);
			
			//right
			}else if(evt.getKeyCode() == KeyEvent.VK_RIGHT){
				System.out.println("Client: Right");
				thepanel.intTank2Def = 5;
				
				//ssm.sendText("client, move, " + thepanel.intTank2Pos);
				
				//call function to send ssm to move tank
				moveTank("client, move, " + thepanel.intTank2Pos);
				
			}
			
			if(blnserverTurn == false && blnShotfreeze ==false){
				//elevate the gun
				if(evt.getKeyCode() == KeyEvent.VK_UP){
					System.out.println("Server: gun elevating");
					thepanel.intTank2Ang = thepanel.intTank2Ang + 5;
					
					System.out.println(thepanel.intTank2Ang);
					
					ssm.sendText("client, angle, " + thepanel.intTank2Ang);
					
				//depress the gun
				}else if(evt.getKeyCode() == KeyEvent.VK_DOWN){
					System.out.println("Server: gun depressing");
					thepanel.intTank2Ang = thepanel.intTank2Ang - 5;
					
					System.out.println(thepanel.intTank2Ang);
					
					ssm.sendText("client, angle, " + thepanel.intTank2Ang);
					
				}
				
				//shoot
				if(evt.getKeyChar() == ' '){
					thepanel.bullet2 = new getBullet((thepanel.intTank2Pos + 40), thepanel.intTank2Pow, (180-thepanel.intTank2Ang), true);
					ssm.sendText("client, shoot");
					System.out.println("FIRED!");
					thepanel.intTank2Def = 0;
					blnShotfreeze = true;
				}
			}
		}
	}
	
	public void keyTyped(KeyEvent evt){
		
	}
	
	//ssm movements method
	public void moveTank(String strMove){
		String strSSM = strMove;
		
		ssm.sendText(strMove);
	}
	
	public void movingTank(){
		if(blnConnected==true){
			if(blnisServer == true){
				ssm.sendText("server, move, " + thepanel.intTank1Pos);
				
			}else if(blnisServer == false){
				ssm.sendText("client, move, " + thepanel.intTank2Pos);
				
			}
		}
	}
	
	public void allowShooting(){
		blnShotfreeze = false;
		if(blnserverTurn == false){
			blnserverTurn = true;
		}else{
			blnserverTurn = false;
		}
	}
	//Once the cannonball lands, switch to the next players turn and allow movement
	
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
		theserver.setEnabled(false);
		thepanel.add(theserver);
		
		//client button
		theclient.setSize(200, 25);
		theclient.setLocation(1080, 25);
		theclient.addActionListener(this);
		theclient.setEnabled(false);
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
		theipAdd.setEnabled(false);
		thepanel.add(theipAdd);
		
		//disconnect button
		thedisconnect.setSize(200, 25);
		thedisconnect.setLocation(1080, 100);
		thedisconnect.addActionListener(this);
		thedisconnect.setEnabled(false);
		thepanel.add(thedisconnect);
		
		//USERNAME
		//user label
		theUser.setSize(200, 25);
		theUser.setHorizontalAlignment(SwingConstants.CENTER);
		theUser.setLocation(1080, 130);
		thepanel.add(theUser);
		
		//username input
		theuserInput.setSize(200, 25);
		theuserInput.setLocation(1080, 155);
		theuserInput.addActionListener(this);
		thepanel.add(theuserInput);
		
		//CHAT
		//chat area
		thechatarealabel.setSize(200, 25);
		thechatarealabel.setLocation(1080, 200);
		thechatarealabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//chat scroll
		thechatscroll = new JScrollPane(thechatarea);
		thechatscroll.setSize(200, 350);
		thechatscroll.setLocation(1080, 200);
		thechatarea.setEnabled(false);
		thepanel.add(thechatscroll);  
	
		//chat label
		thechatlabel.setSize(200, 25);
		thechatlabel.setLocation(1080, 550);
		thechatlabel.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(thechatlabel);
		
		//chat textfield
		thechat.setSize(200, 25);
		thechat.setLocation(1080, 575);
		thechat.addActionListener(this);
		thechat.setEnabled(false);
		thepanel.add(thechat);
		
		//chat button
		chatBut.setSize(200, 25);
		chatBut.setLocation(1080, 600);
		chatBut.addActionListener(this);
		chatBut.setVisible(false);
		thepanel.add(chatBut);
		
		theframe.setContentPane(thepanel);
		thepanel.setLayout(null);
		theframe.setResizable(false);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		
	}
	
	//main method
	public static void main(String[] args){
		new CPTtanks();
	}
}
