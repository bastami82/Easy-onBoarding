package co.uk.androidsolution.lib


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import co.uk.androidsolution.lib.model.SlideModel
import co.uk.androidsolution.lib.util.ChromeTabActivity
import co.uk.androidsolution.lib.util.setOnSingleClickListener
import com.airbnb.lottie.LottieDrawable
import kotlinx.android.synthetic.main.fragment_onboarding.*
import org.parceler.Parcels


private const val ARG_MODEL = "arg_model"

class OnboardingFragment : Fragment() {
    private lateinit var model: SlideModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            model = Parcels.unwrap(it.getParcelable(ARG_MODEL))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageId = model.imageId
        val animationId = model.animationId
        if (animationId == null && imageId == null) {
            imageSectionHolder.visibility = View.GONE
        } else {
            imageSectionHolder.visibility = View.VISIBLE
        }


        if (animationId != null) {
            playAnimation(animationId)
        } else {
            if (imageId != null) {
                onboardingImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        view.context,
                        imageId
                    )
                )
            }
        }
        onboardingLine1.text = model.title
        onboardingLine2.text = model.description
        if (model.linkMask.isNullOrEmpty() || model.linkUrl.isNullOrEmpty()) {
            button_start.visibility = View.GONE
        } else {
            button_start.apply {
                visibility = View.VISIBLE
                text = model.linkMask
                setOnSingleClickListener {
                    model.linkUrl?.let {
                        ChromeTabActivity.startActivity(view.context, it)
                    }

                }
            }
        }
    }


    private fun playAnimation(animationId: Int) {
        lottieAnimationView.apply {
            setAnimation(animationId)
            repeatCount = LottieDrawable.INFINITE
            visibility = View.VISIBLE
            playAnimation()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param model [SlideModel].
         *
         * @return A new instance of fragment OnboardingFragment.
         */
        @JvmStatic
        fun newInstance(
            model: SlideModel
        ) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MODEL, Parcels.wrap(model))
                }
            }
    }
}
