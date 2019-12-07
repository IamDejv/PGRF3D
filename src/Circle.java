import com.sun.prism.impl.VertexBuffer;
import transforms.Point3D;

public class Circle extends Solid {

    public Circle(double y, double z, double r, int n) {
        for(int i = 0; i < n; i++){
            double alpha = i*2*Math.PI/n;
            double vy = r*Math.cos(alpha);
            double vz = r*Math.sin(alpha);
            vertexBuffer.add(new Point3D(0,vy,vz));
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }


    }
}
