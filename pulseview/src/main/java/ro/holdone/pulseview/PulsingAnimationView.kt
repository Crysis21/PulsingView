package ro.holdone.pulseview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.holdone.pulseview.R
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

class PulsingAnimationView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val drawPaint = Paint().apply {
        this.isAntiAlias = true
        this.style = Paint.Style.STROKE
        this.strokeWidth = 1.0F
        this.color = Color.GREEN
    }

    var color: Int = Color.GREEN
        set(value) {
            field = value
            drawPaint.color = value
        }

    var startRadius: Float? = null
    var startRadiusPercent: Float = 0.14F
    var strokeWidth: Float = 1.0F
        set(value) {
            field = value
            drawPaint.strokeWidth = value
        }

    var waveDistance: Float? = null
    var waveDistancePercent: Float = 0.03F
    var autoplay: Boolean = false
    var duration = 1000

    //Computed properties. Don't use
    private var baseRadius: Float = 0.0F
    private var baseWaveDistance: Float = 0.0F
    private var currentDrawDistance: Float = 0.0F
    private var maxRadius: Float = 0.0F
    private var center = PointF()
    private var numberOfWaves = 0

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.PulsingAnimationView, 0, 0)
        startRadius = array.getDimensionPixelSize(R.styleable.PulsingAnimationView_baseRadius, 100)
            .toFloat()
        waveDistance = array.getDimensionPixelSize(R.styleable.PulsingAnimationView_waveDistance, 0)
            .toFloat()
        strokeWidth = array.getDimensionPixelSize(
            R.styleable.PulsingAnimationView_strokeWidth, 1)
            .toFloat()
        duration = array.getInteger(
            R.styleable.PulsingAnimationView_duration, 1000)

        drawPaint.strokeWidth = strokeWidth
        color = array.getColor(R.styleable.PulsingAnimationView_pulseColor, color)
        autoplay = array.getBoolean(R.styleable.PulsingAnimationView_autoplay, false)
        array.recycle()

        if (autoplay) {
            startAnimation()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startRadius?.let {
            baseRadius = it
        } ?: run {
            baseRadius = min(w, h) * startRadiusPercent
        }
        waveDistance?.let {
            baseWaveDistance = it
        } ?: run {
            baseWaveDistance = min(w, h) * waveDistancePercent
        }
        center = PointF(w.toFloat() / 2, h.toFloat() / 2)
        maxRadius = center.distance(PointF(w.toFloat() / 2, 0.0f))

        numberOfWaves = ((maxRadius - baseRadius) / baseWaveDistance).toInt() + 1
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawPaint.alpha = 255
        canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, baseRadius, drawPaint)

        repeat(numberOfWaves) {
            val nextRadius = baseRadius + currentDrawDistance + strokeWidth + it * baseWaveDistance - strokeWidth
            val alpha = 1f - min((nextRadius) / maxRadius, 1.0F)
            drawPaint.alpha = (alpha * 255).toInt()
            canvas?.drawCircle(width.toFloat() / 2, height.toFloat() / 2, nextRadius, drawPaint)
        }
    }


    fun startAnimation() {
        val wave1Animator = ValueAnimator.ofFloat(0F, 1F)
        wave1Animator.repeatCount = ValueAnimator.INFINITE
        wave1Animator.duration = duration.toLong()
        wave1Animator.repeatMode = ValueAnimator.RESTART
        wave1Animator.interpolator = LinearInterpolator()
        wave1Animator.addUpdateListener {
            currentDrawDistance = it.animatedFraction * baseWaveDistance
            invalidate()
        }

        wave1Animator.start()
    }

}

fun PointF.distance(point: PointF): Float {
    return sqrt(
        (this.x - point.x).toDouble().pow(2.0) + (this.y - point.y).toDouble().pow(2.0)
    ).toFloat()
}