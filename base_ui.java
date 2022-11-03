import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Class that contains all the basic drawing elements.
class WorkBench extends JPanel
        implements MouseWheelListener, ActionListener {

    JButton zoomIN;
    JButton zoomOUT;

    public static int originX = 0;
    public static int originY = 0;
    public static int delta = 8;
    public static int height = 0;
    public static int width = 0;

    Color background = new Color(32, 33, 36);
    Color foreground = new Color(61, 64, 67);
    Color dark_lines = new Color(14, 15, 16);

    Insets ins;

    WorkBench() {
        addMouseWheelListener(this);
        zoomIN = new JButton("Zoom In");
        zoomOUT = new JButton("Zoom Out");
        zoomIN.setBounds(0, 0, 20, 20);
        zoomOUT.setBounds(0, 0, 20, 20);
        add(zoomIN);
        add(zoomOUT);
        zoomIN.addActionListener(this);
        zoomIN.setActionCommand("Zoomed In");
        zoomOUT.addActionListener(this);
        zoomOUT.setActionCommand("Zoomed Out");
    }

    // Just plots the grid and labelling for the grid.
    public void makeGrid(Graphics G) {
        int yCoord = 0;

        G.setFont(new Font("monospace", Font.BOLD, delta / 6));

        // Lines towards the right half
        for (int i = originX; i < originX * 2 + width - ins.right; i += delta) {
            G.setColor(background);
            G.drawLine(i, originY * 2 - height, i, originY * 2 + height);
            if (delta > 30 && i != originX) {
                G.drawString(String.valueOf(yCoord), i - delta / 6, originY + delta / 6);

            }
            yCoord++;
        }
        yCoord = 0;

        // Lines towards the left half
        for (int i = originX; i > originX * 2 - width - ins.left; i -= delta) {
            G.setColor(background);
            G.drawLine(i, originY * 2 - height, i, originY * 2 + height);
            if (delta > 30 && i != originX) {
                G.drawString(String.valueOf(yCoord), i + delta / 12, originY + delta / 6);

            }
            yCoord--;
        }

        // Lines towards the bottom half
        int xCoord = 0;
        for (int i = originY; i < originY * 2 + height - ins.top; i += delta) {
            G.setColor(background);
            G.drawLine(originX * 2 - width, i, originX * 2 + width, i);
            if (delta > 30 && i != originY) {
                G.drawString(String.valueOf(-1 * xCoord), originX + delta / 12, i + delta / 6);
            }
            xCoord++;

        }

        // Lines towards the top half
        xCoord = 0;
        for (int i = originY; i > originY * 2 - height - ins.bottom; i -= delta) {
            G.setColor(background);
            G.drawLine(originX * 2 - width, i, originX * 2 + width, i);
            if (delta > 30 && i != originY) {
                G.drawString(String.valueOf(-1 * xCoord), originX + delta / 6, i + delta / 6);
            }
            xCoord--;
        }

        G.setColor(background);
        if (delta > 30)
            G.drawString("(0,0)", originX + delta / 2, originY + delta / 2);
    }

    protected void paintComponent(Graphics G) {
        super.paintComponent(G);
        StdDrawing std = new StdDrawing();
        OtherParts op = new OtherParts();
        height = getHeight();
        width = getWidth();

        ins = getInsets();

        originX = (width - ins.left - ins.right) / 2;
        originY = (height - ins.top - ins.bottom) / 2;

        setBackground(foreground);

        if (delta > 5)
            makeGrid(G);

        std.plotOrigin(G);

        G.setColor(Color.green);
        int x1 = 10;
        int y1 = 10;
        int radius = 60;
        RotationalTransforms rt = new RotationalTransforms(x1, y1 + radius, 35);
        new Body(x1, y1, radius / 2, radius, SpotType.SPOTTED, HairType.HAIRLESS, rt, G);
        op.head(radius / 2, x1, y1 + 3 * radius / 2, 4, 2, G);

        int leg1px = rt.rotateX(x1, y1 - radius);
        int leg1py = rt.rotateY(x1, y1 - radius);
        int leg2px = rt.rotateX(x1 - 5, y1 - radius + 10);
        int leg2py = rt.rotateY(x1 - 5, y1 - radius + 10);

        G.setColor(Color.yellow);
        new MovableParts(leg1px, leg1py, radius / 3, 3 * radius / 2, SpotType.SPOTTED, HairType.HAIRLESS, 0, 5, G);
        new MovableParts(leg2px, leg2py, radius / 3, 3 * radius / 2, SpotType.SPOTTED, HairType.HAIRLESS, -20, 5, G);

        int hand1px = rt.rotateX(x1 - radius / 2 + 10, y1 + radius / 2);
        int hand1py = rt.rotateY(x1 - radius / 2 + 10, y1 + radius / 2);
        int hand2px = rt.rotateX(x1 - radius / 2 + 10, y1 + radius / 2 + 10);
        int hand2py = rt.rotateY(x1 - radius / 2 + 10, y1 + radius / 2 + 10);

        new MovableParts(hand1px, hand1py, radius / 4, radius, SpotType.SPOTTED, HairType.HAIRLESS, -45, -90, G);
        new MovableParts(hand2px, hand2py, radius / 4, radius, SpotType.SPOTTED, HairType.HAIRLESS, -75, -120, G);

        int tailX = rt.rotateX(x1 + 17, y1 - radius);
        int tailY = rt.rotateY(x1 + 17, y1 - radius);
        rt.setPivot(tailX, tailY);
        rt.setAngle(-10);
        new Body(tailX, tailY, radius / 3, radius / 8, SpotType.SPOTLESS, HairType.HAIRY, rt, G);
    }

    // driving the zooming using Mouse Wheel movement
    public void mouseWheelMoved(MouseWheelEvent e) {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            delta = delta + 10;
            delta = delta > 150 ? 150 : delta;
        } else {
            delta = delta - 10;
            delta = delta < 5 ? 5 : delta;
        }
        repaint();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand() == "Zoomed In") {
            delta = delta + 5;
            delta = delta > 150 ? 150 : delta;
        }

        if (ae.getActionCommand() == "Zoomed Out") {
            delta = delta - 5;
            delta = delta < 1 ? 1 : delta;
        }
        repaint();
    }
}

// Class where the UI is organized and managed.
class Base_UI {

    JLabel jlab;
    WorkBench canvas;

    Base_UI() {
        JFrame jfrm = new JFrame("Paint Demo");

        jfrm.setSize(200, 150);
        jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        canvas = new WorkBench();

        jfrm.add(canvas);
        jfrm.setVisible(true);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Base_UI();
            }
        });
    }
}