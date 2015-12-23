package com.mypopsy.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;

import com.mypopsy.drawable.model.ArrowModel;
import com.mypopsy.drawable.model.DrawerModel;

import static java.lang.Math.round;

public class DrawerArrowDrawable extends ToggleDrawable {

    public DrawerArrowDrawable(Context context) {
        this(context, R.attr.drawerArrowDrawableStyle, R.style.DrawerArrowDrawable);
    }

    public DrawerArrowDrawable(Context context, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, defStyleAttr, defStyleRes);

        final TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(null, R.styleable.DrawerArrowToggle, defStyleAttr, defStyleRes);

        float gapSize = round(typedArray.getDimension(R.styleable.DrawerArrowToggle_td_gapSize, 0));
        float barLength = round(typedArray.getDimension(R.styleable.DrawerArrowToggle_td_crossLength, 0));
        float arrowHeadLength = round(typedArray.getDimension(
                R.styleable.DrawerArrowToggle_td_arrowHeadLength, 0));
        float arrowShaftLength = typedArray
                .getDimension(R.styleable.DrawerArrowToggle_td_arrowShaftLength, 0);
        typedArray.recycle();

        DrawerModel drawer = new DrawerModel(barLength, gapSize);
        ArrowModel arrow = new ArrowModel(arrowShaftLength, arrowHeadLength, getStrokeWidth());

        add(drawer.topLine, arrow.topHead);
        add(drawer.middleLine, arrow.body);
        add(drawer.bottomLine, arrow.bottomHead);
    }
}
