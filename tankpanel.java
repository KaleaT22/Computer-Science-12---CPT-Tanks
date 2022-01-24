import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class tankpanel extends JPanel implements ActionListener{
	//Properties
	int intTank1Pos = 320;
	int intTank1Def = 0;
	int intTank1Pow = 80;
	int intTank1PowBoost = 0;
	
	int intTank1Ang = 45;
	int intHealth1 = 100;
	
	
	int intTank2Pos = 960;
	int intTank2Def = 0;
	int intTank2Pow = 80;
	int intTank2PowBoost = 0;
	int intTank2Ang = 45;
	int intHealth2 = 100;
	
	boolean blnPanelServer;
	boolean blnHelpLaunch = false;
	
	//tank images
	BufferedImage greenTank0img = null;
	BufferedImage greenTank18img = null;
	BufferedImage greenTank36img = null;
	BufferedImage greenTank54img = null;
	BufferedImage greenTank72img = null;
	BufferedImage greenTank90img = null;
	
	BufferedImage oranTank0img = null;
	BufferedImage oranTank18img = null;
	BufferedImage oranTank36img = null;
	BufferedImage oranTank54img = null;
	BufferedImage oranTank72img = null;
	BufferedImage oranTank90img = null;
	
	//screen / theme images
	BufferedImage generalMainmenu = null;
	BufferedImage christmasMainmenu = null;
	BufferedImage halloweenMainmenu = null;
	
	BufferedImage generalGame = null;
	BufferedImage christmasGame = null;
	BufferedImage halloweenGame = null;
	
	BufferedImage generalWin = null;
	BufferedImage christmasWin = null;
	BufferedImage halloweenWin = null;
	
	BufferedImage generalLose = null;
	BufferedImage christmasLose = null;
	BufferedImage halloweenLose = null;
	
	String strTheme = "general";
	
	int intRow;
	int intColumn;
	
	BufferedImage HelpTextimg = null;
	
	BufferedImage generalTheme = null;
	BufferedImage christmasTheme = null;
	BufferedImage halloweenTheme = null;
	
	CPTtanks TheTanks;
	
	String strGameOver = "false";
	
	//Spawns bullet off screen in limbo waiting to be launched
	getBullet bullet1 = new getBullet(-30,0,0,false, true);
	
	getBullet bullet2 = new getBullet(1310, 0, 0, false, true);
	
	getBullet bulletHelp = new getBullet(-30, 0, 0, false, true);
	
	Timer thetimer = new Timer(1000/60, this);
	String strScreen = "Start";
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			this.repaint();
			TheTanks.movingTank();
		}
	}//Repaints Panel
	
	public void paintComponent(Graphics g){
		//Home screen
		if(strScreen.equals("Start")){
			//System.out.println("STARTED");
			//g.setColor(Color.GREEN);
			//g.fillRect(0, 0, 1280, 720);
			
			//general theme
			if(strTheme.equals("general")){
				g.drawImage(generalMainmenu, 0, 0, null);
				
			//christmas theme
			}else if(strTheme.equals("christmas")){
				g.drawImage(christmasMainmenu, 0, 0, null);
			
			//halloween theme	
			}else if(strTheme.equals("halloween")){
				g.drawImage(halloweenMainmenu, 0, 0, null);
				
			}
		
		//help screen
		}else if(strScreen.equals("Help")){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1280, 720);
			//g.drawImage(HelpTextimg, 10, 50, null);
			g.drawImage(greenTank18img, intTank1Pos-10, 530, null);
			
			bulletHelp.drawIt(g);
			if(bulletHelp.boolHitbox==false){
				if(bulletHelp.intY>600){
					bulletHelp.boolLaunched=false;
					bulletHelp.boolHitbox=true;
					//TheTanks.allowShooting();
					blnHelpLaunch = false;
					//If the bullet touches the floor, stop the bullet from moving and change it's color to red
				}else if(bulletHelp.intX<0 || bulletHelp.intX>1280){
					bulletHelp.boolLaunched=false;
					bulletHelp.boolHitbox=true;
					//TheTanks.allowShooting();
					blnHelpLaunch = false;
					//If the bullet touches the sides of the screen, stop the bullet from moving and change it's color to red
				}
			//If client's bullet hits enemy tank (server), stop the bullet from moving and change it's color to red
			}
		
		//theme screen
		}else if(strScreen.equals("Theme")){
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 1280, 720);
			
			g.drawImage(generalTheme, 100, 210, null);
			g.drawImage(christmasTheme, 425, 210, null);
			g.drawImage(halloweenTheme, 730, 210, null);
			
		}else if(strScreen.equals("End Screen")){
			
		//Game screen
		}else if(strScreen.equals("Play")){
			//System.out.println("Game screen launched");
			//g.setColor(Color.CYAN);
			//g.fillRect(0, 0, 1280, 720);
			//g.setColor(Color.GREEN);
			//g.fillRect(0, 620, 1280, 100);
			
			//general theme
			if(strTheme.equals("general")){
				g.drawImage(generalGame, 0, 0, null);
	
			//christmas theme
			}else if(strTheme.equals("christmas")){
				g.drawImage(christmasGame, 0, 0, null);
			
			//halloween theme	
			}else if(strTheme.equals("halloween")){
				g.drawImage(halloweenGame, 0, 0, null);
				
			}
			
			//middle column
			g.setColor(Color.GRAY);
			g.fillRect(520,460,40,160);
			
			//TANK 1
			///g.setColor(Color.RED);
			///g.fillRect(intTank1Pos, 560, 100, 60);
			//if angle >= 0
			if(intTank1Ang >= 0 && intTank1Ang < 18 ){
				g.drawImage(greenTank0img, intTank1Pos-10, 530, null);
				
			}
			
			//if angle >= 18
			if(intTank1Ang >= 18 && intTank1Ang < 36){
				g.drawImage(greenTank18img, intTank1Pos-10, 530, null);
				
			}
			
			//if angle >= 36
			if(intTank1Ang >= 36 && intTank1Ang < 54){
				g.drawImage(greenTank36img, intTank1Pos-10, 530, null);
				
			}
			
			//if angle >= 54
			if(intTank1Ang >= 54 && intTank1Ang < 72){
				g.drawImage(greenTank54img, intTank1Pos-10, 530, null);
				
			}
			
			//if angle >= 72
			if(intTank1Ang >= 72 && intTank1Ang < 90){
				g.drawImage(greenTank72img, intTank1Pos-10, 530, null);
				
			}
			
			//if angle = 90
			if(intTank1Ang == 90){
				g.drawImage(greenTank90img, intTank1Pos-10, 530, null);
				
			}

			intTank1Pos = intTank1Pos + intTank1Def;
			
			bullet1.drawIt(g);
			Font Scorefont10 = new Font("Comic Sans MS", Font.PLAIN, 21);
			if(blnPanelServer == true){
				if(strTheme.equals("halloween")){
					g.setColor(Color.WHITE);
					
				}else{
					g.setColor(Color.BLACK);
					
				}
				
				g.setFont(Scorefont10);
				
				g.drawString("Launch angle: "+intTank1Ang, 25, 85);
				g.drawString("Health: "+intHealth1, 25, 50);
				g.drawString("Health: "+intHealth2, 1100, 50);
				g.drawString("POWER: "+(intTank1Pow+intTank1PowBoost), 25, 120);
			}
			else{
				if(strTheme.equals("halloween")){
					g.setColor(Color.WHITE);
					
				}else{
					g.setColor(Color.BLACK);
					
				}
				
				g.setFont(Scorefont10);
				
				g.drawString("Launch angle: "+intTank2Ang, 1100, 85);
				g.drawString("Health: "+intHealth2, 1100, 50);
				g.drawString("Health: "+intHealth1, 25, 50);
				g.drawString("POWER: "+(intTank2Pow+intTank2PowBoost), 1100, 120);
			}
			
			//TANK 2
			///g.setColor(Color.ORANGE);
			///g.fillRect(intTank2Pos, 560, 100, 60);
			//if angle >= 0
			if(intTank2Ang >= 0 && intTank2Ang < 18){
				g.drawImage(oranTank0img, intTank2Pos-10, 530, null);
				
			}
			
			//if angle >= 18
			if(intTank2Ang >= 18 && intTank2Ang < 36){
				g.drawImage(oranTank18img, intTank2Pos-10, 530, null);
				
			}
			
			//if angle >= 36
			if(intTank2Ang >= 36 && intTank2Ang < 54){
				g.drawImage(oranTank36img, intTank2Pos-10, 530, null);
				
			}
			
			//if angle >= 54
			if(intTank2Ang >= 54 && intTank2Ang < 72){
				g.drawImage(oranTank54img, intTank2Pos-10, 530, null);
				
			}
			
			//if angle >= 72
			if(intTank2Ang >= 72 && intTank2Ang < 90){
				g.drawImage(oranTank72img, intTank2Pos-10, 530, null);
				
			}
			
			//if angle == 90
			if(intTank2Ang == 90){
				g.drawImage(oranTank90img, intTank2Pos-10, 530, null);
				
			}
			
			intTank2Pos = intTank2Pos + intTank2Def;
			
			bullet2.drawIt(g);
			if(bullet1.boolHitbox==false){
				if(new Rectangle(bullet1.intX, bullet1.intY, 20, 20).intersects(new Rectangle(intTank2Pos, 560, 100, 60))){
					bullet1.boolLaunched=false;
					bullet1.boolHitbox=true;
					intHealth2 = intHealth2 - 100;
					
					//if play 2 health <= 0, player 2 loses, player 1 wins
					if(intHealth2 <= 0 && intHealth1 > 0){
						strScreen = "tank1Win";
						CPTtanks.returnbut2.setVisible(true);
						
						if(blnPanelServer == true){
							CPTtanks.returnbut2.setEnabled(true);
							
						}else{
							CPTtanks.returnbut2.setEnabled(false);
							
						}
						
						strGameOver = "play1Win";
					}
					
					TheTanks.allowShooting();
					//If the bullet hits a tank
				}else if(bullet1.intY>600){
					bullet1.boolLaunched=false;
					bullet1.boolHitbox=true;
					TheTanks.allowShooting();
					//If the bullet touches the floor, stop the bullet from moving and change it's color to red
				}else if(bullet1.intX<0 || bullet1.intX>1080){
					bullet1.boolLaunched=false;
					bullet1.boolHitbox=true;
					TheTanks.allowShooting();
					//If the bullet touches the sides of the screen, stop the bullet from moving and change it's color to red
				}else if(new Rectangle(bullet1.intX, bullet1.intY, 20, 20).intersects(new Rectangle(520,460,40,160))){
					bullet1.boolLaunched=false;
					bullet1.boolHitbox=true;
					TheTanks.allowShooting();
				}
				//If server's bullet hits enemy tank (client), stop the bullet from moving and change it's color to red
			}
			
			if(bullet2.boolHitbox==false){
				if(new Rectangle(bullet2.intX, bullet2.intY, 20, 20).intersects(new Rectangle(intTank1Pos, 560, 100, 60))){
					bullet2.boolLaunched=false;
					bullet2.boolHitbox=true;
					intHealth1 = intHealth1 - 100;
					
					//if play 1 health <= 0, player 2 wins, player 1 loses
					if(intHealth1 <= 0 && intHealth2 > 0){
						strScreen = "tank2Win";
						CPTtanks.returnbut2.setVisible(true);
						
						if(blnPanelServer == true){
							CPTtanks.returnbut2.setEnabled(true);
							
						}else{
							CPTtanks.returnbut2.setEnabled(false);
							
						}
						
						strGameOver = "play2Win";
					}
					
					TheTanks.allowShooting();
					//If the bullet hits a tank
				}else if(bullet2.intY>600){
					bullet2.boolLaunched=false;
					bullet2.boolHitbox=true;
					TheTanks.allowShooting();
					//If the bullet touches the floor, stop the bullet from moving and change it's color to red
				}else if(bullet2.intX<0 || bullet2.intX>1080){
					bullet2.boolLaunched=false;
					bullet2.boolHitbox=true;
					TheTanks.allowShooting();
					//If the bullet touches the sides of the screen, stop the bullet from moving and change it's color to red
				}else if(new Rectangle(bullet2.intX, bullet2.intY, 20, 20).intersects(new Rectangle(520,460,40,160))){
					bullet2.boolLaunched=false;
					bullet2.boolHitbox=true;
					TheTanks.allowShooting();
				}
			//If client's bullet hits enemy tank (server), stop the bullet from moving and change it's color to red
			}
			
			
		//if player 2 wins
		}else if(strScreen.equals("tank2Win")){
			if(strTheme.equals("general")){
				if(blnPanelServer == true){
					g.drawImage(generalLose, 0, 0, null);
					
				}else{
					g.drawImage(generalWin, 0, 0, null);
					
				}
			
			}else if(strTheme.equals("christmas")){
				if(blnPanelServer == true){
					g.drawImage(christmasLose, 0, 0, null);
					
					
				}else{
					g.drawImage(christmasWin, 0, 0, null);
					
				}
			
			}else if(strTheme.equals("halloween")){
				if(blnPanelServer == true){
					g.drawImage(halloweenLose, 0, 0, null);
					
					
				}else{
					g.drawImage(halloweenWin, 0, 0, null);
					
				}
			}
		
		}else if(strScreen.equals("tank1Win")){
			if(strTheme.equals("general")){
				if(blnPanelServer == true){
					g.drawImage(generalWin, 0, 0, null);
					
					
				}else{
					g.drawImage(generalLose, 0, 0, null);
					
				}
			
			}else if(strTheme.equals("christmas")){
				if(blnPanelServer == true){
					g.drawImage(christmasWin, 0, 0, null);
					
					
				}else{
					g.drawImage(christmasLose, 0, 0, null);
					
				}
			
			}else if(strTheme.equals("halloween")){
				if(blnPanelServer == true){
					g.drawImage(halloweenWin, 0, 0, null);
					
					
				}else{
					g.drawImage(halloweenLose, 0, 0, null);
					
				}
			}
		}
	}
	
	//constructor
	public tankpanel(CPTtanks TheTanks){
		super();
		this.setPreferredSize(new Dimension(1280, 720));
		thetimer.start();
		
		this.TheTanks = TheTanks;
		
		//images
		try{
			//tank images
			greenTank0img = ImageIO.read(new File("THEGREENTANK0.png"));
			greenTank18img = ImageIO.read(new File("THEGREENTANK18.png"));
			greenTank36img = ImageIO.read(new File("THEGREENTANK36.png"));
			greenTank54img = ImageIO.read(new File("THEGREENTANK54.png"));
			greenTank72img = ImageIO.read(new File("THEGREENTANK72.png"));
			greenTank90img = ImageIO.read(new File("THEGREENTANK90.png"));
			
			oranTank0img = ImageIO.read(new File("THEORANGETANK0.png"));
			oranTank18img = ImageIO.read(new File("THEORANGETANK18.png"));
			oranTank36img = ImageIO.read(new File("THEORANGETANK36.png"));
			oranTank54img = ImageIO.read(new File("THEORANGETANK54.png"));
			oranTank72img = ImageIO.read(new File("THEORANGETANK72.png"));
			oranTank90img = ImageIO.read(new File("THEORANGETANK90.png"));
			
			//screen + theme images
			generalMainmenu = ImageIO.read(new File("DOODLETANKS2.png"));
			christmasMainmenu = ImageIO.read(new File("christmasMain.png"));
			halloweenMainmenu = ImageIO.read(new File("halloweenMain.png"));
			
			generalGame = ImageIO.read(new File("gameplayScreen.png"));
			christmasGame = ImageIO.read(new File("christmasScreen.png"));
			halloweenGame = ImageIO.read(new File("halloweenScreen.png"));
			
			HelpTextimg = ImageIO.read(new File("HelpScreen.png"));
			
			generalTheme = ImageIO.read(new File("mainTheme.png"));
			christmasTheme = ImageIO.read(new File("christmasTheme.png"));
			halloweenTheme = ImageIO.read(new File("halloweenTheme.png"));
			
			generalLose = ImageIO.read(new File("doodleLoseScreen.png"));
			christmasLose = ImageIO.read(new File("christmasScreen.png"));
			halloweenLose = ImageIO.read(new File("halloweenLoseScreen.png"));
			
			generalWin = ImageIO.read(new File("doodleWinScreen.png"));
			christmasWin = ImageIO.read(new File("christmasWinScreen.png"));
			halloweenWin = ImageIO.read(new File("halloweenWinScreen.png"));
			
			
		
		}catch(IOException e){
			System.out.println("Unable to load image");
			
		}
	}
}
