package za.co.westcoastexplorers.exploreapp.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import za.co.westcoastexplorers.R;


/**
 * Created by rikus on 2015-09-07.
 */
public class SwipeProgress extends View {

    int count;
    int index;
    int width;
    int height;
    int color;
    float circleradius, spacing;

    public SwipeProgress(Context context) {
        this(context, null);
    }

    public SwipeProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        circleradius = context.getResources().getDimension(R.dimen.swipecircleradius);
        spacing = context.getResources().getDimension(R.dimen.swipecirclespacing);
    }

    public void setSwipeCount(int count){
        this.count = count;
    }

    public void setColor (int color){
        this.color = color;
    }

    public void setIndex (int index){
        this.index = index;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredWidth = count > 1 ? (int)((circleradius * 2 * count) + ((count - 1) * spacing)) : 0;
        int desiredHeight =  count > 1 ? (int)(circleradius * 2) : 0;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        this.setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < count; i ++){
            Paint paint = new Paint();
            paint.setColor(index == i ? color : Color.argb(0x4D, Color.red(color), Color.green(color), Color.blue(color)));
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            float circleplacement = (i * (circleradius * 2));
            canvas.drawCircle(circleradius + circleplacement + (i * spacing), height / 2f, circleradius, paint);
        }

    }
}
