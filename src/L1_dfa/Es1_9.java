package L1_dfa;

public class Es1_9 {
    public static boolean scan(String s) {
		int state = 0;
		int i = 0;
	
		while (state != -1 && i < s.length()) {
			char c = s.charAt(i++);

			switch (state) {
				case 0:
					if (c == 'a')
                        state = 1;
                    else if (c == 'b')
                        state = 2;
                    else
                        state = -1;
					break;

				case 1:
					if (c == 'a')
                        state = 1;
                    else if (c == 'b')
                        state = 3;
                    else
                        state = -1;
					break;

				case 2:
					if (c == 'a')
                        state = 1;
                    else if (c == 'b')
                        state = 2;
                    else
                        state = -1;
					break;

				case 3:
                    if (c == 'a')
                        state = 1;
                    else if (c == 'b')
                        state = 4;
                    else
                        state = -1;
                    break;
                    
                case 4:
					if (c == 'a')
                        state = 1;
                    else if (c == 'b')
                        state = 2;
                    else
                        state = -1;
                    break;
			}
		}
	
		return state == 1 || state == 3 || state == 4;
	}
        
	public static void main(String[] args) {
		System.out.println(scan(args[0]) ? "OK" : "NOPE");
	}
}
