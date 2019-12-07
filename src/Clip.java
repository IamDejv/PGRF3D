import transforms.Point2D;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

public class Clip {

    private List<Point3D> clipPolygon = new ArrayList<>();

    public Clip() {}

    /**
     * Clip polygon points with clipping polygon clipPolygon
     * @param points
     * @param r
     * @return
     */
    public List<Point3D> clipPolygon(List<Point3D> points, Raster r){
        clipPolygon.add(new Point3D(0,0,0));
        clipPolygon.add(new Point3D(0,r.getHeight(),0));
        clipPolygon.add(new Point3D(r.getWidth(),r.getHeight(),0));
        clipPolygon.add(new Point3D(r.getWidth(),0,0));

        List<Point3D> output = points;
        // priradi se posledni bod ze seznamu
        Point3D clip1 = clipPolygon.get(clipPolygon.size() - 1);
        //pro vsechny body se projede celym
        for(Point3D clip2 : clipPolygon){
            if(points.size() < 2){
                break;
            }
            Edge clipEdge = new Edge(clip1, clip2);
            List<Point3D> out = new ArrayList<>();
            out.clear();
            Point3D poly1 = points.get(points.size()-1);
            for(Point3D poly2 : points){
                //Varianta 1 Cela hrana je uvnitr
                //Varianta 2 Hrana je orezana
                //Varinnta 3 Hrana lezi venku
                //Varinnta 4 Hrana je orezana
                if(clipEdge.inside(poly2)){
                    if(!clipEdge.inside(poly1)){
                        out.add(getIntersection(clipEdge, new Edge(poly1,poly2))); //var 4
                    }
                    out.add(poly2); //var 1,4
                } else if(clipEdge.inside(poly1)){
                    out.add(getIntersection(clipEdge, new Edge(poly1, poly2))); //var 2
                }
                poly1 = poly2;
            }
            points = out;
            output = points;
            clip1 = clip2;
        }
        return output;
    }

    /**
     * Get intersection between two edges
     * @param l1
     * @param l2
     * @return
     */
    private Point3D getIntersection(Edge l1, Edge l2) {
        double a1 = l1.getB().getY()- l1.getA().getY();
        double b1 = l1.getA().getX() - l1.getB().getX();
        double c1 = a1 * l1.getA().getX() + b1 * l1.getA().getY();

        double a2 = l2.getB().getY() - l2.getA().getY();
        double b2 = l2.getA().getX() - l2.getB().getX();
        double c2 = a2 * l2.getA().getX() + b2 * l2.getA().getY();

        double delta = a1 * b2 - a2 * b1;
        return new Point3D(((b2 * c1 - b1 * c2) / delta),  ((a1 * c2 - a2 * c1) / delta),0);
    }
}
