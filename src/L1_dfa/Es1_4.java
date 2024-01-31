package L1_dfa;

public class Es1_4 {
        public static boolean scan(String s) {
        int i = 0, state = 0;

        while(state != -1 && i < s.length()){
            char c = s.charAt(i++);

            switch (state) {
                case 0:
                    if(c >= '0' && c <= '9')
                        state = 1;
                    else if(c == '.')
                        state = 2;
                    else if(c == '+' || c == '-')
                        state = 3;
                    else
                        state = -1;
                    break;

                case 1:
                    if(c >= '0' && c <= '9')
                        /*no op*/;
                    else if(c == '.')
                        state = 2;
                    else if(c == 'e')
                        state = 5;
                    else
                        state = -1;
                    break;

                case 2:
                    if(c >= '0' && c <= '9')
                        state = 4;
                    else
                        state = -1;
                    break;

                case 3:
                    if(c >= '0' && c <= '9')
                        state = 1;
                    else if(c == '.')
                        state = 2;
                    else
                        state = -1;
                    break;

                case 4:
                    if(c >= '0' && c <= '9')
                        /*no op*/;
                    else if(c == 'e')
                        state = 5;
                    else
                        state = -1;
                    break;

                case 5:
                    if(c >= '0' && c <= '9')
                        state = 7;
                    else if(c == '+' || c == '-')
                        state = 6;
                    else if(c == '.')
                        state = 8;
                    else
                        state = -1;
                    break;

                case 6:
                    if(c >= '0' && c <= '9')
                        state = 7;
                    else if(c == '.')
                        state = 8;
                    else
                        state = -1;
                    break;

                case 7:
                    if(c >= '0' && c <= '9')
                        /*no op*/;
                    else if(c == '.')
                        state = 8;
                    else
                        state = -1;
                    break;

                case 8:
                    if(c >= '0' && c <= '9')
                        state = 9;
                    else
                        state = -1;
                    break;

                case 9:
                    if(c >= '0' && c <= '9')
                        /*no op*/;
                    else
                        state = -1;
                    break;
            }
        }

        return (state == 1) || (state == 4) || (state == 7) || (state == 9);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: Es1_4 <string>");
            return;
        }
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}
