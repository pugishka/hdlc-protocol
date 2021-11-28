import java.util.ArrayList;

public class Trame {

	public String polynome = "10001000000100001";
	public String flag = "01111110";
	public char type;
	public int num;
	public String data;
	public int crc;
	
	// constructeur à partir du type, num, et data
	public Trame(char type, int num, String data) {
		this.type = type;
		this.num = num;
		this.data = data;
		this.crc = calculateCRC(type, num, data);
	}
	
	// constructeur à partir du résultat du toString()
	public Trame(String trame) {
		int indexType = this.flag.length();
		int indexNum = indexType + 8;
		int indexData = indexNum + 8;
		int indexFlagEnd = trame.length()-this.flag.length();
		int indexCRC = indexFlagEnd-16;
		
		this.type = (char) Integer.parseInt(trame.substring(indexType, indexNum),2);
		this.num = Integer.parseInt(trame.substring(indexNum, indexData),2)-48;

		// TODO
		// probleme : crc parfois a une longueur de 17 bits
		// resolution temporaire : considerer d'ignorer data si longueur calculée est 1, causée par le 1er bit
		// d'un crc de longueur 17 au lieu de 16
		if (trame.substring(indexData, indexCRC).length() == 1) {
			this.data = "";
		} else {
			this.data = trame.substring(indexData, indexCRC);
		}
		//this.data = trame.substring(indexData, indexCRC);
		this.crc = Integer.parseInt(trame.substring(indexCRC, indexFlagEnd),2);
	}
	

	// concatener toutes les infos sous forme de string en binaire
	@Override
	public String toString() {
		
		// flag
		String s = this.flag;
		
		// type
		for (int i=0; i<8-Integer.toBinaryString((int) this.type).length(); i++) {
			s += "0";
		}
		s += Integer.toBinaryString((int) this.type);
		
		// num
		for (int i=0; i<8-Integer.toBinaryString((int) ((char) (this.num+'0'))).length(); i++) {
			s += "0";
		}
		s += Integer.toBinaryString((int) ((char) (this.num+'0')));
		
		// donnees
		s += this.data;
		
		// crc
		for (int i=0; i<16-Integer.toBinaryString(this.crc).length(); i++) {
			s += "0";
		}
		s += Integer.toBinaryString(this.crc);
		
		// flag
		s += this.flag;
		
		// bit stuffing
		//s = bitStuffingAdd(s);
		
		return s;
	}
	
	// ajouter le bit stuffing
	public String bitStuffingAdd(String s) {
		String t = s.substring(8,13);
		for (int i=13; i<s.length()-8; i++) {
			System.out.println(i + " : " + t + "\n");
			if (t.length() == 5) {
				if (t.equals("11111")) {
					s = s.substring(0,i) + "0" + s.substring(i,s.length());
					t = "";
				} else {
					t = t.substring(1, 5) + s.charAt(i);
				}
			} else {
				t += s.charAt(i);
			}
		}
		return s;
	}
	
	// enlever le bit stuffing
	public String bitStuffingRemove(String s) {
		String t = s.substring(8,13);
		for (int i=13; i<s.length()-8; i++) {
			System.out.println(i + " : " + t + "\n");
			if (t.length() == 5) {
				if (t.equals("11111")) {
					s = s.substring(0,i) + s.substring(i+1,s.length());
					t = "";
					i--;
				} else {
					t = t.substring(1, 5) + s.charAt(i);
				}
			} else {
				t += s.charAt(i);
			}
		}
		return s;
	}
	
	// Avoir un string propre à imprimer pour voir les infos d'une trame
	public String info() {
		String s =  "- type : \n      char : " + this.type;
		//s += "\n      int : " + (int) this.type;
		s += "\n      binary : " + Integer.toBinaryString((int) this.type) + " (length : " + Integer.toBinaryString((int) this.type).length() + ")\n";
		s +=  "- num : \n      char : " + (char) (this.num+'0');
		//s += "\n      int of char : " + this.num+'0';
		s += "\n      binary of the char : " + Integer.toBinaryString((int) ((char) (this.num+'0'))) + " (length : " + Integer.toBinaryString((int) ((char) (this.num+'0'))).length() + ")\n";
		s += "- data : " + this.data + "\n";
		s += "- crc : \n      binary : " + Integer.toBinaryString(this.crc) + " (length : " + Integer.toBinaryString(this.crc).length() + ")\n";
		s += "- trame complete : " + this.stringSeparated();
		
		return s;
	}
	
	// Avoir un string du résultat de toString() avec des séparateurs
	public String stringSeparated() {
		
		String s = this.toString();
		String r = s.substring(0, 8) + " " + s.substring(8, 16) + " " + s.substring(16, 24) + " " ;
		
		// TODO
		// probleme : crc parfois a une longueur de 17 bits
		// resolution temporaire : considerer d'ignorer data si longueur calculée est 1, causée par le 1er bit
		// d'un crc de longueur 17 au lieu de 16
		
		if (s.substring(24, s.length()-24).length() == 1) {
			r += " " ;
		} else {
			r += s.substring(24, s.length()-24) + " " ;
		}
		
		//r += s.substring(24, s.length()-24) + " " ;
		r += s.substring(s.length()-24, s.length()-8) + " " + s.substring(s.length()-8, s.length());
		
		return r;
	}
	
	// calcul CRC
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
	
	// methodes get
	public String getPolynome() {
		return this.polynome;
	}
	
	public char getType() {
		return this.type;
	}
	
	public int getNum() {
		return this.num;
	}
	
	public String getData() {
		return this.data;
	}
	
	public int getCrc() {
		return this.crc;
	}
	
}
