package co.uk.androidsolution.lib.util

import android.view.View

private const val CLICK_SLOP_MS = 500L

private class OnSingleClickListener(
    private val action: (View) -> Any?
) : View.OnClickListener {

    private var lastClickTimeMs: Long? = null

    override fun onClick(v: View) {
        val currentTimeMs = System.currentTimeMillis()


        val enoughTimePassed = lastClickTimeMs
            ?.let { currentTimeMs - it }
            ?.let { it > CLICK_SLOP_MS }
            ?: true

        if (enoughTimePassed) {
            lastClickTimeMs = currentTimeMs
            action.invoke(v)
        }
    }
}

fun View.setOnSingleClickListener(action: ((View) -> Any?)?) {
    action?.let { setOnClickListener(OnSingleClickListener(it)) } ?: setOnClickListener(null)
}