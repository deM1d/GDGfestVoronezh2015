package idemidov.com.gdgvrn2015.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import idemidov.com.gdgvrn2015.R;

/**
 * Created by Ilya on 03/10/2015.
 */
public class GdgView extends View implements Gdgable {

    private static final int DEFAULT_BACKGROUND = Color.rgb(0x90, 0x90, 0x90);
    private static final int COLOR_PRESSED_FACTOR = 128;

    private int backgroundColor;
    private String text;
    private Paint textPaint;
    private Rect textBound;

    public GdgView(Context context) {
        super(context);
        init();
    }

    public GdgView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GdgView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GdgView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(backgroundColor);
        if (!isEmpty()) {
            canvas.drawText(text, textBound.left, -textBound.top, textPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(widthSize, textBound.left + textBound.right);
                break;
            case MeasureSpec.UNSPECIFIED:
                width = textBound.left + textBound.right;
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(heightSize, Math.abs(textBound.top) + textBound.bottom);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = Math.abs(textBound.top) + textBound.bottom;
                break;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                performPressedState();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                performUnpressedState();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void darker(int value) {
        backgroundColor = makeColorDarker(backgroundColor, value);
        textPaint.setColor(makeColorLighter(textPaint.getColor(), value));
        invalidate();
    }

    @Override
    public void lighter(int value) {
        backgroundColor = makeColorLighter(backgroundColor, value);
        textPaint.setColor(makeColorDarker(textPaint.getColor(), value));
        invalidate();
    }

    private int makeColorLighter(int color, int diff) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        red = red + diff > 255 ? 255 : red + diff;
        green = green + diff > 255 ? 255 : green + diff;
        blue = blue + diff > 255 ? 255 : blue + diff;

        return Color.rgb(red, green, blue);
    }

    private int makeColorDarker(int color, int diff) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        red = red - diff > 0 ? red - diff : 0;
        green = green - diff > 0 ? green - diff : 0;
        blue = blue - diff > 0 ? blue - diff : 0;

        return Color.rgb(red, green, blue);
    }

    private void init() {
        backgroundColor = DEFAULT_BACKGROUND;
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(getContext().getResources().getDimension(R.dimen.default_text_size));
        textBound = new Rect();
        requestLayout();
    }

    private void performPressedState() {
        backgroundColor = makeColorLighter(backgroundColor, COLOR_PRESSED_FACTOR);
        invalidate();
    }

    private void performUnpressedState() {
        backgroundColor = makeColorDarker(backgroundColor, COLOR_PRESSED_FACTOR);
        invalidate();
    }

    public boolean isEmpty() {
        return text == null || text.length() == 0;
    }

    public void setText(String text) {
        this.text = text;
        if (!isEmpty()) {
            textPaint.getTextBounds(this.text, 0, this.text.length(), textBound);
        } else {
            textBound.set(0, 0, 0, 0);
        }
        requestLayout();
    }

    public void setBackgroundColor(int color) {
        this.backgroundColor = color;
        invalidate();
    }
}
