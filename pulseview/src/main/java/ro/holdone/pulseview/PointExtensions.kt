package ro.holdone.pulseview

import android.graphics.PointF
import kotlin.math.pow

fun PointF.distance(point: PointF): Float {
    return kotlin.math.sqrt(
        (this.x - point.x).toDouble().pow(2.0) + (this.y - point.y).toDouble().pow(2.0)
    ).toFloat()
}