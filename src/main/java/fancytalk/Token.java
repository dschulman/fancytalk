package fancytalk;

public class Token {
    public final String token;
    public final String tag;

    public Token(String token, String tag) {
	this.token = token;
	this.tag = tag;
    }

    @Override public boolean equals(Object o) {
	if (!(o instanceof Token))
	    return false;
	Token t = (Token) o;
	return token.equals(t.token) && tag.equals(t.tag);
    }

    @Override public int hashCode() {
	return token.hashCode()*31 + tag.hashCode();
    }

    @Override public String toString() {
	return "(" + token + ":" + tag + ")";
    }
}