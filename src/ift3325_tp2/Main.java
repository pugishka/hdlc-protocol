import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		//Emetteur e = new Emetteur(askFile());
		Emetteur e = new Emetteur("test.txt");
		Recepteur r = new Recepteur();
		e.sendAllData(r);
		
	}
	
	public static String askFile() {
		Scanner s = new Scanner(System.in);
	    System.out.println("Enter text file directory : ");
	    String r = s.nextLine();
	    s.close();
	    return r;
	}
	
	
	
	

}
