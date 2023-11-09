package L1_dfa;

public class Es1_6 {
    public static boolean scan(String s)
    {
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char c = s.charAt(i++);

            switch (state) {
                case 0:
                    if(c == '*' || c == 'a')
                        state = 1;
                    else if(c == '/')
                        state = 2;
                    else
                        state = -1;
                    break;

                case 1:
                    if(c == '/')
                        state = 2;
                    else if(c == '*' || c == 'a')
                        state = 1;
                    else
                        state = -1;
                    break;

                case 2:
                    if(c == '*')
                        state = 3;
                    else if(c == '/')
                        state = 2;
                    else if(c == 'a')
                        state = 1;
                    else
                        state = -1;
                    break;

                case 3:
                    if(c == 'a' || c == '/')
                        state = 4;
                    else if(c == '*')
                        state = 5;
                    else
                        state = -1;
                    break;

                case 4:
                    if(c == 'a' || c == '/')
                        state = 4;
                    else if(c == '*')
                        state = 5;
                    else
                        state = -1;
                    break;

                case 5:
                    if(c == 'a')
                        state = 4;
                    else if(c == '/')
                        state = 6;
                    else if(c == '*')
                        state = 5;
                    else
                        state = -1;
                    break;

                case 6:
                    if(c == 'a' || c == '*')
                        state = 1;
                    else if(c == '/')
                        state = 2;
                    else if (c == 0) ;
                    else
                        state = -1;
                    break;
            }
        }
        return state == 1 || state == 6;
    }

    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.println("Usage: Es1_1 <string>");
            return;
        }
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}
