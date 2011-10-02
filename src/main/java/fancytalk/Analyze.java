package fancytalk;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;

public class Analyze {
    private final SentenceDetectorME detector;
    private final TokenizerME tokenizer;
    private final POSTaggerME tagger;

    public Analyze(String sdModel, String tokModel, String tagModel)
	throws IOException {
	detector = new SentenceDetectorME(readSdModel(sdModel));
	tokenizer = new TokenizerME(readTokModel(tokModel));
	tagger = new POSTaggerME(readTagModel(tagModel));
    }

    private SentenceModel readSdModel(String model) throws IOException {
	InputStream modelIn = new FileInputStream(model);
	try {
	    return new SentenceModel(modelIn);
	} finally {
	    modelIn.close();
	}
    }

    private TokenizerModel readTokModel(String model) throws IOException {
	InputStream modelIn = new FileInputStream(model);
	try {
	    return new TokenizerModel(modelIn);
	} finally {
	    modelIn.close();
	}
    }

    private POSModel readTagModel(String model) throws IOException {
	InputStream modelIn = new FileInputStream(model);
	try {
	    return new POSModel(modelIn);
	} finally {
	    modelIn.close();
	}
    }

    public List<List<Token>> apply(String input) {
	String[] sentences = detector.sentDetect(input);
	List<List<Token>> out = new ArrayList<List<Token>>(sentences.length);
	for (String sentence : sentences) {
	    String[] words = tokenizer.tokenize(sentence);
	    String[] tags = tagger.tag(words);
	    List<Token> tokens = new ArrayList<Token>(words.length);
	    for (int n=0; n<words.length; n++)
		tokens.add(new Token(words[n], tags[n]));
	    out.add(tokens);
	}
	return out;
    }
}