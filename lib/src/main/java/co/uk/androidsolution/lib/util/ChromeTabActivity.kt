package co.uk.androidsolution.lib.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import co.uk.androidsolution.lib.Constant.TAG
import co.uk.androidsolution.lib.R


class ChromeTabActivity : AppCompatActivity() {

    companion object {
        const val ARG_URL = "arg_url"

        @JvmStatic
        fun startActivity(context: Context, url: String) {
            val intent = Intent(context, ChromeTabActivity::class.java)
            intent.putExtra(ARG_URL, url)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.extras?.getString(ARG_URL).apply {
            if (this != null) tryUsingChromeTabs(this) else finish()
        }
    }

    private fun tryUsingChromeTabs(url: String) {
        try {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this, Uri.parse(url))
            finish()
        } catch (e: Exception) {
            Log.e(TAG, "couldn't use chrome-tabs")
            dontUseChromeTabs(url)
        }
    }

    // fallback - Chrometab already has a fallback for webview, but just incase
    private fun dontUseChromeTabs(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Log.e(TAG, "couldn't start ACTION_VIEW activity")
        } finally {
            finish()
        }

    }

}
