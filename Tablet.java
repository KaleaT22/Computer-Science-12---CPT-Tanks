public class Tablet extends Computer{
	
	//Properties
	private boolean blnSecureBoot;
	public String strOS;
	
	//Methods
	public boolean secureBootStatus(){
		return blnSecureBoot;
	}
	public void playGame(String strGameName){
		System.out.println("Playing: "+strGameName);
	}
	public void surfWeb(){
		System.out.println("Surfing random things on google");
	}
	//override
	public void boot(){
		if(strOS.equalsIgnoreCase("IOS")){
			System.out.println("Apple Fanboy");
		}else if(strOS.equalsIgnoreCase("Android")){
			System.out.println("Android Sheep");
		}else{
			System.out.println("What kind of tablet is this?");
		}
	}
	public void shutdown(){
		System.out.println("Shutting down your tablet");
	}
	
	//Constructor
	public Tablet(int intCPU, int intMem, boolean blnSB, String strOS){
		super(intCPU, intMem);
		this.blnSecureBoot = blnSB;
		this.strOS = strOS;
	}
}
