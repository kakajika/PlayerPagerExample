package com.labo.kaji.aacexample.ext

import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

var Player.muted: Boolean
    get() {
        return (this as SimpleExoPlayer).volume == 0.0f
    }
    set(value) {
        (this as SimpleExoPlayer).volume = if (value) 0.0f else 1.0f
    }