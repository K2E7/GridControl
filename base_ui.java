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

        add(zoomIN);
        add(zoomOUT);
        zoomIN.addActionListener(this);
        zoomIN.setActionCommand("Zoomed In");
        zoomOUT.addActionListener(this);
        zoomOUT.setActionCommand("Zoomed Out");
    }

    // plots a point with a settable color.
    public void plotpoint(int x, int y, Graphics G) {
        G.fillRect(originX + x * delta - delta / 2, originY - y * delta - delta / 2, delta, delta);
        // G.fillOval(originX+x*delta-delta/8,originY-y*delta-delta/8,delta/4,delta/4);
    }

    // plots a point with variable width...
    public void var_plotpoint(int x, int y, int px, Graphics G) {
        G.fillRect(originX + x * delta - delta / 8, originY - y * delta - delta / 8, px * delta / 4, px * delta / 4);
        // G.fillOval(originX+x*delta-delta/8,originY-y*delta-delta/8,delta/4,delta/4);
    }

    // plots origin in a preset color and non-modifiable variable size.
    public void plotOrigin(Graphics G) {
        G.setColor(background);
        G.fillOval(originX - delta / 2, originY - delta / 2, delta, delta);
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

        height = getHeight();
        width = getWidth();

        ins = getInsets();

        originX = (width - ins.left - ins.right) / 2;
        originY = (height - ins.top - ins.bottom) / 2;

        setBackground(foreground);

        if (delta > 5)
            makeGrid(G);

        G.setColor(Color.CYAN);
        plotpoint(0, 0, G);

        plotOrigin(G);

        G.setColor(Color.green);
        // plotpoint(originX + 1, originY + 1, G);

        std.plotEllipse(15, 30, 0, 0, G);
        std.plotEllipse(15, 30, 0, 0, 90, G);
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