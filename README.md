# ToggleDrawable #

ToggleDrawable helps to create animated drawable transitions. The implementation relies on bezier curves instead of SVG graphics, compatible below API 19.

<p align="center">
<img src="https://github.com/renaudcerrato/ToggleDrawable/raw/master/artworks/toggle.gif">
</p>


# How? #

The library provides a few implementations like [`SearchArrowDrawable`]( https://github.com/renaudcerrato/ToggleDrawable/blob/master/library/src/main/java/com/mypopsy/drawable/SearchArrowDrawable.java) or [`DrawerArrowDrawable`](https://github.com/renaudcerrato/ToggleDrawable/blob/master/library/src/main/java/com/mypopsy/drawable/DrawerArrowDrawable.java):

```
ToggleDrawable drawable = new SearchArrowDrawable(context);
mImageView.setImageDrawable(drawable);
drawable.setProgress(...); // animate progress [0f..1f]
```

You can easily create your own transitions by specifying each start/end beziers curves to `ToggleDrawable`:

```
public class MyCustomToggleDrawable extends ToggleDrawable {

        public MyCustomToggleDrawable(Context context) {
            super(context, 0, R.style.CustomToggleDrawableStyle);
            float radius = Math.round(getIntrinsicWidth()*0.5f);

            add(Bezier.quadrant(radius, 0), Bezier.line(radius, radius, radius, -radius));
            add(Bezier.quadrant(radius, 90), Bezier.line(-radius, radius, radius, radius));
            add(Bezier.quadrant(radius, 180), Bezier.line(-radius, radius, -radius, -radius));
            add(Bezier.quadrant(radius, 270), Bezier.line(-radius, -radius, radius, -radius));
        }
    }
```


