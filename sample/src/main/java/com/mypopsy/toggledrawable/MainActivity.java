package com.mypopsy.toggledrawable;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.mypopsy.drawable.DrawerArrowDrawable;
import com.mypopsy.drawable.SearchArrowDrawable;
import com.mypopsy.drawable.SearchCrossDrawable;
import com.mypopsy.drawable.ToggleDrawable;
import com.mypopsy.drawable.util.Bezier;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewGroup mContainer;
    private SeekBar mSeekBar;
    private boolean isFaded;
    private ArrayList<ToggleDrawable> mToggleDrawables = new ArrayList<>();

    private View.OnClickListener mToggleListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            toggle();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContainer = (ViewGroup) findViewById(R.id.container_cardview);
        mContainer.getBackground().setAlpha(0);
        mContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() != MotionEvent.ACTION_DOWN) return false;
                if (!isFaded) return false;
                toggle();
                return true;
            }
        });

        mSeekBar = ((SeekBar)findViewById(R.id.seekbar));
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                for(ToggleDrawable drawable : mToggleDrawables)
                    drawable.setProgress(progress / (float) seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        add(new SearchArrowDrawable(this));
        add(new SearchCrossDrawable(this));
        add(new DrawerArrowDrawable(this));
        add(new MyCustomToggleDrawable(this));
    }

    private void toggle() {
        isFaded = !isFaded;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            AnimatorSet set = new AnimatorSet();

            ObjectAnimator backgroundAnim = ObjectAnimator.ofInt(mContainer.getBackground(), "alpha", isFaded ? 128 : 0);
            ObjectAnimator drawableAnim = ObjectAnimator.ofInt(mSeekBar, "progress", isFaded ? mSeekBar.getMax() : 0);
            set.setDuration(700);
            set.setInterpolator(new DecelerateInterpolator(3f));
            set.playTogether(backgroundAnim, drawableAnim);
            set.start();
        } else {
            Animation set = new ToggleAnimationSet(isFaded);
            set.setDuration(700);
            set.setInterpolator(new DecelerateInterpolator(3f));
            mContainer.startAnimation(set);
        }
    }

    private void add(ToggleDrawable drawable) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_bar, mContainer, false);
        ImageView image = (ImageView) view.findViewById(android.R.id.icon);
        ((TextView) view.findViewById(android.R.id.text1)).setText(drawable.getClass().getSimpleName());
        image.setBackgroundDrawable(drawable);
        view.findViewById(R.id.cardview).setOnClickListener(mToggleListener);
        mToggleDrawables.add(drawable);
        mContainer.addView(view);
    }

    public void onGithubClick(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(BuildConfig.PROJECT_URL)));
    }

    private static class MyCustomToggleDrawable extends ToggleDrawable {

        public MyCustomToggleDrawable(Context context) {
            super(context, 0, R.style.CustomToggleDrawableStyle);
            float radius = Math.round(getIntrinsicWidth()*0.5f);

            add(Bezier.quadrant(radius, 0), Bezier.line(radius, radius, radius, -radius));
            add(Bezier.quadrant(radius, 90), Bezier.line(-radius, radius, radius, radius));
            add(Bezier.quadrant(radius, 180), Bezier.line(-radius, radius, -radius, -radius));
            add(Bezier.quadrant(radius, 270), Bezier.line(-radius, -radius, radius, -radius));
        }
    }

    private class ToggleAnimationSet extends Animation {

        private final int mProgressRemaining;
        private final int mProgressStart;
        private final int mAlphaStart;
        private final int mAlphaRemaining;

        public ToggleAnimationSet(boolean isFaded) {
            mAlphaStart = (int) (mSeekBar.getProgress() * 1f / mSeekBar.getMax() * 128);
            mAlphaRemaining = isFaded ? 128 - mAlphaStart : -mAlphaStart;
            mProgressStart = mSeekBar.getProgress();
            mProgressRemaining = isFaded ? mSeekBar.getMax() - mProgressStart : -mProgressStart;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            mContainer.getBackground().setAlpha((int) (mAlphaStart + interpolatedTime * mAlphaRemaining));
            mSeekBar.setProgress((int) (mProgressStart + interpolatedTime * mProgressRemaining));
        }
    }
}
