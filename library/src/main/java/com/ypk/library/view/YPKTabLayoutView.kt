package com.ypk.library.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.ypk.library.R
import com.ypk.library.interfac.OnTabClickListener
import com.ypk.library.utils.DisplayUtil.dp2px
import com.ypk.library.utils.DisplayUtil.sp2px

/**
 * @Author: YuanPeikai
 * @CreateDate: 2020/4/24 16:32
 * @Description:
 *
 * rQuadTo 和 cubicTo的区别：
 * cubicTo：坐标点都是基于原点的（0,0）
 * rQuadTo：是基于画笔上一次终点坐标的
 *
 * class YPKTabLayoutView @JvmOverloads constructor(
 * private val mContext: Context,
 * attrs: AttributeSet? = null,
 * defStyleAttr: Int = 0
 * ) : View(mContext, attrs, defStyleAttr) {
 *   init {
 *     initData()
 *     initAttr(attrs, defStyleAttr)
 *    }
 * 、、、
 *}
 *
 * 相当于 Java中
 *
 *public YPKTabLayoutView2(Context context) {
 *this(context, null);
 *}

 *public YPKTabLayoutView2(Context context, @Nullable AttributeSet attrs) {
 *this(context, attrs, 0);
 *}

 *public YPKTabLayoutView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
 *super(context, attrs, defStyleAttr);
 *mContext = context;
 *initData();
 *initAttr(attrs, defStyleAttr);
 *}
 */
class YPKTabLayoutView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(mContext, attrs, defStyleAttr) {

    init {
        setDefaultData()
        initAttr(attrs, defStyleAttr)
    }

    private lateinit var paint: Paint
    private lateinit var textPaint: Paint
    private lateinit var indicatorPaint: Paint
    private var viewWidth = 0
    private var viewHeight = 0 //测量的高度，与 customizeViewDefaultHeight 相等

    private val customizeViewDefaultHeight = 100 //自定义的控件默认高度
    private var textWidth = 0f
    private var tabPosition = 0
    private lateinit var tabTextList: MutableList<String>
    private var view_bg_corners = 0f //圆角的大小

    private var tabTextColor = 0
    private var tabSelectTextColor = 0
    private var selectColor = 0
    private var tabTextSize = 0f
    private var tabTextStyle = 0
    private var view_bg = 0
    private var arcControlX = 30 //值越大，弧度越大
    private var tabNumber = 0 //tab的数量

    private val arcControlY = 3
    private val arcWidth = 50 //曲线的宽度
    private var centerX = 0
    private var centerY = 0

    private var showTabIndicator = false//默认不显示指示线
    private var showTabIndicatorSelect = false

    //private var tabIndicatorColor = 0;//指示线的颜色
    private var tabIndicatorHeight = 0f;//指示线的高度
    private var tabIndicatorSpacing = 0f;//指示线的与文字的间隔

    lateinit var rectF_bg: RectF
    lateinit var rectF_bg1: RectF
    private fun setDefaultData() {
        view_bg_corners = dp2px(mContext, 5f).toFloat()
        view_bg = ContextCompat.getColor(mContext, R.color.tab_select_color)
        selectColor = ContextCompat.getColor(mContext, R.color.tab_normal_color)
        tabTextColor = ContextCompat.getColor(mContext, R.color.tab_text_color)
        //默认选中文本和未选中文本是一个颜色值
        tabSelectTextColor = ContextCompat.getColor(mContext, R.color.tab_text_color)

        val font = Typeface.create(Typeface.SANS_SERIF, tabTextStyle)

        //tabIndicatorColor = tabSelectTextColor
        tabIndicatorHeight = dp2px(mContext, 2f).toFloat()
        tabIndicatorSpacing = dp2px(mContext, 5f).toFloat()
        indicatorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        indicatorPaint!!.style = Paint.Style.FILL
        indicatorPaint!!.typeface = font

        tabTextSize = sp2px(mContext, 14f).toFloat()
        tabTextStyle = Typeface.NORMAL
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.style = Paint.Style.FILL
        textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        textPaint!!.style = Paint.Style.FILL

        textPaint!!.typeface = font

        tabTextList = ArrayList()

        tabTextList.add("tab01")
        tabTextList.add("tab02")
        tabTextList.add("tab03")
        tabNumber = tabTextList.size
    }

    private fun initAttr(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = mContext.obtainStyledAttributes(
            attrs,
            R.styleable.YPKTabLayoutView,
            defStyleAttr,
            0
        )
        val indexCount = typedArray.indexCount
        for (i in 0 until indexCount) {
            val index = typedArray.getIndex(i)
            if (index == R.styleable.YPKTabLayoutView_arcControlX) {
                arcControlX = typedArray.getInt(index, arcControlX)
            } else if (index == R.styleable.YPKTabLayoutView_select_tab_color) {
                selectColor = typedArray.getColor(index, selectColor)
            } else if (index == R.styleable.YPKTabLayoutView_tab_view_bg) {
                view_bg = typedArray.getColor(index, view_bg)
            } else if (index == R.styleable.YPKTabLayoutView_tabTextColor) {
                tabTextColor = typedArray.getColor(index, tabTextColor)
            } else if (index == R.styleable.YPKTabLayoutView_tabSelectTextColor) {
                tabSelectTextColor = typedArray.getColor(index, tabSelectTextColor)
            } else if (index == R.styleable.YPKTabLayoutView_tabTextSize) {
                tabTextSize = typedArray.getDimension(index, tabTextSize)
            } else if (index == R.styleable.YPKTabLayoutView_view_bg_corners) {
                view_bg_corners = typedArray.getDimension(index, view_bg_corners)
            } else if (index == R.styleable.YPKTabLayoutView_tab_indicator_height) {
                tabIndicatorHeight = typedArray.getDimension(index, tabIndicatorHeight)
            } else if (index == R.styleable.YPKTabLayoutView_tab_indicator_spacing) {
                tabIndicatorSpacing = typedArray.getDimension(index, tabIndicatorSpacing)
            } else if (index == R.styleable.YPKTabLayoutView_show_indicator) {
                showTabIndicator = typedArray.getBoolean(index, showTabIndicator)
            } else if (index == R.styleable.YPKTabLayoutView_show_indicator_select) {
                showTabIndicatorSelect = typedArray.getBoolean(index, showTabIndicatorSelect)
            }
        }
        typedArray.recycle()
    }


    fun setSelectTab(tabPosition: Int) {
        if (tabPosition < tabNumber) {
            this.tabPosition = tabPosition
            invalidate()
            if (onTabClickListener != null) {
                onTabClickListener!!.tabSelectedListener(tabPosition)
            }
        }
    }

    /**
     * 设置tab项文本的字体样式,默认Typeface.NORMAL
     * Typeface.NORMAL  Typeface.BOLD  Typeface.ITALIC
     *
     * @param tabTextStyle
     */
    fun setTabTextStyle(tabTextStyle: Int) {
        val font = Typeface.create(Typeface.SANS_SERIF, tabTextStyle)
        textPaint!!.typeface = font
        indicatorPaint!!.typeface = font
        invalidate()
    }

    fun setTabTextList(tabTexts: MutableList<String>) {
        tabTextList = tabTexts
        tabNumber = tabTextList!!.size
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, measureHeight(heightMeasureSpec))
    }

    private fun measureHeight(measureSpec: Int): Int {
        var result = 0
        val mode = MeasureSpec.getMode(measureSpec)
        val size = MeasureSpec.getSize(measureSpec)
        if (mode == MeasureSpec.EXACTLY) { //表示父控件已经确切的指定了子View的大小。
            result = size
            //System.out.println("YPKTabLayoutView.measureHeight result1=" + result);
        } else if (mode == MeasureSpec.AT_MOST) { //表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
            result = Math.min(customizeViewDefaultHeight, size)
            // System.out.println("YPKTabLayoutView.measureHeight result2=" + result);
        } else if (mode == MeasureSpec.UNSPECIFIED) { //默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
            result = customizeViewDefaultHeight
        }
        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        viewWidth = right - left
        viewHeight = bottom - top
        centerX = viewWidth / 2
        textWidth = (viewWidth - arcWidth * (tabNumber - 1)) / tabNumber.toFloat()
        rectF_bg = RectF(0f, 0f, viewWidth.toFloat(), viewHeight.toFloat())
        rectF_bg1 =
            RectF((0 + 50).toFloat(), (0 + 50).toFloat(), viewWidth.toFloat(), viewHeight.toFloat())
        textPaint!!.textSize = tabTextSize
        textPaint!!.color = tabTextColor
        indicatorPaint!!.color = tabTextColor
        indicatorPaint.strokeWidth = tabIndicatorHeight

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var isHandleClick = false //是否处理点击事件
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y
                println("YPKTabLayoutView.onTouchEvent x=$x y=$y")
                var i = 0
                while (i < tabNumber) {
                    if (x <= (i + 1) * textWidth + i * arcWidth + arcWidth / 2) { //点击的第一个按钮
                        tabPosition = i
                        if (onTabClickListener != null) {
                            onTabClickListener!!.tabSelectedListener(tabPosition)
                        }
                        invalidate()
                        isHandleClick = true
                        break
                    }
                    i++
                }
                return isHandleClick
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return super.onTouchEvent(event)
    }

    public var onTabClickListener: OnTabClickListener? = null

    //tab 选择监听
    public fun addTabSelectedListener(onTabClickListener: OnTabClickListener?) {
        this.onTabClickListener = onTabClickListener
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint!!.color = view_bg


        //范围裁切 path canvas.save(); canvas.restore();
        val clipPath = Path()
        clipPath.addRoundRect(rectF_bg, view_bg_corners, view_bg_corners, Path.Direction.CW)
        canvas.clipPath(clipPath)
        canvas.drawRect(rectF_bg!!, paint)
        //canvas.drawRoundRect(rectF_bg, view_bg_corners, view_bg_corners, paint);
        if (tabPosition == 0) {
            //最左边的图形
            val pathLeft = Path()
            pathLeft.lineTo(textWidth, 0f)
            /* pathLeft.cubicTo(textWidth + arcControlX, arcControlY.toFloat(),
                 textWidth + arcWidth - arcControlX, viewHeight - arcControlY.toFloat(),
                 textWidth + arcWidth, viewHeight.toFloat()
             )*/
            pathLeft.rCubicTo(
                arcControlX.toFloat(), arcControlY.toFloat(),
                arcWidth.toFloat() - arcControlX, viewHeight - arcControlY.toFloat(),
                arcWidth.toFloat(), viewHeight.toFloat()
            )
            pathLeft.lineTo(0f, viewHeight.toFloat())
            pathLeft.lineTo(0f, 0f)
            paint!!.color = selectColor
            canvas.drawPath(pathLeft, paint!!)
        } else if (tabPosition == tabNumber - 1) {
            //最右边的图形
            val pathRight = Path()
            pathRight.moveTo(viewWidth.toFloat(), 0f)
            pathRight.lineTo(viewWidth - textWidth, 0f)
            /*pathRight.cubicTo(
                viewWidth - textWidth - arcControlX,
                arcControlY.toFloat(),
                viewWidth - textWidth - arcWidth + arcControlX,
                viewHeight - arcControlY.toFloat(),
                viewWidth - textWidth - arcWidth,
                viewHeight.toFloat()
            )*/
            pathRight.rCubicTo(
                -arcControlX.toFloat(), arcControlY.toFloat(),
                -arcWidth.toFloat() + arcControlX, viewHeight - arcControlY.toFloat(),
                -arcWidth.toFloat(), viewHeight.toFloat()
            )
            pathRight.lineTo(viewWidth.toFloat(), viewHeight.toFloat())
            pathRight.lineTo(viewWidth.toFloat(), 0f)
            paint!!.color = selectColor
            canvas.drawPath(pathRight, paint!!)
        } else {

            //中间的图形
            val pathCenter = Path()
            pathCenter.moveTo(tabPosition * textWidth + tabPosition * arcWidth, 0f)
            /*pathCenter.cubicTo(
                tabPosition * textWidth + tabPosition * arcWidth - arcControlX,
                arcControlY.toFloat(),
                tabPosition * textWidth + tabPosition * arcWidth - arcWidth + arcControlX,
                viewHeight - arcControlY.toFloat(),
                tabPosition * textWidth + tabPosition * arcWidth - arcWidth,
                viewHeight.toFloat()
            )*/
            pathCenter.rCubicTo(
                -arcControlX.toFloat(),
                arcControlY.toFloat(),
                -arcWidth.toFloat() + arcControlX,
                viewHeight - arcControlY.toFloat(),
                -arcWidth.toFloat(),
                viewHeight.toFloat()
            )
            pathCenter.lineTo(
                tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcWidth,
                viewHeight.toFloat()
            )
            pathCenter.cubicTo(
                tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcWidth - arcControlX,
                viewHeight - arcControlY.toFloat(),
                tabPosition * textWidth + tabPosition * arcWidth + textWidth + arcControlX,
                arcControlY.toFloat(),
                tabPosition * textWidth + tabPosition * arcWidth + textWidth,
                0f
            )

            pathCenter.lineTo(tabPosition * textWidth + tabPosition * arcWidth, 0f)
            paint!!.color = selectColor
            canvas.drawPath(pathCenter, paint!!)
        }
        drawTextContent(canvas)
    }

    private fun drawTextContent(canvas: Canvas) {
        for (i in tabTextList!!.indices) {
            val strTabText = tabTextList!![i]
            val rectText = Rect()
            textPaint!!.getTextBounds(strTabText, 0, strTabText.length, rectText)
            val strWidth = rectText.width()
            val strHeight = rectText.height()
            if (i == tabPosition) { //选中的tab项文本
                textPaint!!.color = tabSelectTextColor
                indicatorPaint!!.color = tabSelectTextColor
            } else {
                textPaint!!.color = tabTextColor
                if (showTabIndicatorSelect) {
                    indicatorPaint!!.color = Color.TRANSPARENT
                } else {
                    indicatorPaint!!.color = tabTextColor
                }
            }
            if (i == 0) {
                canvas.drawText(
                    strTabText,
                    (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                    viewHeight / 2 + strHeight / 2.toFloat(),
                    textPaint!!
                )
                if (showTabIndicator||showTabIndicatorSelect) {
                    val maxSpace = viewHeight / 2 - strHeight / 2.toFloat()
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace;
                    }
                    canvas.drawLine(
                        (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                        viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                        (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth,
                        viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                        indicatorPaint
                    )
                }


            } else if (i == tabTextList!!.size - 1) {
                canvas.drawText(
                    strTabText,
                    viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                    viewHeight / 2 + strHeight / 2.toFloat(),
                    textPaint!!
                )
                if (showTabIndicator||showTabIndicatorSelect) {
                    val maxSpace = viewHeight / 2 - strHeight / 2.toFloat()
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace;
                    }
                    canvas.drawLine(
                        viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2,
                        viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                        viewWidth - (textWidth + arcWidth / 2) / 2 - strWidth / 2 + strWidth,
                        viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                        indicatorPaint
                    )
                }


            } else {
                canvas.drawText(
                    strTabText,
                    textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2,
                    viewHeight / 2 + strHeight / 2.toFloat(),
                    textPaint!!
                )

                if (showTabIndicator||showTabIndicatorSelect) {
                    val maxSpace = viewHeight / 2 - strHeight / 2.toFloat()
                    if (tabIndicatorSpacing > maxSpace) {
                        tabIndicatorSpacing = maxSpace;
                    }
                    canvas.drawLine(
                        textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2,
                        viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                        textWidth * i + arcWidth * (i - 1) + (textWidth + 2 * arcWidth) / 2 - strWidth / 2 + strWidth,
                        viewHeight / 2 + strHeight / 2.toFloat() + tabIndicatorSpacing,
                        indicatorPaint
                    )
                }

            }
        }
    }

}