import transforms.Point3D;

public class Grid extends Solid{
    public Grid(int m, int n) {

        for(double i = 0; i < m; i++ ){
            for(double j = 0; j < n; j++){
            double vy = i*1./m;
            double vz = j*1./n;
            vertexBuffer.add(new Point3D(0, vy, vz));
            }
        }
        //for(int i = 0; i < m;i++){

        //}
    }
}
