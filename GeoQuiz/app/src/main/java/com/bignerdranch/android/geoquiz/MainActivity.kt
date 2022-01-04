package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)


//      true_button.setOnClickListener { view: View ->
//            build.grdle의 plugin에 (id 'kotlin-android-extensions')를 추가하면 바로 뷰의 id에 접근 가능
//            원래 가능한 기능인데 현재 안드로이드 스튜디오 4.2버전에서는 findViewById를 사용해야 접근 가능하다고 함
//            추후에 업데이트 되어 다시 사용 가능해 질 것이라고 한다.
//      }

        trueButton.setOnClickListener { view: View ->
            //버튼의 응답 처리
            val trueToast : Toast = Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT)

            //챌린지 1(R 버전부터는 setGravity를 지원하지 않는다 한다. 따라서 대안으로 스낵바를 사용하는게 좋다고 함)
            trueToast.setGravity(Gravity.TOP, Gravity.CENTER, 100)

            trueToast.show()
        }

        falseButton.setOnClickListener { view: View ->
            //버튼의 응답 처리
            val falseToast = Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT)

            //챌린지 1(R 버전부터는 setGravity를 지원하지 않는다 한다. 따라서 대안으로 스낵바를 사용하는게 좋다고 함)
            falseToast.setGravity(Gravity.TOP, Gravity.CENTER, 100)

            falseToast.show()
        }
    }
}