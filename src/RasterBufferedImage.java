import java.awt.*;
import java.awt.image.BufferedImage;

public class RasterBufferedImage implements Raster{

    private BufferedImage bufferedImage;

    private Color color;

    public RasterBufferedImage(BufferedImage bufferedImage, Color color) {
        this.bufferedImage = bufferedImage;
        this.color = color;
    }

    public RasterBufferedImage(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
        this.color = Color.white;
    }

    @Override
    public void drawPixel(int x, int y) {
        if (x < 800 && y < 600 && x > 0 && y > 0) {
            bufferedImage.setRGB(x, y, color.getRGB());
        }
    }

    @Override
    public int getWidth() {
        return bufferedImage.getWidth();
    }

    @Override
    public int getHeight() {
        return bufferedImage.getHeight();
    }

    @Override
    public void drawPixel(int x, int y, Color color) {
        if (x < bufferedImage.getWidth() && y < bufferedImage.getHeight() && x >= 0 && y >= 0) {
            bufferedImage.setRGB(x, y, color.getRGB());
        }
    }

    @Override
    public int getRGB(int x, int y) {
        return bufferedImage.getRGB(x,y);
    }

    @Override
    public int getPixel(int x, int y) {
        return bufferedImage.getRGB(x,y);
    }

    public Color getColor(){
        return this.color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
