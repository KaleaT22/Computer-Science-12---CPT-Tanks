//Name: Kalea
//Teacher: Mr. Cadawas
//Class: ICS4U1
//Game: Network Pong

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class netPongKalea implements KeyListener, ActionListener{
	//properties
	//SERVER
	//create main window
	JFrame theframe = new JFrame("Pong");
	PPanel thepanel = new PPanel();
	
	//server button
	JButton serverconnect = new JButton("Server");
	
	//username
	JLabel theuserlabel = new JLabel("Username");
	JTextField theuser = new JTextField("");
	
	String strUser = "";
	
	//connect label
	JLabel connectlabel = new JLabel("Not Connected");
	
	//player 1 label
	JLabel play1label = new JLabel("Player 1: ");
	String strPlay1User = "";
	
	//player 2 label
	JLabel play2label = new JLabel("Player 2: ");
	String strPlay2User = "";
	
	int intPlayStart = 0;
	int intDirec = 0;
	
	int intRow = 0;
	int intColumn = 0;
	
	SuperSocketMaster ssm;
	
	String strPlayer2Move;
	
	//ssm variables
	String strLine;
	String[][] strMessage = new String[10][10];
	String strLineSplit[];
	
	//methods
	public void actionPerformed(ActionEvent evt){
		//if server connects	
		if(evt.getSource() == serverconnect){
			serverconnect.setEnabled(false); 
			
			ssm = new SuperSocketMaster(8374, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				theuser.setEnabled(true);
				theframe.requestFocus();
				
				System.out.println("server");
				
						
			}else{
				serverconnect.setEnabled(true); 
				
			}
		
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
					//strMessage[0][0] = "connect";
					
					System.out.println("TEST 2: " + strMessage[0][0]);
					
					//if connected
					if(strMessage[0][0].equals("connect")){
						System.out.println("Connect works");
						connectlabel.setText("");
						connectlabel.setText("Connected");
					
					//if disconnects
					}else if(strMessage[0][0].equals("disconnect")){
						connectlabel.setText("Not Connected");
						play2label.setText("Player 2: ");
					
					//play 2 username
					}else if(strMessage[0][0].equals("user")){
						strPlay2User = strMessage[0][1];
						
						play2label.setText("Player 2: " + strMessage[0][1]);
					
					//if move
					}else if(strMessage[0][0].equals("move")){
						System.out.println("SSM Recieved: " + strPlayer2Move);
						
						//stop moving ball 
						if(strMessage[0][1].equals("upRel")){
							thepanel.intP2DefY = 0;
							System.out.println("upRel");
							
						//stop moving ball
						}else if(strMessage[0][1].equals("downRel")){
							thepanel.intP2DefY = 0;
							System.out.println("downRel");
						
						//move ball up
						}else if(strMessage[0][1].equals("up")){
							thepanel.intP2DefY = -8;
							
							System.out.println("up");
						
						//move ball down
						}else if(strMessage[0][1].equals("down")){
							thepanel.intP2DefY = 8;
							
							System.out.println("down");
							
						}
					}
					
				}catch(ArrayIndexOutOfBoundsException e){
					System.out.println("No string split");
						
				}
			}
		//server username
		}else if(evt.getSource() == theuser){
			strPlay1User = theuser.getText();
			
			play1label.setText("Player 1: " + strPlay1User);
			theuser.setEnabled(false);
		}
	}
	
	public void keyReleased(KeyEvent evt){
		//START
		if(evt.getKeyChar() == KeyEvent.VK_SPACE){
			System.out.println("Space Bar Start Release");
				
			//determine which player starts
			intPlayStart = (int)(Math.random()* (101 - 1) + 1);
			System.out.println(intPlayStart);
			
			//determine which way the ball moves
			intDirec = (int)(Math.random()* (101 - 1) + 1);
			System.out.println(intDirec);
			
			int intEven;
			int intDirecChoose;
			
			intEven = intPlayStart % 2;
			intDirecChoose = intDirec % 2;
			
			//if even, p1 starts
			if(intEven == 0){
				thepanel.intBallDefX = -3;
				
				//if even, ball goes up
				if(intDirecChoose == 0){
					thepanel.intBallDefY = -3;
					
				//if odd, ball goes down
				}else{
					thepanel.intBallDefY = 3;
					
				}
			
			//if odd, p2 starts
			}else{
				thepanel.intBallDefX = 3;
				
				//if even, ball goes up
				if(intDirecChoose == 0){
					thepanel.intBallDefY = -3;
					
				//if odd, ball goes down
				}else{
					thepanel.intBallDefY = 3;
					
				}
			}
		}
		
		//RESTART
		if(thepanel.intP1Score == 5 || thepanel.intP2Score == 5){
			try{
				//write guess to file
				PrintWriter winWrite = new PrintWriter(new FileWriter("winners.txt", true));
				
				//if player 1 win, write play 1 user to file
				if(thepanel.intP1Score == 5){
					winWrite.println(strPlay1User);
				
				//if player 2 win, write play 2 user to file
				}else{
					winWrite.println(strPlay2User);
					
				}
				
				winWrite.close();
				
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println("No bubble sort");
				
			}catch(FileNotFoundException e){
				System.out.println("File not found.");
				
			}catch(IOException e){
				System.out.println("Error reading from file or closing file.");
			
			}catch(NumberFormatException e){
				System.out.println("");
			}
			
			if(evt.getKeyChar() == 'r'){
				//reset background colour
				thepanel.intR = 0;
				thepanel.intG = 0;
				thepanel.intB = 0;
				
				//reset ball position
				thepanel.intBallX = 400;
				thepanel.intBallY = 300;
				thepanel.intBallDefX = 0;
				thepanel.intBallDefY = 0;
				
				//reset player scores
				thepanel.intP1Score = 0;
				thepanel.intP2Score = 0;
				
				//reset paddle positions
				//player 1
				thepanel.intP1X = 20;
				thepanel.intP1Y = 20;
				
				//player 2
				thepanel.intP2X = 780;
				thepanel.intP2Y = 530;
			
			//for bonus screen
			}else if(evt.getKeyChar() == 'c'){
				//if p1 won, set p2 != 0 to trigger win screen
				if(thepanel.intP1Score == 5){
					thepanel.intP2Score = -1;
					
				//if p2 won, set p1 != 0 to trigger win screen
				}else if(thepanel.intP2Score == 5){
					thepanel.intP1Score = -1;
					
				}
			}
		}
		
		//PLAYER 1
		//move up
		if(evt.getKeyChar() == 'w'){
			System.out.println("PLAYER 1: w release");
		
			thepanel.intP1DefY = 0 ;
			
		//move down
		}else if(evt.getKeyChar() == 's'){
			System.out.println("PLAYER 1: s release");
			
			thepanel.intP1DefY = 0 ;
		}
	
	}
	
	public void keyPressed(KeyEvent evt){
		//START
		if(evt.getKeyChar() == KeyEvent.VK_SPACE){
			System.out.println("Space Bar Start Press");
			
		}
		
		//PLAYER 1
		//move up
		if(evt.getKeyChar() == 'w'){
			System.out.println("PLAYER 1: w press");
			
			thepanel.intP1DefY = -8;
			
		//move down
		}else if(evt.getKeyChar() == 's'){
			System.out.println("PLAYER 1: s press");
			
			thepanel.intP1DefY = 8;
		}
	}
	
	public void keyTyped(KeyEvent evt){
		
	}
	
	
	//constructor
	public netPongKalea(){
		//SERVER
		//server button
		serverconnect.setSize(300, 25);
		serverconnect.setLocation(800, 5);
		serverconnect.addActionListener(this);
		thepanel.add(serverconnect);
		
		//username
		theuserlabel.setSize(300, 25);
		theuserlabel.setLocation(800, 30);
		theuserlabel.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(theuserlabel);
		
		theuser.setSize(300, 25);
		theuser.setLocation(800, 55);
		theuser.setEnabled(false);
		theuser.addActionListener(this);
		thepanel.add(theuser);
		
		//connection label
		connectlabel.setSize(300, 25);
		connectlabel.setLocation(800, 100);
		connectlabel.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(connectlabel);
	
		//player 1 label
		play1label.setSize(300, 25);
		play1label.setLocation(800, 125);
		play1label.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(play1label);
		
		//player 2 label
		play2label.setSize(300, 25);
		play2label.setLocation(800, 150);
		play2label.setHorizontalAlignment(SwingConstants.CENTER);
		thepanel.add(play2label);
		
		//server window
		theframe.addKeyListener(this);
		theframe.requestFocus();
		
		theframe.setContentPane(thepanel);
		theframe.setLayout(null);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.setResizable(false);
		theframe.pack();
		theframe.setVisible(true);
		
		
	}
	
	//main method
	public static void main(String[] args){
		new netPongKalea();
		
	}
}
