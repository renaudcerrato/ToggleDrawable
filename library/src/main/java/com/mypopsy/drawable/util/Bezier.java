package com.mypopsy.drawable.util;

import android.graphics.Path;

import static java.lang.Math.PI;
import static java.lang.Math.tan;

public class Bezier {

    public final Point p1 = new Point(), p2 = new Point();
    public final Point e1 = new Point(), e2 = new Point();

    public Bezier() { }

    public Bezier(Bezier bezier) {
        p1.set(bezier.p1);
        p2.set(bezier.p2);
        e1.set(bezier.e1);
        e2.set(bezier.e2);
    }

    public Bezier offset(float x, float y) {
        p1.offset(x, y);
        p2.offset(x, y);
        e1.offset(x, y);
        e2.offset(x, y);
        return this;
    }

    public Bezier rotate(float x, float y, float degree) {
        p1.rotate(x, y, degree);
        p2.rotate(x, y, degree);
        e1.rotate(x, y, degree);
        e2.rotate(x, y, degree);
        return this;
    }

    public Bezier addTo(Path path) {
        path.moveTo(p1.x, p1.y);
        path.cubicTo(e1.x, e1.y, e2.x, e2.y, p2.x, p2.y);
        return this;
    }

    public Bezier swap() {
        Point tmp = new Point();
        tmp.set(p1); p1.set(p2); p2.set(tmp);
        tmp.set(e1); e1.set(e2); e2.set(tmp);
        return this;
    }

    public static Bezier line(float x1, float y1, float x2, float y2) {
        Bezier bezier = new Bezier();
        bezier.p1.set(x1, y1);
        bezier.e1.set((x1 + x2) / 2, (y1 + y2) / 2);
        bezier.e2.set(bezier.e1);
        bezier.p2.set(x2, y2);
        return bezier;
    }

    /**
     * Construct a quadrant from a bezier line.
     * @param radius radius in pixel of the quadrant
     * @param startDegree start angle, sweep is clockwise
     * @return a quadrant
     */
    public static Bezier quadrant(float radius, float startDegree) {
        float dv = (float) ((4f/3f)* tan(PI / 8)) * radius;
        Bezier bezier = new Bezier();

        bezier.p1.set(radius, 0);
        bezier.p2.set(0, radius);
        bezier.e1.set(bezier.p1.x, bezier.p1.y + dv);
        bezier.e2.set(bezier.p2.x + dv, bezier.p2.y);
        if(startDegree != 0) bezier.rotate(0, 0, startDegree);
        return bezier;
    }
}