

public class Trame {

	public String polynome = "10001000000100001";
	public String flag = "01111110";
	public char type;
	public int num;
	public String data;
	public int crc;
	
	public Trame(char type, int num, String data) {
		this.type = type;
		this.num = num;
		this.data = data;
		this.crc = calculateCRC(type, num, data);
	}
	

	// concatener toutes les infos sous forme de string en binaire
	@Override
	public String toString() {
		String s = this.flag;
		for (int i=0; i<8-Integer.toBinaryString((int) this.type).length(); i++) {
			s += "0";
		}
		s += Integer.toBinaryString((int) this.type);
		for (int i=0; i<8-Integer.toBinaryString((int) ((char) (this.num+'0'))).length(); i++) {
			s += "0";
		}
		s += Integer.toBinaryString((int) ((char) (this.num+'0')));
		s += this.data;
		for (int i=0; i<16-Integer.toBinaryString(this.crc).length(); i++) {
			s += "0";
		}
		s += Integer.toBinaryString(this.crc);
		s += this.flag;
		
		return s;
	}

	
	// Avoir un string propre � imprimer pour voir les infos d'une trame
	public String info() {
		String s = "1. flag : " + this.flag + "\n";
		s +=  "2. type : \n      char : " + this.type;
		s += "\n      int : " + (int) this.type;
		s += "\n      binary : " + Integer.toBinaryString((int) this.type) + " (length : " + Integer.toBinaryString((int) this.type).length() + ")\n";
		s +=  "3. num : \n      char : " + (char) (this.num+'0');
		s += "\n      int : " + this.num;
		s += "\n      binary of char : " + Integer.toBinaryString((int) ((char) (this.num+'0'))) + " (length : " + Integer.toBinaryString((int) ((char) (this.num+'0'))).length() + ")\n";
		s +=  "4. data : " + this.data + "\n";
		s +=  "5. crc : \n      binary : " + Integer.toBinaryString(this.crc) + " (length : " + Integer.toBinaryString(this.crc).length() + ")";
		s += "\n      int : " + this.crc;
		
		return s;
	}
	
	public int calculateCRC(char type, int num, String data) {
		
		String s = Integer.toBinaryString((int) type);
		s += Integer.toBinaryString((int) ((char) (num+'0')));
		s += data;
		s += "0000000000000000";
		
		int[] reste = new int[this.polynome.length()];
		int[] diviseur = new int[this.polynome.length()];
		
		for(int i=0; i<this.polynome.length(); i++) {
			diviseur[i] = Character.getNumericValue(this.polynome.charAt(i));
			reste[i] = Character.getNumericValue(s.charAt(i));
		}
		
		for(int end = this.polynome.length(); end < s.length(); end++)
			if (reste[0] == 0) {
				for(int i=0; i<this.polynome.length()-1; i++) {
					reste[i] = reste[i+1];
				}
				reste[reste.length-1] = Character.getNumericValue(s.charAt(end));
			} else {
				for(int i=0; i<this.polynome.length()-1; i++) {
					reste[i] = reste[i+1] ^ diviseur[i+1];
				}
				reste[reste.length-1] = Character.getNumericValue(s.charAt(end));
			}
		
		String r = "";
		
		for(int i=0; i<reste.length; i++) {
			r += reste[i];
		}
		
		return Integer.parseInt(r,2);
	}
	
}
