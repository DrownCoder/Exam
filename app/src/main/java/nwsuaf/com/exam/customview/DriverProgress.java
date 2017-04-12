package nwsuaf.com.exam.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import nwsuaf.com.exam.R;
import nwsuaf.com.exam.util.DensityUtils;


/**
 * Created by dengzhaoxuan on 2017/2/21.
 * 驾驶盘进度条
 * 可以自定义
 */

public class DriverProgress extends View {
    private Paint paint;
    private Context context;
    //仪表盘半径
    private int PANEL_RADIUS = 70;
    private final int PANEL_RADIUS_DEFALUT = 70;
    //仪表盘宽度
    private int PANEL_WIDTH = 45;
    private final int PANEL_WIDTH_DEFAULT = 45;
    //刻度表的密度
    private int PANEL_DENSITY = 10;
    private final int PANEL_DENSITY_DEFAULT  = 10;
    //刻度表点半径
    private int PANEL_POINT_RADIUS = 3;
    private final int PANEL_POINT_RADIUS_DEFAULT  = 3;
    //仪表盘进度
    private float PANEL_PROGRESS = 50;
    private final int PANEL_PROGRESS_DEFAULT  = 50;
    //仪表盘总进度
    private float PANEL_MAX = 100;
    private final int PANEL_MAX_DEFAULT  = 100;
    //指针大小
    private int INDICATOR_RADIUS = 10;
    private final int INDICATOR_RADIUS_DEFAULT  = 10;

    public DriverProgress(Context context) {
        this(context,null);
    }

    public DriverProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DriverProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.paint = new Paint();
        initAttr(attrs,defStyleAttr);
    }

    private void initAttr(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DriverProgress, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++)
        {
            int attr = a.getIndex(i);
            switch (attr)
            {
                case R.styleable.DriverProgress_panel_radius:
                    PANEL_RADIUS = a.getDimensionPixelSize(attr, DensityUtils.dp2px(getContext(),PANEL_RADIUS_DEFALUT));
                    break;
                case R.styleable.DriverProgress_panel_width:
                    PANEL_WIDTH = a.getDimensionPixelSize(attr, DensityUtils.dp2px(getContext(),PANEL_WIDTH_DEFAULT));
                    break;
                case R.styleable.DriverProgress_panel_density:
                    PANEL_DENSITY = a.getInt(attr,PANEL_DENSITY_DEFAULT);
                    break;
                case R.styleable.DriverProgress_panel_point_radius:
                    PANEL_POINT_RADIUS = a.getDimensionPixelSize(attr,DensityUtils.dp2px(getContext(),PANEL_POINT_RADIUS_DEFAULT));
                    break;
                case R.styleable.DriverProgress_panel_progress:
                    PANEL_PROGRESS = a.getInt(attr,PANEL_PROGRESS_DEFAULT);
                    break;
                case R.styleable.DriverProgress_panel_max:
                    PANEL_MAX = a.getInt(attr,PANEL_MAX_DEFAULT);
                    break;
                case R.styleable.DriverProgress_indicator_radius:
                    INDICATOR_RADIUS = a.getDimensionPixelOffset(attr,DensityUtils.dp2px(getContext(),INDICATOR_RADIUS_DEFAULT));
                    break;
            }

        }
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            int desired = (int) (getPaddingLeft() + 2*PANEL_RADIUS+1.5f*PANEL_WIDTH + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            int desired = (int) (getPaddingTop() + PANEL_RADIUS+PANEL_WIDTH + getPaddingBottom());
            height = desired;
        }



        setMeasuredDimension(width, height);
    }


    /**
     * 仪表盘：白色圆环+蓝色圆弧+一组圆点（sin，cos）
     * 指针：圆形+三角形+小圆点
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        this.paint.setColor(getResources().getColor(R.color.panel_bottom_white));
        this.paint.setAntiAlias(true); //消除锯齿
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆
        /**
         * 绘制仪表盘底色
         */
        /*float x = (getWidth() - getHeight() / 2) / 2;
        float y = getHeight() / 4;*/

        float x = PANEL_WIDTH/2;
        float y = PANEL_WIDTH/2;

        //float radius = ((getWidth() - x) - x) / 2;//半径 = (endx-startx) / 2

        /*RectF oval = new RectF( x, y,
                getWidth() - x, getHeight() - y);*/
        RectF oval = new RectF( x, y,
                PANEL_WIDTH+2*PANEL_RADIUS, 2*PANEL_RADIUS+PANEL_WIDTH);

        this.paint.setStrokeWidth(PANEL_WIDTH);
        canvas.drawArc(oval, 180, 180, false, paint);
        //canvas.drawRect(oval,paint);
        /**
         * 绘制仪表盘进度
         */
        this.paint.setColor(getResources().getColor(R.color.panel_progress_blue));
        this.paint.setAntiAlias(true); //消除锯齿
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆

        this.paint.setStrokeWidth(PANEL_WIDTH);

        int sweepAngle = (int) (PANEL_PROGRESS/PANEL_MAX * 180.0f);

        canvas.drawArc(oval, 180, sweepAngle, false, paint);


        /**
         * 绘制仪表盘刻度
         */
        paint.setColor(getResources().getColor(R.color.white));
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(PANEL_POINT_RADIUS);

        float startx = x ;
        float starty = PANEL_RADIUS+PANEL_WIDTH*0.75f-PANEL_POINT_RADIUS;

        float radius = PANEL_RADIUS+PANEL_WIDTH/4;
        //canvas.drawCircle(x,starty,5, paint);
        //点的密度
        int density = 180 / PANEL_DENSITY;
        float potsx[] = new float[PANEL_DENSITY];
        float potsy[] = new float[PANEL_DENSITY];
        for(int i = 1 ; i <PANEL_DENSITY;i++){
            potsx[i] = (float) (startx + (radius*(1 - Math.cos(density*i* Math.PI/180))));
            potsy[i] = (float) (starty -  radius* Math.sin(density*i* Math.PI/180) + PANEL_POINT_RADIUS);
            canvas.drawCircle(potsx[i],potsy[i],PANEL_POINT_RADIUS, paint);
        }


        /**
         * 绘制底圆
         */
        this.paint.setColor(getResources().getColor(R.color.panel_indicator_color));
        this.paint.setAntiAlias(true); //消除锯齿
        paint.setStyle(Paint.Style.FILL);
        float ix = PANEL_RADIUS+PANEL_WIDTH*0.75f;
        float iy = PANEL_RADIUS+PANEL_WIDTH*0.75f-PANEL_POINT_RADIUS;
        canvas.drawCircle(ix,iy,INDICATOR_RADIUS, paint);

        /**
         * 绘制针头  三角形
         */
        float angle = (float) (PANEL_PROGRESS/PANEL_MAX* Math.PI);
        float px = (float) (ix-(4*INDICATOR_RADIUS* Math.cos(angle)));
        float py = (float) (iy-(4*INDICATOR_RADIUS* Math.sin(angle)));
        Path path = new Path();
        //path.moveTo(ix-INDICATOR_RADIUS, iy-3);// 此点为多边形的起点
        //.lineTo(ix+INDICATOR_RADIUS, iy-3);
        //path.lineTo(ix, iy-4*INDICATOR_RADIUS);
        path.moveTo((float) (ix-INDICATOR_RADIUS* Math.sin(angle)),(float) ((iy+INDICATOR_RADIUS* Math.cos(angle))));
        path.lineTo((float) (ix+INDICATOR_RADIUS* Math.sin(angle)),(float) ((iy-INDICATOR_RADIUS* Math.cos(angle))));
        path.lineTo(px,py);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);

        /**
         * 绘制轴  白色的点
         */

        this.paint.setColor(getResources().getColor(R.color.white));
        this.paint.setAntiAlias(true); //消除锯齿
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(ix,iy,INDICATOR_RADIUS/3, paint);

        super.onDraw(canvas);
    }

    /**
     * 设置进度
     */
    public void setProgress(float progress) {
        this.PANEL_PROGRESS = progress;
        invalidate();
    }
    /**
     * 启动动画
     */
    public void startAnimation(){
        ValueAnimator animator = ValueAnimator.ofFloat(0,PANEL_PROGRESS);
        animator.setDuration(3000);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curValue = (float) animation.getAnimatedValue();
                setProgress(curValue);
            }
        });
        animator.setInterpolator(new OvershootInterpolator());
        animator.start();
    }
}
