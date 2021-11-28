import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		// Si jamais on a besoin de test qqchose
		// Sinon supprimer la classe avant remise
		
		Trame t = new Trame ('A', 0, "000111000");
		System.out.println(t.toString());
		System.out.println(t.info());
		
	}
	
}
