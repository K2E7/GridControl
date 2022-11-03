import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

enum HairType {
    HAIRLESS, HAIRY;
}

enum Size {
    BIG, SMALL, LONG, MEDIUM, SHORT;
}

enum SpotType {
    SPOTTED, SPOTLESS;
}

class Body {
    int centerX, centerY, radiusX, radiusY;
    int spots_no = 15;
    int hairs_no = 25;
    StdDrawing std = new StdDrawing();

    Body(int centerX, int centerY, int radiusX, int radiusY, SpotType st, HairType ht, RotationalTransforms rot,
            Graphics G) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        std.plotEllipse(radiusY, centerY, radiusX, radiusY, rot.getAngle(), G);
        if (st == SpotType.SPOTTED)
            createSpot(G, rot);
        if (ht == HairType.HAIRY)
            createHair(G, rot);
    }

    void createSpot(Graphics G, RotationalTransforms rot) {
        for (int i = 0; i < spots_no; i++) {
            int x = RandomX();
            int y = RandomY();
            int px = rot.rotateX(x, y);
            int py = rot.rotateY(x, y);

            std.plotEllipse(2, 2, px, py, G);
        }
    }

    void createHair(Graphics G, RotationalTransforms rot) {
        int dirx = 1, dx = 2, dy = 3, diry = 1;
        int[] d = { -1, 1 };
        for (int i = 0; i < hairs_no; i++) {
            int wRx = RandomX();
            int wRy = RandomY();

            int px = rot.rotateX(wRx, wRy);
            int py = rot.rotateY(wRx, wRy);

            std.plotLine(px, py, px + dirx * dx, py + diry * dy, G);
            dirx = d[ThreadLocalRandom.current().nextInt(0, 2)];
            diry = d[ThreadLocalRandom.current().nextInt(0, 2)];
        }
    }

    int RandomX() {

        double rho = ThreadLocalRandom.current().nextDouble(0, 1);
        double phi = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);

        double x = Math.sqrt(rho) * Math.cos(phi);
        x = x * (radiusX - 2);

        return centerX + (int) x;
    }

    int RandomY() {
        double rho = ThreadLocalRandom.current().nextDouble(0, 1);
        double phi = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);

        double y = Math.sqrt(rho) * Math.sin(phi);
        y = y * (radiusY - 2);

        return centerY + (int) y;
    }
}

class OtherParts {
    StdDrawing obj = new StdDrawing();

    public void beak(int choice_beak, int radius, int x_centre, int y_centre, Graphics G) {
        // 1: small beak without teeth
        // 2: smalll beak with teeth
        // 3: large beak without teeth
        // 4: large beak with teeth
        int pivot_x, pivot_y;
        int x1, y1, x2, y2, x3, y3, x4, y4;
        x1 = x_centre - (int) (radius * Math.cos(Math.toRadians(10)));
        y1 = y_centre + (int) (radius * Math.sin(Math.toRadians(10)));
        x2 = x_centre - (int) ((radius * Math.cos(Math.toRadians(35))));
        y2 = y_centre + (int) (radius * Math.sin(Math.toRadians(35)));
        x3 = x_centre - (int) (radius * Math.cos(Math.toRadians(10)));
        y3 = y_centre - (int) (radius * Math.sin(Math.toRadians(10)));
        x4 = x_centre - (int) ((radius * Math.cos(Math.toRadians(35))));
        y4 = y_centre - (int) (radius * Math.sin(Math.toRadians(35)));
        pivot_y = y1;
        pivot_x = x1 - (int) (radius * 0.7);
        if (choice_beak <= 2) {
            obj.plotLine(x1, y1, pivot_x, pivot_y, G);
            obj.plotLine(x2, y2, pivot_x, pivot_y, G);
            if (choice_beak % 2 == 0) {
                tooth(pivot_x, pivot_y, x1, y1, radius, -1, G);
            }
            pivot_y = y3;
            obj.plotLine(x3, y3, pivot_x, pivot_y, G);
            obj.plotLine(x4, y4, pivot_x, pivot_y, G);
            if (choice_beak % 2 == 0) {
                tooth(pivot_x + 3, pivot_y, x3, y3, radius, 1, G);
            }
        } else {
            pivot_x = x1 - (int) (radius * 1.5);
            obj.plotLine(x1, y1, pivot_x, pivot_y, G);
            obj.plotLine(x2, y2, pivot_x, pivot_y, G);
            if (choice_beak % 2 == 0) {
                tooth(pivot_x, pivot_y, x1, y1, radius, -1, G);
            }
            pivot_y = y3;
            obj.plotLine(x3, y3, pivot_x, pivot_y, G);
            obj.plotLine(x4, y4, pivot_x, pivot_y, G);
            if (choice_beak % 2 == 0) {
                tooth(pivot_x + 3, pivot_y, x3, y3, radius, 1, G);
            }
        }
    }

    public void ear(int choice_ear, int radius, int x_centre, int y_centre, Graphics G) {
        // 1: triangle ear
        // 2: circle ear
        int pivot_x, pivot_y;
        int x1, y1, x2, y2;
        switch (choice_ear) {
            case 1:
                x1 = x_centre - 5 * radius;
                y1 = y_centre - 5 * radius;
                x2 = x1;
                y2 = y1;
                x1 = x1 + (int) (5 * radius * Math.cos(Math.toRadians(5)));
                y1 = y1 + (int) (5 * radius * Math.sin(Math.toRadians(5)));
                x2 = x2 + (int) (5 * radius * Math.cos(Math.toRadians(55)));
                y2 = y2 + (int) (5 * radius * Math.sin(Math.toRadians(55)));
                pivot_x = (int) (x1 + 3 * x2) / 2;
                pivot_y = y_centre + radius;
                obj.plotLine(x1, y1, pivot_x, pivot_y, G);
                obj.plotLine(x2, y2, pivot_x, pivot_y, G);
                x1 = x_centre - 5 * radius;
                y1 = y_centre - 5 * radius;
                x1 = x1 - (int) (5 * radius * Math.cos(Math.toRadians(75)));
                y1 = y1 + (int) (5 * radius * Math.sin(Math.toRadians(75)));
                pivot_x = (int) (x1 + x2) / 2;
                pivot_y = pivot_y + (int) 2 * radius;
                obj.plotLine(x1, y1, pivot_x, pivot_y, G);
                obj.plotLine(x2, y2, pivot_x, pivot_y, G);
                break;
            default:
                obj.plotCircle(radius + radius / 4, x_centre - radius, y_centre - radius, G);
                obj.plotCircle(radius + radius / 2, x_centre + radius, (int) (y_centre - 2 * radius), G);
                break;
        }
    }

    public void tooth(int x1, int y1, int x2, int y2, int radius, int up, Graphics G) {
        // x1, y1 is smaller
        // up=+1 or -1  `1q
        while (x1 <= x2) {
            obj.plotLine(x1, y1, x1, y1 + (int) ((radius / 10) * up), G);
            x1 = x1 + 5;
        }
    }

    public void head(int radius, int x_centre, int y_centre, int choice_beak, int choice_ear, Graphics G) {
        obj.plotCircle(radius, x_centre, y_centre, G);
        // head
        int eye_r = (int) (radius / 4);
        // eye
        obj.plotCircle(eye_r, x_centre - eye_r, y_centre + eye_r / 2, G);
        ear(choice_ear, radius / 5, x_centre + radius, y_centre + radius, G);
        beak(choice_beak, radius, x_centre, y_centre, G);
    }
}

class Animal {
    Body upperHalf, lowerHalf, fist;
    Size sz;

    public Animal(int topX, int topY, int width, int height, SpotType st,
            HairType ht, int angle0, int angle1, Graphics G) {

        int rx1 = (int) (0.8 * width);
        int ry1 = (int) (0.4 * height);
        int cy1 = topY - ry1;
        int rx2 = (int) (0.6 * width);
        int ry2 = (int) (0.6 * height);
        int cy2 = cy1 - ry1 - ry2 + 3;

        RotationalTransforms rt1 = new RotationalTransforms(topX, topY, angle0);

        int p20 = rt1.rotateX(topX, cy2 + ry2);
        int p21 = rt1.rotateY(topX, cy2 + ry2);
        upperHalf = new Body(topX, cy1, rx1, ry1, st, ht, rt1, G);

        RotationalTransforms rt2 = new RotationalTransforms(p20, p21, angle1);
        int c22x = p20;
        int c22y = p21 - ry2;
        lowerHalf = new Body(c22x, c22y, rx2, ry2, st, ht, rt2, G);

        this.sz = sz;
        p21 -= 2 * ry2;

        int xp20 = p20;
        int xp21 = p21;
        p20 = rt2.rotateX(xp20, xp21);
        p21 = rt2.rotateY(xp20, xp21);

        rt1.setAngle(0);
        fist = new Body(p20 - 8, p21, 10, 4, st, ht, rt1, G);
    }
}