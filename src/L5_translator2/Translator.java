package L5_translator2;

import java.io.*;
import L2_lexer.*;
import L5_support.*;

enum _exprEnum {
	sum, mul, print
};

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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
                try {
                    code.toJasmin();
                } catch (java.io.IOException e) {
                    System.out.println("IO error\n");
                }
                break;
            default:
                error("found " + look + " in prog");

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
                error("found " + look + " in statlist");
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
                error("found " + look + " in statlistp");
        }
    }

    public void stat() {
        switch(look.tag) {
            case Tag.ASSIGN:
                match(Tag.ASSIGN);
                assignlist();   // [expr TO idlist]...
                
                break;
            case Tag.PRINT:
                match(Tag.PRINT);
                match('(');
                exprlist(_exprEnum.print);
                match(')');
                break;
            case Tag.READ:
                match(Tag.READ);
                match('(');
	            idlist(false);
                match(')');
                break;
            case Tag.FOR:
                int condition_true = code.newLabel();
                int condition_false = code.newLabel();
                match(Tag.FOR);
                match('(');
                statp();    //  initialize variable in for-loop
                code.emitLabel(condition_true);
                bexpr(condition_true, condition_false, true);
                match(')');
                match(Tag.DO);
                stat();
                code.emit(OpCode.GOto, condition_true);
                code.emitLabel(condition_false);
                break;
            case Tag.IF:
                match(Tag.IF);
                match('(');
                int lif_false = code.newLabel();
                int lif_true = code.newLabel();
                int lif_end = code.newLabel();
                bexpr(lif_true, lif_false, false);
                match(')');
                code.emitLabel(lif_true);
                stat();
                code.emit(OpCode.GOto, lif_end);   // se eseguo stat salto stats
                code.emitLabel(lif_false);
                stats();    
                code.emitLabel(lif_end);
                match(Tag.END);
                break;
            case '{':
                match('{');
                statlist();
                match('}');
                break;        

            default:
                error("found " + look + " in stat");
        }
    }

    public void statp() {
        switch (look.tag) {
            case Tag.ID:
                int tmp_addr = st.lookupAddress(((Word)look).lexeme);
                if (tmp_addr == -1) {
                    tmp_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                match(Tag.INIT);
                expr();
                code.emit(OpCode.istore, tmp_addr);
                match(';');
                break;
            case Tag.RELOP:
                break;

            default:
                error("found " + look + " in statp");
        }
    }

     public void stats() {
        switch (look.tag) {
            case Tag.ELSE:
                match(Tag.ELSE);
                stat();
            case Tag.END:
                break;

            default:
                error("found " + look + " in stats");
        }
    }

    public void assignlist() {
        switch (look.tag) {
            case '[':
                match('[');
                expr();
                match(Tag.TO);
                idlist(true);
                match(']');
                assignlistp();
                break;

            default:
                error("found " + look + " in assignlist");
        }
    }

    public void assignlistp() {
        switch (look.tag) {
            case '[':
                match('[');
                expr();
                match(Tag.TO);
                idlist(true);
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
                error("found " + look + " in assignlistp");
        }
    }

    private void idlist(boolean isAssign) {
        switch(look.tag) {
	    case Tag.ID:
        	int id_addr = st.lookupAddress(((Word)look).lexeme);
                if (id_addr == -1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                if(isAssign && look.tag == ',') 
                    code.emit(OpCode.dup);
                else if(!isAssign)      // reading
                    code.emit(OpCode.invokestatic, 0);
                code.emit(OpCode.istore, id_addr);
                idlistp(isAssign);
                break;
                
                default:
                error("found " + look + " in idlist");

    	}
    }

    public void idlistp(boolean isAssign) {
        switch (look.tag) {
            case ',':
                match(',');
                int id_addr = st.lookupAddress(((Word)look).lexeme);
                if(id_addr == -1) {
                    id_addr = count;
                    st.insert(((Word)look).lexeme, count++);
                }
                match(Tag.ID);

                if(isAssign && look.tag == ',')
                    code.emit(OpCode.dup);
                else if(!isAssign)         // reading
                    code.emit(OpCode.invokestatic, 0);
                code.emit(OpCode.istore, id_addr);

                idlistp(isAssign);
                break;
            case ')':
            case ']':
                break;
            default:
                error("found " + look + " in idlistp");
        }
    }

    public void bexpr(int label_true, int label_false, boolean isFor) {
        switch (look.tag) {
            case Tag.RELOP:
                String tmp = ((Word)look).lexeme;
                match(Tag.RELOP);
                expr();
                expr();
                switch(tmp) {
					case "<":
						code.emit(OpCode.if_icmpge, label_false);
						break;
					case ">":
						code.emit(OpCode.if_icmple, label_false);
						break;
					case "<=":
						code.emit(OpCode.if_icmpgt, label_false);
						break;
					case ">=":
						code.emit(OpCode.if_icmplt, label_false);
						break;
					case "==":
						code.emit(OpCode.if_icmpne, label_false);
						break;
					case "<>":
						code.emit(OpCode.if_icmpeq, label_false);
						break;
					default:
						break;
				}
                if(!isFor)
                    code.emit(OpCode.GOto, label_true);
                break;
            case Tag.OR:
                match(Tag.OR);
                int orContinue = code.newLabel();
                bexpr(label_true, orContinue, false);
                code.emitLabel(orContinue);
                bexpr(label_true, label_false, false);
                break;
            case Tag.AND:
                match(Tag.AND);
                int andContinue = code.newLabel();
                bexpr(andContinue, label_false, false);
                code.emitLabel(andContinue);
                bexpr(label_true, label_false, false);
                break;
            case '!':
                match('!');
                bexpr(label_false, label_true, false);
                break;
            default:
                error("found " + look + " in bexpr");
        }
    }

    private void expr() {
        switch(look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist(_exprEnum.sum);
                match(')');
                break;
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
            case '*':
                match('*');
                match('(');
                exprlist(_exprEnum.mul);
                match(')');
                break;
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
                break;
            case Tag.NUM:
                code.emit(OpCode.ldc, ((NumberTok)look).value);
                match(Tag.NUM);
                break;  
            case Tag.ID:
                int addr = st.lookupAddress(((Word)look).lexeme);
                if(addr == -1) 
                    error("Variable " + ((Word)look).lexeme + " not initialized.");
                code.emit(OpCode.iload, addr);
                match(Tag.ID);
                break;

            default:
                error("found " + look + " in expr");            
        }
    }

    public void exprlist(_exprEnum type) {
        switch (look.tag) {
            case '+':
            case '-':
            case '*':
            case '/':
            case Tag.NUM:
            case Tag.ID:
                expr();
                if (type == _exprEnum.print) 
                    code.emit(OpCode.invokestatic, 1);
                exprlistp(type);
                break;

            default:
                error("found " + look + " in exprlist");
        }
    }

    public void exprlistp(_exprEnum type) {
        switch (look.tag) {
            case ',':
                match(',');
                expr();
                switch (type) {
                    case sum:
                        code.emit(OpCode.iadd);
                        break;
                    case mul:
                        code.emit(OpCode.imul);
                        break;
                    case print:
                        code.emit(OpCode.invokestatic, 1);
                        break;
                }
                exprlistp(type);
                break;
            case ')':
                break;

            default:
                error("found " + look + " in exprlistp");
        }
    }

    public static void main(String[] args) throws IOException {
		Lexer lex = new Lexer();
		String path = "input.lft"; // il percorso del file da leggere

		BufferedReader br = new BufferedReader(new FileReader(path));
		Translator translator = new Translator(lex, br);
		translator.prog();
		System.out.println("Input OK");
		br.close();
	}
}

