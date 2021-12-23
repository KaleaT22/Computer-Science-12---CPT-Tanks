import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class CPTtanks implements ActionListener, KeyListener{
	//properties
	JFrame theframe = new JFrame("Tanks");
	tankpanel thepanel = new tankpanel();
	boolean boolServer=true;
	
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
	
	//methods
	public void actionPerformed(ActionEvent evt){
		//server connects
		if(evt.getSource() == theserver){
			theserver.setEnabled(false); 
			
			ssm = new SuperSocketMaster(8374, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				playbut.setEnabled(true);
				thedisconnect.setEnabled(true);
				theframe.requestFocus();
				
				System.out.println("server");
				
						
			}else{
				theserver.setEnabled(true); 
				
			}
			
		//if client connects
		}else if(evt.getSource() == theclient){
			theclient.setEnabled(false);
			theipAdd.setEnabled(false);
			
			ssm = new SuperSocketMaster(theipAdd.getText(), 8374, this);
			
			boolean blnIsConnected = ssm.connect();
			
			if(blnIsConnected){
				thedisconnect.setEnabled(true);
			
				System.out.println("client");
					
			}else{
				theclient.setEnabled(true); 
				theipAdd.setEnabled(true);
			
			}
		
		//if server disconnects	
		}else if(evt.getSource() == thedisconnect){
			theclient.setEnabled(true);
			theipAdd.setEnabled(true);
			thedisconnect.setEnabled(false);
		
			System.out.println("Disconnected");
	
			theipAdd.setText("");
			
			ssm.disconnect();
		
		//servers connected
		}else if(evt.getSource() == ssm){
			System.out.println("SSM TEST: " + ssm.readText());
			if(ssm != null){	
				
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
			
			theframe.requestFocus();
			
		}
	}
	
	public void keyReleased(KeyEvent evt){
		if(evt.getKeyChar() == 'a'){
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
		if(evt.getKeyChar() == ' '){
			if(boolServer==true){
				thepanel.bullet1 = new getBullet((thepanel.intTank1Pos+40),thepanel.intTank1Pow,thepanel.intTank1Ang,true);
				System.out.println("FIRED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
