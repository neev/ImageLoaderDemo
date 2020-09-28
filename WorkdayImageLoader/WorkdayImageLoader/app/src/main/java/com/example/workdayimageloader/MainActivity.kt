package com.example.workdayimageloader

import android.R.attr.fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.workdayimageloader.repository.Author
import com.example.workdayimageloader.repository.FeedDataItem
import com.example.workdayimageloader.repository.GifItem
import com.example.workdayimageloader.repository.MessageFeedAdapter
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
        //val INVALID_KEY = "0TEb2mWhixPKEfyQwlgeYHFsniKEluSF"
    }

    //TODO: Set a valid API KEY
    val YOUR_API_KEY = INVALID_KEY

    var settings = GPHSettings(gridType = GridType.waterfall, useBlurredBackground = false, theme = GPHTheme.Light, stickerColumnCount = 3)

    var feedAdapter: MessageFeedAdapter? = null
    var messageItems = ArrayList<FeedDataItem>()

    val dialog = GiphyDialogFragment.newInstance(settings)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Giphy.configure(this, "Ypxz17ewQDY3Q3acVJaZDQugZaSAa3Rh")
        setContentView(R.layout.activity_main)

        setupFeed()
        dialog.gifSelectionListener = getGifSelectionListener()
        dialog.show(supportFragmentManager, "gifs_dialog")

        launchGiphyBtn.setOnClickListener {
            dialog.gifSelectionListener = getGifSelectionListener()
            dialog.show(supportFragmentManager, "gifs_dialog")
        }
    }

    private fun setupFeed() {
       
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
            feedAdapter?.notifyItemInserted(messageItems.size - 1)


        }

        override fun onDismissed(selectedContentType: GPHContentType) {
            Timber.d(TAG, "onDismissed")

        }

        override fun didSearchTerm(term: String) {
            Timber.d(TAG, "didSearchTerm $term")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (dialog.isHidden()) {
            dialog.show(supportFragmentManager, "gifs_dialog")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Timber.d("onActivityResult")
        super.onActivityResult(requestCode, resultCode, data)
    }

}