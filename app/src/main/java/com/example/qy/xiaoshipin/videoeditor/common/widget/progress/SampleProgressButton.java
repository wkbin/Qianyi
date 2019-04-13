package com.example.qy.xiaoshipin.videoeditor.common.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.example.qy.R;


public class SampleProgressButton extends View {

    private FontMetrics mFontMetrics;
    private int mProgress = 0;
    private int mTextColor = Color.WHITE;
    private Paint mTextPaint;
    private float mTextSize = 10;
    private int mForegroundColor;
    private int mBackgroundColor;
    private int mNormalColor;
    private String mText = "";
    private int mMaxProgress = 100;

    private Paint mBackgroundPaintNormal;
    private Paint mBackgroundPaintProgress;

    private RectF mBackgroundBounds;
    private LinearGradient mProgressBgGradient;

    private int mState = STATE_NORMAL;

    public static final int STATE_NORMAL = 1;
    public static final int STATE_PROGRESS = 2;

    public SampleProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public SampleProgressButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.SampleProgressButton);
            mBackgroundColor = typedArray.getInteger(R.styleable.SampleProgressButton_sampleProgressButtonBackgroundColor, Color.GRAY);
            mForegroundColor = typedArray.getInteger(R.styleable.SampleProgressButton_sampleProgressButtonForegroundColor, Color.RED);
            mNormalColor = typedArray.getInteger(R.styleable.SampleProgressButton_sampleProgressButtonNormalColor, Color.parseColor("#ff91e573"));
            mTextColor = typedArray.getInteger(R.styleable.SampleProgressButton_sampleProgressButtonTextcolor, Color.WHITE);
            mMaxProgress = typedArray.getInteger(R.styleable.SampleProgressButton_sampleProgressButtonMax, 100);
            mProgress = typedArray.getInteger(R.styleable.SampleProgressButton_sampleProgressButtonProgress, 0);
            mText = typedArray.getString(R.styleable.SampleProgressButton_sampleProgressButtonText);
            mTextSize = typedArray.getDimension(R.styleable.SampleProgressButton_sampleProgressButtonTextSize, 20);
        }finally {
            if(typedArray != null) {
                typedArray.recycle();
            }
        }

        mBackgroundBounds = new RectF();

        mBackgroundPaintNormal = new Paint();
        mBackgroundPaintNormal.setAntiAlias(true);
        mBackgroundPaintNormal.setStyle(Paint.Style.FILL);

        mBackgroundPaintProgress = new Paint();
        mBackgroundPaintProgress.setAntiAlias(true);
        mBackgroundPaintProgress.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(5);

        float mProgressPercent = mProgress / (mMaxProgress + 0f);

        mProgressBgGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,
                new int[]{mForegroundColor, mBackgroundColor},
                new float[]{mProgressPercent, mProgressPercent + 0.001f},
                Shader.TileMode.CLAMP
        );
    }

    public int getForegroundColor(){
        return mForegroundColor;
    }

    public int getBackgroundColor(){
        return mBackgroundColor;
    }

    public int getNormalColor(){
        return mNormalColor;
    }

    public void setForegroundColor(int foregroundColor){
        mForegroundColor = foregroundColor;
        invalidate();
    }

    public void setBackgroundColor(int backgroundColor){
        mBackgroundColor = backgroundColor;
        invalidate();
    }

    public void setNormalColor(int normalColor){
        mNormalColor = normalColor;
        invalidate();
    }

    public void setTextsize(float textSize){
        mTextSize = textSize;
    }

    public float getTextsize(){
        return mTextSize;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mBackgroundBounds.left = 0;
        mBackgroundBounds.top = 0;
        mBackgroundBounds.right = getMeasuredWidth();
        mBackgroundBounds.bottom = getMeasuredHeight();

        int cornerRadius = getMeasuredHeight() / 2;

        if(mState == STATE_NORMAL){
            mBackgroundPaintNormal.setColor(mNormalColor);
            canvas.drawRoundRect(mBackgroundBounds, cornerRadius, cornerRadius, mBackgroundPaintNormal);
        }else{
            mBackgroundPaintProgress.setShader(mProgressBgGradient);
            canvas.drawRoundRect(mBackgroundBounds, cornerRadius, cornerRadius, mBackgroundPaintProgress);
        }

        if ("".equals(mText) || mText == null) {
            return;
        }

        mTextPaint.setTextSize(this.mTextSize);
        mFontMetrics = mTextPaint.getFontMetrics();
        mTextPaint.setColor(this.mTextColor);

        float textCenterVerticalBaselineY = getHeight() / 2 - mFontMetrics.descent + (mFontMetrics.descent - mFontMetrics.ascent) / 2;
        canvas.drawText(this.mText, (getMeasuredWidth() - mTextPaint.measureText(this.mText)) / 2, textCenterVerticalBaselineY,
                mTextPaint);

    }


    public void setMax(int max) {

        mMaxProgress = max;

        float mProgressPercent = mProgress / (mMaxProgress + 0f);

        mProgressBgGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,
                new int[]{mForegroundColor, mBackgroundColor},
                new float[]{mProgressPercent, mProgressPercent + 0.001f},
                Shader.TileMode.CLAMP
        );

        postInvalidate();
    }


    public void setText(String text) {
        this.mText = text;
        invalidate();
    }


    public void setProgress(int progress) {
        if (progress > mMaxProgress) {
            return;
        }
        mProgress = progress;

        float mProgressPercent = mProgress / (mMaxProgress + 0f);

        mProgressBgGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0,
                new int[]{mForegroundColor, mBackgroundColor},
                new float[]{mProgressPercent, mProgressPercent + 0.001f},
                Shader.TileMode.CLAMP
        );

        invalidate();
    }

    public int getMax() {
        return mMaxProgress;
    }

    public int getProgress() {
        return mProgress;
    }

    public void setState(int state){
        mState = state;
        invalidate();
    }

    public int getState(){
        return mState;
    }


}