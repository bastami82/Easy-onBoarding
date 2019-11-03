package co.uk.androidsolution.lib.model

import androidx.annotation.Keep
import org.parceler.ParcelConstructor

@Keep
@org.parceler.Parcel(org.parceler.Parcel.Serialization.BEAN)
data class SlideModel @ParcelConstructor constructor(
    val imageId: Int?,
    val animationId: Int?,
    val title: String?,
    val description: String?,
    val buttonText: String?,
    val linkUrl: String = "",
    val linkMask: String = ""
)