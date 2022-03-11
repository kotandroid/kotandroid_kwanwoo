package com.bignerdranch.android.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var sceneView: View
    private lateinit var sunView: View
    private lateinit var skyView: View
    private lateinit var animatorSet: AnimatorSet
    private var checkSun: Boolean = true

//    private val sunYStart: Float by lazy {
//        sunView.top.toFloat()
//    }
//    private val sunYEnd: Float by lazy {
//        skyView.height.toFloat()
//    }

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.scene)
        sunView = findViewById(R.id.sun)
        skyView = findViewById(R.id.sky)
        animatorSet = AnimatorSet()

        sceneView.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(sunView, "y", sunYStart, sunYEnd)
            .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        animatorSet.play(heightAnimator)
            .with(sunsetSkyAnimator)
            .before(nightSkyAnimator)
        animatorSet.start()

        if (checkSun) {
            animatorSet.start()
            checkSun = false
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val playTime = animatorSet.currentPlayTime
                animatorSet.apply {
                    pause()
                    reverse()
                    currentPlayTime = animatorSet.totalDuration - playTime
                }
            }
            checkSun = true
        }
    }

    //chapter 31 challenge 1(이 방법 말고 reverse 사용하면 더 쉬움)
//    private fun startReverseAnimation() {
//
//        val heightAnimator = ObjectAnimator
//            .ofFloat(sunView, "y", sunYEnd, sunYStart)
//            .setDuration(3000)
//        heightAnimator.interpolator = AccelerateInterpolator()
//
//        val sunsetSkyAnimator = ObjectAnimator
//            .ofInt(skyView, "backgroundColor", sunsetSkyColor, blueSkyColor)
//            .setDuration(3000)
//        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())
//
//        val nightSkyAnimator = ObjectAnimator
//            .ofInt(skyView, "backgroundColor", nightSkyColor, sunsetSkyColor)
//            .setDuration(1500)
//        nightSkyAnimator.setEvaluator(ArgbEvaluator())
//
//        val animatorSet = AnimatorSet()
//        animatorSet.play(heightAnimator)
//            .with(sunsetSkyAnimator)
//            .after(nightSkyAnimator)
//        animatorSet.start()
//
//        checkSun = true
//    }
}