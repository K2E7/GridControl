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

    // plots a Line using Midpoint Line Drawing Algorithm
    public void plotLine(int x0, int y0, int x1, int y1, int deg, Graphics G) {
        int x_0, x_1, y_0, y_1;
        if (x0 > x1 && y0 > y1) {
            x_0 = x1;
            x_1 = x0;
            y_0 = y1;
            y_1 = y0;
        } else {
            x_0 = x0;
            x_1 = x1;
            y_0 = y0;
            y_1 = y1;
        }

        RotationalTransforms rot = new RotationalTransforms((x_1 + x_0) / 2, (y_0 + y_1) / 2, deg);

        int dx = x_1 - x_0;
        int dy = y_1 - y_0;
        
        int d = dy - dx / 2;

        int incrE = dy;
        int incrNE = (dy - dx);

        int x = x_0;
        int y = y_0;
        G.setColor(Color.RED);
        plotpoint((int) rot.rotateX(x, y), (int) rot.rotateY(x, y), G);

        while (x < x_1) {
            if (d <= 0) {
                d += incrE;
            } else {
                d += incrNE;
                y++;
            }
            x++;
            plotpoint((int) rot.rotateX(x, y), (int) rot.rotateY(x, y), G);
        }
    }

    public void plotLine(int x0, int y0, int x1, int y1, Graphics G) {
        plotLine(x0, y0, x1, y1, 0, G);
    }
}
