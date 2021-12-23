import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PPanel extends JPanel implements ActionListener{
	//properties
	//timer
	Timer thetimer = new Timer(1000 / 60, this);
	
	//ball position
	int intBallX = 400;
	int intBallY = 300;
	
	//ball deflection
	int intBallDefX = 0;
	int intBallDefY = 0;
	
	//PLAYER 1
	//p1 paddle position
	int intP1X = 20;
	int intP1Y = 20;
	
	int intP1DefX = 0;
	int intP1DefY = 0;
	
	//p1 score
	int intP1Score = 0;
	
	//PLAYER 2
	//p2 paddle
	int intP2X = 780;
	int intP2Y = 530;
	
	int intP2DefX = 0;
	int intP2DefY = 0;
	
	//p2 score
	int intP2Score = 0;
	
	//Game Over Screen
	BufferedImage p1Winimg = null;
	BufferedImage p2Winimg = null;
	
	//joke screen
	BufferedImage jokeimg = null;
	
	//background colour
	int intR = 0;
	int intG = 0;
	int intB = 0;
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetimer){
			this.repaint();
		}
	}
	
	//override
	public void paintComponent(Graphics g){
		//background
		g.setColor(new Color(intR, intG, intB));
		g.fillRect(0, 0, 800, 600);
		
		g.setColor(Color.WHITE);
		g.fillRect(406, 0, 5, 600);
		
		//user input screen
		g.fillRect(800, 0, 300, 600);
		
		//PLAYER 1
		//paddle
		g.fillRect(intP1X, intP1Y, 10, 50);
	
		//score
		g.setFont(new Font("Arial", Font.PLAIN, 100)); 
		g.drawString("" + intP1Score, 100, 150);
		
		intP1X = intP1X + intP1DefX;
		intP1Y = intP1Y + intP1DefY;
		
		//stops paddle from leaving screen
		if(intP1Y < 0){
			intP1Y = 0;
			
		}else if(intP1Y > 550){
			intP1Y = 550;
		}
		
		//PLAYER 2
		//paddle
		g.fillRect(intP2X, intP2Y, 10, 50);
		
		//score
		g.drawString("" + intP2Score, 600, 150);
		
		intP2X = intP2X + intP2DefX;
		intP2Y = intP2Y + intP2DefY;
	
		//stop paddle from leaving screen
		if(intP2Y < 0){
			intP2Y = 0;
			
		}else if(intP2Y > 550){
			intP2Y = 550;
		}
		
		//BALL
		//draw ball
		g.fillOval(intBallX, intBallY, 20, 20);
		
		//if ball hits top of screen, bounce
		if(intBallY <= 0){
			intBallDefY = -intBallDefY;
			
		}else if(intBallY >= 580){
			intBallDefY = -intBallDefY;
			
		}
		
		//if ball leaves screen on left, p2 point
		if(intBallX < 0){
			intBallDefX = 0;
			intBallDefY = 0;
			
			intP2Score++;
			
			intBallX = 400;
			intBallY = 300;
			
			System.out.println("P1: " + intP1Score);
			System.out.println("P2: " + intP2Score);
			
		//if ball leaves screen on right, p1 point
		}else if(intBallX > 780){
			intBallDefX = 0;	
			intBallDefY = 0;
			
			intP1Score++;
			
			intBallX = 400;
			intBallY = 300;
			
			System.out.println("P1: " + intP1Score);
			System.out.println("P2: " + intP2Score);
		
		}
		
		//if ball hits paddle 1
		if(intBallX + 20 >= intP1X && intBallX <= intP1X + 10 && intBallY + 20 >= intP1Y && intBallY <= intP1Y + 50){
			System.out.println("Hit Paddle 1");
			intBallDefX = - intBallDefX;
			//intBallDefY = - intBallDefY;
			
		}else if(intBallX + 20 >= intP2X && intBallX <= intP2X + 10 && intBallY + 20 >= intP2Y && intBallY <= intP2Y + 50){
			System.out.println("Hit Paddle 2");
			intBallDefX = - intBallDefX;
			//intBallDefY = - intBallDefY;
			
		}/*else{
			System.out.println("No paddle detected");
		
		}*/
		
		intBallX = intBallX + intBallDefX;
		intBallY = intBallY + intBallDefY;
		
		//change brightness of screen
		if(intP1Score == 3 && intP2Score < 3 || intP2Score == 3 && intP1Score < 3){
			intR = 96;
			intG = 96;
			intB = 96;
		
		//change brightness of screen
		}else if(intP1Score == 4  && intP2Score < 4 || intP2Score == 4 && intP1Score < 4){
			intR = 190;
			intG = 186;
			intB = 186;
		
		//special win bonus joke for p1	
		}else if(intP1Score == 5 && intP2Score == 0){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			
			g.drawImage(jokeimg, 50, 50, null);
			
		//special win bonus joke for p2
		}else if(intP1Score == 0 && intP2Score == 5){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			
			g.drawImage(jokeimg, 50, 50, null);
			
		//player 1 wins
		}else if(intP1Score == 5){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			
			g.drawImage(p1Winimg, 250, 200, null);
			
		//player 2 wins
		}else if(intP2Score == 5){
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 600);
			
			g.drawImage(p2Winimg, 250, 200, null);
			
		}
	}
	
	//constructor
	public PPanel(){
		super();
		this.setPreferredSize(new Dimension(1100, 600));
		
		thetimer.start();
		
		//images
		try{
			p1Winimg = ImageIO.read(new File("gameOverp1.png"));
			p2Winimg = ImageIO.read(new File("gameOverp2.png"));
			jokeimg = ImageIO.read(new File("bonusJoke.png"));
		
		}catch(IOException e){
			System.out.println("Unable to load image");
			
		}
	}
}
