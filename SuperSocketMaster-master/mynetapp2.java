import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class mynetapp2 implements ActionListener{
	
	//creating a server program to recieve basic text from client
	//properties
	JFrame theframe = new JFrame("Basic Server Net APP");
	JPanel thepanel = new JPanel();
	JLabel thelabel = new JLabel("Text Will Appear Here");
	SuperSocketMaster ssm = new SuperSocketMaster(6112, this);
	
	//methods
	public void actionPerformed(ActionEvent evt){
		if(evt.getSource() == ssm){
			thelabel.setText(ssm.readText());
		}
	}
	
	//constructor
	public mynetapp2(){
		thepanel.add(thelabel);
		thepanel.setPreferredSize(new Dimension(300, 300));
		
		theframe.setContentPane(thepanel);
		theframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theframe.pack();
		theframe.setVisible(true);
		
		ssm.connect();
	}
	
	//main method
	public static void main(String[] args){
		new mynetapp2();
	}
}
