import transforms.*;

import java.awt.*;
import java.util.ArrayList;

public class Tetrahedron extends Solid{

    public Tetrahedron(){
        colorBuffer = new ArrayList<>();
        vertexBuffer.add(new Point3D(0,0,0));
        vertexBuffer.add(new Point3D(0,0,2));
        vertexBuffer.add(new Point3D(2,0,0));
        vertexBuffer.add(new Point3D(0,2,0));

        indexBuffer.add(0);
        indexBuffer.add(1);

        indexBuffer.add(1);
        indexBuffer.add(2);

        indexBuffer.add(0);
        indexBuffer.add(2);

        indexBuffer.add(0);
        indexBuffer.add(3);

        indexBuffer.add(1);
        indexBuffer.add(3);

        indexBuffer.add(2);
        indexBuffer.add(3);

        colorBuffer.add(Color.ORANGE);

        transform = new Mat4Identity().mul(new Mat4RotX(Math.PI/180 * 45).mul(new Mat4RotY(Math.PI/180 * 65)));
    }

    public void setColor(Color color){
        colorBuffer.clear();
        colorBuffer.add(Color.white);

    }
}
