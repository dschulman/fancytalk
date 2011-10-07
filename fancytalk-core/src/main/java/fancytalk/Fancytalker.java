package fancytalk;

import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.dictionary.Dictionary;
import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.codec.language.Soundex;

public class Fancytalker {
    private final Analyze analyze;
    private final Fancify fancify;
    private final Reassemble reassemble;

    public Fancytalker() throws Exception {
	String opennlp = System.getProperty("user.home") + "/opennlp/";
	analyze = new Analyze(
	    opennlp + "en-sent.bin",
	    opennlp + "en-token.bin",
	    opennlp + "en-pos-maxent.bin");
	JWNL.initialize(
	    getClass().getResourceAsStream(
		"jwnl_properties.xml"));
	fancify = new Fancify(
	    Dictionary.getInstance(),
	    ScoreFunction.sum(
		new Embiggen(),
		new Alliterate(Soundex.US_ENGLISH).scaled(5),
		new Rhyme(new Metaphone())));
	reassemble = new Reassemble(
	    getClass().getResourceAsStream(
		"latin-detokenizer.xml"));
    }

    public String apply(String input) throws JWNLException {
	return reassemble.applyAll(
	    fancify.applyAllAll(
		analyze.apply(input)));
    }
}