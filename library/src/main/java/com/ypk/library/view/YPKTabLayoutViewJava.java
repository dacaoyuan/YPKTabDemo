package com.ypk.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.ypk.library.R;
import com.ypk.library.interfac.OnTabClickListener;
import com.ypk.library.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YuanPeikai
 * @CreateDate: 2020/4/24 16:32
 * @Description:
 */
public class YPKTabLayoutViewJava extends View {

    private Context mContext;
    private Paint paint;
    private Paint textPaint;
    private Paint indicatorPaint;
    private int viewWidth;
    private int viewHeight;//测量的高度，与 customizeViewDefaultHeight 相等

    private int customizeViewDefaultHeight;//自定义的控件默认高度
    private float textWidth;
    private int tabPosition = 0;
    private List<String> tabTextList;
    private float view_bg_corners;//圆角的大小

    private int tabTextColor;
    private int tabSelectTextColor;
    private int selectColor;
    private float tabTextSize;
    private int tabTextStyle;
    private int view_bg;
    private int arcControlX = 30;//值越大，弧度越大
    private int tabNumber;//tab的数量

    private int arcControlY = 3;
    private int arcWidth = 50;//曲线的宽度
    private int centerX;
    private int centerY;

    private boolean showTabIndicator;//默认不显示指示线
    private boolean showTabIndicatorSelect;

    //private float tabIndicatorColor = 0;//指示线的颜色
    private float tabIndicatorHeight = 0f;//指示线的高度
    private float tabIndicatorSpacing = 0f;//指示线的与文字的间隔

    RectF rectF_bg;
    RectF rectF_bg1;

    public YPKTabLayoutViewJava(Context context) {
        this(context, null);
    }

    public YPKTabLayoutViewJava(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YPKTabLayoutViewJava(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        customizeViewDefaultHeight = DisplayUtil.dp2px(mContext, 45f);
        setDefaultData();
        initAttr(attrs, defStyleAttr);
    }

    private void setDefaultData() {

        view_bg_corners = DisplayUtil.dp2px(mContext, 5f);
        view_bg = ContextCompat.getColor(mContext, R.color.tab_select_color);
        selectColor = ContextCompat.getColor(mContext, R.color.tab_normal_color);
        tabTextColor = ContextCompat.getColor(mContext, R.color.tab_text_color);
        //默认选中文本和未选中文本是一个颜色值
        tabSelectTextColor = ContextCompat.getColor(mContext, R.color.tab_text_color);

        Typeface font = Typeface.create(Typeface.SANS_SERIF, tabTextStyle);

        //tabIndicatorColor = tabSelectTextColor
        tabIndicatorHeight = DisplayUtil.dp2px(mContext, 2f);
        tabIndicatorSpacing = DisplayUtil.dp2px(mContext, 5f);
        indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indicatorPaint.setStyle(Paint.Style.FILL);
        indicatorPaint.setTypeface(font);

        tabTextSize = DisplayUtil.sp2px(mContext, 14f);
        tabTextStyle = Typeface.NORMAL;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);

        textPaint.setTypeface(font);

        tabTextList = new ArrayList<>();

        tabTextList.add("tab01");
        tabTextList.add("tab02");
        tabTextList.add("tab03");
        tabNumber = tabTextList.size();

    }

    private void initAttr(AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.YPKTabLayoutView, defStyleAttr, 0);

        int indexCount = typedArray.getIndexCount();

        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if (index == R.styleable.YPKTabLayoutView_arcControlX) {
                arcControlX = typedArray.getInt(index, arcControlX);
            } else if (index == R.styleable.YPKTabLayoutView_select_tab_color) {
                selectColor = typedArray.getColor(index, selectColor);
            } else if (index == R.styleable.YPKTabLayoutView_tab_view_bg) {
                view_bg = typedArray.getColor(index, view_bg);
            } else if (index == R.styleable.YPKTabLayoutView_tabTextColor) {
                tabTextColor = typedArray.getColor(index, tabTextColor);
            } else if (index == R.styleable.YPKTabLayoutView_tabSelectTextColor) {
                tabSelectTextColor = typedArray.getColor(index, tabSelectTextColor);
            } else if (index == R.styleable.YPKTabLayoutView_tabTextSize) {
                tabTextSize = typedArray.getDimension(index, tabTextSize);
            } else if (index == R.styleable.YPKTabLayoutView_view_bg_corners) {
                view_bg_corners = typedArray.getDimension(index, view_bg_corners);
            } else if (index == R.styleable.YPKTabLayoutView_tab_indicator_height) {
                tabIndicatorHeight = typedArray.getDimension(index, tabIndicatorHeight);
            } else if (index == R.styleable.YPKTabLayoutView_tab_indicator_spacing) {
                tabIndicatorSpacing = typedArray.getDimension(index, tabIndicatorSpacing);
            } else if (index == R.styleable.YPKTabLayoutView_show_indicator) {
                showTabIndicator = typedArray.getBoolean(index, showTabIndicator);
            } else if (index == R.styleable.YPKTabLayoutView_show_indicator_select) {
                showTabIndicatorSelect = typedArray.getBoolean(index, showTabIndicatorSelect);
            }
        }
        typedArray.recycle();
    }


    public void setSelectTab(int tabPosition) {
        if (tabPosition < tabNumber) {
            this.tabPosition = tabPosition;
            invalidate();
            if (onTabClickListener != null) {
                onTabClickListener.tabSelectedListener(tabPosition);
            }
        }
    }

    /**
     * 设置tab项文本的字体样式,默认Typeface.NORMAL
     * Typeface.NORMAL  Typeface.BOLD  Typeface.ITALIC
     *
     * @param tabTextStyle
     */
    public void setTabTextStyle(int tabTextStyle) {
        Typeface font = Typeface.create(Typeface.SANS_SERIF, tabTextStyle);
        textPaint.setTypeface(font);
        invalidate();
    }

    public void setTabTextList(List<String> tabTexts) {
        tabTextList = tabTexts;
        tabNumber = tabTextList.size();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, measureHeight(heightMeasureSpec));
    }


    private int measureHeight(int measureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {//表示父控件已经确切的指定了子View的大小。
            result = size;
            //System.out.println("YPKTabLayoutView.measureHeight result1=" + result);
        } else if (mode == MeasureSpec.AT_MOST) {//表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
            result = Math.min(customizeViewDefaultHeight, size);
            // System.out.println("YPKTabLayoutView.measureHeight result2=" + result);
        } else if (mode == MeasureSpec.UNSPECIFIED) {//默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
            result = customizeViewDefaultHeight;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewWidth = right - left;
        viewHeight = bottom - top;
        centerX = viewWidth / 2;

        textWidth = (viewWidth - arcWidth * (tabNumber - 1)) / tabNumber;
        rectF_bg = new RectF(0, 0, viewWidth, viewHeight);

        rectF_bg1 = new RectF(0 + 50, 0 + 50, viewWidth, viewHeight);

        textPaint.setTextSize(tabTextSize);
        textPaint.setColor(tabTextColor);
        indicatorPaint.setColor(tabTextColor);
        indicatorPaint.setStrokeWidth(tabIndicatorHeight);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isHandleClick = false;//是否处理点击事件

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float x = event.getX();
                float y = event.getY();
                System.out.println("YPKTabLayoutView.onTouchEvent x=" + x + " y=" + y);
                for (int i = 0; i < tabNumber; i++) {
                    if (x <= ((i + 1) * textWidth + i * arcWidth + arcWidth / 2)) {//点击的第一个按钮
                        tabPosition = i;
                        if (onTabClickListener != null) {
                            onTabClickListener.tabSelectedListener(tabPosition);
                        }
                        invalidate();
                        isHandleClick = true;
                        break;
                    }
                }
                return isHandleClick;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }


    public OnTabClickListener onTabClickListener;


    //tab 选择监听
    public void addTabSelectedListener(OnTabClickListener onTabClickListener) {
        this.onTabClickListener = onTabClickListener;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(view_bg);


        //范围裁切 path canvas.save(); canvas.restore();
        Path clipPath = new Path();
        clipPath.addRoundRect(rectF_bg, view_bg_corners, view_bg_corners, Path.Direction.CW);
        canvas.clipPath(clipPath);

        canvas.drawRect(rectF_bg, paint);
        //canvas.drawRoundRect(rectF_bg, view_bg_corners, view_bg_corners, paint);
        if (tabPosition == 0) {
            //最左边的图形
            Path pathLeft = new Path();

            pathLeft.lineTo(textWidth, 0);

            pathLeft.cubicTo(textWidth + arcControlX, arcControlY, textWidth + arcWidth - arcControlX, viewHeight - arcControlY, textWidth + arcWidth, viewHeight);

            pathLeft.lineTo(0, viewHeight);

            pathLeft.lineTo(0, 0);

            paint.setColor(selectColor);
            canvas.drawPath(pathLeft, paint);

        } else if (tabPosition == tabNumber - 1) {
            //最右边的图形
            Path pathRight = new Path();
            pathRight.moveTo(viewWidth, 0);

            pathRight.lineTo(viewWidth - textWidth, 0);

            pathRight.cubicTo(viewWidth - textWidth - arcControlX, arcControlY, viewWidth - textWidth - arcWidth + arcControlX, viewHeight - arcControlY, viewWidth - textWidth - arcWidth, viewHeight);

            pathRight.lineTo(viewWidth, viewHeight);

            pathRight.lineTo(viewWidth, 0);

            paint.setColor(selectColor);
            canvas.drawPath(pathRight, paint);


        } else {

            //中间的图形
            Path pathCenter = new Path();
            pathCenter.moveTo(tabPosition * textWidth + tabPosition * arcWidth, 0);
            pathCenter.cubicTo(tabPosition * textWidth + tabPosition * arcWidth - arcControlX, arcControlY, tabPosition * textWidth + tabPosition * arcWidth - arcWidth + arcControlX, viewHeight - arcControlY, tabPosition * textWidth + tabPosition * arcWidth - arcWidth, viewHeight);
            pathCenter.lineTo(tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcWidth, viewHeight);
            pathCenter.cubicTo(tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcWidth - arcControlX, viewHeight - arcControlY, tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcControlX, arcControlY, tabPosition * textWidth + tabPosition * arcWidth + textWidth, 0);
            pathCenter.lineTo(tabPosition * textWidth + tabPosition * arcWidth, 0);


            paint.setColor(selectColor);
            canvas.drawPath(pathCenter, paint);
        }

        drawTextContent(canvas);


    }

    private void drawTextContent(Canvas canvas) {
        for (int i = 0; i < tabTextList.size(); i++) {
            String strTabText = tabTextList.get(i);
            Rect rectText = new Rect();
            textPaint.getTextBounds(strTabText, 0, strTabText.length(), rectText);
            int strWidth = rectText.width();
            int strHeight = rectText.height();

            if (i == tabPosition) {//选中的tab项文本
                textPaint.setColor(tabSelectTextColor);
                indicatorPaint.setColor(tabSelectTextColor);
            } else {
                textPaint.setColor(tabTextColor);
                if (showTabIndicatorSelect) {
                    indicatorPaint.setColor(Color.TRANSPARENT);
                } else {
                    indicatorPaint.setColor(tabTextColor);
                }
            }
            if (i == 0) {
                canvas.drawText(strTabText, (textWidth + arcWidth / 2) / 2 - strWidth / 2, viewHeight / 2 + strHeight / 2, textPaint);

                if (showTabIndicator || showTabIndicatorSelect) {
                    int maxSpace = viewHeight / 2 - strHeight / 2;
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace;
                    }
                    canvas.drawLine(
                            (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                            viewHeight / 2 + strHeight / 2 + tabIndicatorSpacing,
                            (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth,
                            viewHeight / 2 + strHeight / 2 + tabIndicatorSpacing,
                            indicatorPaint
                    );
                }


            } else if (i == tabTextList.size() - 1) {
                canvas.drawText(strTabText, viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2, viewHeight / 2 + strHeight / 2, textPaint);

                if (showTabIndicator || showTabIndicatorSelect) {
                    int maxSpace = viewHeight / 2 - strHeight / 2;
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace;
                    }
                    canvas.drawLine(
                            viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                            viewHeight / 2 + strHeight / 2 + tabIndicatorSpacing,
                            viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth,
                            viewHeight / 2 + strHeight / 2 + tabIndicatorSpacing,
                            indicatorPaint
                    );
                }


            } else {
                canvas.drawText(strTabText, textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2, viewHeight / 2 + strHeight / 2, textPaint);

                if (showTabIndicator || showTabIndicatorSelect) {
                    int maxSpace = viewHeight / 2 - strHeight / 2;
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace;
                    }
                    canvas.drawLine(
                            textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2,
                            viewHeight / 2 + strHeight / 2 + tabIndicatorSpacing,
                            textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2 + strWidth,
                            viewHeight / 2 + strHeight / 2 + tabIndicatorSpacing,
                            indicatorPaint
                    );
                }

            }


        }
    }
}
