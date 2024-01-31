package L1_dfa;

public class Es1_5 {
    public static boolean scan(String s)
    {
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char c = s.charAt(i++);

            switch (state) {
                case 0:
                    if(c == '/')
                        state = 1;
                    else
                        state = -1;
                    break;

                case 1:
                    if(c == '*')
                        state = 2;
                    else
                        state = -1;
                    break;

                case 2:
                    if(c == 'a' || c == '/') /*no op*/;
                    else if(c == '*')
                        state = 3;
                    else
                        state = -1;
                    break;

                case 3:
                    if(c == 'a')
                        state = 2;
                    else if(c == '*') /*no op*/;
                    else if(c == '/')
                        state = 4;
                    else
                        state = -1;
                    break;

                case 4:
                    /* la stringa deve terminare !! */
                    state = -1;
                    break;
            }
        }
        return state == 5;
    }

    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.println("Usage: Es1_5 <string>");
            return;
        }
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}
