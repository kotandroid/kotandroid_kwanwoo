package com.bignerdranch.android.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel

class MainViewModel(private val assets: AssetManager) : ViewModel(){
    var beatBox: BeatBox = BeatBox(assets)
}