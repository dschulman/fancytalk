package fancytalk;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Alliterate extends ScoreFunction {
    private final StringEncoder enc;

    public Alliterate(StringEncoder enc) {
	this.enc = enc;
    }

    public double score(String word, String prev) {
	if (prev==null)
	    return 0;

	try {
	    word = enc.encode(word);
	    prev = enc.encode(prev);
	} catch (EncoderException e) {
	    return 0;
	}
	
	int n=0;
	while (n<word.length() && n<prev.length() &&
	       word.charAt(n)==prev.charAt(n)) 
	    n++;
	return n;
    }
}