package model;

import transforms.Mat4;
import transforms.Mat4RotX;
import transforms.Mat4RotY;
import transforms.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Cube extends Solid {

    public Cube(){
        Integer[] indexes = new Integer[] { 0, 1, 1, 2, 2, 3, 3, 0, 0, 4, 4, 5, 5, 6, 6, 7, 7, 4, 1, 5, 2, 6, 3, 7 };
        getIndexBuffer().addAll(Arrays.asList(indexes));
        colorBuffer = new ArrayList<>();

        vertexBuffer.add(new Point3D(-1, -1, -1));
        vertexBuffer.add(new Point3D(-1, 1, -1));
        vertexBuffer.add(new Point3D(1, 1, -1));
        vertexBuffer.add(new Point3D(1, -1, -1));

        vertexBuffer.add(new Point3D(-1, -1, 1));
        vertexBuffer.add(new Point3D(-1, 1, 1));
        vertexBuffer.add(new Point3D(1, 1, 1));
        vertexBuffer.add(new Point3D(1, -1, 1));

        colorBuffer.add(Color.white);
    }

    public void setColor(Color color){
        colorBuffer.clear();
        colorBuffer.add(color);
    }

}
