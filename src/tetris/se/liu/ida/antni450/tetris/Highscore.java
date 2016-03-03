package tetris.se.liu.ida.antni450.tetris;

/**
 * this class can be used to create highscores
 */
public class Highscore
{
    private int score;
    private String name;

    public Highscore(final int score, final String name) {
	this.score = score;
	this.name = name;
    }

    public int getScore() {
	return score;
    }

    @Override public String toString() {
	return name+": "+score;
    }
}
