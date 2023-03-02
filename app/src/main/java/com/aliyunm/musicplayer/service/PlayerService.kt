package com.aliyunm.musicplayer.service

import android.content.Intent
import android.os.Bundle
import android.service.media.MediaBrowserService

class PlayerService : MediaBrowserService() {

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        return BrowserRoot(clientUid.toString(), rootHints)
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<android.media.browse.MediaBrowser.MediaItem>>) {

    }

}