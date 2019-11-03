package co.uk.androidsolution.lib

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import co.uk.androidsolution.lib.model.SlideModel


class OnboardingAdapter(private var list: List<SlideModel>, fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return OnboardingFragment.newInstance(
            list[position]
        )
    }

    override fun getCount(): Int = list.size
}
