package com.example.questionsApp.ui.viewUtils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.questionsApp.databinding.QuestionItemBinding
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.models.Question
import com.example.questionsApp.utils.afterTextChanged


class RecyclerAdapter(
    private val questionsList: List<Question>?,
    private var subCallback: (AnswerToSubmit?) -> Unit,
) :
    RecyclerView.Adapter<RecyclerAdapter.QuestionVIewHolder>() {


    private lateinit var binding: QuestionItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionVIewHolder {
        binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionVIewHolder(binding, subCallback)
    }

    override fun onBindViewHolder(holder: QuestionVIewHolder, position: Int) {
        val question = questionsList?.get(position)
        holder.bind(question)
    }

    override fun getItemViewType(position: Int) = position

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemCount(): Int = questionsList?.size!!

    inner class QuestionVIewHolder(private var binding: QuestionItemBinding, private var subCallback: (AnswerToSubmit?) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(question: Question?) {
            binding.apply {
                questionTxt.text = question?.question
                subCallback.invoke(null)
                answerTxt.afterTextChanged { subCallback.invoke(AnswerToSubmit(question?.id, it)) }
            }
        }
    }
}
