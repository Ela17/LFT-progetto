package L4_evaluator;

import java.io.*; 

import L2_lexer.*;

public class Evaluator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Evaluator(Lexer l, BufferedReader br) { 
        lex = l; 
        pbr = br;
        move(); 
    }
   
    void move() { 
	    look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) { 
	    throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
	    if (look.tag == t) {
            if (look.tag != Tag.EOF) move();
        } else error("syntax error");
    }

    public void start() { 
        int expr_val;

        switch (look.tag) {
            case '(':
            case Tag.NUM:
                expr_val = expr();
                match(Tag.EOF);
                System.out.println(expr_val);
                break;
            default:
                error("found " + look + " in start with guide {(, NUM}");
        }
    }

    private int expr() { 
        int term_val, exprp_val = 0;

        switch (look.tag) {
            case '(':
            case Tag.NUM:
                term_val = term();
                exprp_val = exprp(term_val);
                break;
            default:
                error("found " + look + " in expr with guide {(, NUM}");
        }
        return exprp_val;
    }

    private int exprp(int exprp_i) {
        int term_val, exprp_val = 0;

        switch (look.tag) {
            case '+':
                match('+');
                term_val = term();
                exprp_val = exprp(exprp_i + term_val);
                break;
            case '-':
                match('-');
                term_val = term();
                exprp_val = exprp(exprp_i - term_val);
                break;
            case ')':
            case Tag.EOF:
                exprp_val = exprp_i;
                break;
            default:
                error("found " + look + " in exprp with guide {+, -, ), EOF}");
        }

        return exprp_val;
    }

    private int term() { 
	    int term_val = 0, termp_val;

        switch (look.tag) {
            case '(':
            case Tag.NUM:
                termp_val = fact();
                term_val = termp(termp_val);
                break;
            default:
                error("found " + look + " in term with guide {(, NUM}");
        }

        return term_val;
    }
    
    private int termp(int termp_i) { 
        int fact_val, termp_val = 0;

        switch (look.tag) {
            case '*':
                match('*');
                fact_val = fact();
                termp_val = termp(termp_i * fact_val);
                break;
            case '/':
                match('/');
                fact_val = fact();
                termp_val = termp(termp_i / fact_val);
                break;
            case '+':
            case '-':
            case ')':
            case Tag.EOF:
                termp_val = termp_i;
                break;
            default:
                error("found " + look + " in termp with guide {*, /, +, -, ), EOF}");
        }

        return termp_val;
    }
    
    private int fact() { 
        int fact_val = 0;
	    switch (look.tag) {
            case '(':
                match('(');
                fact_val = expr();
                match(')');
                break;
            case Tag.NUM:
                fact_val = ((NumberTok)look).getValue();
                match(Tag.NUM);
                break;
            default:
                error("found " + look + " in fact with guide {(, NUM}");
        }

        return fact_val;
    }

    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "input.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Evaluator valutatore = new Evaluator(lex, br);
            valutatore.start();
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
