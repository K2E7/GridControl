import java.awt.*;

// Class where all the Shape Drawing and plotting algorithms are stored.
public class StdDrawing extends WorkBench {

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

    // plots an Ellipse using Midpoint Ellipse Drawing Algorithm.
    public void plotEllipse(int a, int b, int xc, int yc, int deg, Graphics G) {
        RotationalTransforms rot = new RotationalTransforms(xc, yc, deg);
        double d2;

        int x = 0;
        int y = b;

        double d1 = b * b - a * a * b + 0.25 * a * a;
        plotpoint((int) rot.rotateX(x + xc, y + yc), (int) rot.rotateY(x + xc, y + yc), G);
        plotpoint((int) rot.rotateX(-x + xc, y + yc), (int) rot.rotateY(-x + xc, y + yc), G);
        plotpoint((int) rot.rotateX(x + xc, -y + yc), (int) rot.rotateY(x + xc, -y + yc), G);
        plotpoint((int) rot.rotateX(-x + xc, -y + yc), (int) rot.rotateY(-x + xc, -y + yc), G);

        while (a * a * (y - 0.5) > b * b * (x + 1)) {
            if (d1 < 0) {
                d1 += b * b * (2 * x + 3);
            } else {
                d1 += b * b * (2 * x + 3) + a * a * (-2 * y + 2);
                y--;
            }
            x++;
            plotpoint((int) rot.rotateX(x + xc, y + yc), (int) rot.rotateY(x + xc, y + yc), G);
            plotpoint((int) rot.rotateX(-x + xc, y + yc), (int) rot.rotateY(-x + xc, y + yc), G);
            plotpoint((int) rot.rotateX(x + xc, -y + yc), (int) rot.rotateY(x + xc, -y + yc), G);
            plotpoint((int) rot.rotateX(-x + xc, -y + yc), (int) rot.rotateY(-x + xc, -y + yc), G);
        } // Region 1 //

        d2 = b * b * (x + 0.5) * (x + 0.5) + a * a * (y - 1) * (y - 1) - a * a * b * b;
        while (y > 0) {
            if (d2 < 0) {
                d2 += b * b * (2 * x + 2) + a * a * (-2 * y + 3);
                x++;
            } else
                d2 += a * a * (-2 * y + 3);
            y--;
            plotpoint((int) rot.rotateX(x + xc, y + yc), (int) rot.rotateY(x + xc, y + yc), G);
            plotpoint((int) rot.rotateX(-x + xc, y + yc), (int) rot.rotateY(-x + xc, y + yc), G);
            plotpoint((int) rot.rotateX(x + xc, -y + yc), (int) rot.rotateY(x + xc, -y + yc), G);
            plotpoint((int) rot.rotateX(-x + xc, -y + yc), (int) rot.rotateY(-x + xc, -y + yc), G);
        } // Region 2 //

    }

    // Using degree = 0 if not mentioned.
    public void plotEllipse(int a, int b, int xc, int yc, Graphics G) {
        plotEllipse(a, b, xc, yc, 0, G);
    }

    // plots a Line using Midpoint Line Drawing Algorithm.
    public void plotLine(int x0, int y0, int x1, int y1, Graphics G) {

        int dx = x1 - x0;
        int dy = y1 - y0;
        int d = dy - dx / 2;

        int incrE = dy;
        int incrNE = (dy - dx);

        int x = x0;
        int y = y0;
        plotpoint(x, y, G);

        while (x < x1) {
            if (d <= 0) {
                d += incrE;
            } else {
                d += incrNE;
                y++;
            }
            x++;
            plotpoint(x, y, G);
        }
    } // MidPoint Line //
}
