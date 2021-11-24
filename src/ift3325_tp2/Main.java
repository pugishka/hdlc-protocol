public class Main {

	public static void main(String[] args) {
		
		
		Emetteur e = new Emetteur("test.txt");
		Trame t = new Trame('I', 0, "1011001100101");
		System.out.println(t.info());
		System.out.println(t.toString());
		
	}
	
	
	
	

}
