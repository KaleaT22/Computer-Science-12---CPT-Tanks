//Doodle Tanks (Main)
//By: Atilla Awista, Kalea Tse, & Noor Qureshi
//Date: January 27, 2022

import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/**
 * <h1>Doodle Tanks (CPTtanks Main)<br></h1>
 * By: Atilla Awista, Kalea Tse, Noor Qureshi<br>
 * Date: January 27, 2022<br>
 * <br> This is the main file used for Doodle Tanks
 */

public class CPTtanks implements ActionListener, KeyListener{
	//properties
	JFrame theframe = new JFrame("Tanks");
	tankpanel thepanel = new tankpanel(this);
	TankSound clicky = new TankSound();
	
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
	
	JButton generalbut = new JButton("General");
	JButton christmasbut = new JButton("Christmas");
	JButton halloweenbut = new JButton("Halloween");
	
	String strChosenTheme[] = new String[3];
	
	//help button
	JButton helpbut = new JButton("Help");
	
	//Return from end of game button
	static JButton returnbut2 = new JButton("Back to Menu");
	
	//button to go back to menu from Help Screen
	JButton returnbut = new JButton("Back to Menu");
	
	//Test shoot Button
	JButton TestBut = new JButton("Test Shoot");
	
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
	
	boolean blnGameStart = false;
	
	//SSM
	SuperSocketMaster ssm;
	
	String strLine;
	String[][] strMessage = new String[10][10];
	String strLineSplit[];
	
	boolean blnisServer;
	
	boolean blnserverTurn = true;
	boolean blnShotfreeze = false;
	
	/**
	 * Activates when an ActionEvent occurs.
	 * When activated, the code for that specific event runs.
	 * The function of events vary based on the ActionEvent source.
	 */
	
	public void actionPerformed(ActionEvent evt){
		//if player enters username
		if(evt.getSource() == theuserInput){
			strUser = theuserInput.getText();
			
			theserver.setEnabled(true);
			theclient.setEnabled(true);
			theipAdd.setEnabled(true);
			returnbut.setVisible(false);
			theuserInput.setEnabled(false);
		
		//server connects
		}else if(evt.getSource() == theserver){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			theserver.setEnabled(false); 
			theclient.setEnabled(false);
			theipAdd.setEnabled(false);
			
			ssm = new SuperSocketMaster(8374, this);
			
			blnConnected = true;
			
			boolean blnIsConnected = ssm.connect();
			
			//When you connect, enable buttons and and request focus
			if(blnIsConnected){
				playbut.setEnabled(true);
				themebut.setEnabled(true);
				helpbut.setEnabled(true);
				
				thedisconnect.setEnabled(true);
				theframe.requestFocus();
				
				blnisServer = true;
				thepanel.blnPanelServer = true;
				
						
			}else{
				theserver.setEnabled(true); 
				theclient.setEnabled(true); 
				theipAdd.setEnabled(true);
				
			}
			
		//if client connects
		}else if(evt.getSource() == theclient){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			theclient.setEnabled(false);
			theserver.setEnabled(false);
			theipAdd.setEnabled(false);
			
			ssm = new SuperSocketMaster(theipAdd.getText(), 8374, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){				
				playbut.setEnabled(false);
				themebut.setEnabled(false);
				
				helpbut.setEnabled(true);
				thedisconnect.setEnabled(true);
				thechat.setEnabled(false);
			
				//System.out.println("client");
				
				blnisServer = false;
				
				thechatarea.append("\nConnection Successful\n");
				ssm.sendText("client, connect, " + strUser);
				
				blnConnected = true;
				
			}else{
				theclient.setEnabled(true); 
				theipAdd.setEnabled(true);
				theserver.setEnabled(true);
			
			}
		
		//if player disconnects	
		}else if(evt.getSource() == thedisconnect){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
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
			if(ssm != null){	
				try{		
					for(intRow = 0; intRow < 1; intRow++){
						strLine = ssm.readText();
						
						//this actually does the splits string and puts in a 1D array
						strLineSplit = strLine.split(", ");
								
						for(intColumn = 0; intColumn < 4; intColumn++){
							strMessage[intRow][intColumn] = strLineSplit[intColumn];
						}
					}
					
				}catch(ArrayIndexOutOfBoundsException e){
					
				}
				
				//check if server
				if(strMessage[0][0].equals("server")){
					//if server starts game, set up variables, change panel/buttons
					if(strMessage[0][1].equals("playstart")){
						blnGameStart=true;
						thepanel.strScreen = "Play";
						
						thepanel.intHealth1 = 100;
						thepanel.intHealth2 = 100;
				
						thepanel.intTank1Ang = 45;
						thepanel.intTank2Ang = 45;
				
						thepanel.intTank1Pos = 170;
						thepanel.intTank2Pos = 810;
				
						thepanel.intTank1Pow = 80;
						thepanel.intTank2Pow = 80;
						
						playbut.setVisible(false);
						themebut.setVisible(false);
						thedisconnect.setVisible(false);
						theserver.setVisible(false);
						theclient.setVisible(false);
						theIP.setVisible(false);
						theipAdd.setVisible(false);
						theUser.setVisible(false);
						theuserInput.setVisible(false);
						helpbut.setVisible(false);
						returnbut.setVisible(false);
						TestBut.setVisible(false);
						
						thechatarealabel.setLocation(1080, 150);
						thechatscroll.setLocation(1080, 150);
						thechatlabel.setLocation(1080, 500);
						thechat.setLocation(1080, 525);
						chatBut.setLocation(1080, 550);
						
						chatBut.setVisible(true);
						chatBut.setEnabled(true);
						
						theframe.requestFocus();
					
					// stop server tank movement
					}else if(strMessage[0][1].equals("stop")){
						thepanel.intTank1Def = 0;
						
					}
					
					//Update server tank position
					if(strMessage[0][1].equals("move")){
						thepanel.intTank1Pos = Integer.parseInt(strMessage[0][2]);
						
						
					//Update server tank angle
					}else if(strMessage[0][1].equals("angle")){
						thepanel.intTank1Ang = Integer.parseInt(strMessage[0][2]);				
					
					//Launch cannonball from server tank
					}else if(strMessage[0][1].equals("shoot")){
						clicky.se.setFile("PEWPEWPEW2.wav");
						clicky.se.play();
						thepanel.bullet1 = new getBullet((thepanel.intTank1Pos + 40), thepanel.intTank1Pow+thepanel.intTank1PowBoost, thepanel.intTank1Ang, true, true);
						
						blnShotfreeze = true;
						
					//chat message	
					}else if(strMessage[0][1].equals("chat")){
						thechatarea.append("\n" + strMessage[0][2] + ": " + strMessage[0][3]);
						
						thechatarea.setCaretPosition(thechatarea.getDocument().getLength());
						
					//Power of bullet shot
					}else if(strMessage[0][1].equals("power")){
						thepanel.intTank1PowBoost = Integer.parseInt(strMessage[0][2]);
						System.out.println(thepanel.intTank1PowBoost);
					
					//Set client theme
					}else if(strMessage[0][1].equals("theme")){
						thepanel.strTheme = strMessage[0][2];
					
					//if game over, return to home screen
					}else if(strMessage[0][1].equals("returnMenu")){
						thepanel.strScreen = "Start";
						playbut.setVisible(true);
						themebut.setVisible(true);
						thedisconnect.setVisible(true);
						theserver.setVisible(true);
						theclient.setVisible(true);
						theIP.setVisible(true);
						theipAdd.setVisible(true);
						theUser.setVisible(true);
						theuserInput.setVisible(true);
						helpbut.setVisible(true);
						
						returnbut2.setVisible(false);
						chatBut.setVisible(false);
						
						thechatarealabel.setLocation(1080, 200);
						thechatscroll.setLocation(1080, 200);
						thechatlabel.setLocation(1080, 550);
						thechat.setLocation(1080, 575);
					}
					
				//check if client
				}else if(strMessage[0][0].equals("client")){
					//if connected
					if(strMessage[0][1].equals("connect")){
						thechatarea.append("\n" + strMessage[0][2] + " connected!\n");
						thechat.setEnabled(true);
					
					}
					
					//stop client tank movement
					if(strMessage[0][1].equals("stop")){
						thepanel.intTank2Def = 0;
					
					}
					
					//Update client tank position
					if(strMessage[0][1].equals("move")){
						thepanel.intTank2Pos = Integer.parseInt(strMessage[0][2]);	
						
					}
					
					//Update client tank angle
					if(strMessage[0][1].equals("angle")){
						thepanel.intTank2Ang = Integer.parseInt(strMessage[0][2]);
					
					}
					
					//Updates client tank power
					if(strMessage[0][1].equals("power")){
						thepanel.intTank2PowBoost = Integer.parseInt(strMessage[0][2]);
					}
					
					//Fires cannonball from client tank
					if(strMessage[0][1].equals("shoot")){
						clicky.se.setFile("PEWPEWPEWAT.wav");
						clicky.se.play();
						thepanel.bullet2 = new getBullet((thepanel.intTank2Pos + 40), thepanel.intTank2Pow+thepanel.intTank2PowBoost, (180-thepanel.intTank2Ang), true, false);
						
						blnShotfreeze = true;
					
					}
					
					//chat message	
					if(strMessage[0][1].equals("chat")){
						thechatarea.append("\n" + strMessage[0][2] + ": " + strMessage[0][3]);
						
						thechatarea.setCaretPosition(thechatarea.getDocument().getLength());
					}
				}
			}
		
		//if server starts game
		}else if(evt.getSource() == playbut){
			if(thepanel.strGameOver.equals("false")){
				clicky.se.setFile("CLICKNOISE2.wav");
				clicky.se.play();
				//Set all gameplay values to default
				thepanel.intHealth1 = 100;
				thepanel.intHealth2 = 100;
				
				thepanel.intTank1Ang = 45;
				thepanel.intTank2Ang = 45;
				
				thepanel.intTank1Pos = 170;
				thepanel.intTank2Pos = 810;
				
				thepanel.intTank1Pow = 80;
				thepanel.intTank2Pow = 80;
				
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
				returnbut.setVisible(false);
				TestBut.setVisible(false);
				
				thechatarealabel.setLocation(1080, 150);
				thechatscroll.setLocation(1080, 150);
				thechatlabel.setLocation(1080, 500);
				thechat.setLocation(1080, 525);
				chatBut.setLocation(1080, 550);
				
				thechat.setVisible(true);
				chatBut.setVisible(true);
				chatBut.setEnabled(true);
				helpbut.setVisible(false);
				blnGameStart=true;
				ssm.sendText("server, playstart");
				
				theframe.requestFocus();
				
			}
			
		//if player clicks theme button
		}else if(evt.getSource() == themebut){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			
			thepanel.strScreen = "Theme";
			
			playbut.setVisible(false);
			themebut.setVisible(false);
			thedisconnect.setVisible(false);
			theserver.setVisible(false);
			theclient.setVisible(false);
			theIP.setVisible(false);
			theipAdd.setVisible(false);
			theUser.setVisible(false);
			theuserInput.setVisible(false);
			helpbut.setVisible(false);
			
			returnbut.setVisible(true);
			returnbut.setLocation(100, 100);
			
			generalbut.setVisible(true);
			christmasbut.setVisible(true);
			halloweenbut.setVisible(true);
			
			try{
				//open file
				BufferedReader csvthemefile = new BufferedReader(new FileReader("themes.txt"));
				String strLine;
				
				//load data into array
				for(intRow = 0; intRow < 4; intRow++){
					strLine = csvthemefile.readLine();
					
					strChosenTheme[intRow] = strLine;
				
				}
				
				csvthemefile.close();
				
			}catch(FileNotFoundException e){
				System.out.println("File not found");
				
			}catch(IOException e){
				System.out.println("Error reading from file or closing file");
				
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("Array Out of Index");
				
			}catch(NullPointerException e){
				System.out.println("Null pointer");
			}
			
		//if player clicks general theme button
		}else if(evt.getSource() == generalbut){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			
			thepanel.strTheme = strChosenTheme[0];
			
			ssm.sendText("server, theme, " + strChosenTheme[0]);
			
		//if player clicks christmas theme button
		}else if(evt.getSource() == christmasbut){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			
			thepanel.strTheme = strChosenTheme[1];
			
			ssm.sendText("server, theme, " + strChosenTheme[1]);
			
		//if player clicks halloween theme button
		}else if(evt.getSource() == halloweenbut){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			
			thepanel.strTheme = strChosenTheme[2];
			
			ssm.sendText("server, theme, " + strChosenTheme[2]);
			
		//if player clicks help
		}else if(evt.getSource() == helpbut){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			
			thepanel.strScreen = "Help";
			playbut.setVisible(false);
			themebut.setVisible(false);
			thedisconnect.setVisible(false);
			theserver.setVisible(false);
			theclient.setVisible(false);
			theIP.setVisible(false);
			theipAdd.setVisible(false);
			theUser.setVisible(false);
			theuserInput.setVisible(false);
			helpbut.setVisible(false);
			
			returnbut.setVisible(true);
			TestBut.setVisible(true);
			
			returnbut.setLocation(50, 650);
		
		//if player wins, server brings to main menu
		}else if(evt.getSource() == returnbut2){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			
			thepanel.strScreen = "Start";
			playbut.setVisible(true);
			themebut.setVisible(true);
			thedisconnect.setVisible(true);
			theserver.setVisible(true);
			theclient.setVisible(true);
			theIP.setVisible(true);
			theipAdd.setVisible(true);
			theUser.setVisible(true);
			theuserInput.setVisible(true);
			helpbut.setVisible(true);
			
			returnbut2.setVisible(false);
			chatBut.setVisible(false);
			
			thechatarealabel.setLocation(1080, 200);
			thechatscroll.setLocation(1080, 200);
			thechatlabel.setLocation(1080, 550);
			thechat.setLocation(1080, 575);
			
			ssm.sendText("server, returnMenu");
			
		//if player clicks return to main menu
		}else if(evt.getSource() == returnbut){
			clicky.se.setFile("CLICKNOISE2.wav");
			clicky.se.play();
			
			thepanel.strScreen = "Start";
			playbut.setVisible(true);
			themebut.setVisible(true);
			thedisconnect.setVisible(true);
			theserver.setVisible(true);
			theclient.setVisible(true);
			theIP.setVisible(true);
			theipAdd.setVisible(true);
			theUser.setVisible(true);
			theuserInput.setVisible(true);
			helpbut.setVisible(true);
			
			returnbut.setVisible(false);
			TestBut.setVisible(false);
			generalbut.setVisible(false);
			christmasbut.setVisible(false);
			halloweenbut.setVisible(false);
			
		//When player clicks test shoot button in help menu
		}else if(evt.getSource() == TestBut){
			if(thepanel.blnHelpLaunch == false){
				clicky.se.setFile("PEWPEWPEW2.wav");
				clicky.se.play();
				thepanel.bulletHelp = new getBullet(370, 75, 20, true, true);
				thepanel.blnHelpLaunch = true;
			}
			
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
				
				thechatarea.setCaretPosition(thechatarea.getDocument().getLength());
			
			//send for client
			}else{
				thechatarea.append("\n" + strUser + ": " + strChat);
				
				ssm.sendText("client, chat, " + strUser + ", " + strChat);
				
				thechat.setText("");
				
				thechatarea.setCaretPosition(thechatarea.getDocument().getLength());
			}
		}
	}
	
	/**
	 * Upon key being released, get key character or code (getKeyChar() or getKeyCode()).
	 * Actions in the program are based on whichever key is being obtained.<br>
	 * Eg. if key is 'd', stop tank movement by changing tank deflection to 0.
	 */
	 
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
			if(evt.getKeyChar() == 'a'){
				thepanel.intTank2Def = 0;
				
				ssm.sendText("client, stop");
				ssm.sendText("client, move, " + thepanel.intTank2Pos);
			
			//stop right
			}else if(evt.getKeyChar() == 'd'){
				thepanel.intTank2Def = 0;
				
				ssm.sendText("client, stop");
				ssm.sendText("client, move, " + thepanel.intTank2Pos);
			}
		}
	}
	
	/**
	 * Upon key being pressed, get key character or code (getKeyChar() or getKeyCode()).
	 * Actions in the program are based off of the key that is obtained.<br>
	 * Eg. if key is 'd', move tank to right by changing tank deflection.
	 */
	 
	public void keyPressed(KeyEvent evt){
		//server movements
		if(blnisServer == true && blnShotfreeze == false && blnGameStart == true){
			//left
			if(evt.getKeyChar() == 'a' && thepanel.intTank1Pos > 0){
				System.out.println("Server: Left");
				thepanel.intTank1Def = -5;
				
				//clicky.se.setFile("TankMovement2LOUDER.wav");
				//clicky.se.play();
				
				//ssm.sendText("server, move, " + thepanel.intTank1Pos);
				
				//call function to send ssm to move tank
				moveTank("server, move, " + thepanel.intTank1Pos);
				
			//right
			}else if(evt.getKeyChar() == 'd' && (thepanel.intTank1Pos+80)<1080){
				System.out.println("Server: Right");
				thepanel.intTank1Def = 5;
				
				//clicky.se.setFile("TankMovement2LOUDER.wav");
				//clicky.se.play();
				
				//ssm.sendText("server, move, " + thepanel.intTank1Pos);
				
				//call function to send ssm to move tank
				moveTank("server, move, " + thepanel.intTank1Pos);
				
			}
			
			if(blnserverTurn == true && blnShotfreeze == false){
				//elevate the gun
				if(evt.getKeyChar() == 'w' && thepanel.intTank1Ang<90){
					System.out.println("Server: gun elevating");
					thepanel.intTank1Ang = thepanel.intTank1Ang + 5;
					
					System.out.println(thepanel.intTank1Ang);
					
					ssm.sendText("server, angle, " + thepanel.intTank1Ang);

				//depress the gun
				}else if(evt.getKeyChar() == 's' && thepanel.intTank1Ang>0){
					System.out.println("Server: gun depressing");
					thepanel.intTank1Ang = thepanel.intTank1Ang - 5;
					System.out.println(thepanel.intTank1Ang);
					ssm.sendText("server, angle, " + thepanel.intTank1Ang);
				}
				
				if(evt.getKeyChar() == 'e' && thepanel.intTank1PowBoost<20){
					thepanel.intTank1PowBoost=thepanel.intTank1PowBoost+2;
					ssm.sendText("server, power, " + thepanel.intTank1PowBoost);
				//Increase power
				
				}else if(evt.getKeyChar() == 'q' && thepanel.intTank1PowBoost>0){
					thepanel.intTank1PowBoost=thepanel.intTank1PowBoost-2;
					ssm.sendText("server, power, " + thepanel.intTank1PowBoost);
				}
				//Decrease power
				
				//shoot
				if(evt.getKeyChar() == ' '){
					clicky.se.setFile("PEWPEWPEW2.wav");
					clicky.se.play();
					thepanel.bullet1 = new getBullet((thepanel.intTank1Pos + 40), thepanel.intTank1Pow+thepanel.intTank1PowBoost, thepanel.intTank1Ang, true, true);
					System.out.println("FIRED!");
					
					ssm.sendText("server, shoot");
					thepanel.intTank1Def = 0;
					blnShotfreeze = true;
				}
				//Launches cannonball from server tank
			}
		//client movements
		}else if(blnShotfreeze==false && blnGameStart == true){
			//left
			if(evt.getKeyChar() == 'a' && thepanel.intTank2Pos>0){
				System.out.println("Client: Left");
				thepanel.intTank2Def = -5;
				
				//clicky.se.setFile("TankMovement2LOUDER.wav");
				//clicky.se.play();
				
				//ssm.sendText("client, move, " + thepanel.intTank2Pos);
				
				//call function to send ssm to move tank
				moveTank("client, move, " + thepanel.intTank2Pos);
			
			//right
			}else if(evt.getKeyChar() == 'd' && (thepanel.intTank2Pos+80)<1080){
				System.out.println("Client: Right");
				System.out.println(thepanel.intTank2Pos);
				thepanel.intTank2Def = 5;
				
				//clicky.se.setFile("TankMovement2LOUDER.wav");
				//clicky.se.play();
				
				//ssm.sendText("client, move, " + thepanel.intTank2Pos);
				
				//call function to send ssm to move tank
				moveTank("client, move, " + thepanel.intTank2Pos);
				
			}
			
			if(blnserverTurn == false && blnShotfreeze ==false){
				//elevate the gun
				if(evt.getKeyChar() == 'w' && thepanel.intTank2Ang<90){
					System.out.println("Server: gun elevating");
					thepanel.intTank2Ang = thepanel.intTank2Ang + 5;
					
					System.out.println(thepanel.intTank2Ang);
					
					ssm.sendText("client, angle, " + thepanel.intTank2Ang);
					
				//depress the gun
				}else if(evt.getKeyChar() == 's' && thepanel.intTank2Ang>0){
					System.out.println("Server: gun depressing");
					thepanel.intTank2Ang = thepanel.intTank2Ang - 5;
					
					System.out.println(thepanel.intTank2Ang);
					
					ssm.sendText("client, angle, " + thepanel.intTank2Ang);
					
				}
				
				if(evt.getKeyChar() == 'e' && thepanel.intTank2PowBoost<20){
					thepanel.intTank2PowBoost=thepanel.intTank2PowBoost+2;
					ssm.sendText("client, power, " + thepanel.intTank2PowBoost);
				//Increase power
				
				}else if(evt.getKeyChar() == 'q' && thepanel.intTank2PowBoost>0){
					thepanel.intTank2PowBoost=thepanel.intTank2PowBoost-2;
					ssm.sendText("client, power, " + thepanel.intTank2PowBoost);
				}
				//Decrease power
				
				if(evt.getKeyChar() == ' '){
					clicky.se.setFile("PEWPEWPEWAT.wav");
					clicky.se.play();
					thepanel.bullet2 = new getBullet((thepanel.intTank2Pos + 40), thepanel.intTank2Pow+thepanel.intTank2PowBoost, (180-thepanel.intTank2Ang), true, false);
					ssm.sendText("client, shoot");
					System.out.println("FIRED!");
					thepanel.intTank2Def = 0;
					blnShotfreeze = true;
				}
				//Launches cannonball from client tank
			}
		}
	}
	
	/** 
	 * Activates when a key is typed (waits for both keypressed and keyreleased combined).
	 * This method has no function in our program
	 */
	 
	public void keyTyped(KeyEvent evt){
		
	}
	
	/**
	 * When function is activated (tank moves), get string value.
	 * Set strSSM to the string value and send SSM  to other player to display on their screen.
	 */
	
	//ssm movements method
	public void moveTank(String strMove){
		String strSSM = strMove;
		
		ssm.sendText(strMove);
	}
	
	/**
	 * Creates tank boundaries (for movement), stopping tanks from being able to leave the screen or pass the center obstacle.
	 * Sends out updated tank position to other player using SSM.
	 * Requests focus onto the game frame.
	 * Haltstank controls while in main menu and resumes them once in game.
	 */
	
	public void movingTank(){
		if(blnConnected==true){
			if((thepanel.intTank1Pos+100)>450){
				thepanel.intTank1Pos=350;
			}
			if(thepanel.intTank1Pos<0){
				thepanel.intTank1Pos=0;
			}
			if((thepanel.intTank2Pos+100)>1080){
				thepanel.intTank2Pos=980;
			}
			if(thepanel.intTank2Pos<650){
				thepanel.intTank2Pos=650;
			}
			//This makes sure the tanks can't move out of bounds by replacing them back into bounds the moment they leave
			
			if(blnisServer == true){
				ssm.sendText("server, move, " + thepanel.intTank1Pos);
			}else if(blnisServer == false){
				ssm.sendText("client, move, " + thepanel.intTank2Pos);
				
			}
			//This constantly updates the other user with their positions allowing for smooth 60fps movement
			
			if(blnChat==false){
				theframe.requestFocus();
			}
			//This constantly requests focus while chat mode is off, this prevents errors that disallow the ability to control tanks
			
			if(thepanel.intHealth1<=0){
				blnGameStart=false;
			}else if(thepanel.intHealth1<=0){
				blnGameStart=false;
			}
			//When the game is over (one tanks health = 0), disallow any tank movement and firing, this is to prevent tanks from moving and firing while in the main menu
		}
	}
	//Constantly update the tanks location, also constantly make sure that the tank is teleported back onto screen the moment it goes out of screen
	
	/**
	 * Plays explosion sound effect, allows tanks to move, and toggles blnserverTurn in order to switch to next tank's turn.
	 */
	 
	public void allowShooting(){
		clicky.se.setFile("EXPLOSION.wav");
		clicky.se.play();
		blnShotfreeze = false;
		if(blnserverTurn == false){
			blnserverTurn = true;
		}else{
			blnserverTurn = false;
		}
	}
	//Once the cannonball lands, switch to the next players turn and allow movement
	
	/**
	 * The constructor that is used to build or "construct" the program.
	 * It initializes the GUI / javaxswing elements of the code.<br>
	 * Ex. The buttons are initialized here. Their sizes and location are set.
	 * ActionListeners are added to each one. And they are added to the panel.
	 * Their settings, setEnabled and setVisible, vary based on when they are of use.
	 */
	
	//constructor
	public CPTtanks(){
		theframe.addKeyListener(this);
		theframe.requestFocus();
		
		//play button
		playbut.setSize(200, 25);
		playbut.setLocation(450, 400);
		playbut.addActionListener(this);
		playbut.setEnabled(false);
		thepanel.add(playbut);
		
		//THEME BUTTONS
		themebut.setSize(200, 25);
		themebut.setLocation(450, 430);
		themebut.addActionListener(this);
		themebut.setEnabled(false);
		thepanel.add(themebut);
		
		generalbut.setSize(200, 25);
		generalbut.setLocation(150, 500);
		generalbut.addActionListener(this);
		generalbut.setVisible(false);
		thepanel.add(generalbut);
		
		christmasbut.setSize(200, 25);
		christmasbut.setLocation(475, 500);
		christmasbut.addActionListener(this);
		christmasbut.setVisible(false);
		thepanel.add(christmasbut);
		
		halloweenbut.setSize(200, 25);
		halloweenbut.setLocation(780, 500);
		halloweenbut.addActionListener(this);
		halloweenbut.setVisible(false);
		thepanel.add(halloweenbut);
		
		//HELP BUTTONS
		helpbut.setSize(200, 25);
		helpbut.setLocation(450, 460);
		helpbut.addActionListener(this);
		helpbut.setEnabled(false);
		thepanel.add(helpbut);
		
		//Return button
		returnbut.setSize(200, 50);
		returnbut.setLocation(50, 625);
		returnbut.addActionListener(this);
		returnbut.setVisible(false);
		thepanel.add(returnbut);
		
		//Return from end of game button
		returnbut2.setSize(200, 50);
		returnbut2.setLocation(500, 300);
		returnbut2.addActionListener(this);
		returnbut2.setVisible(false);
		thepanel.add(returnbut2);
		
		//Test arena access button
		TestBut.setSize(200, 50);
		TestBut.setLocation(250, 650);
		TestBut.addActionListener(this);
		TestBut.setVisible(false);
		thepanel.add(TestBut);
		 
		//SSM BUTTONS
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
		thechatarea.setEditable(false);
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
	
	/**
	 * This method us used to initialize and run the program.
	 */
	 
	//main method
	public static void main(String[] args){
		new CPTtanks();
	}
}
