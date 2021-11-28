import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		// Si jamais on a besoin de test qqchose
		// Sinon supprimer la classe avant remise
		
		Trame t = new Trame ('A', 0, "000111000");
		//System.out.println(t.toString());
		//System.out.println(t.info());
		
		//System.out.println("\n\n\n");

		String t2 = t.bitStuffingAdd("01111110011011111111111111111001001111110");
		System.out.println(t2);
		String t3 = t.bitStuffingRemove(t2);
		System.out.println(t3);
		//System.out.println(new Trame(t2).info());
		
	}
	
}
