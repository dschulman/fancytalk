package fancytalk;

import java.util.ArrayList;
import java.util.List;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;

public class Embiggen {
    private final Dictionary dictionary;

    public Embiggen(Dictionary dictionary) {
	this.dictionary = dictionary;
    }

    public List<List<String>> applyAllAll(List<List<Token>> in) 
	throws JWNLException {
	List<List<String>> out = new ArrayList<List<String>> (in.size());
	for (List<Token> i : in)
	    out.add(applyAll(i));
	return out;
    }

    public List<String> applyAll(List<Token> in) 
	throws JWNLException {
	List<String> out = new ArrayList<String> (in.size());
	for (Token i : in)
	    out.add(apply(i));
	return out;
    }

    public String apply(Token in) throws JWNLException {
	POS pos = asPOS(in.tag);
	if (pos == null)
	    return in.token;

	IndexWord iw = dictionary.lookupIndexWord(pos, in.token);
	if (iw == null)
	    return in.token;

	String biggest = in.token;
	for (Synset syn: iw.getSenses())
	    for (Word word: syn.getWords()) {
		String lemma = word.getLemma();
		if (lemma.length() > biggest.length())
		    biggest = lemma;
	    }

	return biggest;
    }

    private POS asPOS(String tag) {
	if (tag.startsWith("JJ"))
	    return POS.ADJECTIVE;
	else if (tag.startsWith("RB"))
	    return POS.ADVERB;
	else if (tag.startsWith("NN"))
	    return POS.NOUN;
	else if (tag.startsWith("VB"))
	    return POS.VERB;
	else
	    return null;
    }
}