import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		// Si jamais on a besoin de test qqchose
		// Sinon supprimer la classe avant remise
		
		Trame t = new Trame('I', 0, "111111111111111");
		System.out.println(t.info() + "\n");
		System.out.println(t.toString());

		Trame t2 = new Trame('R', 0,"");
		
		
	}
	
}
