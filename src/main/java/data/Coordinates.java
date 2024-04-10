package data;

import com.google.gson.annotations.Expose;
import jakarta.validation.constraints.Min;
/**
 * Coordinates of StudyGroup represented by x, y coordinates
 */
public class Coordinates {
    @Min(-300)
    @Expose
    private int x;
    @Expose
    private Double y;

    public Coordinates(int x, Double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * return x coordinate
     * @return x coordinate
     */
    public int getX() {
        return x;
    }
    /**
     * return y coordinate
     * @return y coordinate
     */
    public Double getY() {
        return y;
    }
    /**
     * return coordinates represented by beautiful string
     * @return coordinates represented by beautiful string
     */
    @Override
    public String toString() {
        return "{" + x + ", " + y + "}";
    }
}
