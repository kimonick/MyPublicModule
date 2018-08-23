package com.kimonik.mypublicmodule.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.LinkAddress;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kimonik.mypublicmodule.R;
import com.kimonik.mypublicmodule.app.MApp;
import com.kimonik.utilsmodule.utils.LUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * * ===============================================================
 * name:             LotteryView
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2018/8/22
 * method:
 * <p>
 * <p>
 * description：
 * history：
 * *==================================================================
 */

public class LotteryView extends View {
    private Bitmap bitmapBack,bitmapRed,bitmapXie,bitmapFu,bitmapStart;
    private Rect rectFBack,rectFRed;
    private RectF rectFArc,rectFStart;
    private String  colorBack="#417CF4",deng1="#77C85D",deng2="#DBCD72",rectColor="#368DEA",
            textColor="#6BA6B1",arcColor1="#FFFFFF",arcColor2="#DBFDFF";
    private String  thank="谢谢参与",eight="8",doubleEight="88",threeEight="888",oneEight="188",lucky="幸运福袋";
    private Paint paintBack,paintDeng1,paintDeng2,paintRect,paintText,arcPaint1,arcPaint2;
    private int width=MApp.DEVICE_WIDTH;
    private float radius,centerX,centerY;
    private List<Bitmap> list;
    private List<String> listString;
    /**闪烁小灯圆心所在大圆半径,闪烁小灯圆的半径*/
    private float  flickerRadious,smallRadious;
    /**闪烁小灯位置集合*/
    private List<PointF>  listFlicker;
    public LotteryView(Context context) {
        this(context, null, 0);
    }

    public LotteryView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LotteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LotteryView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){

        radius=width*0.31f;
        centerX=width*0.525f;
        centerY=width*0.55f;
        flickerRadious=width*0.295f;
        smallRadious=width*0.01f;

        bitmapBack= BitmapFactory.decodeResource(getResources(), R.drawable.beijing);
        bitmapFu= BitmapFactory.decodeResource(getResources(), R.drawable.xinyunfudai);
        bitmapRed= BitmapFactory.decodeResource(getResources(), R.drawable.hognbao);
        bitmapXie= BitmapFactory.decodeResource(getResources(), R.drawable.xiexiecanyu);
        bitmapStart= BitmapFactory.decodeResource(getResources(), R.drawable.kaishichoujiang);

        list=new ArrayList<>();
        list.add(bitmapRed);
        list.add(bitmapRed);
        list.add(bitmapRed);
        list.add(bitmapRed);
        list.add(bitmapXie);
        list.add(bitmapFu);

        listString=new ArrayList<>();
        listString.add(lucky);
        listString.add(eight);
        listString.add(doubleEight);
        listString.add(oneEight);
        listString.add(threeEight);
        
        
        listFlicker=new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            PointF pointF=new PointF();
            pointF.x= (float) (centerX+flickerRadious*Math.cos(Math.PI/12*i));
            pointF.y= (float) (centerY-flickerRadious*Math.sin(Math.PI/12*i));
            listFlicker.add(pointF);
        }


        arcPaint1=initPaint(arcColor1, Paint.Style.FILL,5);
        arcPaint2=initPaint(arcColor2, Paint.Style.FILL,5);
        paintBack=initPaint(colorBack, Paint.Style.FILL,5);
        paintDeng1=initPaint(deng1, Paint.Style.FILL,5);
        paintDeng2=initPaint(deng2, Paint.Style.FILL,5);
        paintRect=initPaint(rectColor, Paint.Style.FILL,5);
        paintText=initPaint(textColor, Paint.Style.FILL,5);
        paintText.setTextSize(0.03704f*width);

        rectFBack=new Rect();
        rectFBack.left=0;
        rectFBack.top=0;
        rectFBack.right=width;
        rectFBack.bottom=width;


        rectFStart=new RectF(width*0.445f,width*0.449f,width*0.605f,width*0.651f);
        //0.525--0.242--0.55--0.455--0.525--0.33--0.7918
        rectFRed=new Rect((int)(0.47f*width),(int)(0.338f*width),(int)(0.58f*width),(int)(0.448f*width));
        //半径0.28f--0.295f
        rectFArc=new RectF(width*0.245f,width*0.27f,width*0.805f,width*0.83f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmapBack,null,rectFBack,null);
        canvas.drawCircle(centerX,centerY,radius,paintBack);
        for (int i = 1; i < 7; i++) {
            if (i%2==0){
                canvas.drawArc(rectFArc,60*(i-1),60,true,arcPaint1);
            }else {
                canvas.drawArc(rectFArc,60*(i-1),60,true,arcPaint2);
            }
        }
//        canvas.drawRect(rectFRed,paintRect);
        canvas.drawText(thank,0.525f*width-paintText.measureText(lucky)/2,0.33f*width,paintText);
        canvas.drawBitmap(list.get(0),null,rectFRed,null);
//        canvas.drawBitmap(list.get(0),0,0,null);
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.rotate(60f,centerX,centerY);
//            canvas.drawRect(rectFRed,paintRect);
            canvas.drawText(listString.get(i),0.525f*width-paintText.measureText(listString.get(i))/2,0.33f*width,paintText);
            canvas.drawBitmap(list.get(i),null,rectFRed,null);
        }
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.restore();

//        canvas.drawRect(rectFStart,paintText);
        canvas.drawBitmap(bitmapStart,null,rectFStart,null);

        for (int i = 0; i < 24; i++) {
            if (i%2==0){
                canvas.drawCircle(listFlicker.get(i).x,listFlicker.get(i).y,smallRadious,paintDeng1);
            }else {
                canvas.drawCircle(listFlicker.get(i).x,listFlicker.get(i).y,smallRadious,paintDeng2);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width,width);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private Paint initPaint(String color,Paint.Style style,int width){
        Paint paint=new Paint();
        paint.setColor(Color.parseColor(color));
        paint.setAntiAlias(true);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }
}
