import java.awt.*;

public class guessTools{
	public int lowIntRange(){
		//generate lower number in range (1 - 200)
		int intLowNum = (int)(Math.random()* (200 - 1) + 1);
		System.out.println("Low Number: " + intLowNum);
		
		return intLowNum;
	}
	
	public int highIntRange(){
		//generate higher number in range (400 - 1000)
		int intHighNum = (int)(Math.random()* (1000 - 400) + 400);
		System.out.println("High Number: " + intHighNum);
		
		return intHighNum;
	}
	
	public int randomGuessNum(int intLowNum, int intHighNum){
		int intGuessNum = (int)(Math.random()* (intHighNum - intLowNum) + intLowNum);
		System.out.println("Guessing Number: " + intGuessNum);
		
		return intGuessNum;
	}
	
	public void sortBubble(){
		
	}
}
