

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
	

	@Override
	public String toString() {
		String s = this.flag;
		s += Integer.toBinaryString((int) this.type);
		s += Integer.toBinaryString((int) ((char) (this.num+'0')));
		s += this.data;
		s += Integer.toString(this.crc);
		s += this.flag;
		
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
