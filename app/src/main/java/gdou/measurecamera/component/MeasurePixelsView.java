package gdou.measurecamera.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.InputStream;

import gdou.measurecamera.R;

/**
 * Created by lim9527 on 10/2 0002.
 */

/**
 * 自定义控件：测量图片选定线段的像素值。
 */
public class MeasurePixelsView extends View {

    //-------- attr --------
    private static final String TAG = "MeasurePixelsView";

    /* 游标 */
    public static final int SELECTED_CURSOR_LEFT = 1;
    public static final int SELECTED_CURSOR_RIGHT = 2;
    int CURRENT_SELECTED_CURRSOR = 0;//0表示没选中
    int mCursorPosLeft = 150;
    int mCursorPosRight = 500;
    int mCursorLength = 1000;
    int mCursorWidth = 10;
    int mCursorRadius = 100;
    int mCursorPosY = mCursorLength + mCursorRadius;

    /* 图层 */
    Bitmap mImageBitmap;//原始图片
    Bitmap mScaleBitmap;//缩放图片

    Paint mPaint;

    //-------- method --------

    public MeasurePixelsView(Context context) {
        super(context);
        init();
    }

    public MeasurePixelsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /** 初始化函数 */
    @SuppressWarnings("ResourceType")
    private void init(){
        mPaint = new Paint();

        /* 获取原始图片 */
        InputStream is = this.getResources().openRawResource(R.drawable.test);
        mImageBitmap = BitmapFactory.decodeStream(is);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // TODO: 10/2 0002 绘制图片
        /* 绘制图片 */
        if (mScaleBitmap == null)
            mScaleBitmap = Bitmap.createScaledBitmap(mImageBitmap, getWidth(), getHeight(), true);
        canvas.drawBitmap(mScaleBitmap, new Rect(0, 0, mImageBitmap.getWidth(), mImageBitmap.getHeight()), new Rect(0, 0, getWidth(), getHeight()), mPaint);





        /* 绘制放大镜 */
        drawMagnifyingGlass(canvas, mScaleBitmap, mCursorPosLeft, 920, getWidth(), getHeight());
        drawMagnifyingGlass(canvas, mScaleBitmap, mCursorPosRight, 920, getWidth(), getHeight());

        // TODO: 10/2 0002 绘制游标
        /* 绘制游标 */
        drawCursor(canvas, SELECTED_CURSOR_LEFT);
        drawCursor(canvas, SELECTED_CURSOR_RIGHT);
    }


    /**
     * 绘制游标
     * @param canvas
     * @param model 1表示左游标， 2表示右游标
     */
    protected void drawCursor(Canvas canvas, int model){
        Paint paint = new Paint();

        /* 绘制矩形 */
        paint.setARGB(200, 127, 255, 212);//护眼绿色

        RectF rectangle = null;
        if (model == SELECTED_CURSOR_LEFT)
            rectangle = new RectF(mCursorPosLeft-mCursorWidth, 0, mCursorPosLeft, mCursorLength);
        else if (model == SELECTED_CURSOR_RIGHT)
            rectangle = new RectF(mCursorPosRight, 0, mCursorPosRight+mCursorWidth, mCursorLength);
        canvas.drawRect(rectangle, paint);

        /* 绘制圆环 */
        paint.setAntiAlias(true);
        paint.setARGB(200, 127, 255, 212);//护眼绿色
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        //圆形中心
        int arcX = 0;
        int arcY = mCursorLength + mCursorRadius;
        if (model == 1){
            arcX = mCursorPosLeft - mCursorWidth/2;
        }else {
            arcX = mCursorPosRight + mCursorWidth/2;
        }

        RectF rectF = new RectF(arcX-mCursorRadius, arcY-mCursorRadius, arcX+mCursorRadius, arcY+mCursorRadius);
        canvas.drawArc(rectF, 0, 360, false, paint);

    }



    protected void drawMagnifyingGlass(Canvas canvas, Bitmap imageBitmap, int cursorPosX, int cursorPosY,
                                       int canvasWidth, int canvasHeight){
        /* 计算放大的中心点 */
        float glass_x = cursorPosX;
        float glass_y = cursorPosY;
        int image_width = imageBitmap.getWidth();
        int image_height = imageBitmap.getHeight();
        int center_x = new Float(glass_x/canvasWidth*image_width).intValue();
        int center_y = new Float(glass_y/canvasHeight*image_height).intValue();

        /* 绘制放大镜 */
        int half_pic_width = 20;
        int half_pic_height = 20;
        int half_glass_width = 100;
        int half_glass_height = 100;

        Paint paint = new Paint();

        canvas.drawBitmap(imageBitmap,
                new Rect(center_x - half_pic_width, center_y - half_pic_height,
                        center_x + half_pic_width, center_y + half_pic_height),
                new RectF(glass_x - half_glass_width, glass_y - half_glass_height,
                        glass_x + half_glass_width, glass_y + half_glass_height),
                paint);

//        paint.setColor(Color.GREEN);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawRect(new RectF(glass_x - half_glass_width, glass_y - half_glass_height,
//                glass_x + half_glass_width, glass_y + half_glass_height), paint);
    }


    /**
     * 计算两个游标之间对应的像素值。
     * 两个游标之间的间距长度与该视图的长度的比例，相当于两个游标之间的像素值与该图片的像素值的比例。
     *
     */
    protected int calculatePixelsBetweenCursor(int canvasWidth, int imageWidth){
        float pos_min = mCursorPosLeft;
        float pos_max = mCursorPosRight;

        float pixel = (pos_max - pos_min) / canvasWidth * imageWidth;
        return new Float(pixel).intValue();
    }




    protected int currentSelectedModel(){
        return 1;
    }

    /**
     * 是否选中了左游标。
     * @param x
     * @param y
     * @return
     */
    protected boolean isSelectedLeftCursor(int x, int y){
        return Math.abs(x - mCursorPosLeft)<mCursorRadius && Math.abs(y - mCursorPosY)<mCursorRadius;
    }

    /**
     * 是否选中了右游标。
     * @param x
     * @param y
     * @return
     */
    protected boolean isSelectedRightCursor(int x, int y){
        return Math.abs(x - mCursorPosRight)<mCursorRadius && Math.abs(y - mCursorPosY)<mCursorRadius;
    }


    protected boolean isSelectedLeftGlass(int x, int y){
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isNeedToWork = false;

        float x = event.getX();
        float y = event.getY();


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: dowm"+x+":" + y);

                if (Math.abs(x - mCursorPosLeft)<mCursorRadius){
                    // 如果坐标位于左游标圆环内，表示选中左游标；
                    CURRENT_SELECTED_CURRSOR = SELECTED_CURSOR_LEFT;
                    isNeedToWork = true;
                } else if (Math.abs(x - mCursorPosRight)<mCursorRadius) {
                    // 如果坐标位于右游标圆环内，表示选中右游标；
                    CURRENT_SELECTED_CURRSOR = SELECTED_CURSOR_RIGHT;
                    isNeedToWork = true;//只有在down的地方返回true才能继续往下执行。
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (CURRENT_SELECTED_CURRSOR == SELECTED_CURSOR_LEFT){
                    //当前选中的是左游标
                    mCursorPosLeft = new Float(x).intValue();
                }else if (CURRENT_SELECTED_CURRSOR == SELECTED_CURSOR_RIGHT){
                    //当前选中的是右游标
                    mCursorPosRight = new Float(x).intValue();
                }
                invalidate();
                isNeedToWork = true;
                break;

            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: up"+x+":" + y);
                if (CURRENT_SELECTED_CURRSOR == SELECTED_CURSOR_LEFT){
                    //当前选中的是左游标
                    mCursorPosLeft = new Float(x).intValue();
                }else if (CURRENT_SELECTED_CURRSOR == SELECTED_CURSOR_RIGHT){
                    //当前选中的是右游标
                    mCursorPosRight = new Float(x).intValue();
                }
                // TODO: 10/6 0006 显示像素值
                int pixel = calculatePixelsBetweenCursor(getWidth(), mImageBitmap.getWidth());
                Log.i(TAG, "onTouchEvent: ACTION_UP: pixel=" + pixel);
                invalidate();

                CURRENT_SELECTED_CURRSOR = 0;
                break;

            default:
                break;
        }

        return isNeedToWork;
    }
}
