package com.mypopsy.drawable.model;

import com.mypopsy.drawable.util.Bezier;

public class SearchModel {
    static final int HANDLE_ANGLE = 45;

    public final Bezier topLeftQuadrant;
    public final Bezier topRightQuadrant;
    public final Bezier bottomLeftQuadrant;
    public final Bezier bottomRightQuadrant;
    public final Bezier handle;

    public SearchModel(float radius, float barLength) {

        float size = (float) ((2*radius + barLength)/Math.sqrt(2));
        float center = radius - size/2;

        // quadrants
        bottomRightQuadrant = Bezier.quadrant(radius, 0).offset(center, center);
        bottomLeftQuadrant = Bezier.quadrant(radius, 90).offset(center, center);
        topLeftQuadrant = Bezier.quadrant(radius, 180).offset(center, center);
        topRightQuadrant = Bezier.quadrant(radius, 270).offset(center, center);

        // handle
        handle = Bezier.line(0, 0, barLength, 0)
                .offset(radius, 0)
                .rotate(0, 0, HANDLE_ANGLE)
                .offset(center, center);

    }
}
