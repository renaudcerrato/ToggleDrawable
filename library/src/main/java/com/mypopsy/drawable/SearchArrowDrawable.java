package com.mypopsy.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;

import com.mypopsy.drawable.model.ArrowModel;
import com.mypopsy.drawable.model.SearchModel;

import static java.lang.Math.round;

public class SearchArrowDrawable extends ToggleDrawable {

    public SearchArrowDrawable(Context context) {
        this(context, R.attr.searchArrowDrawableStyle, R.style.SearchArrowDrawable);
    }

    public SearchArrowDrawable(Context context, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, defStyleAttr, defStyleRes);

        final TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(null, R.styleable.SearchArrowToggle, defStyleAttr, defStyleRes);

        float radius = round(typedArray.getDimension(R.styleable.SearchArrowToggle_td_searchRadius, 0));
        float barLength = round(typedArray.getDimension(R.styleable.SearchArrowToggle_td_searchLength, 0));
        float arrowHeadLength = round(typedArray.getDimension(
                R.styleable.SearchArrowToggle_td_arrowHeadLength, 0));
        float arrowShaftLength = typedArray
                .getDimension(R.styleable.SearchArrowToggle_td_arrowShaftLength, 0);
        typedArray.recycle();

        SearchModel search = new SearchModel(radius, barLength);
        ArrowModel arrow = new ArrowModel(arrowShaftLength, arrowHeadLength, getStrokeWidth());

        add(search.handle, arrow.bottomHead);
        add(search.topRightQuadrant, arrow.topHead);
        add(search.bottomRightQuadrant, arrow.bottomHead);
        add(search.topLeftQuadrant, arrow.body);
        add(search.bottomLeftQuadrant, arrow.body);
    }
}
