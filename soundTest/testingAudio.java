import java.io.*;

public class testingAudio{
	public static void main(String[] args){
		
		String filepath = "VICTORYATLAST.wav";
		
		sound soundObject = new sound();
		
		soundObject.playSound(filepath);
		
		soundObject.playSound(filepath);
		
		soundObject.playSound(filepath);
		
	}
}
