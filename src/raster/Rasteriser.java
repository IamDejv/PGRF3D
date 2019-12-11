package raster;

import java.awt.*;

public interface Rasteriser {
    void drawLine(int x1, int y1, int x2, int y2);
    void drawLine(int x1, int y1, int x2, int y2, Color color);
}
