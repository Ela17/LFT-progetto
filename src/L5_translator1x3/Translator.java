package L5_translator1x3;

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
                assignlist();
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
                int lfor_start = code.newLabel();
                int lfor_end = code.newLabel();
                code.emitLabel(lfor_start);
                match(Tag.FOR);
                match('(');
                statp(lfor_end);
                match(')');
                match(Tag.DO);
                stat();
                code.emit(OpCode.GOto, lfor_start);
                code.emitLabel(lfor_end);
                break;
            case Tag.IF:
                match(Tag.IF);
                match('(');
                int lif_false = code.newLabel();
                bexpr(lif_false);
                match(')');
                stat();
                code.emitLabel(lif_false);
                stats();
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

    public void statp(int label) {
        switch (look.tag) {
            case Tag.ID:
                int tmp_addr = st.lookupAddress(((Word)look).lexeme);
                if (tmp_addr == -1) {
                    tmp_addr = count;
                    st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                match(Tag.INIT);
                expr(tmp_addr);
                match(';');
                bexpr(label);
                break;
            case Tag.RELOP:
                bexpr(label);
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
                match(Tag.END);
                break;

            default:
                error("found " + look + " in stats");
        }
    }

    public void assignlist() {
        switch (look.tag) {
            case '[':
            
                match('[');
                expr(-1);
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
                expr(-1);
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
                else if(!isAssign)
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

                if(isAssign)
                    code.emit(OpCode.dup);
                else if(!isAssign)                              // read
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

    public void bexpr(int label) {
        switch (look.tag) {
            case Tag.RELOP:
                String tmp = ((Word)look).lexeme;
                match(Tag.RELOP);
                expr(-1);
                expr(-1);
                switch(tmp) {
					case "<":
						code.emit(OpCode.if_icmpge, label);
						break;
					case ">":
						code.emit(OpCode.if_icmple, label);
						break;
					case "<=":
						code.emit(OpCode.if_icmpgt, label);
						break;
					case ">=":
						code.emit(OpCode.if_icmplt, label);
						break;
					case "==":
						code.emit(OpCode.if_icmpne, label);
						break;
					case "<>":
						code.emit(OpCode.if_icmpeq, label);
						break;
					default:
						break;
				}
                break;
            default:
                error("found " + look + " in bexpr");
        }
    }

    private void expr(int id_addr) {
        switch(look.tag) {
            case '+':
                match('+');
                match('(');
                exprlist(_exprEnum.sum);
                match(')');
                break;
            case '-':
                match('-');
                expr(id_addr);
                expr(id_addr);
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
                expr(id_addr);
                expr(id_addr);
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
                if(id_addr != -1)
                    code.emit(OpCode.istore, id_addr); 
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
                expr(-1);
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
                expr(-1);
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

