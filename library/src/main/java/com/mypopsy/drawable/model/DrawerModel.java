package com.mypopsy.drawable.model;

import com.mypopsy.drawable.util.Bezier;

public class DrawerModel {

    public final Bezier topLine;
    public final Bezier middleLine;
    public final Bezier bottomLine;

    public DrawerModel(float length, float gapSize) {
        topLine = Bezier.line(-length / 2, 0, length / 2, 0);
        topLine.offset(0, -gapSize);

        middleLine = Bezier.line(-length / 2, 0, length / 2, 0);

        bottomLine = Bezier.line(-length / 2, 0, length / 2, 0);
        bottomLine.offset(0, gapSize);
    }
}