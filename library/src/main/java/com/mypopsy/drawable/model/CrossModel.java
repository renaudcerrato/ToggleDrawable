package com.mypopsy.drawable.model;

import com.mypopsy.drawable.util.Bezier;

public class CrossModel {

    private static final int ANGLE = 45;

    public final Bezier upLine;
    public final Bezier downLine;

    public CrossModel(float length) {
        upLine = Bezier.line(-length / 2, 0, length / 2, 0);
        upLine.rotate(0, 0, ANGLE);
        downLine = Bezier.line(-length / 2, 0, length / 2, 0);
        downLine.rotate(0, 0, -ANGLE);
    }
}