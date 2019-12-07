import transforms.Point3D;

import java.awt.*;


public class Vertex {
    Point3D coord;
    Color color;

    public Vertex(Point3D coord, Color color) {
        this.coord = coord;
        this.color = color;
    }

    public Vertex() {
    }

    public Vertex(Point3D point3D) {
    }

    public Point3D getCoord() {
        return coord;
    }

    public Color getColor() {
        return color;
    }
}
