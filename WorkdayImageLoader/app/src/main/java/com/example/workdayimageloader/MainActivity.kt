package com.example.workdayimageloader

import android.R.attr.fragment
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.workdayimageloader.repository.*
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.themes.GPHTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import com.giphy.sdk.ui.views.GiphyDialogFragment.GifSelectionListener
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
        val INVALID_KEY = "Ypxz17ewQDY3Q3acVJaZDQugZaSAa3Rh"
    }

    //TODO: Set a valid API KEY
    val YOUR_API_KEY = INVALID_KEY

    var settings = GPHSettings(gridType = GridType.waterfall, useBlurredBackground = false, theme = GPHTheme.Light, stickerColumnCount = 3)

    var feedAdapter: MessageFeedAdapter? = null
    var messageItems = ArrayList<FeedDataItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Giphy.configure(this, "0TEb2mWhixPKEfyQwlgeYHFsniKEluSF")
        setContentView(R.layout.activity_main)
        GiphyDialogFragment.newInstance().show(supportFragmentManager, "giphy_dialog")

        setupToolbar()
        setupFeed()

        launchGiphyBtn.setOnClickListener {
            val dialog = GiphyDialogFragment.newInstance(settings)
            dialog.gifSelectionListener = getGifSelectionListener()
            dialog.show(supportFragmentManager, "gifs_dialog")
        }
    }

    private fun setupFeed() {
        messageItems.add(
            MessageItem(
                "Hi there! The SDK is perfect for many contexts, including messaging, reactions, stories and other camera features. This is one example of how the GIPHY SDK can be used in a messaging app.",
                Author.GifBot
            )
        )
        messageItems.add(
            MessageItem(
                "Tap the GIPHY button in the bottom left to see the SDK in action. Tap the settings icon in the top right to try out all of the customization options.",
                Author.GifBot
            )
        )
        if (YOUR_API_KEY == INVALID_KEY) {
            //messageItems.add(InvalidKeyItem(Author.GifBot))
        }
        feedAdapter = MessageFeedAdapter(messageItems)
        messageFeed.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        messageFeed.adapter = feedAdapter
    }

    private fun getGifSelectionListener() = object : GifSelectionListener {
        override fun onGifSelected(
            media: Media,
            searchTerm: String?,
            selectedContentType: GPHContentType
        ) {
            Timber.d(TAG, "onGifSelected")
            messageItems.add(GifItem(media, Author.Me))
            val frag = ScrollingFragment::class.java
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction =
                fragmentManager.beginTransaction()
            fragmentTransaction.replace(android.R.id.content, ScrollingFragment())
            fragmentTransaction.commit()
            //feedAdapter?.notifyItemInserted(messageItems.size - 1)
        }

        override fun onDismissed(selectedContentType: GPHContentType) {
            Timber.d(TAG, "onDismissed")
        }

        override fun didSearchTerm(term: String) {
            Timber.d(TAG, "didSearchTerm $term")
        }
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
    }

}