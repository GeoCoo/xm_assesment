package com.example.questionsApp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.questionsApp.databinding.QuestionItemBinding
import com.example.questionsApp.models.Question


class RecyclerAdapter(private val questionsList: List<Question>?) : RecyclerView.Adapter<RecyclerAdapter.QuestionVIewHolder>() {

    private lateinit var binding: QuestionItemBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionVIewHolder {
        binding = QuestionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuestionVIewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionVIewHolder, position: Int) {
        val question = questionsList?.get(position)
        holder.bind(question)
    }

    override fun getItemCount(): Int = questionsList?.size!!


    class QuestionVIewHolder(private var binding: QuestionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question?) {
            binding.question.text = question?.question
        }

    }


}
