package com.example.questionsApp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.questionsApp.databinding.QuestionItemBinding
import com.example.questionsApp.models.Question
import com.example.questionsApp.models.QuestionSubmit
import com.example.questionsApp.ui.view.ButtonView
import kotlin.math.roundToInt


class RecyclerAdapter(private val questionsList: List<Question>?, private var clickCallBack: (String?) -> Unit) :
    RecyclerView.Adapter<RecyclerAdapter.QuestionVIewHolder>() {

    private lateinit var binding: QuestionItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionVIewHolder {
        binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionVIewHolder(binding, clickCallBack)
    }

    override fun onBindViewHolder(holder: QuestionVIewHolder, position: Int) {
        val question = questionsList?.get(position)
        holder.bind(question)
    }

    override fun getItemCount(): Int = questionsList?.size!!


    class QuestionVIewHolder(private var binding: QuestionItemBinding, private var clickCallBack: (String?) -> Unit) :
        RecyclerView.ViewHolder(binding.root), ButtonView.BtnClickListener {
        var currentQuestion: Question? = null
        fun bind(question: Question?) {
            currentQuestion = question
            binding.apply {
                questionTxt.text = question?.question
                submit.bind("Submit")
                submit.btnClickListener = this@QuestionVIewHolder

            }
        }

        override fun onBtnActionClick() {
            clickCallBack.invoke(currentQuestion?.id)
        }

    }


}
