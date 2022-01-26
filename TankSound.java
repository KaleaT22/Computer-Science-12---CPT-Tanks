//Doodle Tanks (Sound Object)
//By: Atilla Awista, Kalea Tse, & Noor Qureshi
//Date: January 27, 2022
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class TankSound{
	//Variable to call in main program to import sounds
	SoundEffect se = new SoundEffect();
	
	//Method to import sound and run it 
	public class SoundEffect{
		
		Clip click;
		
		public void setFile(String soundFileName){
			
			try{
				File file = new File(soundFileName);
				AudioInputStream soundy = AudioSystem.getAudioInputStream(file);
				click = AudioSystem.getClip();
				click.open(soundy);	
			}
			catch(Exception e){
				System.out.println(e.toString());
			}
		}
		public void play(){
			click.setFramePosition(0);
			click.start();
		}
	}
	public TankSound(){
		
	}
}
