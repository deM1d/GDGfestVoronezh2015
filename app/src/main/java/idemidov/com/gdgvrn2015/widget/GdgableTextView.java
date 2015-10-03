package idemidov.com.gdgvrn2015.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Ilya on 03/10/2015.
 */
public class GdgableTextView extends TextView implements Gdgable {

    public GdgableTextView(Context context) {
        super(context);
    }

    public GdgableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GdgableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GdgableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void darker(int value) {
        int red = Color.red(getCurrentTextColor());
        int green = Color.green(getCurrentTextColor());
        int blue = Color.blue(getCurrentTextColor());

        red = red - value > 0 ? red - value : 0;
        green = green - value > 0 ? green - value : 0;
        blue = blue - value > 0 ? blue - value : 0;

        setTextColor(Color.rgb(red, green, blue));
    }

    @Override
    public void lighter(int value) {
        int red = Color.red(getCurrentTextColor());
        int green = Color.green(getCurrentTextColor());
        int blue = Color.blue(getCurrentTextColor());

        red = red + value > 255 ? 255 : red + value;
        green = green + value > 255 ? 255 : green + value;
        blue = blue + value > 255 ? 255 : blue + value;


        setTextColor(Color.rgb(red, green, blue));
    }
}
