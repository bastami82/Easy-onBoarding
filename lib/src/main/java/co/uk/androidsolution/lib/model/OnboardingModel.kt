package co.uk.androidsolution.lib.model

import androidx.annotation.Keep
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Keep
@Parcel(Parcel.Serialization.BEAN)
data class OnboardingModel @ParcelConstructor constructor(
    val slides: List<SlideModel>,
    val portraitOnly: Boolean = true,
    val shouldHideSkip: Boolean = false
)