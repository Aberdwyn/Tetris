package Tetris;

import java.util.ArrayList;
import java.util.List;

public class HighscoreList
{
    private static final HighscoreList INSTANCE = new HighscoreList();
    private List<Highscore> highscores = new ArrayList<>();
    private HighscoreList() {}

    public static HighscoreList getINSTANCE() {
	return INSTANCE;
    }

    public void addHighscore(Highscore highscore) {
	highscores.add(highscore);
    }

    public List<Highscore> getHighscores() {
	return highscores;
    }

    @Override public String toString() {
	StringBuilder str = new StringBuilder();
	for (Highscore highscore: highscores) {
	    str.append(highscore.toString());
	}
	return str.toString();
    }


}
