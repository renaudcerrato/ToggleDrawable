package com.mypopsy.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;

import com.mypopsy.drawable.util.Bezier;
import com.mypopsy.drawable.util.Point;
import com.mypopsy.drawable.util.Vector;

import java.util.ArrayList;

public class ToggleDrawable extends Drawable {

    private final Paint mPaint = new Paint();

    // The thickness of strokes
    protected float mStrokeWidth;
    // Whether the canvas should spin or not during progress
    protected boolean mSpin;
    // Use Path instead of canvas operations so that if color has transparency,
    // overlapping section wont look different
    private final Path mPath = new Path();
    // The interpolated version of the original progress
    protected  float mProgress;
    // The reported intrinsic size of the drawable
    protected  final int mSize;

    private final ArrayList<Bezier> mStart = new ArrayList<>();
    private final ArrayList<Bezier> mEnd = new ArrayList<>();
    private final ArrayList<Bezier> mCurrent = new ArrayList<>();

    public ToggleDrawable(Context context) {
        this(context, 0, R.style.ToggleDrawable);
    }

    public ToggleDrawable(Context context, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        final TypedArray typedArray = context.getTheme()
                .obtainStyledAttributes(null, R.styleable.ToggleDrawable,
                        defStyleAttr, defStyleRes);
        
        mPaint.setAntiAlias(true);
        mPaint.setColor(typedArray.getColor(R.styleable.ToggleDrawable_td_color, 0));
        mSize = typedArray.getDimensionPixelSize(R.styleable.ToggleDrawable_td_drawableSize, 0);
        mStrokeWidth = typedArray.getDimension(R.styleable.ToggleDrawable_td_stroke, 0);
        mSpin = typedArray.getBoolean(R.styleable.ToggleDrawable_td_spin, true);
        typedArray.recycle();

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeWidth(mStrokeWidth);
    }

    public void reset() {
        mStart.clear();
        mEnd.clear();
        mCurrent.clear();
    }

    public void add(Bezier start, Bezier end) {
        Vector v1 = new Vector(start.p1, start.p2);
        Vector v2 = new Vector(end.p1, end.p2);
        if(Math.abs(v1.angle(v2)) >= Math.PI/2)
            start = new Bezier(start).swap();
        mStart.add(start);
        mEnd.add(end);
        mCurrent.add(new Bezier());
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();
        final boolean isRtl = isLayoutRtl();
        final float canvasRotate = lerp(isRtl ? -180 : 0, isRtl ? 0 : 180, mProgress);

        mPath.rewind();

        for(int i = 0; i < mCurrent.size(); i++) {
            Bezier current = mCurrent.get(i);
            lerp(mStart.get(i), mEnd.get(i), mCurrent.get(i), mProgress);
            current.addTo(mPath);
        }

        canvas.save();
        canvas.translate(bounds.centerX(), bounds.centerY());

        if (mSpin) {
            canvas.rotate(canvasRotate * (isRtl ? -1 : 1));
        } else if (isRtl) {
            canvas.rotate(180);
        }

        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    @Override
    public int getIntrinsicHeight() {
        return mSize;
    }

    @Override
    public int getIntrinsicWidth() {
        return mSize;
    }

    @Override
    public boolean isAutoMirrored() {
        return true;
    }

    public float getStrokeWidth() {
        return mStrokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        if(mStrokeWidth != strokeWidth) {
            mStrokeWidth = strokeWidth;
            mPaint.setStrokeWidth(strokeWidth);
            invalidateSelf();
        }
    }

    public boolean isSpinEnabled() {
        return mSpin;
    }

    public void setSpinEnabled(boolean spin) {
        if(mSpin != spin) {
            mSpin = spin;
            invalidateSelf();
        }
    }

    final public Paint getPaint() {
        return mPaint;
    }

    protected boolean isLayoutRtl() {
        return false;
    }

    @Override
    public void setAlpha(int alpha) {
        if (alpha != mPaint.getAlpha()) {
            mPaint.setAlpha(alpha);
            invalidateSelf();
        }
    }

    @Override
    public int getAlpha() {
        return mPaint.getAlpha();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        if(mProgress != progress) {
            mProgress = progress;
            invalidateSelf();
        }
    }

    private static void lerp(Bezier a, Bezier b, Bezier out, float t) {
        lerp(a.p1, b.p1, out.p1, t);
        lerp(a.p2, b.p2, out.p2, t);
        lerp(a.e1, b.e1, out.e1, t);
        lerp(a.e2, b.e2, out.e2, t);
    }

    private static void lerp(Point a, Point b, Point out, float t) {
        out.x = lerp(a.x, b.x, t);
        out.y = lerp(a.y, b.y, t);
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
}
