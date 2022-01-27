//Doodle Tanks (Sound Object)
//By: Atilla Awista, Kalea Tse, & Noor Qureshi
//Date: January 27, 2022
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import java.io.InputStream;
import java.io.FileInputStream;
import java.net.URL;

/**
 * <h1>Doodle Tanks (TankSound)<br></h1>
 * By: Atilla Awista, Kalea Tse, Noor Qureshi<br>
 * Date: January 27, 2022<br>
 * <br> This is the Sound Object file for Doodle Tanks.
 */
 
public class TankSound{
	//Variable to call in main program to import sounds
	 
	SoundEffect se = new SoundEffect();
	 
	/**Method that is used to turn the sound into an object
	 * SoundEffect can be called in the main program and a sound
	 * file is sent to this method. When that is done, 
	 * this method can play the specific sound file given (only .wav) 
	 * when the specific condition is fulfilled (whether it be an if,
	 * while, for, etc.)
	 */
	 
	public class SoundEffect{
	//Method to import sound and run it 
		
		Clip click;
		
		/**
		 * Creates a new "File" where the sound file can be stored to play when it is called.
		 */
		
		public void setFile(String soundFileName){
			InputStream soundclass = null;
			soundclass = this.getClass().getResourceAsStream(soundFileName);
			if(soundclass == null){
			}else{
				try{
					URL viaClass= SoundEffect.class.getResource(soundFileName);
					AudioInputStream soundy = AudioSystem.getAudioInputStream(viaClass);
					click = AudioSystem.getClip();
					click.open(soundy);
				}catch(Exception e){
				}
			}
			//If this is a jar file, try to get sound file from jar file, turn it into AudioInputStream and set up sound variables
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
		
		/**
		 * Plays a sound when the method is called on.
		 */
		 
		public void play(){
			click.setFramePosition(0);
			click.start();
		}
	}
	
	/**
     * A way to call the entire method from another program.
     * Non-static variables persist in this method and cannot be called
     * from static contexts. This method allows for the method to be called 
     * from static variables.
     */
	 
	public TankSound(){
		
	}
}
