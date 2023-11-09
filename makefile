# Variabili
JC = javac
JFLAGS = -d bin -cp src/
JCOMPILE = $(JC) $(JFLAGS)
ARGS = && touch bin/input.lft

# Compila ed esegue il programma
lexer: 
	$(JCOMPILE) src/L2_lexer/Lexer.java $(ARGS)
	
parser1:
	$(JCOMPILE) src/L3_parser1/Parser.java $(ARGS)

# Cancella tutti i file .class nella directory build
clean:
	rm -r bin/