public class RotationalTransforms {
    int ox, oy, angle;

    public RotationalTransforms(int ox, int oy, int angle) {
        this.ox = ox;
        this.oy = oy;
        this.angle = angle;
    }

    public int rotateX(int x, int y) {
        double angle = Math.PI / 180 * this.angle;
        double px = x * Math.cos(angle) - y * Math.sin(angle) + ox * (1.0 - Math.cos(angle)) + oy * Math.sin(angle);
        return (int) px;
    }

    public int rotateY(int x, int y) {
        double angle = Math.PI / 180 * this.angle;
        double py = x * Math.sin(angle) + y * Math.cos(angle) + oy * (1.0 - Math.cos(angle)) - ox * Math.sin(angle);

        return (int) py;
    }

    public int getAngle() {
        return this.angle;
    }

    public boolean setAngle(int angle) {
        if (angle < 0 || angle > 360)
            return false;
        this.angle = angle;
        return true;
    }

    public boolean setPivot(int pX, int pY) {
        this.ox = pX;
        this.oy = pY;
        return true;
    }
}
