/**
 * Represents a set of coordinates with x and y values.
 */
public class Coordinates {

    /**
     * The x value of the coordinates.
     */
    private int x;

    /**
     * The y value of the coordinates.
     */
    private int y;

    /**
     * Constructs a new set of coordinates with the given x and y values.
     *
     * @param x the x value of the coordinates
     * @param y the y value of the coordinates
     */
    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x value of the coordinates.
     *
     * @return the x value of the coordinates
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y value of the coordinates.
     *
     * @return the y value of the coordinates
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x value of the coordinates.
     *
     * @param x the new x value of the coordinates
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y value of the coordinates.
     *
     * @param y the new y value of the coordinates
     */
    public void setY(int y) {
        this.y = y;
    }

}