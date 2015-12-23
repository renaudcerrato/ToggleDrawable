package com.mypopsy.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;

import com.mypopsy.drawable.model.CrossModel;
import com.mypopsy.drawable.model.SearchModel;

import static java.lang.Math.round;

public class SearchCrossDrawable extends ToggleDrawable {

    public SearchCrossDrawable(Context context) {
        this(context, R.attr.searchCrossDrawableStyle, R.style.SearchCrossDrawable);
    }

    public SearchCrossDrawable(Context context, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, defStyleAttr, defStyleRes);

        final TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(null, R.styleable.SearchCrossToggle, defStyleAttr, defStyleRes);

        float radius = round(typedArray.getDimension(R.styleable.SearchCrossToggle_td_searchRadius, 0));
        float searchLength = round(typedArray.getDimension(R.styleable.SearchCrossToggle_td_searchLength, 0));
        float crossLength = round(typedArray.getDimension(R.styleable.SearchCrossToggle_td_crossLength, 0));
        typedArray.recycle();

        SearchModel search = new SearchModel(radius, searchLength);
        CrossModel cross = new CrossModel(crossLength);

        add(search.handle, cross.upLine);
        add(search.topRightQuadrant, cross.upLine);
        add(search.bottomRightQuadrant, cross.upLine);
        add(search.topLeftQuadrant, cross.downLine);
        add(search.bottomLeftQuadrant, cross.downLine);
    }
}
