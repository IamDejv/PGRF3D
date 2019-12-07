import transforms.Cubic;
import transforms.Point3D;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Curves extends Solid{

    private int mode;
    private List<Point3D> curvePoints;
    private Random random;


    public Curves(int mode){
        random = new Random();
        this.mode = mode;
        curve = true;
        random.ints(-20, 20);
        colorBuffer = new ArrayList<>();
        indexBuffer = new ArrayList<>();
        vertexBuffer = new ArrayList<>();
        curvePoints = new ArrayList<>();
        indexBuffer.clear();
        vertexBuffer.clear();
        colorBuffer.clear();
        int points = 10;

        Point3D g1 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());
        Point3D g2 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());
        Point3D g3 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());
        Point3D g4 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());


        curvePoints.add(g1);
        curvePoints.add(g2);
        curvePoints.add(g3);
        curvePoints.add(g4);

        if (mode == 1){
            Cubic fer = new Cubic(Cubic.FERGUSON, g1,g2,g3,g4);
            colorBuffer.add(Color.cyan);
            setPoints(points, fer);

        } else if (mode == 2){
            Cubic bez = new Cubic(Cubic.BEZIER, g1,g4,g2,g3);
            colorBuffer.add(Color.YELLOW);
            setPoints(points, bez);

        } else if (mode == 3){
            Cubic con = new Cubic(Cubic.COONS, g1,g2,g3,g4);
            colorBuffer.add(Color.magenta);
            setPoints(points, con);
        }
    }

    public void addCurve(int n){
        if(mode == 1){
            Point3D g5 = curvePoints.get(1);
            Point3D g6 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());
            Point3D g7 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());
            Point3D g8 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());


            Cubic fer = new Cubic(Cubic.FERGUSON,g5,g6,g7,g8);
            setPoints(n, fer);
        } else if(mode == 2){
            Point3D g5 = curvePoints.get(3);
            Point3D g6 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());
            Point3D g7 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());
            Point3D g8 = new Point3D(random.nextDouble(),random.nextDouble(),random.nextDouble());

            Cubic bez = new Cubic(Cubic.BEZIER,g5,g6,g7,g8);
            setPoints(n, bez);
        } else if (mode == 3){
            Point3D g5 = curvePoints.get(1);
            Point3D g6 = curvePoints.get(2);
            Point3D g7 = curvePoints.get(3);
            Point3D g8 = new Point3D(4,2,0);

            Cubic con = new Cubic(Cubic.COONS, g5,g6,g7,g8);
            setPoints(n,con);
        }
    }

    public void setPoints(int n, Cubic cubic){
        for(int i = 0; i < n; i++){
            vertexBuffer.add(cubic.compute((double)i/n));
            indexBuffer.add(i);
            indexBuffer.add(i + 1);
        }
    }
}
