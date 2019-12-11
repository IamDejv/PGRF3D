package render;

import model.Solid;
import raster.RastersierLine;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SolidRenderer implements Render{
    private BufferedImage img;
    private Mat4 view;
    private Mat4 proj;
    private RastersierLine rastersierLine;
    private List<Color> colorBuffer;
    private int counter;
    private boolean axe;

    private List<Point3D> verticies;
    private List<Integer> indexes;

    public SolidRenderer(RastersierLine rastersierLine){
        this.rastersierLine = rastersierLine;
        verticies = new ArrayList<>();
        colorBuffer = new ArrayList<>();
    }

    public void drawCurve(Solid solid){
        colorBuffer = solid.getColorBuffer();
    }

    @Override
    public void draw(Solid solid) {
        axe = solid.isAxe();

        colorBuffer = solid.getColorBuffer();

        Mat4 transform = solid.getTransform().mul(view).mul(proj);
        for (Point3D vertex : solid.getVertexBuffer()){
            verticies.add(vertex.mul(transform));
        }
        indexes = solid.getIndexBuffer();
        counter = 0;

        int counterIndex;
        if(solid.isCurve()){
            counterIndex = indexes.size() - 1;
        } else {
            counterIndex = indexes.size();
        }

        for(int i = 1; i < counterIndex; i += 2){
            renderLine(verticies.get(indexes.get(i - 1)), verticies.get(indexes.get(i)));
            counter++;
            if(counter > colorBuffer.size() - 1){
                counter = 0;
            }
        }
        verticies = new ArrayList<>();
        indexes = new ArrayList<>();
    }

    private void renderLine(Point3D a , Point3D b){
        //TODO Orezani v homogennich souradnicich
        if(cut(a) || cut(b)){
            return;
        }

        Vec3D va;
        Vec3D vb;

        //Dehomogenizace
        if(!a.dehomog().isPresent() || !b.dehomog().isPresent()){
            return;
        }
        va = window(a.dehomog().get());
        vb = window(b.dehomog().get());

        double x1 = va.getX();
        double y1 = va.getY();

        double x2 = vb.getX();
        double y2 = vb.getY();

        if(!axe){
            rastersierLine.drawLine((int) x1, (int)y1, (int)x2, (int)y2, colorBuffer.get(0));
        } else {
            rastersierLine.drawLine((int) x1, (int)y1, (int)x2, (int)y2, colorBuffer.get(counter));
        }
    }

    @Override
    public void setBufferedImage(BufferedImage img) {
        this.img = img;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public void setProj(Mat4 proj) {
        this.proj = proj;
    }

    private Vec3D window(Vec3D v) {
        return v.mul(new Vec3D(1, -1, 1)).add(new Vec3D(1, 1, 0)).mul(new Vec3D(rastersierLine.getR().getWidth() / 2., rastersierLine.getR().getHeight()/ 2., 1));
    }

    private boolean cut(Point3D a) {
        return (-a.getW() >= a.getX() || -a.getW() >= a.getY() || a.getX() >= a.getW() || a.getY() >= a.getW()
                || 0 >= a.getZ() || a.getZ() >= a.getW());
    }
}
