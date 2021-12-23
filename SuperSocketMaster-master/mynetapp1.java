import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class mynetapp1 implements ActionListener{
	
	//creating a client program to send basic text to a server
	//properties
	JFrame theframe = new JFrame("Basic Client Net APP");
	JPanel thepanel = new JPanel();
	JTextField thetext = new JTextField("Enter Text Here");
	SuperSocketMaster ssm = new SuperSocketMaster("127.0.0.1", 6112, this);
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == thetext){
			System.out.println("I hear you... will send the text over network");
			ssm.sendText(thetext.getText());
			
		}
	
	}
	
	//constructor
	public mynetapp1(){
		thetext.addActionListener(this);
		thepanel.add(thetext);
		thepanel.setPreferredSize(new Dimension(300, 300));
		
		theframe.setContentPane(thepanel);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		
		ssm.connect();
	}
	
	//main method
	public static void main(String[] args){
		new mynetapp1();
	}
}
