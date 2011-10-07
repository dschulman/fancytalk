package fancytalk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class ScoredSequence {
    private final List<List<ScoredWord>> seq = 
	new ArrayList<List<ScoredWord>> ();

    public void add(List<String> choices, ScoreFunction f) {
	List<ScoredWord> scored = new ArrayList<ScoredWord>(choices.size());
	if (seq.isEmpty()) {
	    for (String choice : choices)
		scored.add(new ScoredWord(choice, f.score(choice, null), -1));
	} else {
	    double best = Double.NEGATIVE_INFINITY;
	    int bestPos = -1;
	    List<ScoredWord> prevs = seq.get(seq.size()-1);
	    for (String choice : choices) {
		for (int n=0; n<prevs.size(); ++n) {
		    ScoredWord prev = prevs.get(n);
		    double score = prev.score + f.score(choice, prev.word);
		    if (score > best) {
			best = score;
			bestPos = n;
		    }
		}
		scored.add(new ScoredWord(choice, best, bestPos));
	    }
	}
	seq.add(scored);
    }

    public List<String> best() {
	List<String> best = new ArrayList<String>(seq.size());
	if (seq.isEmpty())
	    return best;

	ListIterator<List<ScoredWord>> it = seq.listIterator(seq.size());

	List<ScoredWord> sws = it.previous();
	double bestScore = Double.NEGATIVE_INFINITY;
	ScoredWord bestSw = null;
	for (ScoredWord sw : sws)
	    if (sw.score > bestScore) {
		bestScore = sw.score;
		bestSw = sw;
	    }

	best.add(bestSw.word);	
	while (it.hasPrevious()) {
	    bestSw = it.previous().get(bestSw.prevInSequence);
	    best.add(bestSw.word);
	}

	Collections.reverse(best);
	return best;
    }
}