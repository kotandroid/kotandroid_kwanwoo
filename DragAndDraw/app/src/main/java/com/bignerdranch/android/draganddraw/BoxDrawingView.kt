package com.bignerdranch.android.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import java.util.ArrayList

private const val TAG = "BoxDrawingView"
private const val BOXEN = "BOXEN"
private const val VIEW_STATE = "VIEW_STATE"

class BoxDrawingView(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    init {
        isSaveEnabled = true
    }

    private var currentBox: Box? = null
    private var boxen = mutableListOf<Box>()
    private val boxPaint = Paint().apply {
        color = 0x22ff0000.toInt()
    }
    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                // 그리기 상태를 재설정한다
                currentBox = Box(current).also {
                    boxen.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }
        Log.i(TAG, "$action at x=${current.x}, y=${current.y}")

        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        //배경을 채운다
        canvas.drawPaint(backgroundPaint)

        boxen.forEach { box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val state = Bundle().apply {
            putParcelable(VIEW_STATE, superState)
            putInt(BOXEN, boxen.size)
            for (i in 0 until boxen.size){
                putFloat("BOX_${i}_T", boxen[i].top)
                putFloat("BOX_${i}_B", boxen[i].bottom)
                putFloat("BOX_${i}_L", boxen[i].left)
                putFloat("BOX_${i}_R", boxen[i].right)
            }
        }

        return state
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            val boxen_size = state.getInt(BOXEN)
            for (i in 0 until boxen_size){
                boxen.add(Box(
                    // start
                    PointF(
                        state.getFloat("BOX_${i}_L"),
                        state.getFloat("BOX_${i}_T")
                    )
                ).apply {
                    end = PointF(
                        state.getFloat("BOX_${i}_R"),
                        state.getFloat("BOX_${i}_B")
                    )
                })
            }
            super.onRestoreInstanceState(state.getParcelable(VIEW_STATE))
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}