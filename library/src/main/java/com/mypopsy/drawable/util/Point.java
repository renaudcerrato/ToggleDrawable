package com.mypopsy.drawable.util;

public class Point {
    public float x,y;

    public Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(Point center, float angle) {
        rotate(center.x, center.y, angle);
    }

    public final void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public final void set(Point p) {
        this.x = p.x;
        this.y = p.y;
    }

    public final void offset(float dx, float dy) {
        x += dx;
        y += dy;
    }

    public void rotate(float centerX, float centerY, float degree) {
        float newX = (float) (centerX + (Math.cos(Math.toRadians(degree)) * (x - centerX) - Math.sin(Math.toRadians(degree)) * (y - centerY)));
        float newY = (float) (centerY + (Math.sin(Math.toRadians(degree)) * (x - centerX) + Math.cos(Math.toRadians(degree)) * (y - centerY)));
        this.x = newX;
        this.y = newY;
    }

    @Override
    public String toString() {
        return super.toString()+ "{"+x+","+y+"}";
    }
}