/**
 * Progettare e implementare un DFA che riconosca il linguaggio degli identificatori
 * in un linguaggio in stile Java: un identificatore e una sequenza non vuota di lettere,
 * numeri, ed il simbolo di “underscore” _ che non comincia con un numero e che non puo
 * essere composto solo dal simbolo _. Compilare e testare il suo funzionamento su un
 * insieme significativo di esempi.
 * Esempi di stringhe accettate: “x”, “flag1”, “x2y2”, “x 1”, “lft lab”, “ temp”, “x 1 y 2”,
 * “x ”, “ 5”
 * Esempi di stringhe non accettate: “5”, “221B”, “123”, “9 to 5”, “ ”
 */
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
            System.out.println("Usage: Es1_1 <string>");
            return;
        }
        System.out.println(scan(args[0]) ? "OK" : "NOPE");
    }
}
