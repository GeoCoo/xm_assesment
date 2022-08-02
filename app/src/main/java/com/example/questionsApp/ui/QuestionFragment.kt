package com.example.questionsApp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.questionsApp.R
import com.example.questionsApp.RecyclerAdapter
import com.example.questionsApp.databinding.FragmentQuestionBinding
import com.example.questionsApp.models.Question
import com.example.questionsApp.ui.view.CustomRecyclerManager
import com.example.questionsApp.utils.convertToModel
import com.example.questionsApp.viewmodels.MainViewModel
import com.example.questionsApp.viewmodels.QuestionViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class QuestionFragment : Fragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: QuestionViewModel by sharedViewModel()
    private lateinit var binding: FragmentQuestionBinding
    private var questionList: List<Question> = mutableListOf()
    private var questionsSize: Int? = null
    private lateinit var adapter: RecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mainViewModel.observeResponse(viewLifecycleOwner) { questionsResponse ->
                questionList = questionsResponse.convertToModel()
                questionsSize = questionsResponse?.size
                questionsTotal.text = questionsSize.toString()
                initRecyclerView(questionList)
            }
            navigateBack()
            clickNextBtn()
            clickPreviousBtn()

            val count: LiveData<Int> = viewModel.getCountTotal()
            count.observe(viewLifecycleOwner) { countSteps ->
                currentQuestion.text = "$countSteps"
                handleBtnVisibility(countSteps)
            }

        }
    }

    private fun initRecyclerView(questionList: List<Question>?) {
        binding.apply {
            questionsRecycler
            adapter = RecyclerAdapter(questionList)
            questionsRecycler.adapter = adapter

            val manager = object : CustomRecyclerManager(requireActivity()) {
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
            }
            manager.orientation = LinearLayoutManager.HORIZONTAL
            questionsRecycler.layoutManager = manager
        }
    }

    private fun clickPreviousBtn() {
        binding.previousBtn.setOnClickListener {
            viewModel.getPreviousCount()
        }
    }

    private fun clickNextBtn() {
        binding.nextBtn.setOnClickListener {
            viewModel.getNextCount()
        }
    }

    private fun navigateBack() {
        binding.backArrow.setOnClickListener {
            findNavController().navigate(R.id.action_go_to_splash)
        }
    }

    private fun handleBtnVisibility(count: Int) {
        binding.apply {
            if (count > 1) previousBtn.visibility = View.VISIBLE else previousBtn.visibility = View.GONE
            if (count < questionsSize!!) nextBtn.visibility = View.VISIBLE else nextBtn.visibility = View.GONE
        }
    }
}
