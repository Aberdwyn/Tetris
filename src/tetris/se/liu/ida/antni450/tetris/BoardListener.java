package tetris.se.liu.ida.antni450.tetris;

/**
 * this is the interface for listeners to the board
 * every boardlistener needs to have a method that gets called when the board is changed
 */
public interface BoardListener
{
    public void boardChanged();
}
