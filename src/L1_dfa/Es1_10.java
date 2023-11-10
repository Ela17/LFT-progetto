package L1_dfa;

public class Es1_10 {
    public static boolean scan(String s) {
		int state = 0;
		int i = 0;
	
		while (state != -1 && i < s.length()) {
			char c = s.charAt(i++);

            switch (state) {
                case 0:
                    if(c == 'E' || c == 'e')
                        state = 1;
                    else 
                        state = 2;
                    break;
                
                case 1:
                    if(c == 'l' || c == 'L')
                        state = 7;
                    else 
                        state = 3;
                    break;

                case 2:
                    if(c == 'l' || c == 'L')
                        state = 3;
                    else 
                        state = -1;
                    break;

                case 3:
                    if(c == 'e' || c == 'E')
                        state = 4;
                    else 
                        state = -1;
                    break;

                case 4:
                    if(c == 'n' || c == 'N')
                        state = 5;
                    else 
                        state = -1;
                    break;

                case 5:
                    if(c == 'a' || c == 'A')
                        state = 6;
                    else 
                        state = -1;
                    break;

                case 6:
                    if(c != 0)
                        state = -1;
                    break;

                case 7:
                    if(c == 'e' || c == 'E')
                        state = 8;
                    else 
                        state = 4;
                    break;

                case 8:
                    if(c == 'n' || c == 'N')
                        state = 9;
                    else 
                        state = 5;
                    break;

                case 9:
                    if(c != 0)
                        state = 6;
                    else 
                        state = -1;
                    break;
            }
        }
        return state == 6;
    }

    public static void main(String[] args)
    {
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}
