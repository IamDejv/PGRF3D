import transforms.Point2D;
import transforms.Point3D;

public class Edge {
    private final Point2D a;
    private final Point2D b;

    public Edge(Point2D a, Point2D b){
        this.a = a;
        this.b = b;

    }

    public Point2D getA() {
        return a;
    }

    public Point2D getB() {
        return b;
    }

    /**
     * Check if Point c is inside Edge
     * @param c
     * @return
     */
    public boolean inside(Point2D c) {
        return (b.getX() - a.getX())*(b.getY()-c.getY()) - (b.getY() - a.getY())*(b.getX()-c.getX()) >= 0.0D;
    }
}
