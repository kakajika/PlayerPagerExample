package com.labo.kaji.aacexample

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory

object PlayerProvider {

    private val BANDWIDTH_METER = DefaultBandwidthMeter()
    private var player: ExoPlayer? = null

    fun getPlayer(context: Context): ExoPlayer {
        player?.let { return it }

        return ExoPlayerFactory.newSimpleInstance(
            DefaultRenderersFactory(context),
            DefaultTrackSelector(AdaptiveTrackSelection.Factory(BANDWIDTH_METER)),
            DefaultLoadControl()
        ).also {
            it.playWhenReady = true
            it.repeatMode = Player.REPEAT_MODE_ALL
            it.prepare(buildMediaSource(context.getString(R.string.media_url_dash)))
            player = it
        }
    }

    private fun buildMediaSource(url: String): MediaSource {
        return DashMediaSource.Factory(
            DefaultDashChunkSource.Factory(DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER)),
            DefaultHttpDataSourceFactory("ua")
        ).createMediaSource(Uri.parse(url))
    }

}
