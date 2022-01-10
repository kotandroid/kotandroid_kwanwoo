package com.bignerdranch.android.geoquiz

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.media.MediaCodec
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    // private lateinit var previousButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        // previousButton = findViewById(R.id.previous_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

//      true_button.setOnClickListener { view: View ->
//            build.grdle의 plugin에 (id 'kotlin-android-extensions')를 추가하면 바로 뷰의 id에 접근 가능
//            원래 가능한 기능인데 현재 안드로이드 스튜디오 4.2버전에서는 findViewById를 사용해야 접근 가능하다고 함
//            추후에 업데이트 되어 다시 사용 가능해 질 것이라고 한다.
//      }

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
//            //버튼의 응답 처리
//            val trueToast : Toast = Toast.makeText(
//                this,
//                R.string.correct_toast,
//                Toast.LENGTH_SHORT)
//
//            //챌린지 1(R 버전부터는 setGravity를 지원하지 않는다 한다. 따라서 대안으로 스낵바를 사용하는게 좋다고 함)
//            trueToast.setGravity(Gravity.TOP, Gravity.CENTER, 100)
//
//            trueToast.show()
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
//            //버튼의 응답 처리
//            val falseToast = Toast.makeText(
//                this,
//                R.string.incorrect_toast,
//                Toast.LENGTH_SHORT)
//
//            //챌린지 1(R 버전부터는 setGravity를 지원하지 않는다 한다. 따라서 대안으로 스낵바를 사용하는게 좋다고 함)
//            falseToast.setGravity(Gravity.TOP, Gravity.CENTER, 100)
//
//            falseToast.show()
        }

        nextButton.setOnClickListener {
            //챌린지 3-2 (마지막 문제를 푼 후 next 버튼을 눌렀을 때 점수 출력)
//            if (currentIndex == 5) {
//                val totalScore = "점수 : " + (score/6*100).toString() + "%"
//                Toast.makeText(this, totalScore, Toast.LENGTH_SHORT).show()
//            }
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener { view ->
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val options =
                    ActivityOptions.makeClipRevealAnimation(view, 0, 0, view.width, view.height)

                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            } else {
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }
        }

//        previousButton.setOnClickListener {
//            currentIndex = if (currentIndex != 0) {
//                    (currentIndex - 1)
//            } else {
//                5
//            }
//            updateQuestion()
//        }

        updateQuestion()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.cheatList[quizViewModel.currentIndex] = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.d(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId) //question_text_view.text = "" 와 같이 id를 바로 사용 가능
        //챌린지 3-1(문제를 업데이트 할때 정답을 맞췄는지 여부에 따라 버튼을 활성화 비활성화)
//        if (questionBank[currentIndex].correct == true) {
//            trueButton.setEnabled(false)
//            falseButton.setEnabled(false)
//        } else {
//            trueButton.setEnabled(true)
//            falseButton.setEnabled(true)
//        }
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

//        val messageResId = if (userAnswer == correctAnswer) {
////            questionBank[currentIndex].correct = true //챌린지 3-1(정답 맞춘 경우 true로 변경)
////            score += 1 // 정답일 경우 맞춘 정답 수 증가
////            //정답인 경우 버튼 비활성화
////            trueButton.setEnabled(false)
////            falseButton.setEnabled(false)
//            R.string.correct_toast
//        } else {
//            R.string.incorrect_toast
//        }
        val messageResId = when {
            quizViewModel.isCheat -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}