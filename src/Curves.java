import transforms.Cubic;
import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;

public class Curves extends Solid{

    public Curves(int mode){
        int points = 10;

        Point3D g1 = new Point3D(0,0,0);
        Point3D g2 = new Point3D(0,1,1);
        Point3D g3 = new Point3D(1,1,0);
        Point3D g4 = new Point3D(2,3,0);

        if (mode == 1){
            Cubic fer = new Cubic(Cubic.FERGUSON, g1,g2,g3,g4);
            setPoints(points, fer);

        } else if (mode ==2){
            Cubic bez = new Cubic(Cubic.BEZIER, g1,g2,g3,g4);
            setPoints(points, bez);

        } else if (mode == 3){
            Cubic con = new Cubic(Cubic.COONS, g1,g2,g3,g4);
            setPoints(points, con);
        }

    }

    public void setPoints(int n, Cubic cubic){
        indexBuffer = new ArrayList<>();
        vertexBuffer = new ArrayList<>();
        indexBuffer.clear();
        vertexBuffer.clear();

        for(int i = 0; i < n; i++){
            vertexBuffer.add(cubic.compute((double)i/n));
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }
    }

}
