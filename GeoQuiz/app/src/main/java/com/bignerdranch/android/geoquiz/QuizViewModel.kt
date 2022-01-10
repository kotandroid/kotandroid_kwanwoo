package com.bignerdranch.android.geoquiz

import android.text.BoringLayout
import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel(){

    private val questionBank = listOf( //문제 리스트
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    //챌린지 6-2
    var cheatList = arrayListOf(false, false, false, false, false, false)
    var currentIndex = 0 // 문제 인덱스
    var score = 0.0 // 챌린지 3-2 전체 점수

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    //챌린지 6-2
    val isCheat : Boolean
        get() = cheatList[currentIndex]

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
}