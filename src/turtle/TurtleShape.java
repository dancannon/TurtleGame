package turtle;

/**
 * Interface for objects that can be drawn by a turtle.
 */
public interface TurtleShape {
    /**
     * Draw a shape using a given turtle.
     * @param theTurtle turtle to use
     */
    public void drawMe(Turtle turtle);
}
