
public class Trame {
	public String flag = "01111110";
	public char type;
	public int num;
	public String data;
	public int crc;
	
	public Trame(char type, int num, String data) {
		this.type = type;
		this.num = num;
		this.data = data;
	}
	
	public String trameToStr() {

		String s = "";
		s += this.flag;
		
		switch (this.type) {
			case 'I' :
				s += "0" + this.num + "0" ;
			    break;
				
			case 'C' :
				break;
				
			case 'A' :
				break;
				
			case 'R' :
				break;
				
			case 'F' :
				break;
				
			case 'P' :
				break;
		
		
		}
		s += this.type;
		
		return s;
		
	}
	
}
