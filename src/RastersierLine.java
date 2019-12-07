import java.awt.*;

public class RastersierLine implements Rasteriser{
    private Raster r;
    private Graphics gr;
    private Color color = Color.white;

    public RastersierLine(Raster r, Graphics gr){
        this.gr = gr;
        this.r = r;
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        //int a1 = (int)((x1+1)/2)*(r.getWidth()-1);
        //int b1 = (int)((-y1+1)/2)*(r.getHeight()-1);

        //int a2 = (int)(x2+1)/2*(r.getWidth()-1);
        //int b2 = (int)(-y2+1)/2*(r.getHeight()-1);
        gr.setColor(color);
        gr.drawLine(x1, y1, x2, y2);
        //gr.drawLine(a1, b1, a2, b2);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        setColor(color);
        drawLine(x1,y1,x2,y2);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Raster getR() {
        return r;
    }
}
