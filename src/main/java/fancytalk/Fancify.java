package fancytalk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.extjwnl.JWNLException;
import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.Synset;
import net.sf.extjwnl.data.Word;
import net.sf.extjwnl.dictionary.Dictionary;
import simplenlg.features.Feature;
import simplenlg.features.NumberAgreement;
import simplenlg.features.Person;
import simplenlg.features.Tense;
import simplenlg.framework.InflectedWordElement;
import simplenlg.framework.LexicalCategory;
import simplenlg.morphology.english.MorphologyProcessor;

public class Fancify {
    private final Dictionary dictionary;
    private final MorphologyProcessor morphologizer;
    private final ScoreFunction f;

    public Fancify(Dictionary dictionary, ScoreFunction f) {
	this.dictionary = dictionary;
	this.morphologizer = new MorphologyProcessor();
	this.f = f;
    }

    public List<List<String>> applyAllAll(List<List<Token>> in) 
	throws JWNLException {
	List<List<String>> out = new ArrayList<List<String>> (in.size());
	for (List<Token> i : in)
	    out.add(applyAll(i));
	return out;
    }

    public List<String> applyAll(List<Token> in) throws JWNLException {
	ScoredSequence seq = new ScoredSequence();
	for (Token i : in)
	    seq.add(getSynonyms(i), f);
	return seq.best();
    }

    public List<String> getSynonyms(Token in) throws JWNLException {
	POS pos = asPOS(in.tag);
	if (pos == null)
	    return Collections.singletonList(in.token);

	IndexWord iw = dictionary.lookupIndexWord(pos, in.token);
	if (iw == null)
	    return Collections.singletonList(in.token);

	List<String> synonyms = new ArrayList<String>();
	for (Synset syn: iw.getSenses())
	    for (Word word: syn.getWords()) {
		String lemma = word.getLemma();
		if (!lemma.contains(" "))
		    synonyms.add(doMorphology(lemma, in.tag));
	    }
	return synonyms;
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

    private String doMorphology(String base, String tag) {
	LexicalCategory cat = asLexicalCategory(tag);
	if (cat==null)
	    return base;

	InflectedWordElement iwe = new InflectedWordElement(base, cat);

	if (tag.equals("NNS"))
	    iwe.setFeature(Feature.NUMBER, NumberAgreement.PLURAL);
	if (tag.equals("JJR") || tag.equals("RBR"))
	    iwe.setFeature(Feature.IS_COMPARATIVE, true);
	if (tag.equals("JJS") || tag.equals("RBS"))
	    iwe.setFeature(Feature.IS_SUPERLATIVE, true);
	if (tag.equals("VBD"))
	    iwe.setFeature(Feature.TENSE, Tense.PAST);
	if (tag.equals("VBP"))
	    iwe.setFeature(Feature.PERSON, Person.FIRST);

	return morphologizer.realise(iwe).getRealisation();
    }

    private LexicalCategory asLexicalCategory(String tag) {
	if (tag.startsWith("JJ"))
	    return LexicalCategory.ADJECTIVE;
	else if (tag.startsWith("RB"))
	    return LexicalCategory.ADVERB;
	else if (tag.startsWith("NN"))
	    return LexicalCategory.NOUN;
	else if (tag.startsWith("VB"))
	    return LexicalCategory.VERB;
	else
	    return null;
    }
}