import transforms.Mat4;

import java.awt.image.BufferedImage;

public interface Render {

    void setBufferedImage(BufferedImage img);

    void draw(Solid solid);

    void setView(Mat4 view);

    void setProj(Mat4 proj);
}
