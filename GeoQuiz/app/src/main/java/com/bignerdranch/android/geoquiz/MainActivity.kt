package com.bignerdranch.android.geoquiz

import android.media.MediaCodec
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf( //문제 리스트
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0 // 문제 인덱스

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton = findViewById(R.id.previous_button)
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
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        previousButton.setOnClickListener {
            currentIndex = if (currentIndex != 0) {
                    (currentIndex - 1)
            } else {
                5
            }
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId) //question_text_view.text = "" 와 같이 id를 바로 사용 가능
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}