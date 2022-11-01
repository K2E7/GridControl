import java.awt.*;
// Class where all the Shape Drawing and plotting algorithms are stored.
public class StdDrawing extends WorkBench {
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
