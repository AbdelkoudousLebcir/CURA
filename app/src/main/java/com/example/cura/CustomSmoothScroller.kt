import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

class CustomSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun calculateSpeedPerPixel(displayMetrics: android.util.DisplayMetrics): Float {
        return 32f / displayMetrics.densityDpi
    }
}
