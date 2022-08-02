package com.example.questionsApp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.questionsApp.R

class ButtonView : CardView, View.OnClickListener {

    interface BtnClickListener {
        fun onBtnActionClick()
    }


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var btnTitle: TextView
    private var button: CardView
    private var btnActionId: Int? = null

    var btnClickListener: BtnClickListener? = null


    init {
        val view = LayoutInflater.from(context).inflate(R.layout.btn_view, this, true)
        btnTitle = view.findViewById(R.id.btnTitle)
        button = view.findViewById(R.id.buttonCv)
        button.setOnClickListener(this)

    }

    fun bind(title: String) {
        btnTitle.text = title
    }


    override fun onClick(p0: View?) {
        btnClickListener?.onBtnActionClick()
    }


}



