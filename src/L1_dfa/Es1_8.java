package L1_dfa;

public class Es1_8 {
    public static boolean scan(String s) {
		int state = 0;
		int i = 0;
	
		while (state != -1 && i < s.length()) {
			char c = s.charAt(i++);

			switch (state) {
				case 0:
					if(Character.isLetter(c)) {
						if((c >= 'a' && c <= 'k') || (c >= 'A' && c <= 'K'))
							state = 1;
						else
							state = 2;
					}
					else
						state = -1;
					break;

				case 1:
					if(Character.isDigit(c)) {
                        if(Character.getNumericValue(c) % 2 == 0)
                            state = 4;
                        else
                            state = 3;
                    }
					else if(Character.isLetter(c))
                        state = 1;
					else
						state = -1;
					break;

				case 2:
					if(Character.isDigit(c)) {
                        if(Character.getNumericValue(c) % 2 == 0)
                            state = 5;
                        else
                            state = 6;
                    }
					else if(Character.isLetter(c))
                        state = 2;
					else
						state = -1;
					break;

				case 3:
                case 4:
					if(Character.isDigit(c)) {
                        if(Character.getNumericValue(c) % 2 == 0)
                            state = 4;
                        else
                            state = 3;
                    }
					else
						state = -1;
                    break;

                case 5:
                case 6:
					if(Character.isDigit(c)) {
                        if(Character.getNumericValue(c) % 2 == 0)
                            state = 5;
                        else
                            state = 6;
                    }
					else
						state = -1;
                    break;
			}
		}
	
		return state == 4 || state == 6;
	}
        
	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}
