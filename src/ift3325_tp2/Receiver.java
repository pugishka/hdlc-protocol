import java.io.*;
import java.net.*;


public class Receiver {

    private ServerSocket socketReceiver;
    private Socket socketSender;
    private PrintWriter out;
    private BufferedReader in;
    
    // constructeur
	public Receiver(int port) throws IOException {
		socketReceiver = new ServerSocket(port);
		socketSender = socketReceiver.accept();
        out = new PrintWriter(socketSender.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socketSender.getInputStream()));
        
        // ne pas terminer la connexion tant qu'on recoit des requetes
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
    		sendResponse(inputLine);
        }
        
	}
	
	// main
    public static void main(String[] args) throws NumberFormatException, UnknownHostException, IOException {
    	Receiver R = new Receiver(Integer.parseInt(args[0]));
    	R.stop();
    }
	
    // on a reçu une trame, decider de la trame reponse à envoyer
	public void sendResponse(String trameS) throws IOException {
    	
		// TODO
		// gestion d'erreurs
    	Trame trame = new Trame(trameS);
    	
    	System.out.println("Trame reçue par Receiver : \n" + trameS + "\n\nInfos :\n" + trame.info() + "\n");
    	
    	if ( trame.getType() == 'I' || trame.getType() == 'C' ||trame.getType() == 'F') {
    		sendTrame(new Trame('A', trame.getNum(), ""));
    	}
	}

	// envoyer la trame t
    public void sendTrame(Trame t) throws IOException {
    	String tSend = t.toString();
    	System.out.println("Envoi de la trame par Receiver : \n" + tSend + "\n\nInfos :\n" + t.info() + "\n\n\n--------------\n\n\n");
        out.println(tSend);
    }
	
    // fermer connexion socket
    public void stop() throws IOException {
        in.close();
        out.close();
        socketSender.close();
        socketReceiver.close();
    }
	
}
