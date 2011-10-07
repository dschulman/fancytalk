package fancytalk;

import java.io.InputStream;
import java.io.IOException;
import java.util.List;
import opennlp.tools.tokenize.DetokenizationDictionary;
import opennlp.tools.tokenize.Detokenizer.DetokenizationOperation;
import opennlp.tools.tokenize.DictionaryDetokenizer;

public class Reassemble {
    private final DictionaryDetokenizer detokenizer;

    public Reassemble(InputStream dict) throws IOException {
	detokenizer = 
	    new DictionaryDetokenizer(
		new DetokenizationDictionary(dict));
    }

    public String applyAll(List<List<String>> in) {
	StringBuilder sb = new StringBuilder();
	boolean needSpace = false;
	for (List<String> i : in) {
	    if (needSpace)
		sb.append(' ');
	    sb.append(apply(i));
	    needSpace = true;
	}
	return sb.toString();
    }

    public String apply(List<String> in) {
	String[] inA = in.toArray(new String[0]);
	DetokenizationOperation[] ops = detokenizer.detokenize(inA);

	StringBuilder sb = new StringBuilder();
	boolean wasMergeRight = true;
	for (int n=0; n<inA.length; n++) {
	    switch (ops[n]) {
	    case MERGE_TO_RIGHT:
		if (!wasMergeRight)
		    sb.append(' ');
		wasMergeRight = true;
		break;

	    case NO_OPERATION:
		if (!wasMergeRight)
		    sb.append(' ');
	    case MERGE_TO_LEFT:
		wasMergeRight = false;
		break;
	    }
	    
	    sb.append(inA[n]);
	}
	
	return sb.toString();
    }
}