package monster.sasakisan.android_spotlight_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewTreeObserver
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.takusemba.spotlight.OnSpotlightStateChangedListener
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.shape.RoundedRectangle
import com.takusemba.spotlight.target.CustomTarget
import kotlinx.android.synthetic.main.item_hello_world_coach_mark.view.*
import monster.sasakisan.android_spotlight_sample.databinding.ActivityMainBinding
import monster.sasakisan.android_spotlight_sample.databinding.ItemHelloWorldCoachMarkBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        showCoachMark()
    }

    private fun showCoachMark() {
        val coachMarkRoot = FrameLayout(this@MainActivity)
        val helloWorldCoachMarkBinding = DataBindingUtil.inflate<ItemHelloWorldCoachMarkBinding>(layoutInflater, R.layout.item_hello_world_coach_mark, coachMarkRoot, false)

        activityMainBinding.root.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    activityMainBinding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    val location = IntArray(2)
                    activityMainBinding.textView.getLocationInWindow(location)
                    helloWorldCoachMarkBinding.textView.y = (location[1] + activityMainBinding.textView.height).toFloat()

                    val target = CustomTarget.Builder(this@MainActivity)
                        .setPoint(activityMainBinding.textView)
                        .setShape(RoundedRectangle(activityMainBinding.textView.height.toFloat(), activityMainBinding.textView.width.toFloat(), 6f))
                        .setOverlay(helloWorldCoachMarkBinding.root)
                        .build()

                    Spotlight.with(this@MainActivity)
                        .setTargets(target)
                        .setClosedOnTouchedOutside(true)
                        .setOnSpotlightStateListener(object : OnSpotlightStateChangedListener {
                            override fun onStarted() {
                            }

                            override fun onEnded() {
                            }
                        })
                        .start()
                }
            })
    }
}
