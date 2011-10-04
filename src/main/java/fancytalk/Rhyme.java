package fancytalk;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.StringEncoder;

public class Rhyme extends ScoreFunction {
    private final StringEncoder enc;

    public Rhyme(StringEncoder enc) {
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
	
	int m = word.length()-1;
	int n = prev.length()-1;
	while (m>0 && n>0 && word.charAt(m)==prev.charAt(n)) {
	    m--;
	    n--;
	}
	return word.length()-m-1;
    }
}