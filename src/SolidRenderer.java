import transforms.Mat4;
import transforms.Point2D;
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
    //private Clip clip;

    private List<Point3D> verticies;
    private List<Integer> indexes;

    public SolidRenderer(RastersierLine rastersierLine){
        this.rastersierLine = rastersierLine;
        verticies = new ArrayList<>();
        colorBuffer = new ArrayList<>();
        //clip = new Clip();
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
        if(solid.curve){
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
        //if(cut(a) || cut(b)){
        //    return;
        //}

        Vec3D va;
        Vec3D vb;

        //Dehomogenizace
        if(!a.dehomog().isPresent() || !b.dehomog().isPresent()){
            return;
        }
        va = window(a.dehomog().get());
        vb = window(b.dehomog().get());


        //List<Point2D> clipped = clip(va, vb);

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
//TODO dodelat opakovani pro jeste jeden bod
    private List<Point2D> clip(Vec3D vec1, Vec3D vec2){
        List<Point2D> clipping = initClip();
        List<Point2D> output = new ArrayList<>();
        Point2D clip1 = clipping.get(clipping.size() - 1);
        for(Point2D clip2 : clipping){
            Edge edge = new Edge(clip1, clip2);
            List<Point2D> out = new ArrayList<>();
            out.clear();
            if(edge.inside(vec2)){
                if(!edge.inside(vec1)){
                    out.add(getIntersection(edge, vec1, vec2));
                }
                out.add(new Point2D(vec2.getX(), vec2.getY()));
            } else if(edge.inside(vec1)){
                out.add(getIntersection(edge, vec1, vec2));
            }
            output = out;
            clip1 = clip2;
        }
        return output;
    }


    private Point2D getIntersection(Edge l1, Vec3D vec1, Vec3D vec2) {
        double a1 = l1.getB().getY()- l1.getA().getY();
        double b1 = l1.getA().getX() - l1.getB().getX();
        double c1 = a1 * l1.getA().getX() + b1 * l1.getA().getY();

        double a2 = vec2.getY() - vec1.getY();
        double b2 = vec1.getX() - vec2.getX();
        double c2 = a2 * vec1.getX() + b2 * vec1.getY();

        double delta = a1 * b2 - a2 * b1;
        return new Point2D(((b2 * c1 - b1 * c2) / delta),  ((a1 * c2 - a2 * c1) / delta));
    }

    private List<Point2D> initClip(){
        List<Point2D> clip = new ArrayList<>(4);
        clip.add(new Point2D(0,0));
        clip.add(new Point2D(0,rastersierLine.getR().getHeight()-1));
        clip.add(new Point2D(rastersierLine.getR().getWidth()-1,rastersierLine.getR().getHeight() -1));
        clip.add(new Point2D(rastersierLine.getR().getHeight()-1,0));
        return clip;
    }
}
