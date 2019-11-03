package co.uk.androidsolution.lib

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View.GONE
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import co.uk.androidsolution.lib.model.OnboardingModel

import kotlinx.android.synthetic.main.activity_onboarding.*
import org.parceler.Parcels

/**
 * Accept [OnboardingModel] as a single intent extra when using static start func
 * returns Activity.RESULT_OK if last button is clicked otherwise returns Activity.RESULT_CANCEL
 */

class OnboardingActivity : AppCompatActivity() {

    companion object {
        private const val ONBOARDING_PRESENTATION_MODEL = "ONBOARDING_PRESENTATION_MODEL"

        /**
         * use this when starter is an Activity
         */
        @JvmStatic
        fun startForResult(
            activity: Activity,
            requestCode: Int,
            onboardingModel: OnboardingModel
        ) {
            Intent(activity, OnboardingActivity::class.java).apply {
                putExtra(ONBOARDING_PRESENTATION_MODEL, Parcels.wrap(onboardingModel))
            }.let {
                activity.startActivityForResult(it, requestCode)
            }

        }


        /**
         * use this when starter is a Fragment
         */
        @JvmStatic
        fun startForResult(
            fragment: Fragment,
            requestCode: Int,
            onboardingModel: OnboardingModel
        ) {
            Intent(fragment.context, OnboardingActivity::class.java).apply {
                putExtra(ONBOARDING_PRESENTATION_MODEL, Parcels.wrap(onboardingModel))
            }.let {
                fragment.startActivityForResult(it, requestCode)
            }

        }
    }

    private lateinit var onBoardingModel: OnboardingModel

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBoardingModel = if (savedInstanceState == null) {
            //on first launch get model from intent extras
            Parcels.unwrap(intent.getParcelableExtra(ONBOARDING_PRESENTATION_MODEL))
        } else {
            //restore model from savedInstances (onConfigurationChanges or process kill)
            Parcels.unwrap<OnboardingModel>(
                savedInstanceState.getParcelable(ONBOARDING_PRESENTATION_MODEL)
            )
        }

        if (onBoardingModel.portraitOnly) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        setContentView(R.layout.activity_onboarding)

        if (onBoardingModel.shouldHideSkip) {
            onboardingCancelButton.visibility = GONE
        }

        if (onBoardingModel.slides.size == 1) {
            //single page onBoarding
            onboardingIndicator.visibility = GONE
            onboardingCancelButton.visibility = GONE
            onboardingNextButton.text =
                onBoardingModel.slides.first().buttonText ?: getString(R.string.onboarding_got_it)
        }

        onboardingPager.adapter = OnboardingAdapter(onBoardingModel.slides, supportFragmentManager)
        onboardingPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                if (position == onBoardingModel.slides.lastIndex) {
                    onboardingNextButton.text =
                        onBoardingModel.slides.last().buttonText
                            ?: getString(R.string.onboarding_got_it)
                } else {
                    onboardingNextButton.text = onBoardingModel.slides[position].buttonText
                        ?: getString(R.string.onboarding_next)
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                //ignored override
            }

            override fun onPageScrollStateChanged(state: Int) {
                //ignored override
            }
        })
        onboardingIndicator.setViewPager(onboardingPager)
        onboardingCancelButton.setOnClickListener { finish() }
        onboardingNextButton.setOnClickListener { nextPage() }


    }

    private fun nextPage() {
        val page = onboardingPager.currentItem + 1
        if (page == onboardingPager.adapter?.count) {
            setResult(Activity.RESULT_OK)
            finish()
        } else {
            onboardingPager.setCurrentItem(page, true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(ONBOARDING_PRESENTATION_MODEL, Parcels.wrap(onBoardingModel))
        super.onSaveInstanceState(outState)
    }
}
