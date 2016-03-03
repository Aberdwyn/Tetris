package tetris.se.liu.ida.antni450.tetris;

/**
 * this is an interface for collision handlers.
 * every collision handler needs a method hasCollision
 * and a getDescription
 */
public interface CollisionHandler
{
    /**
     * checks if there is any collision between fallingPoly and anything on the board
     * @param board the gameboard
     * @return true or false
     */
    public boolean hasCollision(Board board);

    /**
     * the description of the collision handler
     * @return the description
     */
    public String getDescription();
}
