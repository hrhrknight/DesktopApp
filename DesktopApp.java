import java.awt.Color;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Point;

import java.awt.Font;
import javax.swing.JFrame;



class DesktopApp extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Image MainCharaImg;
    boolean is_closeButton_pressed = false;
    boolean is_movable = false;
    Point mouse_position = new Point();
    
    String now = new String();

    DesktopApp() {
        // set drawable windowsize
        setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
        // set window location
        setLocation(0, 0);
        // disable default decoration
        setUndecorated(true);
        // program exit when the window close
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // set background to invisible
        setBackground(new Color(0, 0, 0, 0));
        // set window opacity
        /*
         * setOpacity(0.3f);
         */
        // set always on top
        setAlwaysOnTop(true);
        // load image
        MainCharaImg = Toolkit.getDefaultToolkit().getImage("C:\\Users\\ryoro\\Pictures\\chiwawa-01.png");
        // set listener
        myListener mListener = new myListener();
        this.addMouseListener(mListener);
        this.addMouseMotionListener(mListener);

        foundamental_thread f_runnable = new foundamental_thread();
        Thread f_Thread = new Thread(f_runnable);
        f_Thread.start();

        // set window visible
        setVisible(true);
    }
    


    public void paint(Graphics g) {
        if (is_closeButton_pressed) {
            g.setColor(new Color(255, 0, 0));
        } else {
            g.setColor(new Color(0, 0, 255));
        }
        g.fillRect(20, 20, 100, 50);
        int[] polX = { 10, 0, 20 }, polY = { 0, 10, 10 };
        g.fillPolygon(polX, polY, 3);
        g.drawImage(MainCharaImg, 0, 0, 800, 800, this);
        DrawTest(g);
        g.dispose();
    }

    private void DrawTest(Graphics g) {
        g.setColor(new Color(0, 255, 0));
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 30);
        g.setFont(f);
        g.drawString(now, 100, 500);
    }

    public static void main(String args[]) {

        new DesktopApp();
    }

    public class myListener extends MouseAdapter {
        Point ComponentPoint = new Point();

        public void mouseClicked(MouseEvent e) {
            int x = e.getX(), y = e.getY();
            if ((20 < x && x < 120) && (20 < y && y < 70)) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    System.exit(0);
            }
        }

        public void mousePressed(MouseEvent e) {
            ComponentPoint = e.getPoint();
            int x = e.getX(), y = e.getY();
            if ((20 < x && x < 120) && (20 < y && y < 70)) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    is_closeButton_pressed = true;
                    repaint();
                }
            }
            if (e.getButton() == MouseEvent.BUTTON1) {
                is_movable = true;
            }
        }

        public void mouseReleased(MouseEvent e) {
            is_movable = false;
            if (e.getButton() == MouseEvent.BUTTON3) {
                is_closeButton_pressed = false;
                repaint();
            }
        }

        public void mouseDragged(MouseEvent e) {
            Point eventLocationOnScreen = new Point();
            eventLocationOnScreen = e.getLocationOnScreen();

            if (is_movable == true) {
                e.getComponent().setLocation(eventLocationOnScreen.x - ComponentPoint.x,
                        eventLocationOnScreen.y - ComponentPoint.y);
            }
        }

        public void mouseMoved(MouseEvent e) {
        }
    }

    public class foundamental_thread implements Runnable {

        public void run() {
            while (true) {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                now = sdf.format(date);
                repaint();
            }
        }
    }

}



