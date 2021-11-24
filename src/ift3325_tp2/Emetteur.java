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
	
	
	
}
