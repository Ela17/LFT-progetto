# Variabili
JC = javac
JFLAGS = -d bin -cp src:bin
JCOMPILE = $(JC) $(JFLAGS)
ARGS = input.lft

compile_all:
	$(JCOMPILE) src/L2_lexer/*.java
	$(JCOMPILE) src/L3_parser/*.java

# Esegue il programma
lexer: compile_all
	java -cp bin src.L2_lexer.Lexer $(ARGS)
	
parser1: compile_all
	java -cp bin src.L3_parser.Parser $(ARGS)

# Cancella tutti i file .class nella directory build
clean:
	rm -r bin/