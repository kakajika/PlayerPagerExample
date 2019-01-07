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
import com.labo.kaji.aacexample.ext.muted

object PlayerProvider {

    private val BANDWIDTH_METER = DefaultBandwidthMeter()
    private val playerPool = mutableListOf<ExoPlayer>()

    fun createPlayers(context: Context) {
        buildMediaSources(context).forEach { mediaSource ->
            playerPool.add(ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(AdaptiveTrackSelection.Factory(BANDWIDTH_METER)),
                DefaultLoadControl()
            ).apply {
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_ALL
                muted = true
                prepare(mediaSource)
            })
        }
    }

    fun getPlayer(position: Int): ExoPlayer {
        return playerPool[position % playerPool.size]
    }

    private fun buildMediaSources(context: Context): List<MediaSource> {
        return listOf(
            R.string.media_url_dash1,
            R.string.media_url_dash2,
            R.string.media_url_dash3
        ).map { url ->
            DashMediaSource.Factory(
                DefaultDashChunkSource.Factory(DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER)),
                DefaultHttpDataSourceFactory("ua")
            ).createMediaSource(Uri.parse(context.getString(url)))
        }
    }

    fun mutePlayer() {
        playerPool.forEach { it.muted = true }
    }

    fun resumePlayer() {
        playerPool.forEach { it.playWhenReady = true }
    }

    fun stopPlayer() {
        playerPool.forEach { it.playWhenReady = false }
    }

    fun releasePlayer() {
        playerPool.forEach { it.release() }
        playerPool.clear()
    }

}
