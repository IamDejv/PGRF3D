package raster;

import java.awt.*;

public interface Raster {
    void drawPixel (int x, int y);
    void drawPixel(int x, int y, Color color);
    int getHeight ();
    int getWidth ();

    int getPixel(int x, int y);

    int getRGB(int x, int y);

    void setColor(Color color);
}
