package co.uk.androidsolution.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.uk.androidsolution.lib.OnboardingActivity
import co.uk.androidsolution.lib.model.OnboardingModel
import co.uk.androidsolution.lib.model.SlideModel
import co.uk.androidsolution.lib.util.setOnSingleClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val ONBOARDING_REQUEST_CODE: Int = 111
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_start.setOnSingleClickListener {
            OnboardingActivity.startForResult(
                activity = this,
                requestCode = ONBOARDING_REQUEST_CODE,
                onboardingModel = OnboardingModel(
                    slides = getSlides(),
                    portraitOnly = false,
                    shouldHideSkip = false
                )
            )
        }
    }

    private fun getSlides(): List<SlideModel> {

        val list = ArrayList<SlideModel>()
        val slide1 = SlideModel(
            imageId = null,
            animationId = R.raw.animation_slide_1,
            title = getString(R.string.onboard_screen1_line1),
            description = getString(R.string.onboard_screen1_line2),
            buttonText = null,
            linkUrl = null,
            linkMask = null
        )

        val slide2 = SlideModel(
            imageId = R.drawable.ic_slide_2,
            animationId = null,
            title = getString(R.string.onboard_screen2_line1),
            description = getString(R.string.onboard_screen2_line2),
            buttonText = getString(R.string.onboard_screen2_button),
            linkUrl = null,
            linkMask = null
        )

        val slide3 = SlideModel(
            imageId = null,
            animationId = null,
            title = getString(R.string.onboard_screen3_line1),
            description = getString(R.string.onboard_screen3_line2),
            buttonText = null,
            linkUrl = getString(R.string.onboard_screen3_link_url),
            linkMask = getString(R.string.onboard_screen3_link_mask)
        )

        list.add(slide1)
        list.add(slide2)
        list.add(slide3)
        return list
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ONBOARDING_REQUEST_CODE -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        Toast.makeText(this, "OnBoarding Completed", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        Toast.makeText(this, "OnBoarding Cancelled!", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }

    }
}
