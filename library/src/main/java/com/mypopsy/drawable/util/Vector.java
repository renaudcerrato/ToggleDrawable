package com.mypopsy.drawable.util;

public class Vector {
    public float x, y;

    public Vector(Point a, Point b) {
        x = b.x - a.x;
        y = b.y - a.y;
    }

    /**
     *
     * @param vector to compare
     * @return the relative angle between [-pi..pi]
     */
    public double angle(Vector vector) {
        double angle = Math.atan2(vector.y, vector.x) - Math.atan2(y, x);
        if(angle > Math.PI) angle-=2*Math.PI;
        if(angle < -Math.PI) angle+=2*Math.PI;
        return angle;
    }

    @Override
    public String toString() {
        return super.toString()+ "{"+x+","+y+"}";
    }
}
