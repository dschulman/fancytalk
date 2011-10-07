package fancytalk;

public abstract class ScoreFunction {
    public abstract double score(String word, String prev);

    public ScoreFunction scaled(final double scale) {
	final ScoreFunction f = this;
	return new ScoreFunction() {
	    public double score(String word, String prev) {
		return scale*f.score(word, prev);
	    }
	};
    }

    public static ScoreFunction sum(final ScoreFunction... fs) {
	return new ScoreFunction() {
	    public double score(String word, String prev) {
		double s = 0;
		for (ScoreFunction f : fs)
		    s += f.score(word, prev);
		return s;
	    }
	};
    }
}