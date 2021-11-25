import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Emetteur {
	
	public List<String> data;
	
	public Emetteur(String fileDir) {
		this.data = readFile(fileDir);
	}
	
	public List<String> readFile(String fileDir){
		List<String> data = new ArrayList<String>();
		try {
			File fileData = new File(fileDir);
			Scanner myReader = new Scanner(fileData);
			while (myReader.hasNextLine()) {
				data.add(myReader.nextLine());
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	
		return data;
		
	}
	
	public void sendAllData(Recepteur r) {
		
		// trame de connexion
		Trame trameCo = new Trame ('C', 0, "");
		System.out.println("Demande de connexion\n");
		String trameRecueS = r.sendTrame(trameCo.toString());
		Trame trameRecue = new Trame (trameRecueS);
		System.out.println("Infos de la trame reçue : \n" + trameRecue.info());
		
		
		
		/*for (String d:this.data) {
			new Trame t = Trame(type, num, d);
		}*/
		
	}
	
	
	
}
