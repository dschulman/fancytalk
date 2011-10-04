package fancytalk;

public class ScoredWord {
    public final String word;
    public final double score;
    public final int prevInSequence;

    public ScoredWord(String word, double score, int prevInSequence) {
	this.word = word;
	this.score = score;
	this.prevInSequence = prevInSequence;
    }
}