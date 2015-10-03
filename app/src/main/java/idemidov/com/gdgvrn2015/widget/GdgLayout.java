package idemidov.com.gdgvrn2015.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by Ilya on 03/10/2015.
 */
public class GdgLayout extends ViewGroup {

    private int touchSlop;
    private float downX;
    private float lastX = -1;
    private float elapse = 0;

    public GdgLayout(Context context) {
        super(context);
        init();
    }

    public GdgLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GdgLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public GdgLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.rgb(0xdb, 0xdc, 0xff));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        for (int i = 0; i < getChildCount(); i++) {
            width += getChildAt(i).getMeasuredWidth();
            height += getChildAt(i).getMeasuredHeight();
        }

        switch (widthMode) {
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
            case MeasureSpec.AT_MOST:
                width = Math.min(widthSize, width);
                break;
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                height = Math.min(heightSize, height);
                break;
        }
        setMeasuredDimension(width, height);

        measureViews(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int childLeft = 0;
        int childTop = 0;

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int lc = (int) (childLeft + elapse);
            if (lc + child.getMeasuredWidth() > getMeasuredWidth()) {
                lc = getMeasuredWidth() - child.getMeasuredWidth();
            } else if (lc < 0) {
                lc = 0;
            }
            child.layout(lc, childTop, lc + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());
            childLeft += child.getMeasuredWidth();
            childTop += child.getMeasuredHeight();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getActionIndex() == 0) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = ev.getX();
                    return false;
                case MotionEvent.ACTION_MOVE:
                    return Math.abs(downX - ev.getX()) > touchSlop;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    return lastX != -1;
            }
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                lastX = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (lastX == -1) {
                    lastX = event.getX();
                }
                performChildrenColoring(event);
                performChildrenMoving(event);
                lastX = event.getX();
                break;
        }
        return true;
    }

    private void performChildrenColoring(MotionEvent event) {
        for (int i = 0; i < getChildCount(); i++) {
            if (getChildAt(i) instanceof Gdgable) {
                Gdgable child = (Gdgable) getChildAt(i);
                if (lastX < event.getX()) {
                    child.lighter((int) (event.getX() - lastX));
                } else {
                    child.darker((int) (lastX - event.getX()));
                }
            }
        }
    }

    private void performChildrenMoving(MotionEvent event) {
        elapse += event.getX() - lastX;
        requestLayout();
    }

    private void init() {
        setWillNotDraw(false);
        ViewConfiguration configuration = ViewConfiguration.get(getContext());
        touchSlop = configuration.getScaledTouchSlop();
    }

    private void measureViews(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < getChildCount(); i++) {
            int childWidthSpec;
            int childHeightSpec;

            View child = getChildAt(i);
            LayoutParams lp = child.getLayoutParams();
            if (lp.width == LayoutParams.MATCH_PARENT) {
                childWidthSpec = MeasureSpec.makeMeasureSpec(getMeasuredWidth(), MeasureSpec.EXACTLY);
            } else {
                childWidthSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width);
            }

            if (lp.height == LayoutParams.MATCH_PARENT) {
                childHeightSpec = MeasureSpec.makeMeasureSpec(getMeasuredHeight(), MeasureSpec.EXACTLY);
            } else {
                childHeightSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height);
            }
            child.measure(childWidthSpec, childHeightSpec);
        }
    }
}
