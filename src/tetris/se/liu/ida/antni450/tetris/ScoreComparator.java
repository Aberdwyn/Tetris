package tetris.se.liu.ida.antni450.tetris;

import java.util.Comparator;

/**
 * this is the comparator class for comparing 2 scores
 */
public class ScoreComparator implements Comparator<Highscore> {
    /**
     *
     * @param o1 highscore type
     * @param o2 highscore type
     * @return -1 if o1 is bigger then o2. 0 if they are equal and 1 if o2 is greater then o1
     */
    @Override public int compare(Highscore o1, Highscore o2) {
	if (o1.getScore() > o2.getScore()) {
	    return -1;
	}
	else if (o1.getScore() < o2.getScore()) {
	    return 1;
	}
	else return 0;
    }
}

