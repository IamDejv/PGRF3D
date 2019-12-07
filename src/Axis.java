import transforms.Mat4Scale;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Axis extends Solid{
    public Axis(){
        axe = true;
        colorBuffer = new ArrayList<>();
        Integer[] indexes = new Integer[] { 0, 1, 0, 2, 0, 3 };
        getIndexBuffer().addAll(Arrays.asList(indexes));

        vertexBuffer.add(new Point3D(0,0,0));

        vertexBuffer.add(new Point3D(1, 0, 0));//X

        vertexBuffer.add(new Point3D(0, 1, 0));//Y

        vertexBuffer.add(new Point3D(0, 0, 1));//Z

        colorBuffer.add(Color.blue);
        colorBuffer.add(Color.green);
        colorBuffer.add(Color.red);

        transform = new Mat4Scale(2);
    }
}
