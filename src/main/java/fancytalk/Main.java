package fancytalk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import net.sf.extjwnl.JWNL;
import net.sf.extjwnl.dictionary.Dictionary;

public class Main {
    public static void main(String[] args) throws Exception {
	Analyze analyze = new Analyze(
	    "models/en-sent.bin",
	    "models/en-token.bin",
	    "models/en-pos-maxent.bin");
	JWNL.initialize(
	    Main.class.getResourceAsStream(
		"jwnl_properties.xml"));
	Embiggen embiggen = new Embiggen(
	    Dictionary.getInstance());
	Reassemble reassemble = new Reassemble(
	    Main.class.getResourceAsStream(
		"latin-detokenizer.xml"));

	String input;
	BufferedReader reader = 
	    new BufferedReader(
		new InputStreamReader(System.in));
	while ((input = reader.readLine()) != null) {
	    System.out.println(
		reassemble.applyAll(
		    embiggen.applyAllAll(
			analyze.apply(input))));
	}
    }
}