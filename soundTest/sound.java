import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class sound{
	void playSound(String filepath){
		
		try{
			File sound = new File(filepath);
			
			if(sound.exists()){
				AudioInputStream audio = AudioSystem.getAudioInputStream(sound);
				
				Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
				//clip.loop(-1);
				//Thread.sleep(1000);
				
				JOptionPane.showMessageDialog(null, "Press OK to stop playing");
				
			}else{
				System.out.println("Can't find file");
			}
			
		}catch(Exception e){
			System.out.println("Error");
		}
	}
}
