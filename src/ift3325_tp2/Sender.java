import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Sender {
	
	// liste des donnees du fichier
	public List<String> data;

	// source sockets :
	// https://www.baeldung.com/a-guide-to-java-sockets 
	private Socket socketSender;
    private PrintWriter out;
    private BufferedReader in;
    
    // constructeur
	public Sender(String ip, int port, String fileDir) throws UnknownHostException, IOException, InterruptedException {
		this.data = readFile(fileDir);
		this.socketSender = new Socket(ip, port);
        this.out = new PrintWriter(socketSender.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socketSender.getInputStream()));
        
        // envoyer data au Receiver auquel on s'est connecte
        sendAllData();
	}
	
	// --------------
	
	// main
    public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException, InterruptedException {
    	Sender S = new Sender(args[0], Integer.parseInt(args[1]), args[2]);
		S.stopConnection();
    }
	
    // envoyer une trame au Receiver et retourner la trame reponse
    public Trame sendTrame(Trame t) throws IOException {
    	String tSend = t.toString();
    	System.out.println("Envoi de la trame par Sender : \n" + tSend + "\n\nInfos :\n" + t.info() + "\n");
        out.println(tSend);
        String tReceivedS = in.readLine();
        Trame tReceivedT = new Trame(tReceivedS);
    	System.out.println("Trame recue par Sender : \n" + tReceivedS + "\n\nInfos :\n" + tReceivedT.info() + "\n\n\n--------------\n\n\n");
    	return tReceivedT;
    }

    // fermer connexion socket
    public void stopConnection() throws IOException {
        in.close();
        out.close();
        socketSender.close();
    }
    
    // lire le fichier des donnees
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
	
	// boucler sur chaque data � envoyer au Receiver
	public void sendAllData() throws IOException, InterruptedException {
		
		// trame de connexion
		Trame trameCo = new Trame ('C', 0, "");
		System.out.println("Demande de connexion\n");
		Trame trameRecue = sendTrame(trameCo);
		
		if (trameRecue.getType() == 'A') {
			System.out.println("Connexion autorise\n");
		}
		
		// gestion d'erreurs
		int n = 1;
		for (String d:this.data) {
			Trame t = new Trame('I', n, d);
		
			Trame tRecue = sendTrame(t);
			while(tRecue.getType() == 'R' ){
				tRecue = sendTrame(t);
				System.out.println("Trame erronee\n");
				Thread.sleep(3000);
			}
			n++;
			System.out.println("Trame suivante\n");
		}
		

		// fermeture de connexion
		Trame trameClose = new Trame ('F', n, "");
		System.out.println("Demande de fin de connexion\n");
		trameRecue = sendTrame(trameClose);
		
		if (trameRecue.getType() == 'A') {
			System.out.println("Connexion terminee\n");
		}
		
	}
	
	
	
	
	
}
