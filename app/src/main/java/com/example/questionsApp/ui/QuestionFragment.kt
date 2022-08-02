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
import com.example.questionsApp.databinding.FragmentQuestionBinding
import com.example.questionsApp.models.AnswerToSubmit
import com.example.questionsApp.models.Question
import com.example.questionsApp.ui.viewUtils.ConfirmationVIew
import com.example.questionsApp.ui.viewUtils.CustomRecyclerManager
import com.example.questionsApp.ui.viewUtils.RecyclerAdapter
import com.example.questionsApp.utils.SubmissionConfirmation
import com.example.questionsApp.utils.convertToModel
import com.example.questionsApp.viewmodels.MainViewModel
import com.example.questionsApp.viewmodels.QuestionViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class QuestionFragment : Fragment(), ConfirmationVIew.ConfirmationViewClickListener {

    private lateinit var binding: FragmentQuestionBinding
    private lateinit var adapter: RecyclerAdapter
    private var questionsSize: Int? = 0
    private val mainViewModel: MainViewModel by sharedViewModel()
    private val viewModel: QuestionViewModel by sharedViewModel()
    private var questionList: List<Question> = mutableListOf()
    private var answerToSubmit: AnswerToSubmit? = null

    private var clickCallBack: (AnswerToSubmit?) -> Unit = {
        answerToSubmit = it
        mainViewModel.postSubmittedAnswer(it)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View {
        binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            observeQuestions()
            checkSubmissions()
            navigateBack()
            clickNextBtn()
            clickPreviousBtn()
            clickCount()
            observeSubmissionResponse()
            setSuccessfulSubmissions()
        }
    }

    private fun observeQuestions() {
        mainViewModel.observeResponse(viewLifecycleOwner) { questionsResponse ->
            questionList = questionsResponse.convertToModel()
            questionsSize = questionsResponse?.size
            binding.questionsTotal.text = questionsSize.toString()
            initRecyclerView(questionList)
        }
    }

    private fun checkSubmissions() {
        mainViewModel.observeSubmissionResponse(viewLifecycleOwner) { response ->
            if (response == null) {
                binding.confirmationVIew.bind(SubmissionConfirmation.FAIL)
                binding.confirmationVIew.confirmationClickListener = this@QuestionFragment
            } else binding.confirmationVIew.bind(SubmissionConfirmation.SUCCESS)

        }
    }

    private fun observeSubmissionResponse() {

    }

    private fun setSuccessfulSubmissions() {
        binding.apply {
            val answersCounter: LiveData<Int> = mainViewModel.submissionCounting()
            answersCounter.observe(viewLifecycleOwner) { countAnswers ->
                total.text = resources.getString(R.string.submission_total, countAnswers.toString())
            }
        }
    }

    private fun clickCount() {
        binding.apply {
            val count: LiveData<Int> = viewModel.getCountTotal()
            count.observe(viewLifecycleOwner) { countSteps ->
                currentQuestion.text = "$countSteps"
                handleBtnVisibility(countSteps)
                questionsRecycler.scrollToPosition(countSteps - 1)
            }
        }
    }

    private fun initRecyclerView(questionList: List<Question>?) {
        binding.apply {
            adapter = RecyclerAdapter(questionList, clickCallBack)
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


    override fun onConfirmActionClick(btnAction: ConfirmationVIew.BtnAction) {
        when (btnAction) {
            ConfirmationVIew.BtnAction.RETRY -> {
                clickCallBack.invoke(answerToSubmit)
                binding.confirmationVIew.visibility = View.GONE

            }
            ConfirmationVIew.BtnAction.CLOSE -> {
                binding.confirmationVIew.visibility = View.GONE
            }
        }
    }
}
