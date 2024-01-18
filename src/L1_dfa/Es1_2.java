package L1_dfa;

public class Es1_2 {
	public static boolean scan(String s){
		int state = 0;
		int i = 0;
        
		while (state != -1 && i < s.length()) {
			char c = s.charAt(i++);
			switch (state) {
				case 0:
					if (Character.isDigit(c))
							state = 3;
					else if (Character.isLetter(c))
							state = 1;
					else if (c == '_')
							state = 2;
					else state = -1;
					break;
	
				case 1:
					if(Character.isDigit(c) || Character.isLetter(c) || c == '_')
							state = 1;
					else state = -1;
					break;
	
				case 2:
					if (Character.isDigit(c) || Character.isLetter(c))
							state = 1;
					else if (c == '_')
							state = 2;
					else state = -1;
					break;
	
				case 3:
					if(Character.isDigit(c) || Character.isLetter(c) || c == '_')
							state = 3;
					else state = -1;
					break;
			}
		}
		return state == 1;
	}
        
	public static void main(String[] args){

	if (args.length < 1) {
			System.out.println("Usage: Es1_2 <string>");
			return;
	}
	System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}
