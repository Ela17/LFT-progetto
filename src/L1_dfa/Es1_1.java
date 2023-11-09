package L1_dfa;
public class Es1_1
{
    public static boolean scan(String s)
    {
        int state = 0;
        int i = 0;

        while (state >= 0 && i < s.length()) {
            final char ch = s.charAt(i++);

            switch (state) {
                case 0:
                    if (ch == '0')
                        state = 2;
                    else if (ch == '1')
                        state = 1;
                    else
                        state = -1;
                    break;

                case 1:
                    if (ch == '0')
                        state = 2;
                    else if (ch == '1')
                        state = 1;
                    else
                        state = -1;
                    break;

                case 2:
                    if (ch == '0')
                        state = 3;
                    else if (ch == '1')
                        state = 1;
                    else
                        state = -1;
                    break;

                case 3:
                    if (ch == '0')
                        state = 4;
                    else if (ch == '1')
                        state = 1;
                    else
                        state = -1;
                    break;

                case 4:
                    if (ch == '0' || ch == '1')
                        state = 4;
                    else
                        state = -1;
                    break;
            }
        }
        return (state == 1) || (state == 2) || (state == 3);
    }

    public static void main(String[] args)
    {
        if (args.length < 1) {
            System.out.println("Usage: Es1_1 <string>");
            return;
        }
        String s = args[0];
        System.out.println(scan(s) ? "OK" : "NOPE");
    }
}