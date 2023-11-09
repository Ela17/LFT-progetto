// Purpose: Lexer for the language Es2_1.java
package Lab2_v1;
import java.io.*;

public class Lexer {

    public static int line = 1;
    private char peek = ' ';
    public static final char EOF = (char) -1;

    
    private void readch(BufferedReader br) {
        try {
            peek = (char) br.read();
        } catch (IOException exc) {
            peek = (char) -1; // ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) {
        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {
            if (peek == '\n') line++;
            readch(br);
        }

        switch (peek) {
            case '!':
                peek = ' ';
                return Token.not;

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                peek = ' ';
                return Token.rpt;

            case '[':
                peek = ' ';
                return Token.lpq;

            case ']':
                peek = ' ';
                return Token.rpq;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+' :
                peek = ' ';
                return Token.plus;

            case '-' :
                peek = ' ';
                return Token.minus;

            case '*' :
                peek = ' ';
                return Token.mult;

            case '/' :
                peek = ' ';
                return Token.div;

            case ';' :
                peek = ' ';
                return Token.semicolon;

            case ',' :
                peek = ' ';
                return Token.comma;

            case ':' :
                readch(br);
                if(peek == '=') {
                    peek = ' ';
                    return Word.init;
                } else {
                    System.err.println("\nErroneous character after : : "  + peek );
                    return null;
                }

            case EOF :
                return new Token(Tag.EOF);

            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character"
                            + " after & : "  + peek );
                            return null;
                }

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("\nErroneous character after | : "  + peek );
                    return null;
                }

            case '=':
                readch(br);
                if (peek == '=') {
                    peek = ' ';
                    return Word.eq;
                } else {
                    System.err.println("\nErroneous character after = : "  + peek );
                    return null;
                }

            case '<':
                readch(br);
                if (peek == ' ') {
                    return Word.lt;
                } else if (peek == '>') {
                    peek = ' ';
                    return Word.ne;
                } else if(peek == '=') {
                    peek = ' ';
                    return Word.le;
                }

            case '>':
                readch(br);
                if (peek == ' ') {
                    return Word.gt;
                } else if(peek == '=') {
                    peek = ' ';
                    return Word.ge;
                } else {
                    System.err.println("\nErroneous character after > : "  + peek );
                    return null;
                }

            default:
                if (Character.isLetter(peek)) {
                    String s = "";

                    do {
                        s += "" + peek;
                        readch(br);
                    } while (Character.isLetterOrDigit(peek));

                    switch (s) {
                        case "assign" : return Word.assign;
                        case "to" : return Word.to;
                        case "if" : return Word.iftok;
                        case "else" : return Word.elsetok;
                        case "do" : return Word.dotok;
                        case "for" : return Word.fortok;
                        case "begin" : return Word.begin;
                        case "end" : return Word.end;
                        case "print" : return Word.print;
                        case "read" : return Word.read;
                        default : return new Word(Tag.ID, s);
                    }
                } else if (Character.isDigit(peek)) {
                    int val = peek - '0';   // to int
                    readch(br);

                    // only zero can start in '0'
                    if (val == 0 && Character.isDigit(peek))
                        throw new Error("\nErroneous character after 0 : " + peek);

                    while (Character.isDigit(peek)) {
                        val = val * 10 + peek - '0';
                        readch(br);
                    }
                    return new NumberTok(val);

                } else {
                    System.err.println("Erroneous character: " + peek );
                    return null;
                }
        }
        
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "Test.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}