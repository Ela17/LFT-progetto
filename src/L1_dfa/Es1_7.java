package L1_dfa;

public class Es1_7 {
	public static boolean scan(String s) {
		int state = 0;
		int i = 0;
	
		while (state != -1 && i < s.length()) {
			char c = s.charAt(i++);
			switch (state) {
				case 0:
					if(Character.isDigit(c)) {
						if(Character.getNumericValue(c) % 2 == 0)
							state = 1;
						else
							state = 2;
					}
					else if(c == ' ')
                        state = 0;
                    else
						state = -1;
					break;

				case 1:
					if(Character.isDigit(c)) {
						if(Character.getNumericValue(c) % 2 == 0)
							state = 1;
						else
							state = 2;
					}
                    else if (c == ' ')
                        state = 3;
					else if(Character.isLetter(c)) {
						if ((c >= 'a' && c <= 'k') || (c >= 'A' && c <= 'K'))
							state = 5;
                        else
							state = -1;
					}
                    else
						state = -1;
					break;

				case 2:
					if(Character.isDigit(c)) {
						if(Character.getNumericValue(c) % 2 == 0)
							state = 1;
						else
							state = 2;
					}
                    else if (c == ' ')
                        state = 4;
					else if(Character.isLetter(c)) {
						if ((c >= 'a' && c <= 'k') || (c >= 'A' && c <= 'K'))
							state = -1;
						else
							state = 5;
					}
                    
					else
						state = -1;
					break;

				
                case 3:
                    if ((c >= 'a' && c <= 'k') || (c >= 'A' && c <= 'K'))
                        state = 5;
					else if (c == ' ')
                        state = 3;
                    else
                        state = -1;
                    break;
                
                case 4:
                    if ((c >= 'l' && c <= 'z') || (c >= 'L' && c <= 'Z'))
                        state = 5;
					else if (c == ' ')
                        state = 4;
                    else
                        state = -1;
                    break;

                case 5:
                    if(Character.isLetter(c))
                        state = 5;
                    else if(c == ' ')
                        state = 6;
                    else
                        state = -1;
                    break;

                case 6:
                    if(Character.isLetter(c))
                        state = 5;
                    else if(c == ' ')
                        state = 7;
                    else
                        state = -1;
                    break;

                case 7:
                    if(c == ' ')
                        state = 7;
                    else
                        state = -1;
                    break;
			}
		}
	
		return state == 5 || state == 6 || state == 7;
	}
        
	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}
