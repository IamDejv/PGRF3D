import transforms.Mat4;
import transforms.Mat4Identity;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Solid {
    protected List<Integer> indexBuffer = new ArrayList<>();
    protected List<Point3D> vertexBuffer = new ArrayList<>();
    protected Mat4 transform = new Mat4Identity();
    protected Color color;
    protected List<Color> colorBuffer;
    protected boolean axe = false;
    protected boolean curve = false;


    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Point3D> getVertexBuffer() {
        return vertexBuffer;
    }

    public Mat4 getTransform() {
        return transform;
    }

    public void setTransform(Mat4 transform){
        this.transform = transform;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public List<Color> getColorBuffer() {
        return colorBuffer;
    }

    public void setColorBuffer(List<Color> colorBuffer) {
        this.colorBuffer = colorBuffer;
    }

    public boolean isAxe(){
        return axe;
    }

    public boolean isCurve() {
        return curve;
    }
}
