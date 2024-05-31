package data;

public class Coordinates implements Comparable<Coordinates> {
    private int x;
    private Double y;

    // No-argument constructor
    public Coordinates() {
    }

    // Constructor with parameters
    public Coordinates(int x, Double y) {
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public int compareTo(Coordinates other) {
        // Compare by x first
        int compareX = Integer.compare(this.x, other.x);
        if (compareX != 0) {
            return compareX;
        }
        // If x values are equal, compare by y
        return Double.compare(this.y, other.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
