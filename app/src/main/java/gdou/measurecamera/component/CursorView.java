package gdou.measurecamera.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lim9527 on 10/3 0003.
 */

public class CursorView extends View {

    //-------- attr --------
    int mCirclePosX = 500;
    int mCirclePosY = 1000;
    int mRadius = 100;
    int mHalfRectWidth = 5;

    Paint mCirclePaint;
    Paint mRectPaint;

    //-------- method --------
    public CursorView(Context context) {
        super(context);
        init();
    }

    public CursorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setARGB(200, 127, 255, 212);//护眼绿色
        mCirclePaint.setStrokeWidth(10);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
        mRectPaint.setARGB(200, 127, 255, 212);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mCirclePosX- mHalfRectWidth, 0, mCirclePosX+ mHalfRectWidth, mCirclePosY-mRadius, mRectPaint);


        canvas.drawArc(new RectF(mCirclePosX -mRadius, mCirclePosY -mRadius,
                        mCirclePosX +mRadius, mCirclePosY +mRadius), 0, 360, false, mCirclePaint);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isNeedToWork = false;

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (Math.abs(x - mCirclePosX)<mRadius/2 && Math.abs(y - mCirclePosY)<mRadius/2)
                    isNeedToWork = true;
                break;

            case MotionEvent.ACTION_MOVE:
                mCirclePosX = x;
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                mCirclePosX = x;
                invalidate();
                break;
        }

        return isNeedToWork;
    }
}
