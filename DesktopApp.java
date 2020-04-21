import java.awt.Color;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.*;

class DesktopApp extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Image MainCharaImg;
    boolean is_closeButton_pressed = false;
    boolean is_movable = false;
    boolean is_timestamp_hover = false;
    boolean is_menu_hover = false;
    boolean is_menu_available = false;
    Point mouse_position = new Point();
    Point rltPnt = new Point();
    Point charaPnt = new Point();
    BufferedImage buffer;
    BufferedImage object;
    int screenWidth, screenHeight;
    int charaWidth = 800, charaHeight = 800;
    int timeFrameWidth, timeFrameHeight;
    Point timeFramePosition;
    int timeFrameOutline = 2;
    final int screenMargin = 1;

    String now = new String("This is initialized string.");

    DesktopApp() {
        screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width - screenMargin * 2;
        screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height - screenMargin * 2;
        timeFrameWidth = (int) (screenWidth * 0.2);
        timeFrameHeight = (int) (screenHeight * 0.07);
        timeFramePosition = new Point((int) (screenWidth * 0.75), (int) (screenHeight * 0.9));
        // set drawable windowsize
        setSize(screenWidth, screenHeight);
        // set window location
        setLocation(screenMargin, screenMargin);
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

        buffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        object = createObjectImage();

        foundamental_thread f_runnable = new foundamental_thread();
        Thread f_Thread = new Thread(f_runnable);
        f_Thread.start();

        // set window visible
        setVisible(true);
    }

    private BufferedImage createObjectImage() {
        BufferedImage img = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        return img;
    }

    public void paint(Graphics g) {

        Graphics2D g2 = (Graphics2D) buffer.getGraphics();
        g2.setBackground(new Color(0, 0, 0, 0));
        g2.clearRect(0, 0, screenWidth, screenHeight);
        drawTime();
        if (is_menu_available) {
            drawMenu();
        }
        g2.translate(0, 0);
        g2.drawImage(object, 0, 0, this);

        ((Graphics2D) g).setBackground(new Color(0, 0, 0, 0));
        g.clearRect(0, 0, screenWidth, screenHeight);
        g.drawImage(buffer, screenMargin, screenMargin, this);

    }

    void drawTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        now = sdf.format(Calendar.getInstance().getTime());
        Graphics2D g = (Graphics2D) buffer.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(new Color(0, 0, 255));
        g.drawRect((int) timeFramePosition.getX() - timeFrameOutline, (int) timeFramePosition.getY() - timeFrameOutline,
                timeFrameWidth + 2 * timeFrameOutline, timeFrameHeight + 2 * timeFrameOutline);
        g.setColor(new Color(0x6f, 0xa8, 0xdc, 255));
        g.fillRect((int) timeFramePosition.getX(), (int) timeFramePosition.getY(), timeFrameWidth, timeFrameHeight);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 30);
        g.setFont(f);
        g.setColor(new Color(0, 0, 0));
        FontMetrics metrics = g.getFontMetrics(f);
        int timeStampWidth = metrics.stringWidth(now);
        g.drawString(now, (int) (timeFramePosition.getX() + timeFrameWidth / 2 - timeStampWidth / 2),
                (int) (timeFramePosition.getY() + timeFrameHeight / 2));
    }

    void drawMenu() {

        String dev_tmp_menu_item = "Menu1";

        Graphics2D g = (Graphics2D) buffer.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 25);
        g.setFont(f);
        g.setColor(new Color(80, 80, 255));
        FontMetrics metrics = g.getFontMetrics(f);
        int menuItemWidth = metrics.stringWidth(dev_tmp_menu_item);
        g.drawString(dev_tmp_menu_item, (int) (timeFramePosition.getX() + timeFrameWidth - menuItemWidth),
                (int) (timeFramePosition.getY() - 10));

        return;
    }

    public static void main(String args[]) {
        new DesktopApp();
    }

    public class myListener extends MouseAdapter {
        Point ComponentPoint = new Point();

        public void mouseClicked(MouseEvent e) {
            System.out.println("mouseclicked");
            if (is_timestamp_hover) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    System.exit(0);
            }

        }

        public void mousePressed(MouseEvent e) {

            /*
             * if you want to drag paint, use code below.(#1)
             */
            // if (e.getButton() == MouseEvent.BUTTON1) {
            // is_movable = true;
            // rltPnt.x = mouse_position.x - charaPnt.x;
            // rltPnt.y = mouse_position.y - charaPnt.y;
            // }
        }

        public void mouseReleased(MouseEvent e) {
            is_movable = false;
        }

        public void mouseDragged(MouseEvent e) {

            /*
             * (#1)
             */
            // if (is_movable) {
            // charaPnt.x = mouse_position.x - rltPnt.x;
            // charaPnt.y = mouse_position.y - rltPnt.y;
            // }

            /*
             * if you want to drag component, use code below.
             */
            // Point eventLocationOnScreen = new Point();
            // eventLocationOnScreen = e.getLocationOnScreen();
            // if (is_movable == true) {
            // e.getComponent().setLocation(eventLocationOnScreen.x - ComponentPoint.x,
            // eventLocationOnScreen.y - ComponentPoint.y);
            // }
        }

        public void mouseMoved(MouseEvent e) {

        }
    }

    public class foundamental_thread implements Runnable {

        public void run() {
            while (true) {
                mouse_position = MouseInfo.getPointerInfo().getLocation();
                is_timestamp_hover = isInBounds(mouse_position, timeFramePosition, timeFrameWidth, timeFrameHeight);
                if (is_timestamp_hover) {
                    is_menu_available = true;
                }
                if (!(isInBounds(mouse_position,
                            new Point((int) timeFramePosition.getX(), (int) timeFramePosition.getY() - 30),
                            new Point((int) timeFramePosition.getX() + timeFrameWidth, (int) timeFramePosition.getY() + 10))) && !is_timestamp_hover){
                    is_menu_available = false;
                }
                repaint();

                try {
                    Thread.sleep(20l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        private boolean isInBounds(Point objectPoint, Point lefttop, Point rightbottom) {
            if ((lefttop.getX() < objectPoint.getX()) && (objectPoint.getX() < rightbottom.getX())
                    && (lefttop.getY() < objectPoint.getY()) && (objectPoint.getY() < rightbottom.getY())) {
                return true;
            } else {
                return false;
            }
        }

        private boolean isInBounds(Point objectPoint, Point lefttop, int width, int height) {
            Point rightbottom = new Point((int) (lefttop.getX() + width), (int) (lefttop.getY() + height));
            return isInBounds(objectPoint, lefttop, rightbottom);
        }

    }

}
