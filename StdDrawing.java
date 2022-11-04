import java.awt.*;
import java.lang.Math;

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
    public void plotEllipse(int rx, int ry, int xc, int yc, int angle, Graphics G) {
        double dx, dy, d1, d2, x, y;
        double rot = 3.14 * (angle % 360) / 180;
        x = 0;
        y = ry;
        // Initial decision parameter of region 1
        d1 = (ry * ry) - (rx * rx * ry) + (0.25 * rx * rx);

        dx = 2 * ry * ry * x;
        dy = 2 * rx * rx * y;
        // For region 1
        while (dx < dy) {

            // Print points based on 4-way symmetry
            plotpoint((int) ((x * Math.cos(rot) - y * Math.sin(-rot)) + xc),
                    (int) ((x * Math.sin(-rot) + y * Math.cos(rot)) + yc), G);
            plotpoint((int) (-(x * Math.cos(rot) - y * Math.sin(rot)) + xc),
                    (int) ((x * Math.sin(rot) + y * Math.cos(rot)) + yc), G);
            plotpoint((int) ((x * Math.cos(rot) - y * Math.sin(rot)) + xc),
                    (int) (-(x * Math.sin(rot) + y * Math.cos(rot)) + yc), G);
            plotpoint((int) (-(x * Math.cos(rot) - y * Math.sin(-rot)) + xc),
                    (int) (-(x * Math.sin(-rot) + y * Math.cos(rot)) + yc), G);

            // Checking and updating value of
            // decision parameter based on algorithm
            if (d1 < 0) {
                x++;
                dx = dx + (2 * ry * ry);
                d1 = d1 + dx + (ry * ry);
            } else {
                x++;
                y--;
                dx = dx + (2 * ry * ry);
                dy = dy - (2 * rx * rx);
                d1 = d1 + dx - dy + (ry * ry);
            }
        }

        // Decision parameter of region 2
        d2 = ((ry * ry) * ((x + 0.5) * (x + 0.5))) + ((rx * rx) * ((y - 1) * (y - 1))) - (rx * rx * ry * ry);
        // Plotting points of region 2
        while (y >= 0) {

            // printing points based on 4-way symmetry
            plotpoint((int) ((x * Math.cos(-rot) - y * Math.sin(-rot)) + xc),
                    (int) ((x * Math.sin(-rot) + y * Math.cos(-rot)) + yc), G);
            plotpoint((int) (-(x * Math.cos(rot) - y * Math.sin(rot)) + xc),
                    (int) ((x * Math.sin(rot) + y * Math.cos(rot)) + yc), G);
            plotpoint((int) ((x * Math.cos(rot) - y * Math.sin(rot)) + xc),
                    (int) (-(x * Math.sin(rot) + y * Math.cos(rot)) + yc), G);
            plotpoint((int) (-(x * Math.cos(-rot) - y * Math.sin(-rot)) + xc),
                    (int) (-(x * Math.sin(-rot) + y * Math.cos(-rot)) + yc), G);

            // Checking and updating parameter
            // value based on algorithm
            if (d2 > 0) {
                y--;
                dy = dy - (2 * rx * rx);
                d2 = d2 + (rx * rx) - dy;
            } else {
                y--;
                x++;
                dx = dx + (2 * ry * ry);
                dy = dy - (2 * rx * rx);
                d2 = d2 + dx - dy + (rx * rx);
            }
        }
        // Region 2 //

    }

    // Using degree = 0 if not mentioned.
    public void plotEllipse(int a, int b, int xc, int yc, Graphics G) {
        plotEllipse(a, b, xc, yc, 0, G);
    }

    // plots a Circle based on Ellipse Drawing Algorithm
    public void plotCircle(int r, int xc, int yc, Graphics G) {
        plotEllipse(r, r, xc, yc, G);
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

        int total_steps = Math.max(Math.abs(dx), Math.abs(dy));

        float x_incr = (float) dx / total_steps;
        float y_incr = (float) dy / total_steps;
        float x = x_0;
        float y = y_0;

        for (int i = 0; i <= total_steps; i++) {
            plotpoint((int) rot.rotateX((int) x, (int) y), (int) rot.rotateY((int) x, (int) y), G);
            x += x_incr;
            y += y_incr;
        }
    }

    // plot the default line with degree 0
    public void plotLine(int x0, int y0, int x1, int y1, Graphics G) {
        int dy, dx, px, py, steps;
        double yt, xt, yk, xk;
        dy = y1 - y0;
        dx = x1 - x0;
        if (Math.abs(dy) > Math.abs(dx)) {
            steps = Math.abs(dy);
        } else {
            steps = Math.abs(dx);
        }
        yk = (double) dy / steps;
        xk = (double) dx / steps;
        yt = y0;
        xt = x0;
        for (int temp = 0; temp <= steps; temp++) {
            py = (int) Math.round(yt);
            px = (int) Math.round(xt);
            yt += yk;
            xt += xk;
            plotpoint(px, py, G);
        }
    }

    // plots a triangle using plotLines with added rotational support
    public void plotTriangle1(int base, int height, int xc, int yc, int angle, Graphics G) {
        double rot = 3.14 * (angle % 360) / 180;
        int xt1 = -base / 2;
        int xt2 = base / 2;
        int yt1 = -height / 2;
        int yt2 = height / 2;

        plotLine((int) ((xt1 * Math.cos(rot) - yt1 * Math.sin(rot)) + xc),
                (int) ((xt1 * Math.sin(rot) + yt1 * Math.cos(rot)) + yc),
                (int) ((xt2 * Math.cos(rot) - yt1 * Math.sin(rot)) + xc),
                (int) ((xt2 * Math.sin(rot) + yt1 * Math.cos(rot)) + yc), G);
        plotLine((int) ((xt1 * Math.cos(rot) - yt1 * Math.sin(rot)) + xc),
                (int) ((xt1 * Math.sin(rot) + yt1 * Math.cos(rot)) + yc), (int) ((-yt2 * Math.sin(rot)) + xc),
                (int) ((yt2 * Math.cos(rot)) + yc), G);
        plotLine((int) ((xt2 * Math.cos(rot) - yt1 * Math.sin(rot)) + xc),
                (int) ((xt2 * Math.sin(rot) + yt1 * Math.cos(rot)) + yc), (int) ((-yt2 * Math.sin(rot)) + xc),
                (int) ((yt2 * Math.cos(rot)) + yc), G);
    }

    // plots a triangle with default deg=0
    public void plotTriangle2(int base, int height, int xc, int yc, int angle, Graphics G) {
        double rot = 3.14 * (angle % 360) / 180;
        int xt1 = 0;
        int xt2 = base / 2;
        int yt1 = -height / 2;
        int yt2 = height / 2;

        plotLine((int) ((xt1 * Math.cos(rot) - yt1 * Math.sin(rot)) + xc),
                (int) ((xt1 * Math.sin(rot) + yt1 * Math.cos(rot)) + yc),
                (int) ((xt2 * Math.cos(rot) - yt1 * Math.sin(rot)) + xc),
                (int) ((xt2 * Math.sin(rot) + yt1 * Math.cos(rot)) + yc), G);
        plotLine((int) ((xt1 * Math.cos(rot) - yt1 * Math.sin(rot)) + xc),
                (int) ((xt1 * Math.sin(rot) + yt1 * Math.cos(rot)) + yc), (int) ((-yt2 * Math.sin(rot)) + xc),
                (int) ((yt2 * Math.cos(rot)) + yc), G);
        plotLine((int) ((xt2 * Math.cos(rot) - yt1 * Math.sin(rot)) + xc),
                (int) ((xt2 * Math.sin(rot) + yt1 * Math.cos(rot)) + yc), (int) ((-yt2 * Math.sin(rot)) + xc),
                (int) ((yt2 * Math.cos(rot)) + yc), G);
    }
}
