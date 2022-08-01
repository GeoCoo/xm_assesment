package com.example.questionsApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.questionsApp.databinding.FragmentQuestionBinding
import com.example.questionsApp.viewmodels.MainViewModel
import com.example.questionsApp.viewmodels.QuestionViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionFragment : Fragment() {

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var binding: FragmentQuestionBinding

    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding!!.root
    }


}