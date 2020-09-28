package com.example.workdayimageloader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workdayimageloader.repository.FeedDataItem
import com.example.workdayimageloader.repository.MessageFeedAdapter

class ScrollingFragment : Fragment() {
    var feedAdapter: MessageFeedAdapter? = null
    var messageItems = ArrayList<FeedDataItem>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       // feedAdapter = MessageFeedAdapter(messageItems)

        MessageFeedAdapter(messageItems)
        return inflater.inflate(R.layout.fragment_scrolling, container, false)
    }
}