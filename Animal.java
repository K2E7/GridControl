import java.awt.*;
import javax.swing.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.ThreadLocalRandom.*;

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

    Body(int centerX, int centerY, int radiusX, int radiusY, SpotType st, HairType ht, RotationalTransforms rot, Graphics G) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        std.plotEllipse(radiusY, centerY, radiusX, radiusY, rot.getAngle(), G);
        if(st == SpotType.SPOTTED)
            createSpot(G, rot);
        if(ht == HairType.HAIRY)
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
        int[] d = {-1,1};
        for(int i=0; i<hairs_no; i++) {
            int wRx = RandomX();
            int wRy = RandomY();

            int px = rot.rotateX(wRx, wRy);
            int py = rot.rotateY(wRx, wRy);

            std.plotLine(px, py, px + dirx*dx, py + diry * dy, G);
            dirx = d[ThreadLocalRandom.current().nextInt(0,2)];
            diry = d[ThreadLocalRandom.current().nextInt(0,2)];
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