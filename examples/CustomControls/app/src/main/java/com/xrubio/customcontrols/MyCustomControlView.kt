package com.xrubio.customcontrols

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.withTranslation

class MyCustomControlView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    private var centered = false
    private val enabledPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private val disabledPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private var _orientation = 0
    private var _radius = 0.0f

    @Suppress("unused")
    var isCentered: Boolean
        get() = centered
        set(value) {
            centered = value
            invalidate()
        }

    @Suppress("unused")
    var color: Int
        get() = enabledPaint.color
        set(value) {
            enabledPaint.color = value
            invalidate()
        }

    @Suppress("unused")
    var disabledColor: Int
        get() = disabledPaint.color
        set(value) {
            disabledPaint.color = value
            invalidate()
        }

    @Suppress("unused")
    var orientation: Int
        get() = _orientation
        set(value) = when (value) {
            LinearLayout.HORIZONTAL, LinearLayout.VERTICAL -> {
                _orientation = value
                requestLayout()
            }
            else -> throw IllegalArgumentException("Orientation must be either HORIZONTAL or VERTICAL.")
        }

    @Suppress("unused")
    var radius: Float
        get() = _radius
        set(value) {
            _radius = value
            // Notice: We need to request layout as radius could change the size of the control
            //invalidate()
            requestLayout()
        }

    // Default behavior for View#setEnabled() already triggers invalidate, but we could use this
    // technique if we need to intercept some standard method and trigger invalidation or other
    // behaviors (like recalculating something, for instance).
    /*
    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        invalidate()
    }
    */

    init {

        val defaultCentered = resources.getBoolean(R.bool.default_my_custom_control_view_centered)
        val defaultColor = resources.getColor(R.color.default_my_custom_control_view_color)
        val defaultDisabledColor = resources.getColor(R.color.default_my_custom_control_view_disabled_color)
        val defaultOrientation = resources.getInteger(R.integer.default_my_custom_control_view_orientation)
        val defaultRadius = resources.getDimension(R.dimen.default_my_custom_control_view_radius)

        context?.withStyledAttributes(attrs, R.styleable.MyCustomControlView, defStyleAttr, defStyleRes) {

            centered = getBoolean(R.styleable.MyCustomControlView_centered, defaultCentered)
            enabledPaint.color =
                getColor(R.styleable.MyCustomControlView_color, defaultColor)
            disabledPaint.color =
                getColor(R.styleable.MyCustomControlView_disabledColor, defaultDisabledColor)
            _orientation =
                getInt(R.styleable.MyCustomControlView_android_orientation, defaultOrientation)
            _radius = getDimension(R.styleable.MyCustomControlView_radius, defaultRadius)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val dx = if (centered) (width - paddingStart - paddingEnd) / 2.0f else _radius
        val dy = if (centered) (height - paddingTop - paddingBottom) / 2.0f else _radius

        /* Without KTX:
        canvas.save()
        canvas.translate(paddingStart.toFloat(), paddingTop.toFloat())
        canvas.drawCircle(...) // drawn on the translated canvas
        canvas.restore()
         */
        canvas.apply {
            withTranslation(paddingStart.toFloat(), paddingTop.toFloat()) {
                drawCircle(dx, dy, _radius,
                    if (isEnabled) enabledPaint else disabledPaint)
            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val minW: Int = (paddingStart + paddingEnd + _radius * 2.0f).toInt()
        val w: Int = resolveSizeAndState(minW, widthMeasureSpec, 0)

        val minH: Int = (paddingTop + paddingBottom + _radius * 2.0f).toInt()
        val h: Int = resolveSizeAndState(minH, heightMeasureSpec, 0)

        setMeasuredDimension(w, h)

    }

    /*
    public override fun onRestoreInstanceState(state: Parcelable) {
        val savedState = state as SavedState
        super.onRestoreInstanceState(savedState.superState)
        mCurrentPage = savedState.currentPage
        mSnapPage = savedState.currentPage
        requestLayout()
    }

    public override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)
        savedState.currentPage = mCurrentPage
        return savedState
    }

    internal class SavedState : View.BaseSavedState {
        var currentPage: Int = 0

        constructor(superState: Parcelable) : super(superState) {}

        private constructor(`in`: Parcel) : super(`in`) {
            currentPage = `in`.readInt()
        }

        override fun writeToParcel(dest: Parcel, flags: Int) {
            super.writeToParcel(dest, flags)
            dest.writeInt(currentPage)
        }

        companion object {

            val CREATOR: Parcelable.Creator<SavedState> = object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState> {
                    return arrayOf<SavedState>(size)
                }
            }
        }
    }
    */
}