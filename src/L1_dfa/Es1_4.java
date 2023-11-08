/**
 * Progettare e implementare un DFA che riconosca il linguaggio delle costanti numeriche in virgola mobile
 * utilizzando la notazione scientifica dove il simbolo e indica la funzione esponenziale con base 10.
 * L’alfabeto del DFA contiene i seguenti elementi: le cifre numeriche 0, 1, . . . , 9,
 * il segno . (punto) che precede una eventuale parte decimale, i segni + (piu) e - (meno)
 * per indicare positivita o negativita, e il simbolo 'e'.
 * Le stringhe accettate devono seguire le solite regole per la scrittura delle costanti numeriche.
 * In particolare, una costante numerica consiste di due segmenti, il secondo dei quali e opzionale:
 * il primo segmento e una sequenza di cifre numeriche che (1) puo essere preceduta da un segno
 * + o meno -, (2) puo essere seguita da un segno punto ., che a sua volta deve essere seguito da
 * una sequenza non vuota di cifre numeriche; il secondo segmento inizia con il simbolo e, che a
 * sua volta e seguito da una sequenza di cifre numeriche che soddisfa i punti (1) e (2) scritti per il
 * primo segmento. Si nota che, sia nel primo segmento, sia in un eventuale secondo segmento, un
 * segno punto . non deve essere preceduto per forza da una cifra numerica.
 */
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
                        state = 1;
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
                        state = 4;
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
                        state = 7;
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
                        state = 9;
                    else
                        state = -1;
                    break;
            }
        }

        return (state == 1) || (state == 4) || (state == 7) || (state == 9);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: Es1_1 <string>");
            return;
        }
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}
