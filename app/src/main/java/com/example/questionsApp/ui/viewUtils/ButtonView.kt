package com.example.questionsApp.ui.viewUtils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.questionsApp.R
import com.example.questionsApp.utils.ButtonStates

class ButtonView : CardView, View.OnClickListener {

    interface BtnClickListener {
        fun onBtnActionClick()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var btnTitle: TextView
    private var button: CardView

    var btnClickListener: BtnClickListener? = null


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.btn_view, this, true)
        btnTitle = view.findViewById(R.id.btnTitle)
        button = view.findViewById(R.id.buttonCv)
        button.setOnClickListener(this)
    }

    fun bind(state: ButtonStates) {
        when (state) {
            ButtonStates.START -> {
                btnTitle.text = resources.getString(R.string.start)
                button.setBackgroundColor(resources.getColor(R.color.white))
                btnTitle.setTextColor(ContextCompat.getColor(context, R.color.sub_blue))
                button.isEnabled = true
                button.isClickable = true
            }
            ButtonStates.SUBMITTED_SUCCESS -> {
                btnTitle.text = resources.getString(R.string.submitted_success)
                button.isEnabled = false
                button.isClickable = false
                button.setBackgroundColor(resources.getColor(R.color.white))
                btnTitle.setTextColor(ContextCompat.getColor(context, R.color.sub_blue))
            }
            ButtonStates.SUBMIT -> {
                btnTitle.text = resources.getString(R.string.submit_btn_txt)
                button.setBackgroundColor(resources.getColor(R.color.white))
                btnTitle.setTextColor(ContextCompat.getColor(context, R.color.sub_blue))
                button.isEnabled = true
                button.isClickable = true
            }
            ButtonStates.ALREADY_SUBMITTED -> {
                btnTitle.text = resources.getString(R.string.submitted_already)
                button.isEnabled = false
                button.isClickable = false
                button.setBackgroundColor(resources.getColor(R.color.dark_grey))
                btnTitle.setTextColor(ContextCompat.getColor(context, R.color.white))
            }

        }
    }

    override fun onClick(p0: View?) {
        btnClickListener?.onBtnActionClick()
    }


}



