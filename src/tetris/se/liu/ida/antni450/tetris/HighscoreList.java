package tetris.se.liu.ida.antni450.tetris;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this is a singleton class that is used to store and show highscores
 */
final public class HighscoreList
{
    //HighscoreList creates an instance of itself
    private static final HighscoreList INSTANCE = new HighscoreList();
    private List<Highscore> highscores = new ArrayList<>();
    private HighscoreList() {}

    /**
     * @return an instance of itself
     */
    public static HighscoreList getINSTANCE() {
	return INSTANCE;
    }

    /**
     * add a highscore to the highscorelist
     * @param highscore a highscore type consisting of a name and a score
     */
    public void addHighscore(Highscore highscore) {
	highscores.add(highscore);
    }

    /**
     * @return a string with every highscore
     */
    @Override public String toString() {
	Collections.sort(highscores, new ScoreComparator());
	StringBuilder str = new StringBuilder();
	for (Highscore highscore: highscores) {
	    str.append(highscore);
	    str.append("\n");
	}
	return str.toString();
    }
}
