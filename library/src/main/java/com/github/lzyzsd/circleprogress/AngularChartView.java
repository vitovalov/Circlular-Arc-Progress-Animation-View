package com.github.lzyzsd.circleprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by bruce on 11/6/14.
 */
public class AngularChartView extends View {

  private static final String INSTANCE_STATE = "saved_instance";
  private static final String INSTANCE_STROKE_WIDTH = "stroke_width";
  private static final String INSTANCE_TEXT_SIZE = "text_size";
  private static final String INSTANCE_TEXT_COLOR = "text_color";
  private static final String INSTANCE_PROGRESS = "progress";
  private final float default_stroke_width;
  private final int min_size;
  private final int default_text_color = Color.rgb(122, 122, 122);
  protected Paint textPaint;
  private Paint paint;
  private RectF rectF = new RectF();
  private float strokeWidth;
  private float textSize;
  private int textColor;
  private float default_text_size;
  private int color1 = Color.rgb(125, 188, 249);
  private int color2 = Color.rgb(242, 242, 242);
  private int color3 = Color.rgb(226, 226, 226);
  private float progress1 = 10;
  private float progress2 = -10;
  private boolean normalOrientation = true;
  private float zeroVoffset;
  private float zeroHoffset;
  private float ninetyVoffset;
  private float ninetyHoffset;

  public AngularChartView(Context context) {
    this(context, null);
  }

  public AngularChartView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AngularChartView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    min_size = (int) UiUtils.dpToPx(getResources(), 100);
    default_text_size = UiUtils.sp2px(getResources(), 12);
    default_stroke_width = UiUtils.dpToPx(getResources(), 4);

    TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcProgress, defStyleAttr, 0);
    initByAttributes(attributes);
    attributes.recycle();

    initPainters();

    zeroHoffset = UiUtils.dpToPx(getResources(), 20);
    zeroVoffset = UiUtils.dpToPx(getResources(), 45);
    ninetyVoffset = UiUtils.dpToPx(getResources(), 10);
    ninetyHoffset = UiUtils.dpToPx(getResources(), 40);
  }

  protected void initByAttributes(TypedArray attributes) {
    textColor = attributes.getColor(R.styleable.ArcProgress_text_color, default_text_color);
    textSize = attributes.getDimension(R.styleable.ArcProgress_text_size, default_text_size);
    progress1 = attributes.getInt(R.styleable.ArcProgress_progress1, 10);
    progress2 = attributes.getInt(R.styleable.ArcProgress_progress2, -10);
    normalOrientation = (attributes.getBoolean(R.styleable.ArcProgress_orientation, true));
    strokeWidth = attributes.getDimension(R.styleable.ArcProgress_stroke_width, default_stroke_width);
  }

  protected void initPainters() {
    textPaint = new TextPaint();
    textPaint.setColor(textColor);
    textPaint.setTextSize(textSize);
    textPaint.setAntiAlias(true);

    paint = new Paint();
    paint.setColor(color1);
    paint.setAntiAlias(true);
    paint.setStrokeWidth(strokeWidth);
    paint.setStyle(Paint.Style.STROKE);
  }

  @Override public void invalidate() {
    initPainters();
    super.invalidate();
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    //int progress1 = 10;
    //int progress2 = -10;

    //progress1 = 135;
    //progress2 = 145;

    //boolean normalOrientation = true; // angle 0 on right.

    paint.setColor(color1);

    if (normalOrientation) {

      // first progress blue part
      canvas.drawArc(rectF, progress1, 180 - progress1, false, paint);

      paint.setColor(color2);

      // second progress
      canvas.drawArc(rectF, progress2, Math.abs(progress1) + Math.abs(progress2), false, paint);

      paint.setColor(color3);
      // line of 0º
      canvas.drawArc(rectF, 0, 1, false, paint);
    } else {
      System.out.println(progress1);
      paint.setColor(color2);
      // second progress
      canvas.drawArc(rectF, 0, progress2, false, paint);

      paint.setColor(color1);
      // first progress blue part
      canvas.drawArc(rectF, 0, progress1, false, paint);

    }
    canvas.drawText("90°", (getWidth() - ninetyHoffset - textPaint.measureText("90°")) / 2.0f,
        getHeight() - ninetyVoffset, textPaint);
    canvas.drawText("0°", (getWidth() - zeroHoffset - textPaint.measureText("0°")), zeroVoffset,
        textPaint);

  }

  @Override protected Parcelable onSaveInstanceState() {
    final Bundle bundle = new Bundle();
    bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
    bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth());
    bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
    bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
    //bundle.putInt(INSTANCE_PROGRESS, progress1);
    return bundle;
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    if (state instanceof Bundle) {
      final Bundle bundle = (Bundle) state;
      strokeWidth = bundle.getFloat(INSTANCE_STROKE_WIDTH);
      textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
      textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
      //setProgress(bundle.getInt(INSTANCE_PROGRESS));
      initPainters();
      super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
      return;
    }
    super.onRestoreInstanceState(state);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    int width = MeasureSpec.getSize(widthMeasureSpec);
    int offset = (int) UiUtils.dpToPx(getResources(), 100);
    int rightPadding = (int) UiUtils.dpToPx(getResources(), 60);
    rectF.set(strokeWidth / 2f, -offset + strokeWidth / 2f, width - rightPadding - strokeWidth / 2f,
        MeasureSpec.getSize(heightMeasureSpec) - offset / 4f - strokeWidth / 2f);
  }

  @Override protected int getSuggestedMinimumHeight() {
    return min_size;
  }

  @Override protected int getSuggestedMinimumWidth() {
    return min_size;
  }

  public float getStrokeWidth() {
    return strokeWidth;
  }

  public void setProgress1(int progress1) {
    this.progress1 = progress1;
    invalidate();
  }

  public void setProgress2(int progress2) {
    this.progress2 = progress2;
    invalidate();
  }

  public void setNormalOrientation(boolean normalOrientation) {
    this.normalOrientation = normalOrientation;
  }

  public float getTextSize() {
    return textSize;
  }

  public void setTextSize(float textSize) {
    this.textSize = textSize;
    this.invalidate();
  }

  public int getTextColor() {
    return textColor;
  }

  public void setTextColor(int textColor) {
    this.textColor = textColor;
    this.invalidate();
  }

}