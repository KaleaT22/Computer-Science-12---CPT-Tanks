//Name: Kalea
//Teacher: Mr. Cadawas
//Class: ICS4U1
//Game: Network Guess The Number

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import java.net.*;

public class guessNumNetwork implements ActionListener{
	//properties
	JFrame theframe = new JFrame("Guess the Number");
	JPanel thepanel = new JPanel();
	
	BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
	
	SuperSocketMaster ssm;

	int intRow = 0;
	int intColumn = 0;
	
	String strLine;
	String[][] strMessage = new String[10][10];
	String strLineSplit[];
	
	//text area for game
	JTextArea thetext = new JTextArea("Please connect and enter your username.");
	JScrollPane thescroll = new JScrollPane();
	
	//chat area
	JLabel thechatarealabel = new JLabel("CHAT");
	JTextArea thechatarea = new JTextArea("Come and chat here!");
	JScrollPane thechatscroll = new JScrollPane();
	
	//number guess
	JLabel thenumlabel = new JLabel("Guess");
	JTextField thenumguess = new JTextField("");
	
	//username
	JLabel theuserlabel = new JLabel("Username");
	JTextField theuser = new JTextField("");
	
	String strUser = "";
	
	//chat
	JLabel thechatlabel = new JLabel("Chat");
	JTextField thechat = new JTextField("");
	
	String strChat = "";
	
	//start round
	JButton startBut = new JButton("Start Round");
	
	//server, client
	JButton theserver = new JButton("Server");
	JButton theclient = new JButton("Client");
	
	//IP address
	JLabel theIP = new JLabel("IP");
	JTextField theipAdd = new JTextField("");
	
	//disconnect
	JButton thedisconnect = new JButton("Disconnect");
	
	//game variables
	int intLowNum = 0;
	int intHighNum = 0;
	
	String strPlaytype = "";
	String[] strPlayUser = new String[3];
	String[] strPlayGuess = new String[3];
	int intCount = 0;
	
	String strWin = "";
	
	int intGuessNum = 0;
	int intNumofGuess = 0;
	
	int intPlayGuess = 0;
	int intPlayBefore = 0;
	
	int intPlayerCount = 0;
	
	int intWin;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		//if player selects client
		if(evt.getSource() == theclient){
			theserver.setEnabled(false); 
			theclient.setEnabled(false);
			startBut.setEnabled(false);
			theipAdd.setEnabled(false);
			
			//set up ssm
			ssm = new SuperSocketMaster(theipAdd.getText(), 2712, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				thedisconnect.setEnabled(true);
				theuser.setEnabled(true);
	
				ssm.sendText("clientconnect");
				
				System.out.println("client");
					
			}else{
				theserver.setEnabled(true); 
				theclient.setEnabled(true); 
				theipAdd.setText("");
				theipAdd.setEnabled(true);
			
			}
      			
		//if player selects server
		}else if(evt.getSource() == theserver){
			theserver.setEnabled(false); 
			theclient.setEnabled(false);
			startBut.setEnabled(false);
			theipAdd.setEnabled(false);
			
			//set up ssm
			ssm = new SuperSocketMaster(2712, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				thedisconnect.setEnabled(true);
				theuser.setEnabled(true);
				
				strPlaytype = "server";
				
				intPlayerCount = intPlayerCount + 1;
					
			}else{
				theserver.setEnabled(true); 
				theclient.setEnabled(true);
				theipAdd.setEnabled(true);

			}
		
		//if player disconnects	
		}else if(evt.getSource() == thedisconnect){
			theserver.setEnabled(true);
			theclient.setEnabled(true);
			startBut.setEnabled(false);
			theipAdd.setEnabled(true);
			theuser.setEnabled(false);
			thechat.setEnabled(false);
			thenumguess.setEnabled(false);
			thedisconnect.setEnabled(false);
			
			theuser.setText("");
			thenumguess.setText("");
			
			System.out.println("Disconnected");
			
			intPlayerCount = intPlayerCount - 1;
			
			ssm.sendText("disconnect, " + strUser);
			thetext.append("\nYou have disconnected.");
			
			ssm.disconnect();
			
		//servers connected
		}else if(evt.getSource() == ssm){
			if(ssm != null){	
				try{		
					for(intRow = 0; intRow < 1; intRow++){
						strLine = ssm.readText();
						
						//this actually does the splitting and puts in a 1D array
						strLineSplit = strLine.split(", ");
								
						for(intColumn = 0; intColumn < 4; intColumn++){
							strMessage[intRow][intColumn] = strLineSplit[intColumn];
							System.out.println(strMessage[intRow][intColumn]);
							
						}
					}
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("No string split");
				}
				
				//checks if it's a username
				if(strMessage[0][0].equals("user")){
					thetext.append("\n" + strMessage[0][1] + " has joined the server");
				
				//checks if it's a message for the chat
				}else if(strMessage[0][0].equals("chat")){
					thechatarea.append("\n" + strMessage[0][1]);
				
				//checks if it's a player that has disconnected
				}else if(strMessage[0][0].equals("disconnect")){
					thetext.append("\n" + strMessage[0][1] + " has disconnected");
					
				//checks if the game is starting
				}else if(strMessage[0][0].equals("start")){
					thetext.append("\n" + strMessage[0][1]);
					
				//checks the number ranges
				}else if(strMessage[0][0].equals("range")){
					thetext.append("\nGuess a number between " + strMessage[0][1] + " - " + strMessage[0][2]);
					
				}else if(strMessage[0][0].equals("clientconnect")){
					intPlayerCount = intPlayerCount + 1;
					
				//checks for guess and actual number (sent together)
				}else if(strMessage[0][0].equals("guess")){
					System.out.println("strMessage[0][1] / user: " + strMessage[0][1]);
					System.out.println("strMessage[0][2] / guess: " + strMessage[0][2]);
					
					System.out.println("intCount 3: " + intCount);
					
					thetext.append("\n" + strMessage[0][1] + " guessed: " + strMessage[0][2]);
					
						//checks if it is a server, to load values into array
						if(strPlaytype.equals("server")){
							//load username
							strPlayUser[intCount] = strMessage[0][1];
							
							//load guess
							strPlayGuess[intCount] = strMessage[0][2];
							
							intNumofGuess = intNumofGuess + 1;
						}
						
						System.out.println("Next strPlayUser: " + strPlayUser[intCount]);
						System.out.println("Next strPlayGuess: " + strPlayGuess[intCount]);
						
						if(strPlaytype.equals("server")){
							intCount = intCount + 1;
							
							System.out.println("intCount 4: " + intCount);
							
						}
				
				}else if(strMessage[0][0].equals("win")){
					thetext.append("\n" + strMessage[0][1] + " won with a guess of: " + strMessage[0][2]);
					thetext.append("\nThe number was: " + strMessage[0][3]);
					
					thenumguess.setText("");
				
				}
				
				//checks for winner
				if(strPlaytype.equals("server") && intNumofGuess == intPlayerCount && intNumofGuess > 1){
					strWin = "win";
					
					int[] intDiff = new int[intPlayerCount];
					
					//load values into the array
					for(intRow = 0; intRow < intNumofGuess; intRow++){
						//if guess is larger than final number
						if(Integer.parseInt(strPlayGuess[intRow]) > intGuessNum){
							//load difference
							intDiff[intRow] = Integer.parseInt(strPlayGuess[intRow]) - intGuessNum;
							
						//if guess is less than final number
						}else if(Integer.parseInt(strPlayGuess[intRow]) < intGuessNum){
							//load difference
							intDiff[intRow] = intGuessNum - Integer.parseInt(strPlayGuess[intRow]);
							
						//if guess is equal to final number
						}else{
							//load difference
							intDiff[intRow] = 0;
				
						}
					}
					
					//bubble sort differences
					int intLeft;
					int intRight;
					int intTemp;
					
					String strTemp;
					
					for(intRow = 0; intRow < intPlayerCount; intRow++){
						for(intColumn = 1; intColumn < (intPlayerCount); intColumn++){
							intLeft = intDiff[intColumn];
							intRight = intDiff[intColumn - 1];
							
							System.out.println("Bubbling");
							
							//if the number before intRight is greater, bubble sort
							if(intRight > intLeft){
								//names
								strTemp = strPlayUser[intColumn];
								strPlayUser[intColumn] = strPlayUser[intColumn - 1];
								strPlayUser[intColumn - 1] = strTemp;
								System.out.println("Moving users");
								
								//difference
								intTemp = intDiff[intColumn];
								intDiff[intColumn] = intDiff[intColumn - 1];
								intDiff[intColumn - 1] = intTemp;
								System.out.println("Moving differences");
								
								//original guess
								strTemp = strPlayGuess[intColumn];
								strPlayGuess[intColumn] = strPlayGuess[intColumn - 1];
								strPlayGuess[intColumn - 1] = strTemp;
								System.out.println("Moving guess");
							}
						}
					}
					
					//if win
					if(strPlaytype.equals("server") && strWin.equals("win")){
						thetext.append("\n" + strPlayUser[0] + " wins with a guess of: " + strPlayGuess[0]);
						thetext.append("\nThe number was: " + intGuessNum);
						thetext.append("\nTo play again, select the start round button");
						
						ssm.sendText("win, " + strPlayUser[0] + ", " + strPlayGuess[0] + ", " + intGuessNum);
						
						System.out.println("win user: " + strPlayUser[0]);
						System.out.println("win user: " + strPlayGuess[0]);
						
						startBut.setEnabled(true);
						thenumguess.setText("");
						strWin = "no win";
					}
					
				}
				
			}else{
				System.out.println("null :(");
				
			}
			
		//enter user
		}else if(evt.getSource() == theuser){
			strUser = theuser.getText();
			thetext.append("\nWelcome " + strUser);
	
			ssm.sendText("user, " + strUser);
			thechat.setEnabled(true);
			theuser.setEnabled(false);
			thenumguess.setEnabled(true);
			
			//if server, turn start button on
			if(strPlaytype.equals("server")){
				startBut.setEnabled(true);
			}
	
		//user wants to chat	
		}else if(evt.getSource() == thechat){
			strChat = thechat.getText();
			
			thechat.setText("");
			
			ssm.sendText("chat, " + strUser + ": " + strChat);
			thechatarea.append("\n" + "You: " + strChat);
			
			//thechat.setText("");
		
		//server starts game
		}else if(evt.getSource() == startBut){
			startBut.setEnabled(false);
			thenumguess.setText("");
			intNumofGuess = 0;
			intCount = 0;
			
			System.out.println("Number of players: " + intPlayerCount);
			
			thetext.append("\nThe game has begun.");
			ssm.sendText("start, The game has begun.");
			
			//generate lower number in range (1 - 200)
			intLowNum = (int)(Math.random()* (200 - 1) + 1);
			System.out.println("Low Number: " + intLowNum);
			
			//generate higher number in range (400 - 1000)
			intHighNum = (int)(Math.random()* (1000 - 400) + 400);
			
			System.out.println("High Number: " + intHighNum);
			
			//generate number to guess (LowNum - HighNum)
			intGuessNum = (int)(Math.random()* (intHighNum - intLowNum) + intLowNum);
			System.out.println("Guessing Number: " + intGuessNum);
			
			ssm.sendText("check, " + intGuessNum);
			
			ssm.sendText("range, " + intLowNum + ", " + intHighNum); 
			thetext.append("\nGuess a number between " + intLowNum + " - " + intHighNum);
			
			//thenumguess.setEnabled(true);
			
		//players guess
		}else if(evt.getSource() == thenumguess){
			intPlayBefore = Integer.parseInt(thenumguess.getText());
			
			thetext.append("\n" + strUser + " guessed: " + intPlayBefore);
			ssm.sendText("guess, " + strUser + ", " + intPlayBefore);
				
			System.out.println("Number of guesses: " + intNumofGuess);
			
			System.out.println("intCount 1: " + intCount);
			
			if(strPlaytype.equals("server")){
				//puts user and guess into array
				strPlayUser[intCount] = theuser.getText();
				strPlayGuess[intCount] = Integer.toString(intPlayBefore);
				
				System.out.println("strPlayUser: " + strPlayUser[intCount]);
				System.out.println("strPlayGuess: " + strPlayGuess[intCount]);
				
				intNumofGuess = intNumofGuess + 1;
			}
			
			if(strPlaytype.equals("server")){
				intCount = intCount + 1;
				
				System.out.println("intCount 2: " + intCount);
			}
		
			System.out.println("Number of people that have guessed: " + intNumofGuess);
			
			
			//checks for winner
			if(strPlaytype.equals("server") && intNumofGuess == intPlayerCount && intNumofGuess > 1){
				strWin = "win";
				System.out.println("ALTERNATE WINNER CHECK");
				
				int[] intDiff = new int[intPlayerCount];
				
				//load values into the array
				for(intRow = 0; intRow < intNumofGuess; intRow++){
					//if guess is larger than final number
					if(Integer.parseInt(strPlayGuess[intRow]) > intGuessNum){
						//load difference
						intDiff[intRow] = Integer.parseInt(strPlayGuess[intRow]) - intGuessNum;
						System.out.println("intDiff[intRow] > intGuessNum: " + intDiff[intRow]);
						
					//if guess is less than final number
					}else if(Integer.parseInt(strPlayGuess[intRow]) < intGuessNum){
						//load difference
						intDiff[intRow] = intGuessNum - Integer.parseInt(strPlayGuess[intRow]);
						System.out.println("intDiff[intRow] < intGuessNum: " + intDiff[intRow]);
					
					//if guess is equal to final number
					}else{
						//load difference
						intDiff[intRow] = 0;
			
					}
				}
				
				//bubble sort differences
				int intLeft;
				int intRight;
				int intTemp;
				
				String strTemp;
				
				for(intRow = 0; intRow < intPlayerCount; intRow++){
					for(intColumn = 1; intColumn < (intPlayerCount) ; intColumn++){
						System.out.println("hi");
						intLeft = intDiff[intColumn];
						intRight = intDiff[intColumn - 1];
						
						System.out.println("Bubbling ALT");
						
						//if the number before intRight is greater, bubble sort
						if(intRight < intLeft){
							//names
							strTemp = strPlayUser[intColumn];
							strPlayUser[intColumn] = strPlayUser[intColumn - 1];
							strPlayUser[intColumn - 1] = strTemp;
							System.out.println("Moving users ALT");
							
							//difference
							intTemp = intDiff[intColumn];
							intDiff[intColumn] = intDiff[intColumn - 1];
							intDiff[intColumn - 1] = intTemp;
							System.out.println("Moving differences ALT");
							
							//original guess
							strTemp = strPlayGuess[intColumn];
							System.out.println("strTemp og guess: " + strTemp);
							strPlayGuess[intColumn] = strPlayGuess[intColumn - 1];
							System.out.println("StrPlayGuess[intColumn] og guess: " + strPlayGuess[intColumn]);
							strPlayGuess[intColumn - 1] = strTemp;
							System.out.println("StrPlayGuess[intColumn - 1] og guess: " + strPlayGuess[intColumn - 1]);
							System.out.println("Moving guess ALT");
						}
					}
				}
				
				//if win
				if(strPlaytype.equals("server") && strWin.equals("win")){
					thetext.append("\n" + strPlayUser[1] + " wins with a guess of: " + strPlayGuess[1]);
					thetext.append("\nThe number was: " + intGuessNum);
					thetext.append("\nTo play again, select the start round button");
					
					ssm.sendText("win, " + strPlayUser[1] + ", " + strPlayGuess[1] + ", " + intGuessNum);
					
					System.out.println("win ALT user: " + strPlayUser[1]);
					System.out.println("win ALT user: " + strPlayGuess[1]);
					
					startBut.setEnabled(true);
					thenumguess.setText("");
					strWin = "no win";
				}
				
			}

		}
	}

	//constructor
	public guessNumNetwork(){
		//text info area
		thescroll = new JScrollPane(thetext);
		thescroll.setSize(400, 475);
		thescroll.setLocation(0, 0);
		thetext.setEnabled(false);
		thepanel.add(thescroll);  
		
		//chat area
		thechatarealabel.setSize(200, 25);
		thechatarealabel.setLocation(400, 0);
		thechatarealabel.setHorizontalAlignment(SwingConstants.CENTER);
		thechatscroll = new JScrollPane(thechatarea);
		thechatscroll.setSize(200,450);
		thechatscroll.setLocation(400, 25);
		thechatarea.setEnabled(false);
		thepanel.add(thechatscroll);  
		
		//user
		theuserlabel.setSize(200, 25);
		theuserlabel.setLocation(0, 475);
		theuserlabel.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(theuserlabel);
		
		theuser.setSize(200, 25);
		theuser.setLocation(0, 500);
		theuser.setEnabled(false);
		theuser.addActionListener(this);
		
		thepanel.add(theuser);
		
		//number guess
		thenumlabel.setSize(200, 25);
		thenumlabel.setLocation(200, 475);
		thenumlabel.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(thenumlabel);
		
		thenumguess.setSize(200, 25);
		thenumguess.setLocation(200, 500);
		thenumguess.addActionListener(this);
		thenumguess.setEnabled(false);
		thepanel.add(thenumguess);
	
		//chat
		thechatlabel.setSize(200, 25);
		thechatlabel.setLocation(400, 475);
		thechatlabel.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(thechatlabel);
		
		thechat.setSize(200, 25);
		thechat.setLocation(400, 500);
		thechat.addActionListener(this);
		thechat.setEnabled(false);
		thepanel.add(thechat);
		
		//start button
		startBut.setSize(600, 25);
		startBut.setLocation(0, 525);
		startBut.addActionListener(this);
		startBut.setEnabled(false);
		thepanel.add(startBut);
		
		//server button
		theserver.setSize(200, 25);
		theserver.setLocation(0, 550);
		theserver.addActionListener(this);
		thepanel.add(theserver);
		
		//client button
		theclient.setSize(200, 25);
		theclient.setLocation(200,550);
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
		
		thepanel.setPreferredSize(new Dimension(600, 600));
		
		theframe.setContentPane(thepanel);
		thepanel.setLayout(null);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		
	}
	
	//main method
	public static void main(String[] args){
		new guessNumNetwork();
		
	}
}
