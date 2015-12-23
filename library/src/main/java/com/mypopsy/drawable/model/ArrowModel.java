package com.mypopsy.drawable.model;

import com.mypopsy.drawable.util.Bezier;

public class ArrowModel {
    static final int HEAD_ANGLE = 45;

    public final Bezier topHead;
    public final Bezier bottomHead;
    public final Bezier body;

    public ArrowModel(float length, float headLength, float stroke) {
        // top head
        topHead = Bezier.line(0, 0, -headLength, 0);
        topHead.offset(stroke/2, 0);
        topHead.rotate(0, 0, HEAD_ANGLE);
        topHead.offset(length / 2, 0);

        // body
        body = Bezier.line(length / 2, 0, -length / 2, 0);

        // bottom head
        bottomHead = Bezier.line(0, 0, -headLength, 0);
        bottomHead.offset(stroke/2, 0);
        bottomHead.rotate(0, 0, -HEAD_ANGLE);
        bottomHead.offset(length / 2, 0);
    }
}