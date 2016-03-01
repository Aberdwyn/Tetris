package Tetris;

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

    public String getName() {
	return name;
    }

    @Override public String toString() {
	return name+": "+score;
    }
}
