package com.example.questionsApp.ui.viewUtils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.questionsApp.R
import com.example.questionsApp.utils.SubmissionConfirmation

class ConfirmationVIew : ConstraintLayout, View.OnClickListener {

    enum class BtnAction {
        RETRY, CLOSE
    }

    interface ConfirmationViewClickListener {
        fun onConfirmActionClick(btnAction: BtnAction)
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var result: TextView
    private var button: Button
    private var confirmationVIew: ConstraintLayout
    private var closeAction: TextView

    var confirmationClickListener: ConfirmationViewClickListener? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.comfirmation_view, this, true)
        result = view.findViewById(R.id.comfirmationResult)
        button = view.findViewById(R.id.retryBtn)
        closeAction = view.findViewById(R.id.closeAction)
        confirmationVIew = view.findViewById(R.id.confirmationVIew)
    }

    fun bind(confirmation: SubmissionConfirmation) {
        confirmationVIew.visibility = View.VISIBLE
        when (confirmation) {
            SubmissionConfirmation.SUCCESS -> {
                confirmationVIew.setBackgroundColor(resources.getColor(R.color.green))
                result.text = resources.getString(R.string.success)
                closeAction.setOnClickListener(this)

            }
            SubmissionConfirmation.FAIL -> {
                confirmationVIew.setBackgroundColor(resources.getColor(R.color.red))
                result.text = resources.getString(R.string.fail)
                closeAction.setOnClickListener(this)
                button.setOnClickListener(this)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.closeAction -> {
                confirmationClickListener?.onConfirmActionClick(BtnAction.CLOSE)
            }
            R.id.retryBtn -> {
                confirmationClickListener?.onConfirmActionClick(BtnAction.RETRY)
            }
        }
    }
}