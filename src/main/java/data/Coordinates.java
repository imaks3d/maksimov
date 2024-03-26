package data;

import jakarta.validation.constraints.Min;
/**
 * Coordinates of StudyGroup represented by x, y coordinates
 */
public class Coordinates {
    @Min(-300)
    private int x;
    private Double y;

    public Coordinates(int x, Double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * @return x coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * @return y coordinate
     */
    public Double getY() {
        return y;
    }
    /**
     * @return coordinates represented by beautiful string
     */
    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
