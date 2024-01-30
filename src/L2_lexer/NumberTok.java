package L2_lexer;

public class NumberTok extends Token{
	public int value;
	
	// Constructor
	public NumberTok(int value) {
		super(Tag.NUM);
		this.value = value;
	}
	
	public String toString() {
		return "<" + tag + ", " + value + ">";
	}

	public int getValue() {
		return value;
	}
}
