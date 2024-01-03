package L3_parser2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import L2_lexer.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
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

    public void prog() {
        switch (look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.FOR:
            case Tag.IF:
            case '{':
                statlist();
                match(Tag.EOF);
                break;
        
            default:
                error("found " + look + " in prog with guide { ASSIGN, PRINT, READ, FOR, IF, { }");
        }
    }

    public void statlist() {
        switch (look.tag) {
            case Tag.ASSIGN:
            case Tag.PRINT:
            case Tag.READ:
            case Tag.FOR:
            case Tag.IF:
            case '{':
                stat();
                statlistp();
                break;
        
            default:
                error("found " + look + " in statlist with guide { ASSIGN, PRINT, READ, FOR, IF, { }");
                break;
        }
    }

    public void statlistp() {
        switch (look.tag) {
            case ';':
                match(';');
                stat();
                statlistp();
                break;
            case Tag.EOF:
            case '}':
                break;
        
            default:
                error("found " + look + " in statlistp with guide { ;, EOF, } }");
                break;
        }
    }

    public void stat() {
        switch (look.tag) {
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                assignlist();
                break;
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist();
                match(')');
                break;
            case Tag.READ:
                match(Tag.READ);
                match('(');
                idlist();
                match(')');
                break;
            case Tag.FOR:
                match(Tag.FOR);
                match('(');
                statp();
                match(')');
                match(Tag.DO);
                stat();
                break;
            case Tag.IF:
                match(Tag.IF);
                match('(');
                bexpr();
                match(')');
                stat();
                stats();
                break;
            case '{':
                match('{');
                statlist();
                match('}');
                break;        

            default:
                error("found " + look + " in stat with guide { ASSIGN, PRINT, READ, FOR, IF, { }");
                break;
        }
    }

    public void statp() {
        switch (look.tag) {
            case Tag.ID:
                match(Tag.ID);
                match(Tag.INIT);
                expr();
                match(';');
                bexpr();
                break;
            case Tag.RELOP:
                match(Tag.RELOP);
                bexpr();
                break;

            default:
                error("found " + look + " in statp with guide { ID, RELOP }");
                break;
        }
    }

     public void stats() {
        switch (look.tag) {
            case Tag.ELSE:
                match(Tag.ELSE);
                stat();
                match(Tag.END);
                break;
            case Tag.END:
                match(Tag.END);
                break;

            default:
                error("found " + look + " in stats with guide { ELSE, END }");
                break;
        }
    }

    public void assignlist() {
        switch (look.tag) {
            case '[':
                match('[');
                expr();
                match(Tag.TO);
                idlist();
                match(']');
                assignlistp();
                break;

            default:
                error("found " + look + " in assignlist with guide { [ }");
                break;
        }
    }
    public void assignlistp() {
        switch (look.tag) {
            case '[':
                match('[');
                expr();
                match(Tag.TO);
                idlist();
                match(']');
                assignlistp();
                break;
            case ';':
            case '}':
            case Tag.ELSE:
            case Tag.END:
            case Tag.EOF:
                break;

            default:
                error("found " + look + " in assignlistp with guide { [, ;, }, ELSE, END, EOF }");
                break;
        }
    }

    public void idlist() {
        switch (look.tag) {
            case Tag.ID:
                match(Tag.ID);
                idlistp();
                break;

            default:
                error("found " + look + " in idlist with guide { ID }");
                break;
        }
    }

    public void idlistp() {
        switch (look.tag) {
            case ',':
                match(',');
                match(Tag.ID);
                idlistp();
                break;
            case ')':
            case ']':
                break;
            default:
                error("found " + look + " in idlistp with guide { ,, ), ] }");
                break;
        }
    }

    public void bexpr() {
        switch (look.tag) {
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
                break;
            default:
                error("found " + look + " in bexpr with guide { RELOP }");
                break;
        }
    }

    public void expr() {
        switch (look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist();
                match(')');
                break;
            case '-':
                match('-');
                expr();
                expr();
                break;
            case '*':
                match('*');
                match('(');
                exprlist();
                match(')');
                break;
            case '/':
                match('/');
                expr();
                expr();
                break;
            case Tag.NUM:
                match(Tag.NUM);
                break;  
            case Tag.ID:
                match(Tag.ID);
                break;

            default:
                error("found " + look + " in expr with guide { +, -, *, /, NUM, ID }");
                break;
        }
    }

    public void exprlist() {
        switch (look.tag) {
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                exprlistp();
                break;

            default:
                error("found " + look + " in exprlist with guide { +, -, *, /, NUM, ID }");
                break;
        }
    }

    public void exprlistp() {
        switch (look.tag) {
            case ',':
                match(',');
                expr();
                exprlistp();
                break;
            case ')':
                break;

            default:
                error("found " + look + " in exprlistp with guide { ,, ) }");
                break;
        }
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "input.lft"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
