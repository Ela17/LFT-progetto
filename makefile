# Variabili
JC = javac
JFLAGS = -d bin -cp src/
JCOMPILE = $(JC) $(JFLAGS)
ARGS = && touch bin/input.lft

all: lexer parser1 parser2 evaluator translator1 translator2

# Compila ed esegue il programma
lexer: 
	$(JCOMPILE) src/L2_lexer/Lexer.java $(ARGS)
	
parser1:
	$(JCOMPILE) src/L3_parser1/Parser.java $(ARGS)

parser2:
	$(JCOMPILE) src/L3_parser2/Parser.java $(ARGS)

evaluator:
	$(JCOMPILE) src/L4_evaluator/Evaluator.java $(ARGS)

translator1:
	$(JCOMPILE) src/L5_translator1/*.java $(ARGS)

translator2:
	$(JCOMPILE) src/L5_translator2/*.java $(ARGS)

# Cancella tutti i file .class nella directory build
clean:
	rm -r bin/