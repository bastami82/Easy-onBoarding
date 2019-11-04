[![](https://jitpack.io/v/bastami82/Easy-onBoarding.svg)](https://jitpack.io/#bastami82/Easy-onBoarding/v1.0.0)


# Easy onBoarding
Use this library to add onBoarding slides to your app easily.

<img src="lib/src/main/res/raw/onboarding.gif" width="500" height="889">


# Setup

Add to your project build.gradle
```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

Add to your app module build.gradle
```gradle
dependencies {
        implementation 'com.github.bastami82:Easy-onBoarding:v1.0.0'
}
```



# How to use:
see sample app included in lib for working example:

1- Start onBoarding Activity for result like this from your Activity or Fragment
 ```
  OnboardingActivity.startForResult(
                activity = this,
                requestCode = ONBOARDING_REQUEST_CODE,
                onboardingModel = OnboardingModel(
                slides = getSlides(),
                portraitOnly = false,
                shouldHideSkip = false
                )
            )
 ```
2- create your slide(s) and pass it to `slides` parameter above.
```
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
``` 

3- OnboardingActivity will return:

 `Activity.RESULT_OK`  if final button (in last slide) is tapped,
 
 `Activity.RESULT_CANCEL`  if back or skip button is tapped.



# License:

Licensed under the Apache License, Version 2.0 
