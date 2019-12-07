import transforms.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Canvas {

    private JFrame frame;
    private JPanel panel;
    private BufferedImage img;
    private RasterBufferedImage raster;
    private RastersierLine rasteriserLine;
    private SolidRenderer renderer;
    private Tetrahedron tetrahedron;
    private Cube cube;
    private Axis axis;
    private Circle circle;
    private Grid grid;
    private Point2D camera;
    private Point2D cameraEnd;
    private Point2D rotation;
    private double step = 0.5;
    private JPanel menu;
    private JRadioButton perspective;
    private JRadioButton projection;
    private JRadioButton rbTetra;
    private JRadioButton rbCube;
    private ButtonGroup btnGroupRotate;
    private JCheckBox move;
    private Cubic fer;
    private Cubic bez;
    private Curves curve;
    private Mat4 proj = new Mat4OrthoRH(6,6,0.001,100);
    private Mat4 persp = new Mat4PerspRH(Math.PI/3,3/4.0,0.1,25);
    private Mat4 projectionView = persp;
    private Camera cam = new Camera(new Vec3D(-7,0,0),0,0,1,false);
    private int zoom;
    private int mouseButton;
    private Solid moving;
    private int mode;
    private Point2D startMove;
    private Solid rotating;

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("PGRF David Kalianko ");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        raster = new RasterBufferedImage(img);
        rasteriserLine = new RastersierLine(raster, img.getGraphics());
        renderer = new SolidRenderer(rasteriserLine);
        perspective = new JRadioButton("Perspective", true);
        projection = new JRadioButton("Projection");
        rbCube = new JRadioButton("Cube", true);
        rbTetra = new JRadioButton("Tetrahedron");
        rbCube.addActionListener(e->{
            if(rbCube.isSelected()){
                rotating = cube;
            }
        });
        rbTetra.addActionListener(e->{
            if(rbTetra.isSelected()){
                rotating = tetrahedron;
            }
        });
        move = new JCheckBox("Move");
        move.addActionListener(e->{
            moving = cube;
            mode = 1;
        });
        perspective.addActionListener(e->{
            if(perspective.isSelected()){
                projectionView = persp;
                draw();
            }
        });

        projection.addActionListener(e-> {
            if(projection.isSelected()){
                projectionView = proj;
                draw();
            }
        });


        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        ButtonGroup group = new ButtonGroup();
        btnGroupRotate = new ButtonGroup();
        group.add(projection);
        group.add(perspective);
        btnGroupRotate.add(rbTetra);
        btnGroupRotate.add(rbCube);
        menu = new JPanel();
        panel.add(menu);
        menu.add(rbCube);
        menu.add(rbTetra);
        menu.add(projection);
        menu.add(perspective);
        menu.add(move);
        panel.requestFocus();
        panel.requestFocusInWindow();
        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if(e.getWheelRotation() > 0){
                    cam = cam.backward(step);
                } else if (cam.getPosition().getX() < 0.0){
                    cam = cam.forward(step);
                }
                draw();
            }
        });
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A:
                        cam = cam.left(step);
                        break;
                    case KeyEvent.VK_D:
                        cam = cam.right(step);
                        break;
                    case KeyEvent.VK_E:
                        cam = cam.forward(step);
                        break;
                    case KeyEvent.VK_Q:
                        cam = cam.backward(step);
                        break;
                    case KeyEvent.VK_W:
                        cam = cam.up(step);
                        break;
                    case KeyEvent.VK_S:
                        cam = cam.down(step);
                        break;
                }
                draw();
                panel.repaint();
            }
        });
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(move.isSelected()){
                    startMove = new Point2D(e.getX(), e.getY());
                } else {
                    camera = new Point2D(e.getX(), e.getY());
                    rotation = new Point2D(e.getX(), e.getY());
                    mouseButton = e.getButton();
                }

            }
        });
        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(move.isSelected()){
                    moving.setTransform(moving.getTransform().add(new Mat4Transl(0.0, (startMove.getX()-e.getX())/10, (-e.getY() + startMove.getY())/10)));

                } else {
                    System.out.println(mouseButton);
                    System.out.println(e.getButton());
                    if(mouseButton ==  MouseEvent.BUTTON3){
                        //Rotace objektu
                        cameraEnd = new Point2D(e.getX(), e.getY());

                        double dx = (cameraEnd.getX() - rotation.getX());
                        double dy = (cameraEnd.getY() - rotation.getY());

                        rotating.setTransform(rotating.getTransform().mul(new Mat4RotX(Math.PI / 10000 * dx)));
                        rotating.setTransform(rotating.getTransform().mul(new Mat4RotY(-Math.PI / 10000 * dy)));

                        //Mat4Transl translation = new Mat4Transl(0, 0, 0);
                        //rotating.setTransform(rotating.getTransform().mul(translation.inverse().get()));
                        //rotating.setTransform(rotating.getTransform().mul(new Mat4RotXYZ(-dx * Math.PI / 5000, dy * Math.PI / 5000, 0)));
                        //rotating.setTransform(rotating.getTransform().mul(translation));


                        double rx = rotation.getX();
                        //cube.setTransform(cube.getTransform().mul(new Mat4RotY()));
                    } else if(mouseButton == MouseEvent.BUTTON1){
                        //Posun kamery
                        cameraEnd = new Point2D(e.getX(), e.getY());
                        double dx = (cameraEnd.getX()-camera.getX());
                        double dy = (cameraEnd.getY()-camera.getY());

                        camera = new Point2D(cameraEnd);
                        cam = cam.addAzimuth((Math.PI / 2048) * dx);
                        cam = cam.addZenith((Math.PI / 2048) * dy);
                    } else if (mouseButton == MouseEvent.BUTTON2){
                        //Zvetsovani objektu
                        cameraEnd = new Point2D(e.getX(), e.getY());

                        double dy = (cameraEnd.getY()-rotation.getY());
                        cube.setTransform(cube.getTransform().add(new Mat4Scale(dy)));
                    }
                }
                draw();

            }
        });
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }


    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    private void initCurve(){
        curve = new Curves(2);
    }

    private void draw() {
        clear();

        renderer.setProj(projectionView);
        renderer.setView(cam.getViewMatrix());
        renderer.draw(cube);
        renderer.draw(tetrahedron);
        initCurve();
        renderer.draw(axis);
        renderer.draw(curve);
        //renderer.draw(circle);
        //renderer.draw(grid);

        panel.repaint();
    }

    public void start() {
        clear();
        tetrahedron = new Tetrahedron();
        axis = new Axis();
        cube = new Cube();
        circle = new Circle(0,0,0.5,25);
        grid = new Grid(5,4);
        rotating = cube;
        draw();
        panel.repaint();
    }
}
