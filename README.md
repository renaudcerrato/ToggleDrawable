# ToggleDrawable #

ToggleDrawable helps to create animated drawable transitions using bezier curves.

![animated gif](https://github.com/renaudcerrato/ToggleDrawable/raw/master/artworks/toggle.gif)



# How? #

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


