package fancytalk;

public class Embiggen extends ScoreFunction {
    public double score(String word, String prev) {
	return word.length();
    }
}
