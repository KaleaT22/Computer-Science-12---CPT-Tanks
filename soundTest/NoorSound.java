import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class NoorSound{
	String clickSound;
	SoundEffect se = new SoundEffect();
	int intRun = 0;
	
	
	/*public void Sound(){
		clickSound = "CLICKNOISE2.mp3";
	}*/
	
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
	public NoorSound(){
		
	}
}
