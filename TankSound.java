//Doodle Tanks (Sound Object)
//By: Atilla Awista, Kalea Tse, & Noor Qureshi
//Date: January 27, 2022
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 * <h1>Doodle Tanks (TankSound)<br></h1>
 * By: Atilla Awista, Kalea Tse, Noor Qureshi<br>
 * Date: January 27, 2022<br>
 * <br> This is the Sound Object file for Doodle Tanks
 */

/**
 * The Constructor which you can send the Sound File to, and play when you need it to.
 * The sound file can be activated using the play() method.
 */
public class TankSound{
	//Variable to call in main program to import sounds
	SoundEffect se = new SoundEffect();
	
	/**
	 * Constructor that is used to turn the sound into an object
	 * In the main program, SoundEffect can be called and a sound
	 * file can be sent to this constructor. When that is done, 
	 * this method can play a specific sound file given (only .wav) 
	 * when the specific condition is fulfilled (whether it be an if,
	 * while, for, etc.)
	 */
	//Method to import sound and run it 
	public class SoundEffect{
		
		Clip click;
		
		public void setFile(String soundFileName){
			/**
			 * Creates a new "File" where the sound file can be stored to play when it is called.
			 */
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
		 * Plays a sound when the variable is called
		 */
		public void play(){
			click.setFramePosition(0);
			click.start();
		}
	}
	public TankSound(){
		
	}
}
