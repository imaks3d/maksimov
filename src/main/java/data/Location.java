package data;

public class Location {
    private double x;
    private Double y;
    private double z;
    private String name;
    public Location(float x, Double y, double z, String name){
        this.x=x;
        this.y=y;
        this.z=z;
        this.name=name;
    }
    public Location(){

    }

    public double getX() {
        return this.x;
    }

    public Double getY() {
        return this.y;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getZ() {
        return z;
    }
    @Override
    public String toString() {
        return "Location[" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", name='" + name + '\'' +
                ']';
    }

}
